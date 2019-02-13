package com.learn.service;

import javax.enterprise.inject.Alternative;

import com.learn.customannotation.TempEmp;

@TempEmp
//@Alternative 
public class TempEmpRegisterServiceImp implements EmpRegisterService{

	@Override
	public void display() {
		System.out.println("Temp emp service Imp display method called");
	}

}
