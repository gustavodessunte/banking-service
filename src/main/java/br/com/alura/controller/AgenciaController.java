package br.com.alura.controller;

import br.com.alura.domain.Agencia;
import br.com.alura.service.http.AgenciaService;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.UriInfo;
import org.jboss.resteasy.reactive.RestResponse;

import java.util.List;

@Path("/agencia")
public class AgenciaController {

    private final AgenciaService agenciaService;

    public AgenciaController(AgenciaService agenciaService) {
        this.agenciaService = agenciaService;
    }

    @POST
    public RestResponse<Void> cadastrar(Agencia agencia, @Context UriInfo uriInfo) {
        this.agenciaService.cadastrar(agencia);
        return RestResponse.created(uriInfo.getAbsolutePath());
    }

    @GET
    public RestResponse<List<Agencia>> buscarTodos() {
        var agencias = this.agenciaService.obterTodas();
        if (agencias.isEmpty()) {
            return RestResponse.notFound();
        }

        return RestResponse.ok(agencias);
    }

    @GET
    @Path("obter-por-id/{id}")
    public RestResponse<Agencia> buscarPorId(@PathParam("id") int id) {
        try{
            if (id <= 0) {
                return RestResponse.status(400, "Id não pode ser menor ou igual a zero!");
            }
            return RestResponse.ok(this.agenciaService.obterAgenciaPorId(id));
        }catch(Exception ex){
            return RestResponse.status(500, ex.getMessage());
        }

    }

    @GET
    @Path("obter-por-cnpj/{cnpj}")
    public RestResponse<Agencia> obterPorCnpj(@PathParam("cnpj") String cnpj) {
        if (cnpj == null || cnpj.isEmpty()) {
            return RestResponse.status(400, "CNPJ não pode ser nulo ou vazio");
        }

        var agencia = this.agenciaService.obterAgenciaPorCnpj(cnpj);
        return RestResponse.ok(agencia);
    }

    @DELETE
    @Path("{cnpj}")
    public RestResponse<Void> remover(@PathParam("cnpj") String cnpj) {
        if (cnpj == null || cnpj.isEmpty()) {
            return RestResponse.status(400, "CNPJ não pode ser nulo ou vazio");
        }

        this.agenciaService.deletarAgenciaPorCnpj(cnpj);
        return RestResponse.noContent();
    }

    @PATCH
    public RestResponse<Agencia> atualizar(Agencia agencia) {
        if(agencia == null || agencia.getId() <= 0) {
            return RestResponse.status(400, "Agência não deve ser nula e seu id deve ser maior que zero!");
        }

        this.agenciaService.atualizar(agencia);
        return RestResponse.ok(agencia);
    }
}
