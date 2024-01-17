package com.bili.biliquguanggao;


public class StackUtils {
    public static boolean isCallingFromupdate() {
        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
        for (StackTraceElement element : stackTraceElements) {
           if (element.getClassName().equals("tv.danmaku.bili.update.api.supplier.b") &&
           element.getMethodName().equals("c"))
               return true;
        }

        return false;
    }
}
