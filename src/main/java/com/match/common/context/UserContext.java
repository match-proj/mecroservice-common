package com.match.common.context;

/**
 * @Author zhangchao
 * @Date 2019/5/22 10:34
 * @Version v1.0
 */
public abstract class UserContext {

    private static ThreadLocal<User> threadLocal = new ThreadLocal<User>();


    public static void setUser(User id){
        threadLocal.set(id);
    }

    public static User getUser(){
        return threadLocal.get();
    }

}
