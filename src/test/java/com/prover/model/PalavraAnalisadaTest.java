package com.prover.model;

import org.junit.Test;
import static org.junit.Assert.*;

public class PalavraAnalisadaTest {

    @Test
    public void testConstrutorPadrao() {
        PalavraAnalisada palavraAnalisada = new PalavraAnalisada();
        assertNotNull(palavraAnalisada);
        assertNull(palavraAnalisada.getTexto());
        assertNull(palavraAnalisada.getOcorrencias());
    }

    @Test
    public void testConstrutorComParametros() {
        String texto = "teste";
        Integer ocorrencias = 3;
        
        PalavraAnalisada palavraAnalisada = new PalavraAnalisada(texto, ocorrencias);
        
        assertEquals(texto, palavraAnalisada.getTexto());
        assertEquals(ocorrencias, palavraAnalisada.getOcorrencias());
    }

    @Test
    public void testConstrutorComFraseAnalisada() {
        String texto = "exemplo";
        Integer ocorrencias = 2;
        FraseAnalisada fraseAnalisada = new FraseAnalisada("frase teste", 2, 3);
        
        PalavraAnalisada palavraAnalisada = new PalavraAnalisada(texto, ocorrencias, fraseAnalisada);
        
        assertEquals(texto, palavraAnalisada.getTexto());
        assertEquals(ocorrencias, palavraAnalisada.getOcorrencias());
        assertEquals(fraseAnalisada, palavraAnalisada.getFraseAnalisada());
    }

    @Test
    public void testSettersEGetters() {
        PalavraAnalisada palavraAnalisada = new PalavraAnalisada();
        
        Long id = 1L;
        String texto = "palavra";
        Integer ocorrencias = 5;
        FraseAnalisada fraseAnalisada = new FraseAnalisada("frase", 1, 1);
        
        palavraAnalisada.setId(id);
        palavraAnalisada.setTexto(texto);
        palavraAnalisada.setOcorrencias(ocorrencias);
        palavraAnalisada.setFraseAnalisada(fraseAnalisada);
        
        assertEquals(id, palavraAnalisada.getId());
        assertEquals(texto, palavraAnalisada.getTexto());
        assertEquals(ocorrencias, palavraAnalisada.getOcorrencias());
        assertEquals(fraseAnalisada, palavraAnalisada.getFraseAnalisada());
    }

    @Test
    public void testEquals() {
        PalavraAnalisada palavra1 = new PalavraAnalisada("teste", 1);
        PalavraAnalisada palavra2 = new PalavraAnalisada("teste", 2);
        PalavraAnalisada palavra3 = new PalavraAnalisada("outro", 1);
        
        // Mesmo texto deve ser considerado igual, independente do número de ocorrências
        assertEquals(palavra1, palavra2);
        
        // Textos diferentes devem ser considerados diferentes
        assertNotEquals(palavra1, palavra3);
        
        // Comparação com null e outros tipos
        assertNotEquals(palavra1, null);
        assertNotEquals(palavra1, "teste");
    }

    @Test
    public void testHashCode() {
        PalavraAnalisada palavra1 = new PalavraAnalisada("teste", 1);
        PalavraAnalisada palavra2 = new PalavraAnalisada("teste", 2);
        
        // Mesmo texto deve gerar mesmo hashCode
        assertEquals(palavra1.hashCode(), palavra2.hashCode());
    }

    @Test
    public void testToString() {
        PalavraAnalisada palavraAnalisada = new PalavraAnalisada("teste", 3);
        String toString = palavraAnalisada.toString();
        
        assertTrue(toString.contains("teste"));
        assertTrue(toString.contains("3"));
        assertTrue(toString.contains("PalavraAnalisada"));
    }
} 