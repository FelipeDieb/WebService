package br.com.crud.webservice;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;

import br.com.crud.modelo.Pessoa;
import br.com.crud.modelo.Telefone;

@Path("/WebService/")
@ApplicationScoped
public class WebService  {

	@PersistenceContext(unitName = "default", type = PersistenceContextType.EXTENDED)
	private EntityManager objEM ;

	
	@GET
	@Path("/listar")
	@Produces("application/json")
	public String listar() {
		List<Pessoa> l  = new ArrayList<Pessoa>();
		System.out.println(objEM);
		Pessoa p = new Pessoa();
		p.setNome("Felipe Dieb");
		p.setEmail("felipe_dieb@hotmail.com");
		p.setCpf("057.449.643.20");
		p.setCod(new Long("1"));
		Telefone t =  new Telefone();
		t.setPessoa(p);
		t.setDdd("85");
		t.setNumero("312312312");
		List<Telefone> listaT = new ArrayList<Telefone>();
		p.setListaTelefone(listaT);
		Pessoa p2 = new Pessoa();
		p2.setNome("Chiara");
		l.add(p);
		l.add(p2);
		try {
			String query = "select c from Pessoa c";
			//List<Pessoa> clientes = objEM.createQuery(query, Pessoa.class).getResultList();
			//objEM.close();
			return new Gson().toJson(l).toString();
		} catch (Exception e) {
			e.printStackTrace();
			throw new WebApplicationException(500);
		}
	}

	@GET
	@Path("/buscar/{id_cliente}")
	@Produces("application/json")
	public Pessoa buscar(@PathParam("id_cliente") int id_cliente) {
		try {
			Pessoa cliente = objEM.find(Pessoa.class, id_cliente);
			objEM.close();
			return cliente;
		} catch (Exception e) {
			throw new WebApplicationException(500);
		}
	}

	@POST
	@Path("/cadastrar")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response cadastrar( @PathParam("pessoa") Pessoa pessoa) {
		try {
			objEM.getTransaction().begin();
			objEM.persist(pessoa);
			objEM.getTransaction().commit();
			objEM.close();
			return Response.status(200).entity("cadastro realizado.").build();
		} catch (Exception e) {
			throw new WebApplicationException(500);
		}
	}

	@PUT
	@Path("/alterar")
	@Consumes("application/json")
	public Response alterar(Pessoa pessoa) {
		try {
			objEM.getTransaction().begin();
			objEM.merge(pessoa);
			objEM.getTransaction().commit();
			objEM.close();
			return Response.status(200).entity("cadastro alterado.").build();
		} catch (Exception e) {
			throw new WebApplicationException(500);
		}

	}

	@DELETE
	@Path("/excluir/{id_cliente}")
	public Response excluir(@PathParam("id_cliente") int id_cliente) {
		try {
			Pessoa pessoa = objEM.find(Pessoa.class, id_cliente);

			objEM.getTransaction().begin();
			objEM.remove(pessoa);
			objEM.getTransaction().commit();
			objEM.close();

			return Response.status(200).entity("cadastro excluído.").build();
		} catch (Exception e) {
			throw new WebApplicationException(500);
		}
	}

}
