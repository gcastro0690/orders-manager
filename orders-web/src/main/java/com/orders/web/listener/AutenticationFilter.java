package com.orders.web.listener;

import java.io.IOException;

import javax.faces.context.FacesContext;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet Filter implementation class AutenticationFilter
 */
@WebFilter("/view/*")
public class AutenticationFilter implements Filter {

	/**
	 * Default constructor.
	 */
	public AutenticationFilter() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		 HttpServletRequest httpReq = (HttpServletRequest) request;
	    HttpServletResponse httpResp = (HttpServletResponse) response;
	    HttpSession session = httpReq.getSession();
		    
		String url = "";
		String queryString = "";
		
		if (request instanceof HttpServletRequest) {
			url = ((HttpServletRequest) request).getRequestURL().toString();
			queryString = ((HttpServletRequest) request).getQueryString();
		}
		
		if(!url.contains("index.xhtml")) {
			FacesContext context = FacesContext.getCurrentInstance();			
			HttpServletResponse httpResponse = (HttpServletResponse) response;
			
			
			if(session.getAttribute("user") == null) {
				httpResponse.sendRedirect("/ORDERS/index.xhtml");
			}	
			
		}
		chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
