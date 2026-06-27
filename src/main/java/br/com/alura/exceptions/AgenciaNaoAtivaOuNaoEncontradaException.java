package br.com.alura.exceptions;

public class AgenciaNaoAtivaOuNaoEncontradaException extends RuntimeException {
    public AgenciaNaoAtivaOuNaoEncontradaException(String nomeAgencia) {
        super(String.format("%s não está ativa ou não foi encontrada", nomeAgencia));
    }
}
