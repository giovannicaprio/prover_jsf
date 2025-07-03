package com.prover.model;

import java.io.Serializable;

public class Palavra implements Serializable {
    private String texto;
    private int ocorrencias;

    public Palavra() {
        this.texto = null;
        this.ocorrencias = 0;
    }

    public Palavra(String texto, int ocorrencias) {
        this.texto = texto;
        this.ocorrencias = ocorrencias;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public int getOcorrencias() {
        return ocorrencias;
    }

    public void setOcorrencias(int ocorrencias) {
        this.ocorrencias = ocorrencias;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Palavra palavra = (Palavra) o;
        return texto != null ? texto.equals(palavra.texto) : palavra.texto == null;
    }

    @Override
    public int hashCode() {
        return texto != null ? texto.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Palavra{" +
               "texto='" + texto + '\'' +
               ", ocorrencias=" + ocorrencias +
               '}';
    }
} 