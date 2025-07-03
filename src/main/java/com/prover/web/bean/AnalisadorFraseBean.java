package com.prover.web.bean;

import com.prover.model.Palavra;
import com.prover.service.AnalisadorService;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Comparator;
import java.util.ArrayList;

@Named
@ViewScoped
public class AnalisadorFraseBean implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Inject
    private AnalisadorService analisadorService;
    
    private String frase;
    private List<Palavra> palavras;
    private int totalPalavrasDistintas;
    private boolean resultadoVisivel;
    private boolean processando;
    
    public void analisarFrase() {
        try {
            processando = true;
            
            if (frase == null || frase.trim().isEmpty()) {
                limparResultados();
                return;
            }
            
            palavras = analisadorService.analisarTexto(frase);
            totalPalavrasDistintas = palavras.size();
            resultadoVisivel = true;
        } finally {
            processando = false;
        }
    }
    
    private void limparResultados() {
        palavras = Collections.emptyList();
        totalPalavrasDistintas = 0;
        resultadoVisivel = false;
    }
    
    public String getFrase() {
        return frase;
    }
    
    public void setFrase(String frase) {
        this.frase = frase;
    }
    
    public List<Palavra> getPalavras() {
        return palavras != null ? palavras : Collections.emptyList();
    }
    
    public List<Palavra> getOcorrenciasOrdenadas() {
        if (palavras == null) {
            return Collections.emptyList();
        }
        
        List<Palavra> ordenadas = new ArrayList<>(palavras);
        ordenadas.sort((p1, p2) -> p2.getOcorrencias() - p1.getOcorrencias());
        return ordenadas;
    }
    
    public int getTotalPalavrasDistintas() {
        return totalPalavrasDistintas;
    }
    
    public boolean isResultadoVisivel() {
        return resultadoVisivel;
    }
    
    public boolean isProcessando() {
        return processando;
    }
} 