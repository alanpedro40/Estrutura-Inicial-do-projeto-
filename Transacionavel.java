package com.carteira.model;

import java.math.BigDecimal;

/**
 * Contrato para qualquer objeto que movimenta dinheiro (interface).
 */
public interface Transacionavel {

    void depositar(BigDecimal valor);

    void sacar(BigDecimal valor);

    BigDecimal getSaldo();
}