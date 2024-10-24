package com.java.parser.common.logger;

import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public abstract class CommonPerformanceLoggerAspect extends BaseLoggerAspect {

    private static final Logger errorLogger = LoggerFactory.getLogger("errorLogger");
    private static final Logger performanceLogger = LoggerFactory.getLogger("performanceLogger");

    public Object tracePerformance(ProceedingJoinPoint joinPoint) throws Throwable {

        Object response         = null;
        String className        = joinPoint.getTarget().getClass().getName();
        String methodName       = joinPoint.getSignature().getName();
        long entryTime          = System.currentTimeMillis();

        try {
            response = joinPoint.proceed();
        } catch (Exception ex) {
            errorLogger.error(String.format("%s.%s %s", className, methodName, ex.getMessage()), ex);
            throw ex;
        } catch (Throwable tx) {
            tx.printStackTrace();
            errorLogger.error(tx.getMessage(), tx);
            throw tx;
        }

        long exitTime = System.currentTimeMillis();

        performanceLogger.trace(String.format("Executed %s.%s in %d milliseconds", className, methodName, (exitTime - entryTime)));

        return response;
    }

}
