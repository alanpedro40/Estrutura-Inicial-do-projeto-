package com.carteira.model;

/**
 * Titular das contas e do cartão. Os atributos são privados (encapsulamento)
 * e só podem ser lidos por meio de getters.
 */
public class Cliente {

    private final String nome;
    private final String cpf;

    public Cliente(String nome, String cpf) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("Nome é obrigatório.");
        }
        if (cpf == null || !cpf.matches("\\d{11}")) {
            throw new IllegalArgumentException("CPF deve ter 11 dígitos numéricos.");
        }
        this.nome = nome;
        this.cpf = cpf;
    }

    public String getNome() {
        return nome;
    }

    /** O CPF completo não é exposto: só a versão mascarada. */
    public String getCpfMascarado() {
        return "***." + cpf.substring(3, 6) + ".***-" + cpf.substring(9);
    }

    @Override
    public String toString() {
        return nome + " (CPF " + getCpfMascarado() + ")";
    }
}
