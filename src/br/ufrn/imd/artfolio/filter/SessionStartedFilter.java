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

//@TODO: Fix url Patterns
@WebFilter(urlPatterns = {"/artfolio/index.jsf", "/artfolio/signup.jsf"})
public class SessionStartedFilter implements Filter {

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		
		User sessionUser = (User) req.getSession().getAttribute("sessionUser");
		
		if (sessionUser != null) 
			res.sendRedirect("artfolio/dashboard/");
		else 
			chain.doFilter(request, response);
	}
}