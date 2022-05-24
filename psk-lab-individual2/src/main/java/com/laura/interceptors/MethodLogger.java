package com.laura.interceptors;

import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import java.io.Serializable;

@Logger
@Interceptor
public class MethodLogger implements Serializable {

    @AroundInvoke
    public Object performLogging(InvocationContext context) throws Exception {
        System.out.println("===========================");
        System.out.println("Called method: " + context.getMethod().getDeclaringClass().getSimpleName() + "." + context.getMethod().getName() + "()");
        System.out.println("===========================");
        return context.proceed();
    }
}
