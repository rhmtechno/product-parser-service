package com.java.parser.common.logger;

import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public abstract class CommonTraceLoggerAspect extends BaseLoggerAspect {

    private static final Logger errorLogger = LoggerFactory.getLogger("errorLogger");
    private static final Logger traceLogger = LoggerFactory.getLogger("traceLogger");

    public Object trace(ProceedingJoinPoint joinPoint) throws Throwable {
        String requestData = formatRequestParameters(joinPoint);
        traceLogger.trace(String.format("Invoking : %s", requestData));

        Object response;

        try {
            response = joinPoint.proceed();
            String jsonResponse = serializeResponseToJson(joinPoint, response);
            String formattedResponse = String.format("Invocation Returned: %s", jsonResponse);
            traceLogger.trace(formattedResponse);
        } catch (Exception ex) {
            errorLogger.error(ex.getMessage(), ex);
            traceLogger.trace("Exception Occurred: "+ex.getMessage());
            throw ex;
        }

        return response;
    }

}
