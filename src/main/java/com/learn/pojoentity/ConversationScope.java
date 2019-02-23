package com.learn.pojoentity;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;

@ConversationScoped
public class ConversationScope implements Serializable {

	private static final long serialVersionUID = 1L;
	
	 @Inject
	 private Conversation conversation;
	 private int counter;
	 
	 @PostConstruct
	  public void init(){
	    counter = 0;
	    conversation.begin();
	    System.out.println("<<< conversation scope object created");
	    
	  }
	 public Conversation getConversation() {
		    return conversation;
     }
	 public void endConversation(){
		 System.out.println("ending conversation");
		 System.out.println(conversation.isTransient());
		 if(!conversation.isTransient()){
		      conversation.end();
		    }
	 }
	  
	  public void increment(){
	    counter++;
	  }
	@PreDestroy
	public void destroy(){
		System.out.println("<<< conversation scope object destroyed");
	}

}
