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
import org.eurekaclinical.i2b2.client.comm.I2b2AuthMetadata;
import org.eurekaclinical.i2b2.client.comm.I2b2Concept;
import org.eurekaclinical.i2b2.client.comm.I2b2PatientSet;
import org.eurekaclinical.i2b2.client.pdo.I2b2PdoResults;

/**
 *
 * @author Andrew Post
 */
public final class I2b2ClientImpl implements I2b2Client {
    private final I2b2AuthMetadata authMetadata;
    
    I2b2ClientImpl(I2b2AuthMetadata authMetadata) throws I2b2AuthenticationException {
        if (authMetadata == null) {
            throw new IllegalArgumentException("authMetadata cannot be null");
        }
        this.authMetadata = new I2b2AuthMetadata(authMetadata);
        this.authMetadata.setPasswordNode(new I2b2UserAuthenticatorImpl().authenticateUser(this.authMetadata));
    }
    
    @Override
    public void setUser(String username, String password, String fullName, String email, boolean admin) throws I2b2UserSetterException {
        new I2b2UserSetterImpl().setUser(authMetadata, username, password, fullName, email, admin);
    }
    
    @Override
    public void setRole(String username, String projectId, String role) throws I2b2RoleSetterException {
        new I2b2RoleSetterImpl().setRole(authMetadata, username, projectId, role);
    }
    
    @Override
    public I2b2PdoResults retrievePdo(Collection<I2b2Concept> concepts, I2b2PatientSet patientSet) throws I2b2PdoRetrieverException {
        return new I2b2PdoRetrieverImpl().retrieve(authMetadata, concepts, patientSet);
    }

    @Override
    public void close() {
    }
    
}
