package com.learn.rest;

import java.util.Optional;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import com.learn.customannotation.Emp;
import com.learn.customannotation.TempEmp;
import com.learn.service.EmpRegisterService;

@Path("hello")
public class HelloRest {
	
	@Inject @TempEmp
	EmpRegisterService tempEmpRegisterServiceImp;
	
	@Inject @Emp
	EmpRegisterService empRegisterServiceImp;

	 @GET                        // http://localhost:8080/cdiInjectionWebProj/rest/hello
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