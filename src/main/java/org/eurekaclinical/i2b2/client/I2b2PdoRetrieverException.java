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

/**
 *
 * @author Andrew Post
 */
public final class I2b2PdoRetrieverException extends I2b2Exception {
    
    private static final long serialVersionUID = 1L;

    I2b2PdoRetrieverException() {
    }

    I2b2PdoRetrieverException(String message) {
        super(message);
    }

    I2b2PdoRetrieverException(Throwable cause) {
        super(cause);
    }

    I2b2PdoRetrieverException(String message, Throwable cause) {
        super(message, cause);
    }
    
}
