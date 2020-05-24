package com.kongzue.baseframework.best;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ProxyFactory {
    private Object obj;//目标对象
    private BeforeAdvice before;
    private AfterAdvice after;

    public Object newProxyInstance() {
        //object即为代理对象
        return Proxy.newProxyInstance(
                this.getClass().getClassLoader(), obj.getClass()
                        .getInterfaces(), new InvocationHandler() {

                    @Override
                    public Object invoke(Object proxy, Method method,
                                         Object[] args) throws Throwable {

                        if (before != null) {
                            before.before();
                        }
                        //result即为接口的真实实现类的返回值
                        Object result = method.invoke(obj, args);

                        if (after != null) {
                            after.after();
                        }

                        return result;
                    }
                });
    }


    public ProxyFactory() {
        super();
        // TODO Auto-generated constructor stub
    }

    public ProxyFactory(Object obj, BeforeAdvice before, AfterAdvice after) {
        super();
        this.obj = obj;
        this.before = before;
        this.after = after;
    }

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }

    public BeforeAdvice getBefore() {
        return before;
    }

    public void setBefore(BeforeAdvice before) {
        this.before = before;
    }

    public AfterAdvice getAfter() {
        return after;
    }

    public void setAfter(AfterAdvice after) {
        this.after = after;
    }

}

//前置增强
interface BeforeAdvice {
    public void before();
}

//后置增强
interface AfterAdvice {
    public void after();
}

