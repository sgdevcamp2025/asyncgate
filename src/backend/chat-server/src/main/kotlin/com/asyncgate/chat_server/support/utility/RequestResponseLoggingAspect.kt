package com.asyncgate.chat_server.support.utility

import jakarta.servlet.http.HttpServletRequest
import org.aspectj.lang.JoinPoint
import org.aspectj.lang.annotation.After
import org.aspectj.lang.annotation.AfterReturning
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before
import org.aspectj.lang.annotation.Pointcut
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.slf4j.MDC
import org.springframework.stereotype.Component
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes
import java.util.*

@Aspect
@Component
class RequestResponseLoggingAspect {

    @Pointcut("execution(* com.asyncgate.chat_server.controller..*Controller.*(..))")
    fun apiControllerMethods() {}

    @Pointcut("!execution(* com.asyncgate.chat_server.controller.HealthCheckController.*(..))")
    fun excludeHealthCheck() {}

    @Pointcut("apiControllerMethods() && excludeHealthCheck()")
    fun apiControllerMethodsExcludingHealthCheck() {}

    @Before("apiControllerMethodsExcludingHealthCheck()")
    fun logRequest(joinPoint: JoinPoint) {
        setMDC()
        requestLogger.info("Request received for method: {}", joinPoint.signature.name)
    }

    @AfterReturning(pointcut = "apiControllerMethodsExcludingHealthCheck()")
    fun logResponse() {
        val startTimeStr = MDC.get("startTime")
        val startTime = startTimeStr?.toLong() ?: 0L
        val executionTime = (System.nanoTime() - startTime) / 1_000_000_000.0
        MDC.put("responseTime", String.format("%.3f초", executionTime))
        responseLogger.info("Response sent successfully")
    }

    @After("apiControllerMethodsExcludingHealthCheck()")
    fun clearMDC() {
        MDC.clear()
    }

    private fun setMDC() {
        val request = currentHttpRequest
        if (request != null) {
            MDC.put("method", request.method)
            MDC.put("requestUri", request.requestURI)
            MDC.put("sourceIp", request.getHeader("X-Real-IP") ?: request.remoteAddr)
            MDC.put("userAgent", request.getHeader("User-Agent"))
            MDC.put("xForwardedFor", request.getHeader("X-Forwarded-For"))
            MDC.put("xForwardedProto", request.getHeader("X-Forwarded-Proto"))
            MDC.put("requestId", UUID.randomUUID().toString())
            MDC.put("startTime", System.nanoTime().toString())
        }
    }

    private val currentHttpRequest: HttpServletRequest?
        get() {
            val attributes = RequestContextHolder.getRequestAttributes() as? ServletRequestAttributes
            return attributes?.request
        }

    companion object {
        private val requestLogger: Logger = LoggerFactory.getLogger("HttpRequestLog")
        private val responseLogger: Logger = LoggerFactory.getLogger("HttpResponseLog")
    }
}
