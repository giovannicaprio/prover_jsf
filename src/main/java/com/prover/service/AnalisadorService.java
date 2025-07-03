package com.prover.service;

import com.prover.dao.FraseAnalisadaDAO;
import com.prover.model.FraseAnalisada;
import com.prover.model.Palavra;
import com.prover.model.PalavraAnalisada;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@ApplicationScoped
public class AnalisadorService {
    
    @Inject
    private FraseAnalisadaDAO fraseAnalisadaDAO;
    
    public synchronized List<Palavra> analisarTexto(String texto) {
        if (texto == null || texto.trim().isEmpty()) {
            return Collections.emptyList();
        }

        // Normaliza e divide o texto em palavras
        String[] palavras = normalizarTexto(texto);
        
        // Conta ocorrências e cria objetos Palavra
        Map<String, Long> ocorrencias = Arrays.stream(palavras)
                .filter(p -> !p.isEmpty())
                .collect(Collectors.groupingBy(
                        p -> p,
                        Collectors.counting()
                ));
        
        // Converte para lista de Palavra e ordena
        return ocorrencias.entrySet().stream()
                .map(entry -> new Palavra(entry.getKey(), entry.getValue().intValue()))
                .sorted(Comparator.<Palavra>comparingInt(Palavra::getOcorrencias).reversed()
                        .thenComparing(Palavra::getTexto))
                .collect(Collectors.toList());
    }
    
    @Transactional
    public FraseAnalisada analisarESalvarFrase(String texto) {
        if (texto == null || texto.trim().isEmpty()) {
            return null;
        }
        
        // Analisa o texto
        List<Palavra> palavras = analisarTexto(texto);
        int totalPalavrasDistintas = palavras.size();
        int totalPalavras = palavras.stream().mapToInt(Palavra::getOcorrencias).sum();
        
        // Cria a entidade FraseAnalisada
        FraseAnalisada fraseAnalisada = new FraseAnalisada(texto, totalPalavrasDistintas, totalPalavras);
        
        // Converte as palavras para PalavraAnalisada e associa à frase
        List<PalavraAnalisada> palavrasAnalisadas = palavras.stream()
                .map(palavra -> new PalavraAnalisada(palavra.getTexto(), palavra.getOcorrencias(), fraseAnalisada))
                .collect(Collectors.toList());
        
        fraseAnalisada.setPalavrasAnalisadas(palavrasAnalisadas);
        
        // Salva no banco de dados
        fraseAnalisadaDAO.salvar(fraseAnalisada);
        
        return fraseAnalisada;
    }
    
    public List<FraseAnalisada> buscarHistorico() {
        return fraseAnalisadaDAO.buscarTodasOrdenadasPorData();
    }
    
    public List<FraseAnalisada> buscarUltimasAnalises(int quantidade) {
        return fraseAnalisadaDAO.buscarUltimas(quantidade);
    }
    
    public FraseAnalisada buscarAnalisePorId(Long id) {
        return fraseAnalisadaDAO.buscarPorId(id);
    }
    
    public void removerAnalise(Long id) {
        fraseAnalisadaDAO.removerPorId(id);
    }
    
    public long contarTotalAnalises() {
        return fraseAnalisadaDAO.contarTotal();
    }
    
    private String[] normalizarTexto(String texto) {
        // Primeiro converte para minúsculas
        texto = texto.toLowerCase();
        
        // Divide em palavras mantendo caracteres especiais
        String[] palavras = texto.split("\\s+");
        
        // Remove pontuação exceto caracteres especiais permitidos
        for (int i = 0; i < palavras.length; i++) {
            // Remove @ do início da palavra
            if (palavras[i].startsWith("@")) {
                palavras[i] = palavras[i].substring(1);
            }
            
            // Remove # do início da palavra
            if (palavras[i].startsWith("#")) {
                palavras[i] = palavras[i].substring(1);
            }
            
            // Mantém hífen e ponto, remove outras pontuações
            palavras[i] = palavras[i].replaceAll("[^a-záéíóúâêîôûãõàèìòùäëïöüç\\-\\.]", "");
        }
        
        return palavras;
    }
    
    public int contarPalavrasDistintas(List<Palavra> palavras) {
        return palavras.size();
    }
} 