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

import java.util.Collection;
import org.eurekaclinical.i2b2.client.comm.I2b2Concept;
import org.eurekaclinical.i2b2.client.comm.I2b2PatientSet;
import org.eurekaclinical.i2b2.client.pdo.I2b2PdoResults;

/**
 *
 * @author Andrew Post
 */
public interface I2b2Client extends AutoCloseable {

    I2b2PdoResults retrievePdo(Collection<I2b2Concept> concepts, I2b2PatientSet patientSet) throws I2b2PdoRetrieverException;

    void setRole(String username, String projectId, String role) throws I2b2RoleSetterException;

    void setUser(String username, String password, String fullName, String email, boolean admin) throws I2b2UserSetterException;
    
    @Override
    void close() throws I2b2ClientCloseException;

}