package com.java.parser.common.aspect;

import com.java.parser.common.logger.CommonTraceLoggerAspect;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ParserServiceTracingAspect extends CommonTraceLoggerAspect {

    @Pointcut("execution(* com.java.parser.api..*(..)))")
    public void skeletonControllerAspect() {}

    @Pointcut("execution(* com.java.parser.service..*.*(..)))")
    public void skeletonServiceTrace() {}

    @Around("skeletonServiceTrace() && !noLogging()")
    public Object logService(ProceedingJoinPoint joinPoint) throws Throwable {
        return trace(joinPoint);
    }

    @Around("skeletonControllerAspect() && !noLogging()")
    public Object logController(ProceedingJoinPoint joinPoint) throws Throwable {
        return trace(joinPoint);
    }

}
