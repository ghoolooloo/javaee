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
  
 
1: Copy configuration to Wildfly
---------------------------------------
The OAUTH example comes with a configuration directory.  You must copy the contents to the standalone configuration of Wildfly

1. cd oauth2/configuration/standalone
2. cp *.keystore $JBOSS_HOME/standalone/configuration
3. cp *.truststore $JBOSS_HOME/standalone/configuration
4. cp *.properties $JBOSS_HOME/standalone/configuration

然后修改$JBOSS_HOME/standalone/configuration/standalone.xml：（具体可参考oauth2/configuration/standalone/standalone.xml）

### 1.1 配置安全域

	<security-domain name="commerce" cache-type="default">
		<authentication>
			<login-module code="UsersRoles" flag="required">
				<module-option name="usersProperties" value="${jboss.server.config.dir}/commerce-users.properties"/>
				<module-option name="rolesProperties" value="${jboss.server.config.dir}/commerce-roles.properties"/>
			</login-module>
		</authentication>
	</security-domain>
	
### 1.2 启用SSL：

	<security-realm name="MyRealm">
		<server-identities>
			<ssl>
				<keystore path="server.keystore" relative-to="jboss.server.config.dir" keystore-password="keystore_password" alias="server" key-password="key_password" />
			</ssl>
		</server-identities>
	</security-realm>
   
### 1.3 为SSL配置 Undertow subsystem：

	<subsystem xmlns="urn:jboss:domain:undertow:2.0">
		...
		<server name="default-server">
			...
			<https-listener name="https" socket-binding="https" security-realm="MyRealm"/>
			...
		</server>
	</subsystem>
	
	
2: Boot Wildfly
---------------------------------------
Boot Wildfly in 'standalone' mode.


3: 创建auth-server（认证服务器）
---------------------------------------

### 3.1 添加Auth服务配置文件

将配置文件*.json到WEB-INF/下（也可以放在任意位置，这时要通过部署描述符中的context-param来指定）

### 3.2 在部署描述符web.xml中配置security constraints等

必须是FORM认证方式。

### 3.3 在jboss-web.xml中配置安全域等

### 3.4 在jboss-deployment-structure.xml中配置对skelenton key的依赖


4: 创建Rest服务端——database-service
----------------------------------------

### 4.1 添加Auth服务配置文件

可通过 https://.../auth-server/j_oauth_realm_info.html 来获取realm-public-key等信息。

### 4.2 在部署描述符web.xml中配置security constraints等

### 4.3 配置jboss-web.xml

### 4.4 在jboss-deployment-structure.xml中配置对skelenton key的依赖等
