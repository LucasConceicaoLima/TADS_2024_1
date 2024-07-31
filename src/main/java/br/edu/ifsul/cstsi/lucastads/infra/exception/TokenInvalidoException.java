package br.edu.ifsul.cstsi.lucastads.infra.exception;

public class TokenInvalidoException extends RuntimeException{
    public TokenInvalidoException(String mensagem) {
        super(mensagem);
    }
}
