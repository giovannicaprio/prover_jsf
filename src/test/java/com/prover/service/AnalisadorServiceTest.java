package com.prover.service;

import com.prover.model.Palavra;
import org.junit.Before;
import org.junit.Test;
import java.util.List;
import static org.junit.Assert.*;

public class AnalisadorServiceTest {
    
    private AnalisadorService analisadorService;
    
    @Before
    public void setUp() {
        analisadorService = new AnalisadorService();
    }
    
    @Test
    public void testAnalisarTextoVazio() {
        List<Palavra> resultado = analisadorService.analisarTexto("");
        assertTrue(resultado.isEmpty());
    }
    
    @Test
    public void testAnalisarTextoNulo() {
        List<Palavra> resultado = analisadorService.analisarTexto(null);
        assertTrue(resultado.isEmpty());
    }
    
    @Test
    public void testAnalisarTextoSimples() {
        List<Palavra> resultado = analisadorService.analisarTexto("casa casa jardim");
        assertEquals(2, resultado.size());
        
        Palavra primeiraPalavra = resultado.get(0);
        assertEquals("casa", primeiraPalavra.getTexto());
        assertEquals(2, primeiraPalavra.getOcorrencias());
        
        Palavra segundaPalavra = resultado.get(1);
        assertEquals("jardim", segundaPalavra.getTexto());
        assertEquals(1, segundaPalavra.getOcorrencias());
    }
    
    @Test
    public void testAnalisarTextoComPontuacao() {
        List<Palavra> resultado = analisadorService.analisarTexto("casa, casa! jardim.");
        assertEquals(2, resultado.size());
        
        Palavra primeiraPalavra = resultado.get(0);
        assertEquals("casa", primeiraPalavra.getTexto());
        assertEquals(2, primeiraPalavra.getOcorrencias());
    }
    
    @Test
    public void testAnalisarTextoComAcentos() {
        List<Palavra> resultado = analisadorService.analisarTexto("café café árvore");
        assertEquals(2, resultado.size());
        
        Palavra primeiraPalavra = resultado.get(0);
        assertEquals("café", primeiraPalavra.getTexto());
        assertEquals(2, primeiraPalavra.getOcorrencias());
    }
    
    @Test
    public void testContarPalavrasDistintas() {
        List<Palavra> palavras = analisadorService.analisarTexto("casa casa jardim");
        int total = analisadorService.contarPalavrasDistintas(palavras);
        assertEquals(2, total);
    }
} 