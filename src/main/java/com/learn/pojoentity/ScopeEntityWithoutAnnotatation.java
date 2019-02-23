package com.learn.pojoentity;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

public class ScopeEntityWithoutAnnotatation {
	
	@PostConstruct
	public void init(){
		System.out.println("<<< scope without annotation object created");
	}
	public void addName(){
		System.out.println("class without scope entity called");
	}
	@PreDestroy
	public void destroy(){
		System.out.println("<<< scope without annotation object destroyed");
	}

}
