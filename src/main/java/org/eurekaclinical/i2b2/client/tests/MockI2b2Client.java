package org.eurekaclinical.i2b2.client.tests;

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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.eurekaclinical.i2b2.client.I2b2Client;
import org.eurekaclinical.i2b2.client.I2b2PdoRetrieverException;
import org.eurekaclinical.i2b2.client.comm.I2b2Concept;
import org.eurekaclinical.i2b2.client.comm.I2b2PatientSet;
import org.eurekaclinical.i2b2.client.pdo.Event;
import org.eurekaclinical.i2b2.client.pdo.I2b2PdoResults;
import org.eurekaclinical.i2b2.client.pdo.Observation;
import org.eurekaclinical.i2b2.client.pdo.Patient;

/**
 *
 * @author Andrew Post
 */
public class MockI2b2Client implements I2b2Client {
    
    MockI2b2Client() {
    }

    @Override
    public I2b2PdoResults retrievePdo(Collection<I2b2Concept> concepts, I2b2PatientSet patientSet) throws I2b2PdoRetrieverException {
        Patient patient = new Patient.Builder("1").build();
        Event event = new Event.Builder("1", patient).build();
        Observation obx = new Observation.Builder(event).conceptPath("\\\\i2b2\\Concept").build();

        List<Patient> patients = new ArrayList<>();
        patients.add(patient);

        List<Event> events = new ArrayList<>();
        events.add(event);

        List<Observation> obxs = new ArrayList<>();
        obxs.add(obx);

        I2b2PdoResults results = new I2b2PdoResults(patients, events,
                new ArrayList<>(), obxs);

        return results;
    }

    @Override
    public void setRole(String username, String projectId, String role) {
    }

    @Override
    public void setUser(String username, String password, String fullName, String email, boolean admin) {
    }

    @Override
    public void close() {
    }
    
}
