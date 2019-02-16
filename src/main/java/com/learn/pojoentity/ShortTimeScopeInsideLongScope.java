package com.learn.pojoentity;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class ShortTimeScopeInsideLongScope {
	
	@Inject
	RequestScope requestScope;
	
	public void addName(String name){
		System.out.println("long time scope method called to check short time scope status reqscope:"+requestScope+" appScope :"+this);
		requestScope.addName(name);
	}
	public RequestScope getRequestObj(){
		return requestScope;
	}

}
