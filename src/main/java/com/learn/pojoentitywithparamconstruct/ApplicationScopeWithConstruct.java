package com.learn.pojoentitywithparamconstruct;

public class ApplicationScopeWithConstruct {     
	
	private String name;
	
	public ApplicationScopeWithConstruct(String name){
		this.name = name;
	}
	
	public String getString(){
		return name;
	}

}
