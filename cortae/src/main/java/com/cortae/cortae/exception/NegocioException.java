package com.cortae.cortae.exception;

// Exceção específica para erros de REGRA DE NEGÓCIO (esperados, com mensagem segura pro usuário)
// Diferente de um RuntimeException genérico, que pode esconder bugs de verdade
public class NegocioException extends RuntimeException {
    
    public NegocioException(String mensagem) {
        super(mensagem);
    }
}
