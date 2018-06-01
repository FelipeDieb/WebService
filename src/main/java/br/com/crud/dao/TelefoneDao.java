package br.com.crud.dao;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import br.com.crud.modelo.Telefone;

public class TelefoneDao {

	private final EntityManager em;
	
	@Inject
	public TelefoneDao(EntityManager em){
		this.em = em;
	}
	
	public TelefoneDao(){
		this(null);
	}
	
	public void salvar(Telefone Telefone) throws Exception{
		em.persist(Telefone);
	}
	public void salvarListaTelefone(List<Telefone> lista) throws Exception{
		for(int i=0;i<lista.size();i++){
			salvar(lista.get(i));
		}
	}
	
	public void merge(Telefone Telefone) throws Exception{
		em.merge(Telefone);
	}
	
	public void atualizarLista(List<Telefone> lista) throws Exception{
		for(int i=0;i<lista.size();i++){
			merge(lista.get(i));
		}
	}
	
	public void remove(Telefone Telefone) {
		em.remove(busca(Telefone));
	}
	
	public void removeLista(List<Telefone> lista){
		for(int i=0;i<lista.size();i++){
			remove(lista.get(i));
		}
	}

	public Telefone busca(Telefone Telefone) {
		return em.find(Telefone.class, Telefone.getCod());
	}

	public List<Telefone> listarTelefones(){
		return em.createQuery("select t from telefones t",Telefone.class).getResultList();
	}
	
	
}
