package com.numetriclabz.fbfriendslist;



/**
 * Created by THINH DO on 9/17/2015.
 */
public class MyExceptionHandler implements
        Thread.UncaughtExceptionHandler {
    private String LOG_TAG = this.getClass().getSimpleName();

    public void uncaughtException(Thread thread, Throwable exception) {

        exception.printStackTrace();
        System.exit(0);
    }
}
