/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.daniel.repository;


import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import br.com.daniel.model.PessoaModel;
import br.com.daniel.repository.entity.PessoaEntity;
import br.com.daniel.uteis.Uteis;

public class Pessoas implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
        PessoaEntity pessoaEntity;

	EntityManager manager;
	
	@SuppressWarnings("unchecked")
	public List<PessoaModel> filtrados(FiltroPessoa filtro) {
		Criteria criteria = criarCriteriaParaFiltro(filtro);
		
		criteria.setFirstResult(filtro.getPrimeiroRegistro());
		criteria.setMaxResults(filtro.getQuantidadeRegistros());
		
		if (filtro.isAscendente() && filtro.getPropriedadeOrdenacao() != null) {
			criteria.addOrder(Order.asc(filtro.getPropriedadeOrdenacao()));
		} else if (filtro.getPropriedadeOrdenacao() != null) {
			criteria.addOrder(Order.desc(filtro.getPropriedadeOrdenacao()));
		}
		
		return criteria.list();
	}
	
	public int quantidadeFiltrados(FiltroPessoa filtro) {
		Criteria criteria = criarCriteriaParaFiltro(filtro);
		
		criteria.setProjection(Projections.rowCount());
		
		return ((Number) criteria.uniqueResult()).intValue();
	}
	
	private Criteria criarCriteriaParaFiltro(FiltroPessoa filtro) {
                manager = Uteis.JpaEntityManager();
		/* Session session = manager.unwrap(Session.class); hibernate */
                java.sql.Connection connection = manager.unwrap(java.sql.Connection.class);
		Criteria criteria = connection.createCriteria(PessoaModel.class);
		
		if (StringUtils.isNotEmpty(filtro.getNome())) {
			criteria.add(Restrictions.ilike("nome", filtro.getNome(), MatchMode.ANYWHERE));
		}
		
		return criteria;
	}

}