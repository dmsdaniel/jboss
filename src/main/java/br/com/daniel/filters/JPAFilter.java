/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.daniel.filters;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.naming.NamingException;
 
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.transaction.UserTransaction;
 
/***
 * ESSE FILTER VAI SER CHAMADO TODA VEZ QUE FOR REALIZADO 
 * UMA REQUISIÇÃO PARA O FACES SERVLET.
 * */
@WebFilter(servletNames ={ "Faces Servlet" })
public class JPAFilter implements Filter {
 
 
	private EntityManagerFactory entityManagerFactory;
 
	private final String persistence_unit_name = "unit_app";
 
    public JPAFilter() {
 
    }
 
        @Override
	public void destroy() {
 
		this.entityManagerFactory.close();
	}
 
        @Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
 
		/*CRIANDO UM ENTITYMANAGER*/
		EntityManager entityManager =  this.entityManagerFactory.createEntityManager();
 
		/*ADICIONANDO ELE NA REQUISIÇÃO*/
		request.setAttribute("entityManager", entityManager);
 
		/*INICIANDO UMA TRANSAÇÃO*/
		/*entityManager.getTransaction().begin();  */

		/*INICIANDO FACES SERVLET*/
		chain.doFilter(request, response);
 
		try {
 
			/*SE NÃO TIVER ERRO NA OPERAÇÃO ELE EXECUTA O COMMIT*/

			/*entityManager.getTransaction().commit(); */
 
		} catch (Exception e) {
 
			/*SE TIVER ERRO NA OPERAÇÃO É EXECUTADO O rollback*/
			/*entityManager.getTransaction().rollback();*/

		}
		finally{
 
			/*DEPOIS DE DAR O COMMIT OU ROLLBACK ELE FINALIZA O entityManager*/
			entityManager.close();
		}
	}
 
        @Override
	public void init(FilterConfig fConfig) throws ServletException {
 
		/*CRIA O entityManagerFactory COM OS PARÂMETROS DEFINIDOS NO persistence.xml*/
		this.entityManagerFactory = Persistence.createEntityManagerFactory(this.persistence_unit_name); 
	}
 
}
