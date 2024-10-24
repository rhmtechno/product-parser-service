package com.java.parser.common.aspect;

import com.java.parser.common.logger.CommonPerformanceLoggerAspect;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ParserServicePerformanceAspect extends CommonPerformanceLoggerAspect {

    @Pointcut("execution(public * com.java.parser.repository..*.*(..))")
    public void skeletonRepositoryPerformanceTrace() {}

    @Around("skeletonRepositoryPerformanceTrace())")
    public Object calculateSkeletonServicePerformance(ProceedingJoinPoint joinPoint) throws Throwable {
        return tracePerformance(joinPoint);
    }

}
