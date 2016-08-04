package org.eurekaclinical.i2b2.client;

/*-
 * #%L
 * i2b2 Client
 * %%
 * Copyright (C) 2016 Emory University
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

import freemarker.template.Template;
import freemarker.template.TemplateException;
import java.io.IOException;
import java.io.StringWriter;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import javax.xml.parsers.ParserConfigurationException;
import org.eurekaclinical.i2b2.client.comm.I2b2AuthMetadata;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author Andrew Post
 */
class I2b2UserSetterImpl extends AbstractI2b2Messager implements I2b2UserSetter {

    I2b2UserSetterImpl() {
    }

    @Override
    public void setUser(I2b2AuthMetadata authMetadata, String username, String password, String fullName, String email, boolean admin) throws I2b2UserSetterException {
        try {
            Template tmpl = getTemplate(I2b2CommUtil.TEMPLATES_DIR + "/i2b2_set_user.ftl");
            StringWriter writer = new StringWriter();

            Map<String, Object> params = new HashMap<>();
            params.put("redirectHost", authMetadata.getRedirectHost());
            params.put("adminUsername", authMetadata.getUsername());
            params.put("adminPasswordNode", authMetadata.getPasswordNode());
            params.put("messageId", generateMessageId());
            params.put("domain", authMetadata.getDomain());
            params.put("projectId", authMetadata.getProjectId());
            params.put("username", username);
            params.put("password", password);
            params.put("fullName", fullName);
            params.put("email", email);
            params.put("admin", Boolean.toString(admin));
            params.put("sendingFacilityName", getSendingFacilityName());
            params.put("todayDate", new Date());
            params.put("countryCode", Locale.getDefault().getISO3Country());
            tmpl.process(params, writer);
            Document respXml = doPost(new URL(authMetadata.getProxyUrl()), writer.toString());
            NodeList nList = respXml.getElementsByTagName("status");
            Element el = (Element) nList.item(0);
            if (el.getAttribute("type").equals("ERROR")) {
                throw new I2b2UserSetterException("Error while setting user. Please contact the administrator.");
            }
        } catch (IOException | TemplateException | SAXException | ParserConfigurationException ex) {
            throw new I2b2UserSetterException(ex);
        }
    }

}
