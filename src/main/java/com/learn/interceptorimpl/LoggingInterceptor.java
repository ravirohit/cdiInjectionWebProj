package com.learn.interceptorimpl;

import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

@Interceptor
@Log  //binding the interceptor here. now any method annotated with @Log would be intercepted by logMethodEntry
public class LoggingInterceptor {
    @AroundInvoke
    public Object logMethodEntry(InvocationContext ctx) throws Exception {
        System.out.println("Entering method: " + ctx.getMethod().getName());
        //or logger.info statement 
        return ctx.proceed();
    }
}
