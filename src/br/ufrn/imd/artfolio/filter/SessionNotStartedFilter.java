package br.ufrn.imd.artfolio.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.ufrn.imd.artfolio.model.User;

@WebFilter("/dashboard/*")
public class SessionNotStartedFilter implements Filter {

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		
		User sessionUser = (User) req.getSession().getAttribute("sessionUser");
		
		if (sessionUser == null) 
			res.sendRedirect("/artfolio/");
		else 
			chain.doFilter(request, response);
	}
}