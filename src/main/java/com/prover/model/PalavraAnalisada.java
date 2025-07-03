package com.prover.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "palavras_analisadas")
public class PalavraAnalisada implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "texto", nullable = false, length = 100)
    private String texto;
    
    @Column(name = "ocorrencias", nullable = false)
    private Integer ocorrencias;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "frase_analisada_id", nullable = false)
    private FraseAnalisada fraseAnalisada;
    
    public PalavraAnalisada() {
    }
    
    public PalavraAnalisada(String texto, Integer ocorrencias) {
        this.texto = texto;
        this.ocorrencias = ocorrencias;
    }
    
    public PalavraAnalisada(String texto, Integer ocorrencias, FraseAnalisada fraseAnalisada) {
        this(texto, ocorrencias);
        this.fraseAnalisada = fraseAnalisada;
    }
    
    // Getters e Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getTexto() {
        return texto;
    }
    
    public void setTexto(String texto) {
        this.texto = texto;
    }
    
    public Integer getOcorrencias() {
        return ocorrencias;
    }
    
    public void setOcorrencias(Integer ocorrencias) {
        this.ocorrencias = ocorrencias;
    }
    
    public FraseAnalisada getFraseAnalisada() {
        return fraseAnalisada;
    }
    
    public void setFraseAnalisada(FraseAnalisada fraseAnalisada) {
        this.fraseAnalisada = fraseAnalisada;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PalavraAnalisada that = (PalavraAnalisada) o;
        return texto != null ? texto.equals(that.texto) : that.texto == null;
    }
    
    @Override
    public int hashCode() {
        return texto != null ? texto.hashCode() : 0;
    }
    
    @Override
    public String toString() {
        return "PalavraAnalisada{" +
               "id=" + id +
               ", texto='" + texto + '\'' +
               ", ocorrencias=" + ocorrencias +
               '}';
    }
} 