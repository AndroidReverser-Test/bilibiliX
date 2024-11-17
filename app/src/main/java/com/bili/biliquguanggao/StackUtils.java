package com.bili.biliquguanggao;


public class StackUtils {
    public static boolean isCallingFromupdate() {
        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
        for (StackTraceElement element : stackTraceElements) {
           if (element.getClassName().contains("tv.danmaku.bili.update"))
               return true;
        }

        return false;
    }
}
