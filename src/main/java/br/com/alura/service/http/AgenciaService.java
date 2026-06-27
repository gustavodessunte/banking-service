package br.com.alura.service.http;

import br.com.alura.domain.Agencia;
import br.com.alura.domain.http.SituacaoCadastral;
import br.com.alura.exceptions.AgenciaNaoAtivaOuNaoEncontradaException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.POST;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class AgenciaService {

    @RestClient
    private SituacaoCadastralHttpService situacaoCadastralHttpService;

    private final List<Agencia> agencias = new ArrayList<>();

    public void cadastrar(Agencia agencia) {
        var agenciaHttp = situacaoCadastralHttpService.buscarPorCnpj(agencia.getCnpj());

        if (agenciaHttp == null || !agenciaHttp.getSituacaoCadastral().equals(SituacaoCadastral.ATIVO)) {
            throw new AgenciaNaoAtivaOuNaoEncontradaException(agencia.getNome());
        }

        agencias.add(agencia);
    }

    public List<Agencia> obterTodas() {
        return agencias;
    }

    public Agencia obterAgenciaPorId(int id) {
        return agencias.stream().filter(agencia ->
                agencia.getId().equals(id)).findFirst().orElseThrow(
                () -> new AgenciaNaoAtivaOuNaoEncontradaException(String.format("Agencia com id %d", id)));
    }

    public Agencia obterAgenciaPorCnpj(String cnpj) {
        return agencias.stream().filter(agencia ->
                agencia.getCnpj().equals(cnpj)).findFirst().orElseThrow(
                () -> new AgenciaNaoAtivaOuNaoEncontradaException(String.format("Agencia com CNPJ %S", cnpj)));
    }

    public void deletarAgenciaPorCnpj(String cnpj) {
        agencias.removeIf(agencia -> agencia.getCnpj().equals(cnpj));
    }

    public void atualizar(Agencia agencia) {
        var agenciaOriginal = agencias.stream().filter(ag -> ag.getId().equals(agencia.getId())).findFirst()
                .orElseThrow(
                        () -> new AgenciaNaoAtivaOuNaoEncontradaException("Agencia com id " + agencia.getId()));

        alterarInformacoesAgencias(agenciaOriginal, agencia);
    }



    private void alterarInformacoesAgencias(Agencia agenciaOriginal, Agencia agenciaNova){
        if(!agenciaNova.getCnpj().equals(agenciaOriginal.getCnpj())) {agenciaOriginal.setCnpj(agenciaNova.getCnpj());}
        if(!agenciaNova.getNome().equals(agenciaOriginal.getNome())) {agenciaOriginal.setNome(agenciaNova.getNome());}
        if(!agenciaNova.getEndereco().equals(agenciaOriginal.getEndereco())) {agenciaOriginal.setEndereco(agenciaNova.getEndereco());}
        if(!agenciaNova.getRazaoSocial().equals(agenciaOriginal.getRazaoSocial())) {agenciaOriginal.setRazaoSocial(agenciaNova.getRazaoSocial());}
    }
}
