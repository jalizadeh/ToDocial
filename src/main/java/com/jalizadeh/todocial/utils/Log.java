package com.jalizadeh.todocial.utils;

import lombok.extern.slf4j.Slf4j;
import java.time.LocalDateTime;

@Slf4j
public class Log {

    private static ThreadLocal<StringBuilder> logs = new ThreadLocal<>();

    public static synchronized void info(String msg){
        log.info(msg);
        appendLog("INFO", msg);
    }

    public static synchronized void error(String msg) {
        log.error(msg);
        appendLog("ERROR", msg);
    }

    public static synchronized void debug(String msg) {
        log.debug(msg);
        appendLog("DEBUG", msg);
    }

    public static synchronized void warn(String msg) {
        log.warn(msg);
        appendLog("WARN", msg);
    }


    private static void appendLog(String type, String msg) {
        if(logs.get() == null){
            logs.set(new StringBuilder());
        }

        String currentTime = LocalDateTime.now().toString();
        currentTime = currentTime.replaceAll("T", " ");
        String logMsg = currentTime + " " + Thread.currentThread() + " " + type + " - " + msg + "\n";
        logs.get().append(logMsg);
        System.out.println(logMsg);
    }

}
