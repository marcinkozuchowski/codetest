
1. Build project:
    > mvn clean install
2. Run server:
	> java -jar  payara-micro-5.183.jar --deploy ${projectHomeDir}/blog-web/target/blog-web.war --logproperties ${projectHomeDir}/etc/logging.properties
3. Run tests:
	> mvn verify -Dit.test=*
4. Checkout blog page
	> http://localhost:8080/index.jsp
	
	
There is also configured (but currently commented) automation for downloading and starting payara, deploying war and running integration tests, just by one command: _mvn verify -f integration-test_

But there has to be some bug in rest-assured or payara-micro-maven-plugin, because when comes to invoking PUT method, program hangs waiting for response to the request.
