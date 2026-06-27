package br.com.alura.domain.dto.mapper;

import br.com.alura.domain.Endereco;
import br.com.alura.domain.dto.EnderecoDto;

public class EnderecoMapper {
    public static Endereco toEntity(EnderecoDto enderecoDto) {
        var endereco = new Endereco();

        endereco.complemento  = enderecoDto.getComplemento();
        endereco.rua =  enderecoDto.getRua();
        endereco.numero = enderecoDto.getNumero();
        endereco.logradouro = enderecoDto.getLogradouro();

        return endereco;
    }
}
