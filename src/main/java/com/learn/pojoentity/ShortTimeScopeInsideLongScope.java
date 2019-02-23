package com.learn.pojoentity;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class ShortTimeScopeInsideLongScope {
	
	@Inject
	RequestScope requestScope;
	
	@PostConstruct
	public void init(){
		System.out.println("<<< ShortTimeScopeInsideLongScope object created");
	}
	
	public void addName(String name){
		System.out.println("long time scope method called to check short time scope status reqscope:"+requestScope+" appScope :"+this);
		requestScope.addName(name);
	}
	public RequestScope getRequestObj(){
		return requestScope;
	}
	@PreDestroy
	public void destroy(){
		System.out.println("<<<  ShortTimeScopeInsideLongScope object destroyed");
	}

}
