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
import org.eurekaclinical.i2b2.client.xml.I2b2XmlException;

/**
 * Interface for authenticating an i2b2 user given authentication metadata for
 * that user.
 *
 * @author Michel Mansour
 * @since 1.0
 */
public interface I2b2UserAuthenticator {

    /**
     * Determines whether or not a user is authenticated given their
     * authentication metadata.
     *
     * @param authMetadata the i2b2 authentication metadata to provide to i2b2
     * to authenticate the user
     * @return <code>true</code> if the user was authenticated by i2b2,
     * <code>false</code> otherwise
     * @throws I2b2XmlException if an error occurs processing the i2b2 response
     * XML
     */
    public boolean authenticateUser(I2b2AuthMetadata authMetadata) throws I2b2XmlException;
}
