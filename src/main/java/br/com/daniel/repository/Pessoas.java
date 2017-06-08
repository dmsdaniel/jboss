package br.com.daniel.repository;

import br.com.daniel.model.PessoaModel;
import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import br.com.daniel.repository.entity.PessoaEntity;
import br.com.daniel.uteis.Uteis;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.apache.commons.lang3.StringUtils;

public class Pessoas implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    PessoaEntity pessoaEntity;
    private EntityManager manager;

    @SuppressWarnings("unchecked")
    public List<PessoaEntity> filtrados(FiltroPessoa filtro) {

        /*Criteria criteria = criarCriteriaParaFiltro(filtro); */
        Query criteria = criarCriteriaParaFiltro(filtro);

        criteria.setFirstResult(filtro.getPrimeiroRegistro());
        /*criteria.getMaxResults(filtro.getQuantidadeRegistros());*/

        if (filtro.isAscendente() && filtro.getPropriedadeOrdenacao() != null) {
            /*criteria.addOrder(Order.asc(filtro.getPropriedadeOrdenacao()));*/
        } else if (filtro.getPropriedadeOrdenacao() != null) {
            /*criteria.addOrder(Order.desc(filtro.getPropriedadeOrdenacao()));*/
        }

        return criteria.getResultList();
    }

    public int quantidadeFiltrados(FiltroPessoa filtro) {
        Query criteria = criarCriteriaParaFiltro(filtro);

        /*criteria.setProjection(Projections.rowCount());*/
        criteria.getMaxResults();
        /*return ((Number) criteria.uniqueResult()).intValue();*/
        return criteria.getMaxResults();
    }

    private Query criarCriteriaParaFiltro(FiltroPessoa filtro) {
        manager = Uteis.JpaEntityManager();
// Query for a List of objects.
        CriteriaBuilder cb = manager.getCriteriaBuilder();
        CriteriaQuery<PessoaEntity> cq = cb.createQuery(PessoaEntity.class);
        Root pessoas = cq.from(PessoaEntity.class);
        Query query = manager.createQuery(cq);            
        if (StringUtils.isNotEmpty(filtro.getNome())) {
        } else {
            query = manager.createQuery(cq);
        }
        return query;
    }
}
