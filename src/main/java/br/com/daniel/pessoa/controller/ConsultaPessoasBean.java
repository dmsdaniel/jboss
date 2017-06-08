package br.com.daniel.pessoa.controller;

import br.com.daniel.model.PessoaModel;
import br.com.daniel.repository.FiltroPessoa;
import br.com.daniel.repository.Pessoas;
import br.com.daniel.repository.entity.PessoaEntity;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
@Named(value = "consultarPessoasBean")
@ViewScoped
public class ConsultaPessoasBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private Pessoas pessoas;
	
	private FiltroPessoa filtro = new FiltroPessoa();
	private LazyDataModel<PessoaEntity> model;
	
	public ConsultaPessoasBean() {
		model = new LazyDataModel<PessoaEntity>() {

			private static final long serialVersionUID = 1L;
			
			@Override
			public List<PessoaEntity> load(int first, int pageSize,
					String sortField, SortOrder sortOrder,
					Map<String, Object> filters) {
				
				filtro.setPrimeiroRegistro(first);
				filtro.setQuantidadeRegistros(pageSize);
				filtro.setAscendente(SortOrder.ASCENDING.equals(sortOrder));
				filtro.setPropriedadeOrdenacao(sortField);
				
				setRowCount(pessoas.quantidadeFiltrados(filtro));
				
				return pessoas.filtrados(filtro);
			}
			
		};
	}
	
	public FiltroPessoa getFiltro() {
		return filtro;
	}

	public LazyDataModel<PessoaEntity> getModel() {
		return model;
	}

}