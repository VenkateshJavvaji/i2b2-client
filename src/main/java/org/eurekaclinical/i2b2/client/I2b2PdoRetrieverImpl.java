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
import org.eurekaclinical.i2b2.client.comm.I2b2PatientSet;
import org.eurekaclinical.i2b2.client.comm.I2b2Concept;
import org.eurekaclinical.i2b2.client.pdo.I2b2PdoResults;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.StringWriter;
import java.net.URL;
import java.time.Instant;
import java.util.Collection;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Implementation of the i2b2 PDO retriever interface. It retrieves the data by
 * filling the PDO request XML template and sending that XML to the i2b2 service
 * as defined the application's properties file (see {@link I2b2CommUtil}).
 *
 * @author Michel Mansour
 * @since 1.0
 */
final class I2b2PdoRetrieverImpl extends AbstractI2b2Messager implements I2b2PdoRetriever {

    /**
     */
    I2b2PdoRetrieverImpl() {
    }

    @Override
    public I2b2PdoResults retrieve(I2b2AuthMetadata authMetadata,
            Collection<I2b2Concept> concepts,
            I2b2PatientSet patientSet) throws I2b2PdoRetrieverException {
        try {
            Template tmpl = getTemplate(I2b2CommUtil.TEMPLATES_DIR + "/i2b2_pdo_request.ftl");
            StringWriter writer = new StringWriter();
            
            Map<String, Object> params = new HashMap<>();
            params.put("redirectHost", authMetadata.getRedirectHost());
            params.put("domain", authMetadata.getDomain());
            params.put("username", authMetadata.getUsername());
            params.put("passwordNode", authMetadata.getPasswordNode());
            params.put("messageId", generateMessageId());
            params.put("i2b2ProjectId", authMetadata.getProjectId());
            params.put("patientListMax", patientSet.getPatientSetSize());
            params.put("patientListMin", "1");
            params.put("patientSetCollId", patientSet.getPatientSetCollId());
            params.put("items", concepts);
            params.put("sendingFacilityName", getSendingFacilityName());
            params.put("countryCode", Locale.getDefault().getISO3Country());
            params.put("todayDate", Instant.now().toString());

            tmpl.process(params, writer);
            Document respXml = doPost(new URL(authMetadata.getProxyUrl()), writer.toString());
            I2b2PdoResultParser parser = new I2b2PdoResultParser(respXml);
            return parser.parse();
        } catch (IOException | TemplateException | SAXException | ParserConfigurationException | I2b2XmlException e) {
            throw new I2b2PdoRetrieverException(e);
        }
    }
}
