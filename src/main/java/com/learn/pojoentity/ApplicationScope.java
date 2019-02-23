package com.learn.pojoentity;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;


@ApplicationScoped
public class ApplicationScope {

	private List<String> nameList = new ArrayList<>();
	
	@PostConstruct
	public void objectCreated(){
		System.out.println("<<< application scope object created");
	}
	
	public void addName(String name){
		System.out.println("list before adding name in application scope:"+nameList+"  object ref:"+this);
		this.nameList.add(name);
	}
    
	public void display(){
		System.out.println("name added in the application scope:"+nameList);
	}
	@PreDestroy
	public void destroy(){
		System.out.println("<<< application scope object destroyed");
	}
}
