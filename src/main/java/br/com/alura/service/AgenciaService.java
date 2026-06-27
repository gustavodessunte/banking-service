package br.com.alura.service;

import br.com.alura.domain.Agencia;
import br.com.alura.domain.dto.AgenciaDto;
import br.com.alura.domain.dto.mapper.AgenciaMapper;
import br.com.alura.domain.http.SituacaoCadastral;
import br.com.alura.exceptions.AgenciaNaoAtivaOuNaoEncontradaException;
import br.com.alura.exceptions.NoContentException;
import br.com.alura.exceptions.UnprocessableEntityException;
import br.com.alura.repository.AgenciaRepository;
import br.com.alura.service.http.SituacaoCadastralHttpService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.util.List;

@ApplicationScoped
public class AgenciaService {

    @RestClient
    private SituacaoCadastralHttpService situacaoCadastralHttpService;

    private final AgenciaRepository agenciaRepository;

    public AgenciaService(AgenciaRepository agenciaRepository) {
        this.agenciaRepository = agenciaRepository;
    }

    @Transactional
    public void cadastrar(AgenciaDto dto) {
        var agenciaHttp = situacaoCadastralHttpService.buscarPorCnpj(dto.getCnpj());

        if (agenciaHttp == null || !agenciaHttp.getSituacaoCadastral().equals(SituacaoCadastral.ATIVO)) {
            throw new AgenciaNaoAtivaOuNaoEncontradaException(dto.getNome());
        }

        agenciaRepository.persistAndFlush(AgenciaMapper.toEntity(dto));
    }

    public List<Agencia> obterTodas() {
        var agenciaList = agenciaRepository.listAll();

        if (agenciaList.isEmpty()) {
            throw new NoContentException("Nenhuma agencia foi encontrada");
        }

        return agenciaList;
    }

    public Agencia obterAgenciaPorId(Long id) {
        return agenciaRepository.findByIdOptional(id).orElseThrow(
                () -> new NoContentException("Nenhuma agencia foi encontrada")
        );
    }

    public Agencia obterAgenciaPorCnpj(String cnpj) {
        var agencia = agenciaRepository.findByCnpj(cnpj);

        if (agencia == null) {
            throw new NoContentException("Nenhuma agencia foi encontrada");
        }

        return agencia;
    }

    @Transactional
    public void deletarAgenciaPorCnpj(String cnpj) {
        if (agenciaRepository.deleteByCnpj(cnpj) <= 0) {
            throw new UnprocessableEntityException("Não possivel processar deletar");
        }
    }

    @Transactional
    public void atualizar(Agencia agencia) {
        if(!(agenciaRepository.update(agencia) > 0)){
            throw new UnprocessableEntityException("Nenhuma agencia foi atualizada");
        }
    }
}
