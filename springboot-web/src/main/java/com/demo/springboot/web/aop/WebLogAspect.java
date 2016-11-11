package com.demo.springboot.web.aop;
/**
 * Created by leo on 2016/10/26.
 */

import com.demo.springboot.web.annotation.FruitColor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * WebLogAspect
 * 描 述：
 * 作 者：leo
 * 时 间：2016/10/26 10:03
 */
@Aspect
@Component
public class WebLogAspect {

    private static final Logger logger = LoggerFactory.getLogger(WebLogAspect.class);

    private static ThreadLocal<Long> startTime = new ThreadLocal<>();

    // 定义AOP规则
    @Pointcut("execution(public * com.leo.springboot.demo..*.*(..))")
    public void webLog(){}

//    // 在切入点开始处切入内容
//    @Before("webLog()") //指定拦截器规则；也可以直接把“execution(* com.xjj.........)”写进这里
//    public void doBefore(JoinPoint joinPoint) throws Throwable {
//        startTime.set(System.currentTimeMillis());
//        // 省略日志记录内容
//        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
//        HttpServletRequest request = attributes.getRequest();
//
//        // 记录下请求内容
//        logger.info("URL : " + request.getRequestURL().toString());
//        logger.info("HTTP_METHOD : " + request.getMethod());
//        logger.info("IP : " + request.getRemoteAddr());
//        logger.info("CLASS_METHOD : " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
//        logger.info("ARGS : " + Arrays.toString(joinPoint.getArgs()));
//
//    }

//    // 在切入点return内容之后切入内容（可以用来对处理返回值做一些加工处理）
//    @AfterReturning(returning = "ret", pointcut = "webLog()")
//    public void doAfterReturning(Object ret) throws Throwable {
//        // 处理完请求，返回内容
//        logger.info("RESPONSE : " + ret);
//        logger.info("SPEND TIME : " + (System.currentTimeMillis() - startTime.get()) + "ms");
//    }

//    // 在切入点结尾处切入内容
//    @After("webLog()")
//    public Object doAfter(ProceedingJoinPoint proceedingJoinPoint) throws Throwable{
//        logger.info("doAfter()");
//        return proceedingJoinPoint.proceed();
//    }

    //  在切入点前后切入内容，并自己控制何时执行切入点自身的内容
    @Around("webLog() and @annotation(org.springframework.web.bind.annotation.RequestMapping) and @annotation(fruitColor)")
    public Object doAround(ProceedingJoinPoint proceedingJoinPoint, FruitColor fruitColor) throws Throwable{
        logger.info("doAround()");
        long start = System.currentTimeMillis();
        Object res = proceedingJoinPoint.proceed();

        logger.info("doAround(),耗时" + (System.currentTimeMillis()-start) + "ms");
        return res;
    }

//    // 用来处理当切入内容部分抛出异常之后的处理逻辑
//    @AfterThrowing
//    public void doAfterThrowing(JoinPoint joinPoint){
//        logger.info("doAfterThrowing()");
//    }
}
