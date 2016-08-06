package org.eurekaclinical.i2b2.client.comm;

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

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * A bean for storing i2b2 user authentication metadata. Instances of this class
 * will generally be created and populated using JSON objects sent from the
 * client.
 *
 * @author Michel Mansour
 * @since 1.0
 */
public final class I2b2AuthMetadata {
    
    /*
     * the user's i2b2 domain
     */
    private String domain;

    /*
     * the user's username
     */
    private String username;

    /*
     * the user's password node
     */
    private String passwordNode;

    /*
     * the ID of the project the user is accessing
     */
    private String projectId;

    private String redirectHost;

    private String proxyUrl;

    public I2b2AuthMetadata() {
    }

    public I2b2AuthMetadata(I2b2AuthMetadata i2b2AuthMetadata) {
        if (i2b2AuthMetadata != null) {
            this.domain = i2b2AuthMetadata.domain;
            this.username = i2b2AuthMetadata.username;
            this.passwordNode = i2b2AuthMetadata.passwordNode;
            this.projectId = i2b2AuthMetadata.projectId;
            this.redirectHost = i2b2AuthMetadata.redirectHost;
            this.proxyUrl = i2b2AuthMetadata.proxyUrl;
        }
    }

    /**
     * Gets the user's domain.
     *
     * @return the domain as a String
     */
    public String getDomain() {
        return domain;
    }

    /**
     * Sets the user's domain.
     *
     * @param domain the domain as a String
     */
    public void setDomain(String domain) {
        this.domain = domain;
    }
    
    /**
     * Gets the user's username.
     *
     * @return the username as a String
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the user's username.
     *
     * @param username the username as a String
     */
    public void setUsername(String username) {
        this.username = username;
    }
    
    /**
     * Gets the user's password node. This is an entire XML node containing any
     * security information related to the user's password.
     *
     * @return the XML of the password node, as a String
     */
    public String getPasswordNode() {
        return passwordNode;
    }

    /**
     * Sets the user's password XML node.
     *
     * @param passwordNode the password node as a String
     */
    public void setPasswordNode(String passwordNode) {
        this.passwordNode = passwordNode;
    }
    
    public void setPassword(String password) {
        if (password != null) {
            this.passwordNode = "<password>" + password + "</password>";
        } else {
            this.passwordNode = "<password></password>";
        }
    }
    
    /**
     * Gets the user's project ID.
     *
     * @return the project ID as a String
     */
    public String getProjectId() {
        return projectId;
    }

    /**
     * Sets the user's project ID
     *
     * @param projectId the project ID as a String
     */
    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }
    
    public String getRedirectHost() {
        return redirectHost;
    }

    public void setRedirectHost(String redirectHost) {
        this.redirectHost = redirectHost;
    }
    
    public String getProxyUrl() {
        return proxyUrl;
    }

    public void setProxyUrl(String proxyUrl) {
        this.proxyUrl = proxyUrl;
    }
    
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
