package com.prover.bean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

@ManagedBean
@ViewScoped
public class AnalisadorFraseBean implements Serializable {
    
    private String frase;
    private Map<String, Integer> ocorrencias;
    private int totalPalavrasDistintas;
    private boolean resultadoVisivel;
    
    public void analisarFrase() {
        if (frase == null || frase.trim().isEmpty()) {
            ocorrencias = new HashMap<>();
            totalPalavrasDistintas = 0;
            resultadoVisivel = false;
            return;
        }
        
        // Divide a frase em palavras e remove pontuação
        String[] palavras = frase.toLowerCase()
                .replaceAll("[^a-záàâãéèêíïóôõöúçñ\\s]", "")
                .split("\\s+");
        
        // Conta as ocorrências de cada palavra
        ocorrencias = Arrays.stream(palavras)
                .filter(p -> !p.isEmpty())
                .collect(Collectors.groupingBy(
                        p -> p,
                        LinkedHashMap::new,
                        Collectors.collectingAndThen(Collectors.counting(), Long::intValue)
                ));
        
        totalPalavrasDistintas = ocorrencias.size();
        resultadoVisivel = true;
    }
    
    public List<Map.Entry<String, Integer>> getOcorrenciasOrdenadas() {
        if (ocorrencias == null) return new ArrayList<>();
        
        return ocorrencias.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed()
                        .thenComparing(Map.Entry.comparingByKey()))
                .collect(Collectors.toList());
    }
    
    // Getters e Setters
    public String getFrase() {
        return frase;
    }
    
    public void setFrase(String frase) {
        this.frase = frase;
    }
    
    public Map<String, Integer> getOcorrencias() {
        return ocorrencias;
    }
    
    public int getTotalPalavrasDistintas() {
        return totalPalavrasDistintas;
    }
    
    public boolean isResultadoVisivel() {
        return resultadoVisivel;
    }
} 