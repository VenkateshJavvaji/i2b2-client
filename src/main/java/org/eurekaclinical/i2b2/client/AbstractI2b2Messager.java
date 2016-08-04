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

import freemarker.core.ParseException;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.MalformedTemplateNameException;
import freemarker.template.Template;
import java.io.IOException;
import java.net.URL;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**
 *
 * @author Andrew Post
 */
public abstract class AbstractI2b2Messager {

    private final Configuration config;
    private final I2b2XmlPostSupport i2b2XmlPostSupport;

    protected AbstractI2b2Messager() {
        this.config = new Configuration();
        this.config.setClassForTemplateLoading(this.getClass(), "/");
        this.config.setObjectWrapper(new DefaultObjectWrapper());
        this.config.setNumberFormat("0.######");  // to prevent addition of commas to numbers
        // FreeMarker uses the locale to format numbers
        // in a human-readable way, but this XML is not
        // for humans.
        this.i2b2XmlPostSupport = new I2b2XmlPostSupport();
    }

    protected Template getTemplate(String inPath) throws MalformedTemplateNameException, ParseException, IOException {
        return this.config.getTemplate(inPath);
    }

    protected String generateMessageId() {
        return this.i2b2XmlPostSupport.generateMessageId();
    }

    protected Document doPost(URL proxyUrl, String xml) throws IOException, IllegalStateException, SAXException, ParserConfigurationException {
        return this.i2b2XmlPostSupport.postXmlToI2b2(proxyUrl, xml);
    }
    
    protected String getSendingFacilityName() {
        return "foo";
    }
}
