package com.java.parser.common.logger;


import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.rmi.RemoteException;

public abstract class CommonIntegrationLoggerAspect extends BaseLoggerAspect {

    private static final Logger errorLogger         = LoggerFactory.getLogger("errorLogger");
    private static final Logger performanceLogger   = LoggerFactory.getLogger("integrationPerformanceLogger");
    private static final Logger traceLogger         = LoggerFactory.getLogger("integrationTraceLogger");

    public Object trace(ProceedingJoinPoint joinPoint) throws Throwable {
        Object response;
        long entryTime = 0;
        long exitTime;

        traceLogger.trace(String.format("Invoking: %s", formatRequestParameters(joinPoint)));

        try {
            entryTime = System.currentTimeMillis();
            response = joinPoint.proceed();
            exitTime = System.currentTimeMillis();
        } catch (RemoteException rex) {
            exitTime = System.currentTimeMillis();
            errorLogger.error(String.format("Ended with exception: %s, Exit with in %d milliseconds", rex.getMessage(), (exitTime - entryTime)), rex);
            traceLogger.trace(String.format("Ended with exception: %s", rex.getMessage()));
            throw rex;
        } catch (Exception ex) {
            exitTime = System.currentTimeMillis();
            errorLogger.error(String.format("Ended with exception: %s, Exit with in %d milliseconds", ex.getMessage(), (exitTime - entryTime)), ex);
            traceLogger.trace(String.format("Ended with exception: %s", ex.getMessage()));
            throw ex;
        }

        traceLogger.trace(String.format("Invocation returned: %s", serializeResponseToJson(joinPoint, response)));

        performanceLogger.trace(String.format("Executed %s.%s in %d milliseconds", joinPoint.getTarget().getClass().getName(), joinPoint.getSignature().getName(), (exitTime - entryTime)));

        return response;
    }
}
