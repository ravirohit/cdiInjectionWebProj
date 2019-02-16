-> One of the most significant changes in CD 1.1/Java EE 7 is that by popular request, CDI is now enabled by default. This means that there's no need to 
   explicitly add a beans.xml to enable DI any more.
-> if we enable RestConfiguration.java file then there is no use of web.xml(deployment descriptor).
   http url to test: http://localhost:8080/cdiInjectionWebProj/rest12/hello
-> but we want to handle the request using web.xml not the java file(RestConfiguration.java). we have to add below line in the web.xml:
	     <servlet-mapping>
		    <servlet-name>javax.ws.rs.core.Application</servlet-name>
		    <url-pattern>/rest/*</url-pattern>
		 </servlet-mapping>
   http url to test: http://localhost:8080/cdiInjectionWebProj/rest/hello
   
-> if there is one interface and multiple implemented class. and if we want to inject with the interface name. then it will create a ambiguity while
   injecting. here @Default and @Alternate annotation comes into the pictures.
-> if we have not annotated any of the implemented class then the class annotated with @Default annotation will be inject ignoring the class annotated
   with @Alternative.

-> About Annotation:
@Inject
@Default
@Alternative
@Named
@ApplicationScoped
@Qualifier

Note: can't use custom qualifier and @Default or custom qualifier and @Alternative annotation together on the same class.

-> The set of bean defining annotations contains:
		@ApplicationScoped, @SessionScoped, @ConversationScoped and @RequestScoped annotations,
-> all other normal scope types,
		@Interceptor and @Decorator annotation
-> all stereotype annotations (i.e. annotations annotated with @Stereotype), and the @Dependent scope annotation.
-> does below annotation belong to CDI or if not where does it belong:
		@Dependent @Singleton
		
-> explanation about annotation:   
 1. @RequestScoped: for each http request, new object will be created to service it.
 2. @ApplicationScoped: An object which is defined as @ApplicationScoped is created once for the duration of the application.
                        even if we will try to inject multiple times, same object will be injected. never created new one.
                        test url: http://localhost:8080/cdiInjectionWebProj/rest/hello/test
 
 
 
 
 
 
 
 
 ------------------- Q&A ----------------------
 Q) How to inject the class annotated with @Alternatetive   
Ans: create custom qualifier annotation: 


 Q) What is difference between @ApplicationScoped and @Singleton?
 Ans: I checked the behavior, both worked in the same manner. but i believe, @Singleton doesn't come with CDI specification.
 
 Q) What is the behavior if we put small life time scope object inside of the more one like: putting request class inject inside of the application 
    scope object.
Ans: I checked requestscoped inside of applicationscoped. whenever i was hitting the api, same instance of applicationScoped was getting accessed but
   it's dependent requestscoped was creating new one everytime.
   url test for this project:  http://localhost:8080/cdiInjectionWebProj/rest/hello/interscopetest?name=%22raghu%22
   
Q) How to limit what CDI(which class) considers to be managed beans?
Ans: attribute bean-discovery-mode in beans.xml is used to get controlled over what class should be considered as bean for injection.
    This attribute has three possible values:
	1. 'annotated' - loosely translated, means that only components with a class-level annotation are processed.
	2. 'all' - all components are processed, just like they were in Java EE 6 with the explicit beans.xml.
	3. 'none' - CDI is effectively disabled.
 -> By default, if you does not specify bean-discovery-mode or there is no beans.xml, the bean discovery mode of 'annotated' assumed. 
 -> controlling the bean scanning example using beans.xml:
    <?xml version="1.0" encoding="UTF-8"?>
	<beans xmlns="http://xmlns.jcp.org/xml/ns/javaee"
	       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	       xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/javaee 
	       http://xmlns.jcp.org/xml/ns/javaee/beans_1_1.xsd"
	       bean-discovery-mode="all">
	</beans>
 Q) follow up question with the previous one, How we will controlled the class as a bean if beans.xml is not any mandatory in cdi 1.1 in JAVA EE 7.

Q) by default what is scope type of the class?
Ans: I don't know the scope type but what i can say is, while injecting the class without annotation, it will always create a new object.





------- unanswered questions --------
Q) what is jsf annotation? 

Q) i believe by default, CDI scan whole project for injecting the class object. How can we restrict the scan scope to some package level like in spring.
Q) How to inject bean class of library one and same for the spring case?
Q) How do we inject multiple class implementing same interface using the interface reference? 
Ans: in this situation, @Default and @Alternatives comes into the picture. but again class annotated with @Default annotation will only be injected as 
    below and not sure how can we inject the class annotated with @Alternatives(but is possible using beans.xml).
	  	 	@Inject
			EmpRegisterService tempEmpRegisterServiceImp;    // EmpRegisterService is interface
	So, to avoid it and make it simple. we can create a custom Qualifier to annotate the class and on the interface reference and don't use 
	@Default and @Alternatives annotation on the class: . Like i did:
			@Inject @TempEmp
			EmpRegisterService tempEmpRegisterServiceImp;  
			  
     
 
   

	           