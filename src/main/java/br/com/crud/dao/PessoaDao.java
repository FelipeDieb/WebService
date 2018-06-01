package br.com.crud.dao;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import br.com.crud.modelo.Pessoa;

public class PessoaDao {

	private final EntityManager em;
	
	@Inject
	public PessoaDao(EntityManager em){
		this.em = em;
	}
	
	public PessoaDao(){
		this(null);
	}
	
	public void salvar(Pessoa Pessoa) throws Exception{
		em.persist(Pessoa);
	}
	
	public void merge(Pessoa Pessoa) throws Exception{
		em.merge(Pessoa);
	}
	
	public void remove(Pessoa Pessoa) {
		em.remove(busca(Pessoa));
	}

	public Pessoa busca(Pessoa Pessoa) {
		return em.find(Pessoa.class, Pessoa.getCod());
	}

	public List<Pessoa> listarPessoas(){
		return em.createQuery("select p from pessoas p",Pessoa.class).getResultList();
	}
	
	
}
