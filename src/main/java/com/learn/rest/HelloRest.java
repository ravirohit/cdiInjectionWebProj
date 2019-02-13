package com.learn.rest;

import java.util.Optional;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import com.learn.customannotation.TempEmp;
import com.learn.service.EmpRegisterService;

@Path("hello")
public class HelloRest {
	
	@Inject @TempEmp
	EmpRegisterService empRegisterService;

	 @GET                        // http://localhost:8080/cdiInjectionWebProj/rest/hello
	 public String hello() {
		 System.out.println("hiiiii rest resource method hello rest get called:"+empRegisterService);
         
		 Optional<EmpRegisterService> opt = Optional.ofNullable(empRegisterService);
		 if(opt.isPresent()){
			 System.out.println("employee service is not null");
		 }
		 opt.ifPresent( emp -> { System.out.println("if object is not null");
		 
		              });
		 opt.ifPresent( emp -> { System.out.println("if object is not null:");
		                         emp.display();
                       });
		 
		 
	     return "Hello method get called!";
	 }
	 
	 @Path("test")    //  http://localhost:8080/cdiInjectionWebProj/rest/hello/paramtest/rohit
	 @GET
	 public String test() {
		 System.out.println("test method called");
	     return "Test method get called!";
	 }
	 @Path("paramtest/{name}")    // http://localhost:8080/cdiInjectionWebProj/rest/hello/paramtest/rohit
	 @GET
	 public String test(@PathParam("name") String name) {
		 System.out.println("param method called:"+name);
	     return "Hello "+name+"!";
	 }
 
}





/*//safe, prettier
Optional<String> optionalTypeDirName = optionalProject
     .flatMap(project -> project.getApplicationTypeOptional())
     .flatMap(applicationType -> applicationType.getTypeDirNameOptional());
optionalTypeDirName.ifPresent(typeDirName -> System.out.println(typeDirName));*/