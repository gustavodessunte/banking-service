package br.com.alura.domain.dto.mapper;

import br.com.alura.domain.Agencia;
import br.com.alura.domain.dto.AgenciaDto;

public class AgenciaMapper {

    public static Agencia toEntity(AgenciaDto dto) {
        var agencia = new Agencia();

        agencia.cnpj = dto.getCnpj();
        agencia.endereco = EnderecoMapper.toEntity(dto.getEnderecoDto());
        agencia.nome = dto.getNome();
        agencia.razaoSocial = dto.getRazaoSocial();

        return agencia;
    }
}
