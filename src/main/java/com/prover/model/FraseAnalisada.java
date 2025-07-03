package com.prover.model;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "frases_analisadas")
public class FraseAnalisada implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "frase_original", nullable = false, length = 1000)
    private String fraseOriginal;
    
    @Column(name = "total_palavras_distintas", nullable = false)
    private Integer totalPalavrasDistintas;
    
    @Column(name = "total_palavras", nullable = false)
    private Integer totalPalavras;
    
    @Column(name = "data_analise", nullable = false)
    private LocalDateTime dataAnalise;
    
    @OneToMany(mappedBy = "fraseAnalisada", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<PalavraAnalisada> palavrasAnalisadas;
    
    public FraseAnalisada() {
        this.dataAnalise = LocalDateTime.now();
    }
    
    public FraseAnalisada(String fraseOriginal, Integer totalPalavrasDistintas, Integer totalPalavras) {
        this();
        this.fraseOriginal = fraseOriginal;
        this.totalPalavrasDistintas = totalPalavrasDistintas;
        this.totalPalavras = totalPalavras;
    }
    
    // Getters e Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getFraseOriginal() {
        return fraseOriginal;
    }
    
    public void setFraseOriginal(String fraseOriginal) {
        this.fraseOriginal = fraseOriginal;
    }
    
    public Integer getTotalPalavrasDistintas() {
        return totalPalavrasDistintas;
    }
    
    public void setTotalPalavrasDistintas(Integer totalPalavrasDistintas) {
        this.totalPalavrasDistintas = totalPalavrasDistintas;
    }
    
    public Integer getTotalPalavras() {
        return totalPalavras;
    }
    
    public void setTotalPalavras(Integer totalPalavras) {
        this.totalPalavras = totalPalavras;
    }
    
    public LocalDateTime getDataAnalise() {
        return dataAnalise;
    }
    
    public void setDataAnalise(LocalDateTime dataAnalise) {
        this.dataAnalise = dataAnalise;
    }
    
    public List<PalavraAnalisada> getPalavrasAnalisadas() {
        return palavrasAnalisadas;
    }
    
    public void setPalavrasAnalisadas(List<PalavraAnalisada> palavrasAnalisadas) {
        this.palavrasAnalisadas = palavrasAnalisadas;
    }
    
    @Override
    public String toString() {
        return "FraseAnalisada{" +
               "id=" + id +
               ", fraseOriginal='" + fraseOriginal + '\'' +
               ", totalPalavrasDistintas=" + totalPalavrasDistintas +
               ", totalPalavras=" + totalPalavras +
               ", dataAnalise=" + dataAnalise +
               '}';
    }
} 