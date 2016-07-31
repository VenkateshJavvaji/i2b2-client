/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.eurekaclinical.i2b2.client.props;

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

/**
 *
 * @author arpost
 */
public interface I2b2Properties {

    /**
     * Gets the URL of the i2b2 services. Reads from the properties file first
     * if necessary.
     *
     * @return the URL as a String
     */
    String getI2b2ServiceHostUrl();

    /**
     * Gets the URL of the i2b2 proxy cell. Reads from the properties file first
     * if necessary.
     *
     * @return the URL as a String
     */
    String getProxyUrl();

}
