package org.eurekaclinical.i2b2.client;

/*
 * #%L
 * i2b2 Eureka Service
 * %%
 * Copyright (C) 2015 Emory University
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
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

/**
 *
 * @author Andrew Post
 */
class I2b2XmlPostSupport {

    private static final Logger LOGGER = LoggerFactory.getLogger(I2b2XmlPostSupport.class);

    I2b2XmlPostSupport() {
    }

    /**
     * Performs an HTTP POST of an XML request to the i2b2 proxy cell. Request
     * XML must contain the redirect_url node with the address of the actual
     * service endpoint to call in order for the request to be properly routed.
     *
     * @param xml the XML request to send
     * @return the XML response from i2b2 as a {@link Document}
     * @throws IOException if an error occurs
     * @throws ParserConfigurationException if an error occurs
     * @throws SAXException if an error occurs
     * @throws IllegalStateException if an error occurs
     */
    Document postXmlToI2b2(URL proxyUrl, String xml) throws IOException, IllegalStateException,
            SAXException, ParserConfigurationException {
        LOGGER.debug("POSTing XML: {}", xml);
        try (InputStream contentIn = executePost(proxyUrl, xml)) {
            Document result = DocumentBuilderFactory.newInstance()
                    .newDocumentBuilder()
                    .parse(contentIn);
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("Response XML: {}", xmlToString(result));
            }
            return result;
        }
    }

    /**
     * Generates a random message number for i2b2 requests. Copied from the i2b2
     * SMART project: https://community.i2b2.org/wiki/display/SMArt/SMART+i2b2
     * in class: edu.harvard.i2b2.smart.ws.SmartAuthHelper
     *
     * @return a unique message ID
     */
    String generateMessageId() {
        StringWriter strWriter = new StringWriter();
        for (int i = 0; i < 20; i++) {
            int num = getValidAcsiiValue();
            strWriter.append((char) num);
        }
        return strWriter.toString();
    }
    
    protected InputStream executePost(URL proxyUrl, String xml) throws IOException {
        HttpURLConnection openConnection = (HttpURLConnection) proxyUrl.openConnection();
        openConnection.setRequestMethod("POST");
        openConnection.setRequestProperty("Content-Type", "text/xml");
        openConnection.setDoOutput(true);
        try (BufferedWriter w = new BufferedWriter(new OutputStreamWriter(openConnection.getOutputStream()))) {
            w.write(xml);
        }
        int responseCode = openConnection.getResponseCode();
        if (responseCode != 200) {
            StringBuilder response = new StringBuilder();
            String line;
            try (BufferedReader r = new BufferedReader(new InputStreamReader(openConnection.getInputStream()))) {
                while ((line = r.readLine()) != null) {
                    response.append(line);
                }
            }
            throw new IOException("Error calling i2b2: status code=" + responseCode + "; response: " + response.toString());
        } else {
            return openConnection.getInputStream();
        }
    }

    private static String xmlToString(Node node) {
        Source source = new DOMSource(node);
        StringWriter stringWriter = new StringWriter();
        try {
            Result result = new StreamResult(stringWriter);
            TransformerFactory factory = TransformerFactory.newInstance();
            Transformer transformer = factory.newTransformer();
            transformer.transform(source, result);
            stringWriter.close();
        } catch (IOException | TransformerException e) {
            LOGGER.error("Error in xmlToString: ", e);
            try {
                stringWriter.close();
            } catch (IOException ignore) {
            }
            return null;
        }
        return stringWriter.getBuffer().toString();
    }

    private static int getValidAcsiiValue() {
        int number = 48;
        while (true) {
            number = 48 + (int) Math.round(Math.random() * 74);
            if ((number > 47 && number < 58) || (number > 64 && number < 91)
                    || (number > 96 && number < 123)) {
                break;
            }
        }
        return number;
    }
}
