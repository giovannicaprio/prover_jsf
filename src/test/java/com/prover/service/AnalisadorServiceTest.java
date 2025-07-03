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

    @Test
    public void testAnalisarTextoComMultiplasLinhas() {
        String texto = "primeira linha\nsegunda linha\nprimeira linha";
        List<Palavra> resultado = analisadorService.analisarTexto(texto);
        assertEquals(3, resultado.size());
        
        assertTrue(resultado.stream().anyMatch(p -> p.getTexto().equals("primeira") && p.getOcorrencias() == 2));
        assertTrue(resultado.stream().anyMatch(p -> p.getTexto().equals("linha") && p.getOcorrencias() == 3));
        assertTrue(resultado.stream().anyMatch(p -> p.getTexto().equals("segunda") && p.getOcorrencias() == 1));
    }

    @Test
    public void testAnalisarTextoComEspacosExtras() {
        String texto = "  palavra   palavra     outra  ";
        List<Palavra> resultado = analisadorService.analisarTexto(texto);
        assertEquals(2, resultado.size());
        
        assertTrue(resultado.stream().anyMatch(p -> p.getTexto().equals("palavra") && p.getOcorrencias() == 2));
        assertTrue(resultado.stream().anyMatch(p -> p.getTexto().equals("outra") && p.getOcorrencias() == 1));
    }

    @Test
    public void testAnalisarTextoComCaracteresEspeciais() {
        String texto = "palavra-chave @email.com #hashtag";
        List<Palavra> resultado = analisadorService.analisarTexto(texto);
        assertEquals(3, resultado.size());
        
        assertTrue(resultado.stream().anyMatch(p -> p.getTexto().equals("palavra-chave")));
        assertTrue(resultado.stream().anyMatch(p -> p.getTexto().equals("email.com")));
        assertTrue(resultado.stream().anyMatch(p -> p.getTexto().equals("hashtag")));
    }

    @Test
    public void testAnalisarTextoComMaiusculasEMinusculas() {
        String texto = "Palavra PALAVRA palavra";
        List<Palavra> resultado = analisadorService.analisarTexto(texto);
        assertEquals(1, resultado.size());
        
        Palavra palavra = resultado.get(0);
        assertEquals("palavra", palavra.getTexto());
        assertEquals(3, palavra.getOcorrencias());
    }
} 