package org.eurekaclinical.i2b2.client;

/*
 * #%L
 * i2b2 Export Service
 * %%
 * Copyright (C) 2013 Emory University
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */
import org.eurekaclinical.i2b2.client.comm.I2b2AuthMetadata;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import java.io.IOException;
import java.io.StringWriter;
import java.net.URL;
import java.time.Instant;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSSerializer;

/**
 * Implementation of the i2b2 user authentication interface. It authenticates
 * i2b2 users by filling out the i2b2 user authentication XML template and
 * sending it to the i2b2 service defined in the application's properties file
 * (see: {@link I2b2CommUtil}).
 *
 * @author Michel Mansour
 * @since 1.0
 */
final class I2b2UserAuthenticatorImpl extends AbstractI2b2Messager implements I2b2UserAuthenticator {

    private static final Logger LOGGER = LoggerFactory.getLogger(I2b2UserAuthenticatorImpl.class);

    /**
     * Creates object for creating an i2b2 session.
     */
    I2b2UserAuthenticatorImpl() {
    }

    @Override
    public String authenticateUser(I2b2AuthMetadata authMetadata) throws I2b2AuthenticationException {
        try {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("Attempting to authenticate i2b2 user: {} with password node: {} in domain {} for project {}",
                        new String[]{
                            authMetadata.getUsername(), authMetadata.getPasswordNode(),
                            authMetadata.getDomain(), authMetadata.getProjectId()});
            }

            Template tmpl = getTemplate(I2b2CommUtil.TEMPLATES_DIR + "/i2b2_get_user_configuration.ftl");
            StringWriter writer = new StringWriter();

            String messageId = generateMessageId();

            Map<String, Object> params = new HashMap<>();
            params.put("redirectHost", authMetadata.getRedirectHost());
            params.put("domain", authMetadata.getDomain());
            params.put("username", authMetadata.getUsername());
            params.put("passwordNode", authMetadata.getPasswordNode());
            params.put("messageId", messageId);
            params.put("messageDatetime", Instant.now().toString());
            params.put("i2b2ProjectId", authMetadata.getProjectId());
            params.put("sendingFacilityName", getSendingFacilityName());
            params.put("countryCode", Locale.getDefault().getISO3Country());

            tmpl.process(params, writer);
            Document respXml = doPost(new URL(authMetadata.getProxyUrl()), writer.toString());

            String status = (String) XmlUtil.evalXPath(respXml,
                    "//response_header/result_status/status/@type",
                    XPathConstants.STRING);

            LOGGER.debug("Received authentication status: {}", status);

            if (status.equals("ERROR")) {
                throw new I2b2AuthenticationException("Error while authenticating user. Please contact the administrator.");
            }
            NodeList nList = respXml.getElementsByTagName("password");
            Element passwordNode = (Element) nList.item(0);

            DOMImplementationLS lsImpl = (DOMImplementationLS) passwordNode.getOwnerDocument().getImplementation().getFeature("LS", "3.0");
            LSSerializer serializer = lsImpl.createLSSerializer();
            serializer.getDomConfig().setParameter("xml-declaration", false);
            return serializer.writeToString(passwordNode);
        } catch (IOException | XPathExpressionException | SAXException | ParserConfigurationException | TemplateException e) {
            throw new I2b2AuthenticationException(e);
        }

    }
}
