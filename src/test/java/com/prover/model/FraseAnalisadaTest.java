package com.prover.model;

import org.junit.Test;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class FraseAnalisadaTest {

    @Test
    public void testConstrutorPadrao() {
        FraseAnalisada fraseAnalisada = new FraseAnalisada();
        assertNotNull(fraseAnalisada);
        assertNotNull(fraseAnalisada.getDataAnalise());
        assertTrue(fraseAnalisada.getDataAnalise().isAfter(LocalDateTime.now().minusMinutes(1)));
    }

    @Test
    public void testConstrutorComParametros() {
        String fraseOriginal = "teste teste exemplo";
        Integer totalPalavrasDistintas = 2;
        Integer totalPalavras = 3;
        
        FraseAnalisada fraseAnalisada = new FraseAnalisada(fraseOriginal, totalPalavrasDistintas, totalPalavras);
        
        assertEquals(fraseOriginal, fraseAnalisada.getFraseOriginal());
        assertEquals(totalPalavrasDistintas, fraseAnalisada.getTotalPalavrasDistintas());
        assertEquals(totalPalavras, fraseAnalisada.getTotalPalavras());
        assertNotNull(fraseAnalisada.getDataAnalise());
    }

    @Test
    public void testSettersEGetters() {
        FraseAnalisada fraseAnalisada = new FraseAnalisada();
        
        Long id = 1L;
        String fraseOriginal = "frase de teste";
        Integer totalPalavrasDistintas = 4;
        Integer totalPalavras = 6;
        LocalDateTime dataAnalise = LocalDateTime.now();
        List<PalavraAnalisada> palavrasAnalisadas = Arrays.asList(
            new PalavraAnalisada("teste", 2),
            new PalavraAnalisada("frase", 1)
        );
        
        fraseAnalisada.setId(id);
        fraseAnalisada.setFraseOriginal(fraseOriginal);
        fraseAnalisada.setTotalPalavrasDistintas(totalPalavrasDistintas);
        fraseAnalisada.setTotalPalavras(totalPalavras);
        fraseAnalisada.setDataAnalise(dataAnalise);
        fraseAnalisada.setPalavrasAnalisadas(palavrasAnalisadas);
        
        assertEquals(id, fraseAnalisada.getId());
        assertEquals(fraseOriginal, fraseAnalisada.getFraseOriginal());
        assertEquals(totalPalavrasDistintas, fraseAnalisada.getTotalPalavrasDistintas());
        assertEquals(totalPalavras, fraseAnalisada.getTotalPalavras());
        assertEquals(dataAnalise, fraseAnalisada.getDataAnalise());
        assertEquals(palavrasAnalisadas, fraseAnalisada.getPalavrasAnalisadas());
    }

    @Test
    public void testToString() {
        FraseAnalisada fraseAnalisada = new FraseAnalisada("teste", 1, 1);
        String toString = fraseAnalisada.toString();
        
        assertTrue(toString.contains("teste"));
        assertTrue(toString.contains("1"));
        assertTrue(toString.contains("FraseAnalisada"));
    }
} 