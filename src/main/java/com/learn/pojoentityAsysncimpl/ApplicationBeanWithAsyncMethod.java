package com.learn.pojoentityAsysncimpl;

import java.util.Optional;
import java.util.concurrent.SynchronousQueue;

import javax.ejb.Asynchronous;
import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@ApplicationScoped          // this is just a dummy class as unable to impl asynchronous we i wanted. so useless 
public class ApplicationBeanWithAsyncMethod {
	public static void main(String[] str){
		ApplicationBeanWithAsyncMethod obj = new ApplicationBeanWithAsyncMethod();
		obj.display();
	}

	@Asynchronous
	public void display(){
		System.out.println("start");
		Client client = ClientBuilder.newClient();
		WebTarget webTarget = client.target("http://localhost:8080/cdiInjectionWebProj");
		WebTarget employeeWebTarget = webTarget.path("rest/hello");
		Invocation.Builder invocationBuilder  = employeeWebTarget.request(MediaType.APPLICATION_JSON);
		
		 Response response  = invocationBuilder.get(); // Employee class is response got  for GET
		//Response response1 = invocationBuilder.post(Entity.entity(employee, MediaType.APPLICATION_JSON); //  payload is employee for POST
		 System.out.println("response:"+response);
		 Optional<Response> resopt = Optional.ofNullable(response);
		 resopt.ifPresent(res -> System.out.println("response string:"+res.readEntity(String.class)));
		 System.out.println("end");
	}
}
