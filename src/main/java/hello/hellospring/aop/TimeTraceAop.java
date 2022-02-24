package hello.hellospring.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

// @Aspect 애너테이션을 붙여야 aop로 사용이 가능하다.
@Aspect
 @Component // TimeTraceAop를 컴포넌트 스캔시켜도 되지만, AOP같은 비정형화된 것은 직접 Config에 적는 것이 더 좋음.(인지할 수 있게)
public class TimeTraceAop {

    // hello.hellospring 하위에는 다 적용하라는 의미
    @Around("execution(* hello.hellospring..*(..))")
    // joinPointer에 있는 여러 메소드를 이용해서 여러가지를 할 수 있다.
    // 인터셉터해서 무언가 조작을 하거나 정보를 볼 수 있는 기술 = AOP
    public Object execute(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        // joinPoint.toString()로, 어떤 메소드를 call 한지 볼 수 있음.
        System.out.println("START: " + joinPoint.toString());
        try {
            // joinPoint.proceed()로, 다음 메소드로 진행시킬 수 있음.
            Object result = joinPoint.proceed();
            return result;
//            return joinPoint.proceed(); // 인라인으로 합친 것.
        } finally {
            long finish = System.currentTimeMillis();
            long timeMs = finish - start;
            System.out.println("END: " + joinPoint.toString() + " " + timeMs + "ms");

        }
    }
}
