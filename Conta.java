package com.carteira.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Classe base abstrata da hierarquia de contas.
 * O saldo e o histórico são privados: só mudam por meio dos métodos da classe.
 */
public abstract class Conta implements Transacionavel {

    private final String numero;
    private final Cliente titular;
    private BigDecimal saldo = BigDecimal.ZERO;
    private final List<Transacao> historico = new ArrayList<>();

    protected Conta(String numero, Cliente titular) {
        this.numero = numero;
        this.titular = titular;
    }

    // ---------- leitura (getters) ----------

    public String getNumero() {
        return numero;
    }

    public Cliente getTitular() {
        return titular;
    }

    @Override
    public BigDecimal getSaldo() {
        return saldo;
    }

    /** Quanto o cliente pode gastar agora. Subclasses podem ampliar (ex.: limite). */
    public BigDecimal getSaldoDisponivel() {
        return saldo;
    }

    public List<Transacao> getHistorico() {
        return Collections.unmodifiableList(historico);
    }

    /** Nome do produto, definido por cada tipo de conta (polimorfismo). */
    public abstract String getTipoConta();

    // ---------- operações públicas ----------

    @Override
    public void depositar(BigDecimal valor) {
        creditar(valor, TipoTransacao.DEPOSITO, "Depósito em conta");
    }

    @Override
    public void sacar(BigDecimal valor) {
        debitar(valor, TipoTransacao.SAQUE, "Saque");
    }

    public void pagar(BigDecimal valor, String descricao) {
        debitar(valor, TipoTransacao.PAGAMENTO, descricao);
    }

    public void enviarPix(Conta destino, BigDecimal valor) {
        if (destino == this) {
            throw new IllegalArgumentException("Não é possível enviar Pix para a própria conta.");
        }
        debitar(valor, TipoTransacao.PIX_ENVIADO, "Pix para " + destino.getTitular().getNome());
        destino.creditar(valor, TipoTransacao.PIX_RECEBIDO, "Pix de " + titular.getNome());
    }

    // ---------- operações internas (visíveis só para a hierarquia) ----------

    protected void creditar(BigDecimal valor, TipoTransacao tipo, String descricao) {
        validarValor(valor);
        saldo = saldo.add(valor);
        historico.add(new Transacao(tipo, valor, descricao));
    }

    protected void debitar(BigDecimal valor, TipoTransacao tipo, String descricao) {
        validarValor(valor);
        if (valor.compareTo(getSaldoDisponivel()) > 0) {
            throw new SaldoInsuficienteException("Saldo insuficiente na conta " + numero + ".");
        }
        saldo = saldo.subtract(valor);
        historico.add(new Transacao(tipo, valor, descricao));
    }

    private void validarValor(BigDecimal valor) {
        if (valor == null || valor.signum() <= 0) {
            throw new IllegalArgumentException("O valor deve ser maior que zero.");
        }
    }

    @Override
    public String toString() {
        return String.format("%s %s - %s", getTipoConta(), numero, titular.getNome());
    }
}