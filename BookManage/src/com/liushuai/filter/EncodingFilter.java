package com.liushuai.filter;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;


public class EncodingFilter implements Filter {

    public EncodingFilter() {
    }
	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		HttpServletRequest req = (HttpServletRequest)request;
		HttpServletResponse res=(HttpServletResponse)response;
		EnhenceRequest enhenceRequest  = new EnhenceRequest(req);
		chain.doFilter(enhenceRequest, response);
		
		
	}
		class EnhenceRequest extends HttpServletRequestWrapper{
		private HttpServletRequest request;
		public EnhenceRequest(HttpServletRequest request) {
			super(request);
			this.request=request;
		}
		public String getParameter(String name) {
				String parameter = request.getParameter(name);
				if(parameter!=null){
				try {
						parameter = new String(parameter.getBytes("iso-8859-1"),"utf-8");
				} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
				}
				return parameter;
			}
				return null;
		}
	}
	public void init(FilterConfig fConfig) throws ServletException {
	}

}
