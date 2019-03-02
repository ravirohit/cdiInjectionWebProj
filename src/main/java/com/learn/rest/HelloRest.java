package com.learn.rest;

import java.util.Optional;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;

import com.learn.customannotation.Emp;
import com.learn.customannotation.TempEmp;
import com.learn.interceptorimpl.Log;
import com.learn.pojoentity.ApplicationScope;
import com.learn.pojoentity.ConversationScope;
import com.learn.pojoentity.RequestScope;
import com.learn.pojoentity.ScopeEntityWithoutAnnotatation;
import com.learn.pojoentity.SessionStore;
import com.learn.pojoentity.ShortTimeScopeInsideLongScope;
import com.learn.pojoentity.SingletonScope;
import com.learn.pojoentityAsysncimpl.ApplicationBeanWithAsyncMethod;
import com.learn.pojoentitywithparamconstruct.ApplicationScopeWithConstruct;
import com.learn.produceeimpl.GreetingCard;
import com.learn.produceeimpl.GreetingCardImpl;
import com.learn.produceeimpl.GreetingType;
import com.learn.produceeimpl.Greetings;
import com.learn.service.EmpRegisterService;

@Path("hello")
@Log
public class HelloRest {
	
	// testing multiple class implementing same interface
	@Inject @TempEmp
	EmpRegisterService tempEmpRegisterServiceImp;    
	@Inject @Emp
	EmpRegisterService empRegisterServiceImp;

	
	@Inject 
	RequestScope requestScope;
	@Inject 
	RequestScope requestScope1;
	
	@Inject
	ApplicationScope applicationScope;
	@Inject
	ApplicationScope applicationScope1;
	
	@Inject                      // It is early initialize. beans whenever this class object(rest class) is reference that time is created, it is
	SingletonScope singletonScope;      //being used or not no matter it will be injected.
	
	@Inject 
	ShortTimeScopeInsideLongScope shortTimeScopeInsideLongScope;   // it is lazy initialize, when it is used then object created
	
	@Inject                         // it is early initialization just like Singleton class.
	ScopeEntityWithoutAnnotatation scopeEntityWithoutAnnotatation;
	
	@Inject 
	ScopeEntityWithoutAnnotatation scopeEntityWithoutAnnotatation1;
	
	
	
	// session scope
	@Inject                               // it is lazy initialization. it is created when this variable is referenced.
	SessionStore session;
	
	@Inject
	ConversationScope conversationScope;
	
	/*@Inject
	@Greetings(GreetingType.HELLO)
	GreetingCard greetingCard;*/
	
	@Inject
	@Named("admin")     // how to use the @produces annotation
	ApplicationScopeWithConstruct applicationScopeWithConstruct;
	
	@Inject
	ApplicationBeanWithAsyncMethod applicationBeanWithAsyncMethod;
	
	 @Path("test")    // http://localhost:8080/cdiInjectionWebProj/rest/hello/test
	 @GET
	 public String test() {  // applicationscope and singleton both worked in the same ways.
		 System.out.println("printing object for different scope:");
		 System.out.println("applicationScope:"+applicationScope+" applicationScope1:"+applicationScope1);
		 System.out.println("scopeEntityWithoutAnnotatation:"+scopeEntityWithoutAnnotatation+" scopeEntityWithoutAnnotatation1:"+scopeEntityWithoutAnnotatation1);
		 System.out.println("requestScope:"+requestScope+" requestScope1:"+requestScope1);
	     return "Test method get called!";
	 }
	
	 @GET          //  http://localhost:8080/cdiInjectionWebProj/rest/hello
	 public String hello() {
		 System.out.println("Rest resource method hello rest get called:"+tempEmpRegisterServiceImp);

        
		 Optional<EmpRegisterService> tempOpt = Optional.ofNullable(tempEmpRegisterServiceImp);
		 Optional<EmpRegisterService> opt = Optional.ofNullable(empRegisterServiceImp);
		 if((tempOpt.isPresent()) && (opt.isPresent())){
			 System.out.println("empl and temp emp service object is not null");
		 }

		 tempOpt.ifPresent(tempEmp -> {
			 System.out.println("calling temp emp display method:");
			 tempEmp.display();
		 });
		 
		 opt.ifPresent(emp -> {
			 System.out.println("calling emp display method:");
			 emp.display();
		 });
		
	     return "Hello method get called!";
	 }
	 
	 
	 @Path("paramtest/{name}")    // http://localhost:8080/cdiInjectionWebProj/rest/hello/paramtest/rohit
	 @GET
	 public String test(@PathParam("name") String name) {
		 System.out.println("param method called:"+name);
	     return "Hello "+name+"!";
	 }
	 
	 @Path("scopetest/{name}")
	 @GET
	 public String requestScopeTest(@PathParam("name") String name){
		 System.out.println("request app resouce called");
		 Optional<RequestScope> reqOpt = Optional.ofNullable(requestScope);
		 Optional<ApplicationScope> appOpt = Optional.ofNullable(applicationScope);
		 Optional<SingletonScope>  scopeOpt = Optional.ofNullable(singletonScope);
		 
		 reqOpt.ifPresent(req -> {
			 req.addName(name);
		 });
		 appOpt.ifPresent(app -> {
			 app.addName(name);
		 });
		 scopeOpt.ifPresent(scope -> {
			scope.addName(name); 
		 });
		 return "Name added in the request scope";
	 }
	 
	 @Path("interscopetest")
	 @GET                           // http://localhost:8080/cdiInjectionWebProj/rest/hello/interscopetest?name=%22raghu%22
	 public String interScopeTest(@QueryParam("name") String name){
		 System.out.println("called scope test");
		 shortTimeScopeInsideLongScope.addName(name);
		 
		 Optional<ShortTimeScopeInsideLongScope> opt = Optional.ofNullable(shortTimeScopeInsideLongScope);
		 
		// Optional<RequestScope> reqopt = opt.flatMap();
		 
		 //reqopt.ifPresent(req -> req.);
		 return "interscopetest called:" + name;
	 }
	 
	 @Path("login/{uname}")        // for session object
	 @GET
	 public String sessionTest(@PathParam("uname") String uname){

        System.out.println("session established for the user:"+uname);
		session.setUname(uname);
		
		 return "session scope method called";
	 }
	 @Path("logout")               // for session object
	 @GET
	 public String logout(@Context HttpServletRequest request){

		 System.out.println("invalidated session object for the user:"+session.getUname());
		 HttpSession httpSession = request.getSession();
         System.out.println(httpSession.getId());
         httpSession.invalidate();
         if(session.getUname() == null){
             return "user doesn't exist";
         }
         
         return "user logout successfully";
	 }
	 @Path("cookieInfo")               // for session object
	 @GET
	 public String printCookie(@Context HttpServletRequest request){

         Cookie[] cookies = request.getCookies();
         System.out.println("creating optional of cookies object");
         Optional<Cookie[]> cookieOpt = Optional.ofNullable(cookies);
         System.out.println("created optional of cookies object");
         cookieOpt.ifPresent(cookies1 -> {
        	  for(Cookie cookie: cookies1){
             	 System.out.println(cookie);
             	 System.out.println(cookie.getValue());
              }
        	 
         });
		
         return "cookie info has been has been logged to console";
	 }
	 
	 @Path("conversationscope")
	 @GET
	 public String conversationScopeTest(){
		 
		 System.out.println("conversation scope object:"+conversationScope+"   id:"+conversationScope.getConversation().getId());
		 return "conversationscope method invoked";
	 }
	 /*@Path("producetest")      // @produces annotation implementation copied example
	 @GET
	 public String produceannotation(){
		 
		 System.err.println("produce way of creating object:"+greetingCard+"  class type:" + (greetingCard instanceof GreetingCardImpl));
		 return "produce annotation test method called";
	 }*/
	 
	 @Path("scopewithcontruct")   // @produces annotation implementation mine example
	 @GET
	 public String scopeWithConst(){
		 
		 System.out.println("scope object with contructor:"+applicationScopeWithConstruct);
		 System.out.println(applicationScopeWithConstruct.getString());
		 return "object created for the scope with param contructor";
	 }
	 
	 @Path("asynmethod")
	 @GET
	 public String callAsynMethod(){
		 System.out.println("calling async method");
		 applicationBeanWithAsyncMethod.display();
		 System.out.println("called async method");
		 return "asyn method impl called";
	 }
 
}





/*//safe, prettier
Optional<String> optionalTypeDirName = optionalProject
     .flatMap(project -> project.getApplicationTypeOptional())
     .flatMap(applicationType -> applicationType.getTypeDirNameOptional());
optionalTypeDirName.ifPresent(typeDirName -> System.out.println(typeDirName));*/