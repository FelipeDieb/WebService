package br.com.crud.webservice;

import javax.inject.Inject;
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
import javax.ws.rs.core.Response;

import com.google.gson.Gson;

import br.com.crud.dao.PessoaDao;
import br.com.crud.dao.TelefoneDao;
import br.com.crud.modelo.Pessoa;

@Path("WebService")
public class WebService  {

	@Inject
	private PessoaDao pessoaDao;
	
	@Inject
	private TelefoneDao telefoneDao;
	
	@PersistenceContext(unitName = "default", type = PersistenceContextType.EXTENDED)
	private EntityManager objEM ;

	
	@GET
	@Path("listar")
	@Produces("application/json")
	public String listar() {
		try {
			return new Gson().toJson(pessoaDao.listarPessoas()).toString();
		} catch (Exception e) {
			e.printStackTrace();
			throw new WebApplicationException(500);
		}
	}

	@POST
	@Path("/cadastrar")
	@Consumes("application/json")
	@Produces("application/json")
	public Response cadastrar( Pessoa pessoa) {
		try {
			telefoneDao.salvarListaTelefone(pessoa.getListaTelefone());
			pessoaDao.salvar(pessoa);
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
			telefoneDao.atualizarLista(pessoa.getListaTelefone());
			pessoaDao.merge(pessoa);
			return Response.status(200).entity("Alteração com sucesso.").build();
		} catch (Exception e) {
			throw new WebApplicationException(500);
		}

	}

	@DELETE
	@Path("/excluir/{codPessoa}")
	public Response excluir(@PathParam("codPessoa") Long codPessoa) {
		try {
			Pessoa pessoa = new Pessoa();
			pessoa.setCod(codPessoa);
			pessoa = pessoaDao.busca(pessoa);
			telefoneDao.removeLista(pessoa.getListaTelefone());
            pessoaDao.remove(pessoa);
			return Response.status(200).entity("Exclusão com sucesso.").build();
		} catch (Exception e) {
			throw new WebApplicationException(500);
		}
	}

}
