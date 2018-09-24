package com.ailhanli.moneytransfer.aspect;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class PerformanceTimingAspect {
	
	private static Logger log = LogManager.getLogger(PerformanceTimingAspect.class);

	@Around("execution(* com.ailhanli.moneytransfer.service.*.*(..))")
	public Object calculateRunningTime(ProceedingJoinPoint jp){
		 
		long before = System.currentTimeMillis();
		try {
			return jp.proceed();
		} catch (Throwable e) {
			log.error(e);
		}finally {
			long after = System.currentTimeMillis();
			log.info((after-before)+" ms  is passed");
		}
		return null;
	}
}
