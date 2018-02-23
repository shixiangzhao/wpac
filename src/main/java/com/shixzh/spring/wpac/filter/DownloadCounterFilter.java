package com.shixzh.spring.wpac.filter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

@WebFilter(filterName = "DownloadCounterFilter", urlPatterns = "/*" )
public class DownloadCounterFilter implements Filter {

	ExecutorService executorService = Executors.newSingleThreadExecutor();
	Properties downloadLog;
	File logFile;
	
	@Override
	public void init(FilterConfig filterConfig) {
		System.out.println("DownloadCounterFilter Init: ");
		String appPath = filterConfig.getServletContext().getRealPath("/");
		logFile = new File(appPath, "downloadLog.txt");
		if (!logFile.exists()) {
			try {
				logFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		downloadLog = new Properties();
		try {
			downloadLog.load(new FileReader(logFile));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void destroy() {
		executorService.shutdown();
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
			throws IOException, ServletException {
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		final String uri = httpServletRequest.getRequestURI();
		executorService.execute(new Runnable() {
			@Override
			public void run() {
				String property = downloadLog.getProperty(uri);
				if(property == null) {
					downloadLog.setProperty(uri, "1");
				} else {
					int count = Integer.parseInt(property);
					count++;
					downloadLog.setProperty(uri, Integer.toString(count));
				}
			}
		});
		filterChain.doFilter(request, response);
	}

}