package com.prover.model;

import org.junit.Test;
import static org.junit.Assert.*;

public class PalavraTest {

    @Test
    public void testConstrutorPadrao() {
        Palavra palavra = new Palavra();
        assertNotNull(palavra);
        assertEquals(0, palavra.getOcorrencias());
        assertNull(palavra.getTexto());
    }

    @Test
    public void testConstrutorComParametros() {
        Palavra palavra = new Palavra("teste", 3);
        assertEquals("teste", palavra.getTexto());
        assertEquals(3, palavra.getOcorrencias());
    }

    @Test
    public void testSettersEGetters() {
        Palavra palavra = new Palavra();
        
        palavra.setTexto("exemplo");
        assertEquals("exemplo", palavra.getTexto());
        
        palavra.setOcorrencias(5);
        assertEquals(5, palavra.getOcorrencias());
    }

    @Test
    public void testEquals() {
        Palavra palavra1 = new Palavra("teste", 1);
        Palavra palavra2 = new Palavra("teste", 2);
        Palavra palavra3 = new Palavra("outro", 1);
        
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
        Palavra palavra1 = new Palavra("teste", 1);
        Palavra palavra2 = new Palavra("teste", 2);
        
        // Mesmo texto deve gerar mesmo hashCode
        assertEquals(palavra1.hashCode(), palavra2.hashCode());
    }

    @Test
    public void testToString() {
        Palavra palavra = new Palavra("teste", 3);
        String toString = palavra.toString();
        
        // Verifica se o toString contém as informações essenciais
        assertTrue(toString.contains("teste"));
        assertTrue(toString.contains("3"));
    }
} 