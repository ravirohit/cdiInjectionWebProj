package com.learn.produceeimpl;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Produces;

@SessionScoped
public class GreetingCardFactory implements Serializable {
 
    private static final long serialVersionUID = -44416514616012281L;
 
   // private GreetingType greetingType;
 
   /* @Produces
    public GreetingCard getGreetingCard() {
 
        switch (greetingType) {
        case HELLO:
            return new GreetingCardImpl();
        case HI:
            return new AnotherGreetingCardImpl();
        default:
            return new GreetingCardImpl();
        }
    }*/
    
    @Produces
    @Greetings(GreetingType.HELLO)
    public GreetingCard getGreetingCard() {
        return new GreetingCardImpl();
    }
     
    @Produces
    @Greetings(GreetingType.HI)
    public GreetingCard getAnotherGreetingCard() {
        return new AnotherGreetingCardImpl();
    }
}
