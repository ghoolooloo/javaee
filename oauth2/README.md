OAuth2 Login, Distributed SSO, Distributed Logout, and Token Grant Wildfly Examples
===================================
The following examples requires Wildfly and have been tested on version 9.0.0.  Here's the highlights of the examples
* Turning a jboss security domain into a remote authentication server
* Delegating authentication of a web app to the remote authentication server via OAuth 2 protocols
* Distributed Single-Sign-On and Single-Logout
* Transferring identity and role mappings via a special bearer token (Skeleton Key Token).
* Bearer token authentication and authorization of JAX-RS services
* Obtaining bearer tokens via the OAuth2 protocol

There are 6 WAR projects.  These all will run on the same jboss instance, but pretend each one is running on a different
machine on the network or Internet.
* **auth-server**: A WAR that turns a security domain into a remote login server and oauth token service
* **customer-app** A WAR applications that does remote login using OAUTH2 browser redirects with the auth server
* **product-app** A WAR applications that does remote login using OAUTH2 browser redirects with the auth server
* **database-service** JAX-RS services authenticated by bearer tokens only.  The customer and product app invoke on it
  to get data
* **third-party** Simple WAR that obtain a bearer token using OAuth2 using browser redirects to the auth-server.
* **client-grant** Simple WAR that obtains a token from the auth-server using a direct protocol and then uses the token
  to invoke on database services.
  
 
Step 1: Copy configuration to Wildfly
---------------------------------------
The OAUTH example comes with a configuration directory.  You must copy the contents to the standalone configuration of Wildfly

1. cd oauth2/configuration/standalone
2. cp *.jks $JBOSS_HOME/standalone/configuration
3. cp *.ts $JBOSS_HOME/standalone/configuration
4. cp *.properties $JBOSS_HOME/standalone/configuration

然后修改$JBOSS_HOME/standalone/configuration/standalone.xml：（具体可参考oauth2/configuration/standalone/standalone.xml）

	<security-domain name="commerce" cache-type="default">
		<authentication>
			<login-module code="UsersRoles" flag="required">
				<module-option name="usersProperties" value="${jboss.server.config.dir}/commerce-users.properties"/>
				<module-option name="rolesProperties" value="${jboss.server.config.dir}/commerce-roles.properties"/>
			</login-module>
		</authentication>
	</security-domain>

and add SSL

	<connector name="https" protocol="HTTP/1.1" scheme="https" socket-binding="https" secure="true">
		<ssl name="ssl" key-alias="server" password="password" certificate-key-file="${jboss.server.config.dir}/server.jks" verify-client="false"/>
	</connector>
	
enabled SSL：

	<security-realm name="ManagementRealm">
   		<server-identities>
      		<ssl>
         		<keystore path="server.keystore" relative-to="jboss.server.config.dir" keystore-password="keystore_password" alias="server" key-password="key_password" />
         </ssl>
		</server-identities>
      ...
	</security-realm>