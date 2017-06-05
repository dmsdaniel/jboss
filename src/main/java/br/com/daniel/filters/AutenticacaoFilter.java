/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.daniel.filters;

import java.io.IOException;
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
 
import br.com.daniel.model.UsuarioModel;
 
@WebFilter("/mavenproject1-1.0-SNAPSHOT/*")
public class AutenticacaoFilter implements Filter {
 
    public AutenticacaoFilter() {
 
    }
 
    @Override
	public void destroy() {
 
	}
 
    @Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
 
		HttpSession httpSession 				= ((HttpServletRequest) request).getSession(); 
 
		HttpServletRequest httpServletRequest   = (HttpServletRequest) request;
 
		HttpServletResponse httpServletResponse = (HttpServletResponse) response;
 
		if(!httpServletRequest.getRequestURI().contains("index.xhtml")){
 
			UsuarioModel usuarioModel =(UsuarioModel) httpSession.getAttribute("usuarioAutenticado");
 
			if(usuarioModel == null){
 
				httpServletResponse.sendRedirect(httpServletRequest.getContextPath()+ "/index.xhtml");
 
			}
			else{
 
				chain.doFilter(request, response);
			}
		}		
		else{
 
			chain.doFilter(request, response);
		}
	}
 
    /**
     *
     * @param fConfig
     * @throws ServletException
     */
    @Override
	public void init(FilterConfig fConfig) throws ServletException {
 
	}
 
}