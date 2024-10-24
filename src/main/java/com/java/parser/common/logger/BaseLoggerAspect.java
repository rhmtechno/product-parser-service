package com.java.parser.common.logger;

import com.java.parser.common.annotations.CardNumberSerializer;
import com.java.parser.common.annotations.SensitiveData;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Objects;

public abstract class BaseLoggerAspect {

    @Pointcut("@within(com.java.parser.common.annotations.NoLogging)")
    public void noLogging() {
    }

    private ObjectMapper objectMapper = new ObjectMapper();
    private static final String BLANK = " - ";

    protected String serializeResponseToJson(ProceedingJoinPoint joinPoint, Object object) throws JsonProcessingException {

        if (Objects.isNull(object)
                || doesReturnTypeHaveSensitiveAnnotation(joinPoint))
            return BLANK;

        String response = objectMapper.writeValueAsString(object);

        if (response == null) response = BLANK;

        return String.format("%s.%s Response: %s", joinPoint.getTarget().getClass().getName(), joinPoint.getSignature().getName(), response);
    }

    protected String formatRequestParameters(JoinPoint joinPoint) throws JsonProcessingException {

        String className = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        MethodSignature ms = (MethodSignature) joinPoint.getSignature();
        Method m = ms.getMethod();
        String[] parameterNames = ms.getParameterNames();
        Annotation[][] paramAnnotations = m.getParameterAnnotations();
        Object[] args = joinPoint.getArgs();

        String requestParameters = objectMapper.writeValueAsString(parameterNames);

        StringBuilder sb = new StringBuilder();
        int index = 0;
        for (Object param : args) {

            if (param instanceof BindingResult) {
                index++;
                continue;
            }
            if (doParametersHaveSensitiveAnnotation(index, paramAnnotations)) {
                if (index > 0) sb.append(" | ");
                sb.append("******");
                index++;
                continue;
            }

            if (Objects.isNull(param)) {
                if (index > 0) sb.append(" | ");
                sb.append(" - ");
            } else if (isParamHasCardNumberSerializerAnnotation(index, paramAnnotations)) {
                if (index > 0) sb.append(" | ");
                String value = String.valueOf(param);
                String serializedValue = (StringUtils.hasLength(value) && value.length() == 16) ? value.substring(0, 6) + "******" + value.substring(12, 16) : value;
                sb.append(serializedValue);
            } else {
                if (index > 0) sb.append(" | ");
                String jsonParam = objectMapper.writeValueAsString(param);
                sb.append(jsonParam);
            }
            index++;
        }

        return String.format("\nClass: %s \nMethod: %s \nArguments Name: %s \nArguments Value: %s\n", className, methodName, requestParameters, sb);
    }

    protected boolean doesReturnTypeHaveSensitiveAnnotation(ProceedingJoinPoint joinPoint) {
        MethodSignature ms = (MethodSignature) joinPoint.getSignature();
        Method m = ms.getMethod();
        return m.isAnnotationPresent(SensitiveData.class);
    }

    protected boolean isParamHasCardNumberSerializerAnnotation(int index, Annotation[][] paramAnnotations) {
        Annotation[] annotations = paramAnnotations[index];
        return Arrays
                .stream(annotations)
                .anyMatch(CardNumberSerializer.class::isInstance);
    }

    protected boolean doParametersHaveSensitiveAnnotation(int index, Annotation[][] paramAnnotations) {
        Annotation[] annotations = paramAnnotations[index];
        return Arrays
                .stream(annotations)
                .anyMatch(SensitiveData.class::isInstance);
    }


}
