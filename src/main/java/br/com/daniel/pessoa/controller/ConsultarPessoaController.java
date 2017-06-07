/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.daniel.pessoa.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Produces;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import br.com.daniel.model.PessoaModel;
import br.com.daniel.repository.FiltroPessoa;
import br.com.daniel.repository.PessoaRepository;
import br.com.daniel.repository.Pessoas;
import java.util.Map;

@Named(value = "consultarPessoaController")
@ViewScoped
public class ConsultarPessoaController implements Serializable {

    private static final long serialVersionUID = 1L;
    @Inject
    transient  Pessoas pessoaslazy;

    @Inject
    transient private PessoaModel pessoaModel;

    @Produces
    private List<PessoaModel> pessoas;
    
    private FiltroPessoa filtro = new FiltroPessoa();
    private LazyDataModel<PessoaModel> model;

    @Inject
    transient private PessoaRepository pessoaRepository;

    public List<PessoaModel> getPessoas() {
        return pessoas;
    }

    public void setPessoas(List<PessoaModel> pessoas) {
        this.pessoas = pessoas;
    }

    public PessoaModel getPessoaModel() {
        return pessoaModel;
    }

    public void setPessoaModel(PessoaModel pessoaModel) {
        this.pessoaModel = pessoaModel;
    }

    /**
     * *
     * CARREGA AS PESSOAS NA INICIALIZAÇÃO
     */
    @PostConstruct
    public void init() {

        //RETORNAR AS PESSOAS CADASTRADAS
        this.pessoas = pessoaRepository.GetPessoas();
    }

    /**
     * *
     * CARREGA INFORMAÇÕES DE UMA PESSOA PARA SER EDITADA
     *
     * @param pessoaModel
     */
    public void Editar(PessoaModel pessoaModel) {

        /*PEGA APENAS A PRIMEIRA LETRA DO SEXO PARA SETAR NO CAMPO(M OU F)*/
        pessoaModel.setSexo(pessoaModel.getSexo().substring(0, 1));

        this.pessoaModel = pessoaModel;

    }

    /**
     * *
     * ATUALIZA O REGISTRO QUE FOI ALTERADO
     */
    public void AlterarRegistro() {

        this.pessoaRepository.AlterarRegistro(this.pessoaModel);

        /*RECARREGA OS REGISTROS*/
        this.init();
    }

    /**
     * *
     * EXCLUINDO UM REGISTRO
     *
     * @param pessoaModel
     */
    public void ExcluirPessoa(PessoaModel pessoaModel) {

        //EXCLUI A PESSOA DO BANCO DE DADOS
        this.pessoaRepository.ExcluirRegistro(pessoaModel.getCodigo());

        //REMOVENDO A PESSOA DA LISTA
        //ASSIM QUE É A PESSOA É REMOVIDA DA LISTA O DATATABLE É ATUALIZADO
        this.pessoas.remove(pessoaModel);

    }
    public ConsultarPessoaController() {
		model = new LazyDataModel<PessoaModel>() {

			private static final long serialVersionUID = 1L;
			
			@Override
			public List<PessoaModel> load(int first, int pageSize,
					String sortField, SortOrder sortOrder,
					Map<String, Object> filters) {
				
				filtro.setPrimeiroRegistro(first);
				filtro.setQuantidadeRegistros(pageSize);
				filtro.setAscendente(SortOrder.ASCENDING.equals(sortOrder));
				filtro.setPropriedadeOrdenacao(sortField);
				
				setRowCount(pessoaslazy.quantidadeFiltrados(filtro));
				
				return pessoaslazy.filtrados(filtro);
			}
			
		};
	}
	
	public FiltroPessoa getFiltro() {
		return filtro;
	}

	public LazyDataModel<PessoaModel> getModel() {
		return model;
	}
}
