package com.learn.service;

import javax.enterprise.inject.Default;

import com.learn.customannotation.Emp;

@Emp
//@Default
public class EmpRegisterServiceImp implements EmpRegisterService{

	@Override
    public void display(){
		
		System.out.println("Emp service impl display method called");
	}
}
