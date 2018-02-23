package com.shixzh.spring.wpac.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

@WebFilter(filterName = "ImageProtectorFilter", urlPatterns = {
        "*.png", "*.jpg", "*.gif"
})
public class ImageProtectorFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void destroy() {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String referer = httpServletRequest.getHeader("referer");
        System.out.println("referer: " + referer);
        if (referer != null) {
            chain.doFilter(request, response);
        } else {
            throw new ServletException("Image not available");
        }
    }
}
