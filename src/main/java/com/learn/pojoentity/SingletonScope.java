package com.learn.pojoentity;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Singleton;

@Singleton
public class SingletonScope {
   private List<String> nameList = new ArrayList<>();
   
   @PostConstruct
	public void objectCreated(){
		System.out.println("<<< Singleton scope object created");
	}
   
   public void addName(String name){
	   System.out.println("list beore adding name in Singleton scope:"+nameList+"  object ref:"+this);
	   this.nameList.add(name);
   }
   public void display(){
	   
   }
	@PreDestroy
	public void destroy(){
		System.out.println("<<<  Singleton object destroyed");
	}
}
