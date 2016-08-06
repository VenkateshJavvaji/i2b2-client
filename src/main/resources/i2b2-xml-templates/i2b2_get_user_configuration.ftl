<#--
 #%L
 i2b2 Client
 %%
 Copyright (C) 2016 Emory University
 %%
 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at
 
      http://www.apache.org/licenses/LICENSE-2.0
 
 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
 #L%
-->
<i2b2:request xmlns:i2b2="http://www.i2b2.org/xsd/hive/msg/1.1/" xmlns:pm="http://www.i2b2.org/xsd/cell/pm/1.1/">
    <message_header>
        <proxy>
            <redirect_url>${redirectHost}/i2b2/services/PMService/getServices</redirect_url>
        </proxy>
        <i2b2_version_compatible>1.1</i2b2_version_compatible>
        <hl7_version_compatible>2.4</hl7_version_compatible>
        <sending_application>
            <application_name>${project.artifactId}</application_name>
            <application_version>${project.version}</application_version>
        </sending_application>
        <sending_facility>
            <facility_name>${sendingFacilityName}</facility_name>
        </sending_facility>
        <receiving_application>
            <application_name>i2b2_ProjectManagementCell</application_name>
            <application_version>1.6</application_version>
        </receiving_application>
        <receiving_facility>
            <facility_name>${domain}</facility_name>
        </receiving_facility>
        <datetime_of_message>${messageDatetime}</datetime_of_message>
        <security>
            <domain>${domain}</domain>
            <username>${username}</username>
            ${passwordNode}
        </security>
        <message_control_id>
            <session_id>${messageDatetime}</session_id>
            <message_num>${messageId}</message_num>
            <instance_num>0</instance_num>
        </message_control_id>
        <processing_id>
            <processing_id>P</processing_id>
            <processing_mode>I</processing_mode>
        </processing_id>
        <accept_acknowledgement_type>AL</accept_acknowledgement_type>
        <application_acknowledgement_type>AL</application_acknowledgement_type>
        <country_code>${countryCode}</country_code>
        <project_id>undefined</project_id>
    </message_header>
    <request_header>
        <result_waittime_ms>180000</result_waittime_ms>
    </request_header>
    <message_body>
        <pm:get_user_configuration>
            <project>${i2b2ProjectId!}</project>
        </pm:get_user_configuration>
    </message_body>
</i2b2:request>
