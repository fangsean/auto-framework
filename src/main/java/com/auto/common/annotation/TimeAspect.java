//package com.auto.common.annotation;
//
//import com.auto.common.annotation.adapters.LogWapper;
//import com.auto.common.mertics.JProfile;
//import org.apache.commons.lang3.ArrayUtils;
//import org.aspectj.lang.JoinPoint;
//import org.aspectj.lang.JoinPoint;
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.*;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Component;
//
//import java.lang.reflect.Method;
//import java.text.MessageFormat;
//
//@Aspect
//@Component("timeAspect")
//public class TimeAspect {
//
//    private static final Logger logger = LoggerFactory.getLogger("timeFilterLogger");
//
//    private Integer timeOut = 100;
//
////    @Pointcut("execution(* com.auto.service..*.*(..))")
//    @Pointcut("execution(public * com.auto..*.*(..) ) ")
//    public void timeLogPointCut() {
//
//    }
//
//    @Before(value = "timeLogPointCut()")
//    public void doBefore(JoinPoint point) throws Throwable {
//
//    }
//
//    public void doBeforeReturing(JoinPoint point) throws Throwable {
//
//    }
//
//    @Around(value = "timeLogPointCut()")
//    public Object doAround(ProceedingJoinPoint point) throws Throwable {
//        String requestString = dumpLog(point);
//        JProfile.start("Service Interceptor Time Count starting...");
//        try {
//            Object result = point.proceed();
//            return result;
//        } finally {
//            JProfile.release();
//            long duration = JProfile.getDuration();
//            if (duration > timeOut) {
//                if (logger.isInfoEnabled()) {
//                    logger.warn(MessageFormat.format(" Response of {1} returned in {2,number}ms", new Object[]{requestString, new Long(duration)}));
//                }
//            }
//            JProfile.reset();
//        }
//    }
//
//    @After(value = "timeLogPointCut() ")
//    public void doAfter(JoinPoint point) throws Throwable {
//
//    }
//
//    public void doAfterThrowing(JoinPoint point) throws Throwable {
//
//    }
//
//
//    private String dumpLog(JoinPoint point) throws Throwable {
//
//        StringBuffer buffer = new StringBuffer();
//        Class<?> pointClass = point.getTarget().getClass();
//        String pointMethod = point.getSignature().getName();
//        buffer.add("Class: ")
//                .add(pointClass.getName()).add("\r\n\t")
//                .add("Method:")
//                .add(point.getSignature().toString());
//
//        Object[] args = point.getArgs();
//        if (ArrayUtils.isNotEmpty(args)) {
//            int length = args.length;
//
//            Class[] paramenterTypes = new Class[length];
//
//            for (int i = 0; i < length; i++) {
//                paramenterTypes[i] = args[i].getClass();
//            }
//
//            Method method = pointClass.getMethod(pointMethod, paramenterTypes);
//            LogWapper annotationLog = method.getAnnotation(LogWapper.class);
//
//            if (null != annotationLog) {
//                if (annotationLog.isSave()) {
//                    save(buffer.toString());
//                }
//            }
//
//        }
//
//        return buffer.toString();
//    }
//
//
//    private void save(String log) {
//        System.out.println("save info :" + log);
//    }
//
//
//}
//
