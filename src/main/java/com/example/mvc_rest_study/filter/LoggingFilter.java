package com.example.mvc_rest_study.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
public class LoggingFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        long startTime = System.currentTimeMillis();

        String method = request.getMethod();
        String uri = request.getRequestURI();
        String query = request.getQueryString();
        String fullUri = uri!=null?uri+"?"+query:uri;
        log.info("-> {} {}", method, fullUri);

        try {
            filterChain.doFilter(request, response);
        }
        finally {
            long duration = System.currentTimeMillis() - startTime;
            log.info("-> {} {} | {} | {}", method, fullUri,duration,response.getStatus());
        }
    }
}
