package com.prover.web.bean;

import com.prover.model.FraseAnalisada;
import com.prover.model.Palavra;
import com.prover.service.AnalisadorService;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;

@Named
@ViewScoped
public class AnalisadorFraseBean implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Inject
    private AnalisadorService analisadorService;
    
    private String frase;
    private List<Palavra> palavras;
    private int totalPalavrasDistintas;
    private int totalPalavras;
    private boolean resultadoVisivel;
    private boolean processando;
    private List<FraseAnalisada> historico;
    private FraseAnalisada ultimaAnalise;
    private Long analiseParaRemover;
    
    public void analisarFrase() {
        try {
            processando = true;
            
            if (frase == null || frase.trim().isEmpty()) {
                addMessage(FacesMessage.SEVERITY_WARN, 
                    "Atenção", "Por favor, digite uma frase para análise.");
                limparResultados();
                return;
            }
            
            // Analisa e salva no banco
            ultimaAnalise = analisadorService.analisarESalvarFrase(frase);
            
            if (ultimaAnalise != null) {
                // Converte para o formato da interface
                palavras = ultimaAnalise.getPalavrasAnalisadas().stream()
                        .map(p -> new Palavra(p.getTexto(), p.getOcorrencias()))
                        .collect(java.util.stream.Collectors.toList());
                
                totalPalavrasDistintas = ultimaAnalise.getTotalPalavrasDistintas();
                totalPalavras = ultimaAnalise.getTotalPalavras();
                resultadoVisivel = true;
                
                // Atualiza o histórico
                carregarHistorico();
                
                // Mensagem de sucesso
                addMessage(FacesMessage.SEVERITY_INFO, 
                    "Sucesso", "Frase analisada com sucesso!");
            } else {
                addMessage(FacesMessage.SEVERITY_ERROR, 
                    "Erro", "Não foi possível analisar a frase. Tente novamente.");
                limparResultados();
            }
        } catch (Exception e) {
            addMessage(FacesMessage.SEVERITY_ERROR, 
                "Erro", "Ocorreu um erro durante a análise. Tente novamente.");
            limparResultados();
        } finally {
            processando = false;
        }
    }
    
    public void carregarHistorico() {
        try {
            historico = analisadorService.buscarHistorico();
            addMessage(FacesMessage.SEVERITY_INFO, 
                "Sucesso", "Histórico atualizado com sucesso!");
        } catch (Exception e) {
            addMessage(FacesMessage.SEVERITY_ERROR, 
                "Erro", "Erro ao carregar o histórico. Tente novamente.");
        }
    }
    
    public void removerAnalise(Long id) {
        analisadorService.removerAnalise(id);
        carregarHistorico();
    }
    
    private void limparResultados() {
        palavras = Collections.emptyList();
        totalPalavrasDistintas = 0;
        totalPalavras = 0;
        resultadoVisivel = false;
        ultimaAnalise = null;
    }
    
    public List<Palavra> getOcorrenciasOrdenadas() {
        return getPalavras(); // O service já retorna ordenado
    }
    
    // Getters e Setters
    public String getFrase() {
        return frase;
    }
    
    public void setFrase(String frase) {
        this.frase = frase;
    }
    
    public List<Palavra> getPalavras() {
        return palavras != null ? palavras : Collections.emptyList();
    }
    
    public int getTotalPalavrasDistintas() {
        return totalPalavrasDistintas;
    }
    
    public int getTotalPalavras() {
        return totalPalavras;
    }
    
    public boolean isResultadoVisivel() {
        return resultadoVisivel;
    }
    
    public boolean isProcessando() {
        return processando;
    }
    
    public List<FraseAnalisada> getHistorico() {
        if (historico == null) {
            carregarHistorico();
        }
        return historico;
    }
    
    public void setHistorico(List<FraseAnalisada> historico) {
        this.historico = historico;
    }
    
    public FraseAnalisada getUltimaAnalise() {
        return ultimaAnalise;
    }
    
    public void setUltimaAnalise(FraseAnalisada ultimaAnalise) {
        this.ultimaAnalise = ultimaAnalise;
    }
    
    public long getTotalAnalises() {
        return analisadorService.contarTotalAnalises();
    }
    
    public Long getAnaliseParaRemover() {
        return analiseParaRemover;
    }
    
    public void setAnaliseParaRemover(Long analiseParaRemover) {
        this.analiseParaRemover = analiseParaRemover;
    }
    
    /**
     * Método helper para adicionar mensagens ao FacesContext
     * Verifica se o FacesContext está disponível antes de usar
     */
    private void addMessage(FacesMessage.Severity severity, String summary, String detail) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        if (facesContext != null) {
            facesContext.addMessage(null, new FacesMessage(severity, summary, detail));
        }
    }
} 