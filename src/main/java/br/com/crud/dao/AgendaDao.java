package br.com.crud.dao;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import br.com.caelum.vraptor.model.Agenda;
import br.com.caelum.vraptor.model.Igreja;
import br.com.caelum.vraptor.model.Membros;

public class AgendaDao {

	private final EntityManager em;
	
	@Inject
	public AgendaDao(EntityManager em){
		this.em = em;
	}
	
	public AgendaDao(){
		this(null);
	}
	
	public void salvar(Agenda agenda) throws Exception{
		em.persist(agenda);
	}
	
	public void merge(Agenda agenda) throws Exception{
		em.merge(agenda);
	}
	
	public List<Agenda> getAgenda(Membros membroLogado){
		return em.createQuery("select a from Agenda a where (a.igreja = :igreja or 'MASTER' = :tipoAcesso)",Agenda.class).setParameter("igreja", membroLogado.getIgreja()).setParameter("tipoAcesso", membroLogado.getTipoAcesso()).getResultList();
	}
	
	public void remove(Agenda agenda) {
		em.remove(busca(agenda));
	}

	public Agenda busca(Agenda agenda) {
		return em.find(Agenda.class, agenda.getId());
	}
	
	public List<Agenda> eventosParaApp(){
		return em.createQuery("select a from Agenda a where "
				+ "cast(inicio as date)  between (current_date-30) and (current_date) "
				+ " and visibleApp = :visible "
				+ "order by a.start desc ",Agenda.class).setParameter("visible", "S").getResultList();
	}
	
	public List<Agenda> eventosParaGraficos(Igreja igreja){
		return em.createQuery("select a from Agenda a where "
				+ "cast(inicio as date)  <= (current_date)"
				+ " and igreja = :igreja "
				+ "order by a.start desc ",Agenda.class).setParameter("igreja", igreja).getResultList();
	}
	
	
}
