package com.learn.pojoentity;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class RequestScope {
	
	private List<String> nameList = new ArrayList<>();
	
	@PostConstruct
	public void objectCreated(){
		System.out.println("<<< Request scope object created");
	}
	
	public void addName(String name){
		System.out.println("list before adding name in request scope:"+nameList+"  object ref:"+this);
		this.nameList.add(name);
	}
    
	public void display(){
		System.out.println("name added in the request scope:"+nameList);
	}
	@PreDestroy
	public void destroy(){
		System.out.println("<<< Request scope object destroyed");
	}
}
