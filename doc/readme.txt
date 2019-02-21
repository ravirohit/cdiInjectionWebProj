-> CDI link: https://docs.jboss.org/weld/reference/2.0.2.Final/en-US/pdf/weld-reference.pdf
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
@Any
@ApplicationScoped
@Qualifier
@PostConstruct

Note: can't use custom qualifier and @Default or custom qualifier and @Alternative annotation together on the same class.

-> The set of bean defining annotations contains:
		@ApplicationScoped, @SessionScoped, @ConversationScoped and @RequestScoped annotations,
-> by default CDI bean dependency is lazy initialization means, whenever we will use the or reference the bean variable that time only object will be 
   created. 
-> while Singleton is early initialization means, singleton object is created whenever the class containing the singleton class reference is used or 
   created.
   Example: A is class. i have inject B singleton class inside the A object. So as soon as A object is created or reference B object instance will be
            also injected inside of A instance while in case of CDI scope class, all the injected variable will be have with actual instance at the
            time of the use.
    
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
 
->  @PostConstruct annotation is used on a method that needs to be executed after dependency injection is done to perform any initialization.

----- Interceptor 

 
 
 ------------------- Q&A ----------------------
 Q) How to inject the class annotated with @Alternative   
Ans: create custom qualifier annotation: 

Q) CDI bean class dependency injection is lazy loading or early?
Ans: lazy loading while for the singleton class it is early loading.
 Q) What is difference between @ApplicationScoped and @Singleton?
 Ans: I checked the behavior, both worked in the same manner. but i believe, @Singleton doesn't come with CDI specification.
    and second thing is, @Singleton is early initialization while @ApplicationScoped is lazy initialization.
 
 Q) What is the behavior if we put small life time scope object inside of the more one like: putting request class inject inside of the application 
    scope object.
Ans: I checked requestscoped inside of applicationscoped. whenever i was hitting the api, same instance of applicationScoped was getting accessed but
   it's dependent requestscoped object was creating new one each time.
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
   -> So, it is not ApplicationScope and not even Request scope because in one request multiple injecting of the class will have multiple different 
     instance.
   -> it might be Singleton as because, it is early initialization.
Q) How @SessionScoped bean works?
Ans: Let a class SessionStore is annotated with @SessionScoped and injected in the rest resource class.
  Functionality: 
  SessionScope works on the concept of cookies. What it does is, if we are using injected object of class annotated with @SessionScoped in our 
  resource method,then while sending response out of that resource, the CDI create a cookie and corresponding object of the class Annotated with @SessionScoped 
  and might be maintaining by some kind of Map DS.This cookie information store by the client(Browser). and from the next time whatever request comes
  from the client to the server it keep the cookie information with it and CDI will inject the corresponding Object of the Session type to the resource.    
  Implementation example:
  Login page will generally send first request -> for this request, session object will be created and with the help of login detail, we will update the session 
  object with user info and send the response to the client -> client will store the cookie and for the next sending request it will keep the cookie information in 
  the request(taken care by browser itself to keep cookie in the request) -> when request reach to the server, based on the cookie value CDI will inject the session object 
  in the server. so for the different cookie value CDI will inject different corresponding session object .. and if in the server side, session has been invalidated
  then there would not be any session object for the incoming request cookie, so CDI will inject a fresh Session object(without having user info) so we have to make
  sure with the content of the Session object if ti is valid request or invalid. 
  invalid request: session object will be empty means user info in the Session object will not be there.
  valid request: which actually being updated in the session object at login request means this session object will have user info.
  -> In these cases the bean will keep its state across client interactions using Cookies information.
  -> and Container know which session scope instance to fetch to serve the request.    
 -> Follow up question: VVI
 Q) How long the session object will be available in the server side, for example, we have login to the account and didn't logout but clear the cookie from browser.
   as logging out api is not getting called server will not invalidate the session to delete the session object. as session object will be managed by CDI. So how
   long this object will be alive at the server. 
Q) How to use @Named qualifier annotation in CDI?
Ans: -> implementation class of interface
	@Named("bmwAutoService")
	@RequestScoped
    class AutoServiceIml implements AutoService{};
    -> injecting implemented class to interface 
    @Inject
    @Named("bmwAutoService")
    private AutoService bmwAutoService;
----- Interceptor and Decorator -------   



------- unanswered questions --------
Q) what is jsf annotation in cdi? 
Q) @Stateful vs @SessionScoped
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
Q) SessionScope annotated class, observation:
  -> how CDI framework, keep the track of session class object and the corresponding cookie value. as when request comes form browser(client) with cookies value,
     particular session object is injected. and if there is no cookies(like for login page) then it created the cookies and send it to the client which store the
     cookies in the browser and attach the same while making the further api call.
Q) Follow up question: How to remove a CDI session scoped bean?
Q) concept of @ConversationScoped functionality.
Q) Does CDI is different for different server like jboss, tomcat, wildfly?




			  
     
 
   

	           