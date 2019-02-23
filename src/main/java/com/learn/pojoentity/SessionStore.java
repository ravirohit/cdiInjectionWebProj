package com.learn.pojoentity;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicLong;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.SessionScoped;

@SessionScoped
public class SessionStore implements Serializable{
   
	private static final long serialVersionUID = 1L;

	public static AtomicLong INSTANCE_COUNT = new AtomicLong(0);
    String uname;
    
    
    @PostConstruct
    public void onNewSession(){
    	System.out.println("<<< session scope object created");
        INSTANCE_COUNT.incrementAndGet();
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

	@PreDestroy
	public void destroy(){
		System.out.println("<<<  session scope object destroyed");
	}
    
}
