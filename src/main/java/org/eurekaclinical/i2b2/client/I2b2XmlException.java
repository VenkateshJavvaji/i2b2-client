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
 * An exception for errors that occur when dealing with i2b2 XML messaging.
 *
 * @author Michel Mansour
 * @since 1.0
 */
class I2b2XmlException extends Exception {

    private static final long serialVersionUID = 1L;

    /**
     * Default no-arg constructor
     */
    I2b2XmlException() {
    }

    /**
     * Creates an exception with the given message and cause to wrap.
     *
     * @param message the exception's message
     * @param cause the {@link Throwable} cause to wrap
     */
    I2b2XmlException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Creates an exception with the given message.
     *
     * @param message the exception's message
     */
    I2b2XmlException(String message) {
        super(message);
    }

    /**
     * Creates an exception wrapping the given cause.
     *
     * @param cause the {@link Throwable} cause to wrap
     */
    I2b2XmlException(Throwable cause) {
        super(cause);
    }

}
