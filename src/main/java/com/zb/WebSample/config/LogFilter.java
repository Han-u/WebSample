package com.zb.WebSample.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import java.io.IOException;

@Slf4j
@Component
public class LogFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        // 외부 -> filter -> 처리 -> filter -> 외부
        log.info("Hello filter:" + Thread.currentThread());
        chain.doFilter(request, response); // 여기가 -> 처리 ->
        log.info("Bye filter" + Thread.currentThread());


    }
}
