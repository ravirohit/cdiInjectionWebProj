package com.learn.rest;

import java.util.Optional;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;

import com.learn.customannotation.Emp;
import com.learn.customannotation.TempEmp;
import com.learn.pojoentity.ApplicationScope;
import com.learn.pojoentity.RequestScope;
import com.learn.pojoentity.ScopeEntityWithoutAnnotatation;
import com.learn.pojoentity.ShortTimeScopeInsideLongScope;
import com.learn.pojoentity.SingletonScope;
import com.learn.service.EmpRegisterService;

@Path("hello")
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
	
	@Inject
	SingletonScope singletonScope;
	
	@Inject 
	ShortTimeScopeInsideLongScope shortTimeScopeInsideLongScope;
	
	@Inject 
	ScopeEntityWithoutAnnotatation scopeEntityWithoutAnnotatation;
	
	@Inject 
	ScopeEntityWithoutAnnotatation scopeEntityWithoutAnnotatation1;
	
	 @Path("test")    // http://localhost:8080/cdiInjectionWebProj/rest/hello/test
	 @GET
	 public String test() {  // applicationscope and singleton both worked in the same ways.
		 System.out.println("printing object for different scope:");
		 System.out.println("applicationScope:"+applicationScope+" applicationScope1:"+applicationScope1);
		 System.out.println("scopeEntityWithoutAnnotatation:"+scopeEntityWithoutAnnotatation+" scopeEntityWithoutAnnotatation1:"+scopeEntityWithoutAnnotatation1);
		 System.out.println("requestScope:"+requestScope+" requestScope1:"+requestScope1);
	     return "Test method get called!";
	 }
	
	 @GET             //  http://localhost:8080/cdiInjectionWebProj/rest/hello
	 public String hello() {
		 System.out.println("hiiiii rest resource method hello rest get called:"+tempEmpRegisterServiceImp);
         
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
 
}





/*//safe, prettier
Optional<String> optionalTypeDirName = optionalProject
     .flatMap(project -> project.getApplicationTypeOptional())
     .flatMap(applicationType -> applicationType.getTypeDirNameOptional());
optionalTypeDirName.ifPresent(typeDirName -> System.out.println(typeDirName));*/