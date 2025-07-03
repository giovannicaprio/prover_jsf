package com.prover.service;

import com.prover.model.Palavra;
import javax.enterprise.context.ApplicationScoped;
import java.util.*;
import java.util.stream.Collectors;

@ApplicationScoped
public class AnalisadorService {
    
    public List<Palavra> analisarTexto(String texto) {
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
    
    private String[] normalizarTexto(String texto) {
        return texto.toLowerCase()
                .replaceAll("[^a-záàâãéèêíïóôõöúçñ\\s]", "")
                .split("\\s+");
    }
    
    public int contarPalavrasDistintas(List<Palavra> palavras) {
        return palavras.size();
    }
} 