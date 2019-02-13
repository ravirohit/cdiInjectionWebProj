-> if we enable RestConfiguration.java file then there is no use of web.xml(deployment descriptor).
   http url to test: http://localhost:8080/cdiInjectionWebProj/rest12/hello
-> but we want to handle the request using web.xml not the java file(RestConfiguration.java). we have to add below line in the web.xml:
	     <servlet-mapping>
		    <servlet-name>javax.ws.rs.core.Application</servlet-name>
		    <url-pattern>/rest/*</url-pattern>
		 </servlet-mapping>
   http url to test: http://localhost:8080/cdiInjectionWebProj/rest/hello
Q) How the Application class figures it out where to look for the resource endpoint?

-> if there is one interface and multiple implemented class. and if we want to inject with the interface name. then it will create a ambiguity while
   injecting. here @Default and @Alternate annotation comes into the pictures.
-> if we have not annotated any of the implemented class then the class annotated with @Default annotation will be inject ignoring the class annotated
   with @Alternative.
Q) How to inject the class annotated with @Alternatetive   
Ans: create custom qualifier annotation: 
Q) How CDI figure it out where to scan for the injecting the class object?

-> About Annotation:
@Inject
@Default
@Alternative
@Named
@ApplicationScoped
@Qualifier

Note: can't use custom qualifier and @Default or custom qualifier and @Alternative annotation together
   

	           