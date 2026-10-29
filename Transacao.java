package com.carteira.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * Registro imutável de uma movimentação (extrato).
 */
public class Transacao {

    private static final DateTimeFormatter FORMATO = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    private static final Locale PT_BR = Locale.forLanguageTag("pt-BR");

    private final TipoTransacao tipo;
    private final BigDecimal valor;
    private final String descricao;
    private final LocalDateTime dataHora;

    public Transacao(TipoTransacao tipo, BigDecimal valor, String descricao) {
        this.tipo = tipo;
        this.valor = valor;
        this.descricao = descricao;
        this.dataHora = LocalDateTime.now();
    }

    public TipoTransacao getTipo() {
        return tipo;
    }

    public BigDecimal getValor() {
        return valor;
    }

    @Override
    public String toString() {
        return String.format(PT_BR, "%s | %-14s | R$ %,10.2f | %s",
                dataHora.format(FORMATO), tipo.getDescricao(), valor, descricao);
    }
}