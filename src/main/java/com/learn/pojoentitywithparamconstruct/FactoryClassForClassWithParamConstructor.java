package com.learn.pojoentitywithparamconstruct;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Named;

@ApplicationScoped
public class FactoryClassForClassWithParamConstructor {

	@Produces
	@Named("admin")
	public ApplicationScopeWithConstruct getApplicationScopeForAdmin(){
		return new ApplicationScopeWithConstruct("admin");
	}
	@Produces
	@Named("user")
	public ApplicationScopeWithConstruct getApplicationScopeForUser(){
		return new ApplicationScopeWithConstruct("user");
	}
}
