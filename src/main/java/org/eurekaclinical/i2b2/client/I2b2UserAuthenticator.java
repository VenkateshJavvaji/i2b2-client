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

/**
 * Interface for creating an i2b2 session using connection and authentication 
 * metadata.
 *
 * @author Michel Mansour
 * @since 1.0
 */
interface I2b2UserAuthenticator {

    /**
     * Creates an i2b2 session.
     *
     * @param authMetadata the connection and authentication metadata.
     * 
     * @return a password node containing a session.
     * @throws I2b2AuthenticationException
     * 
     */
    String authenticateUser(I2b2AuthMetadata authMetadata) throws I2b2AuthenticationException;
}
