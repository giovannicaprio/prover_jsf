package com.prover.bean;

import com.prover.model.Palavra;
import com.prover.service.AnalisadorService;
import com.prover.web.bean.AnalisadorFraseBean;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class AnalisadorFraseBeanTest {

    private AnalisadorFraseBean bean;
    
    @Mock
    private AnalisadorService analisadorService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        bean = new AnalisadorFraseBean();
        // Injetando o mock manualmente já que não temos CDI no teste
        try {
            java.lang.reflect.Field field = AnalisadorFraseBean.class.getDeclaredField("analisadorService");
            field.setAccessible(true);
            field.set(bean, analisadorService);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testAnalisarFraseComTextoValido() {
        // Arrange
        String frase = "teste teste exemplo";
        List<Palavra> palavrasEsperadas = Arrays.asList(
            new Palavra("teste", 2),
            new Palavra("exemplo", 1)
        );
        when(analisadorService.analisarTexto(frase)).thenReturn(palavrasEsperadas);

        bean.setFrase(frase);

        // Act
        bean.analisarFrase();

        // Assert
        assertEquals(palavrasEsperadas, bean.getPalavras());
        assertEquals(2, bean.getTotalPalavrasDistintas());
        verify(analisadorService).analisarTexto(frase);
        assertTrue(bean.isResultadoVisivel());
        assertFalse(bean.isProcessando());
    }

    @Test
    public void testAnalisarFraseComTextoVazio() {
        // Arrange
        bean.setFrase("");

        // Act
        bean.analisarFrase();

        // Assert
        assertTrue(bean.getPalavras().isEmpty());
        assertEquals(0, bean.getTotalPalavrasDistintas());
        assertFalse(bean.isResultadoVisivel());
        assertFalse(bean.isProcessando());
    }

    @Test
    public void testAnalisarFraseComTextoNulo() {
        // Arrange
        bean.setFrase(null);

        // Act
        bean.analisarFrase();

        // Assert
        assertTrue(bean.getPalavras().isEmpty());
        assertEquals(0, bean.getTotalPalavrasDistintas());
        assertFalse(bean.isResultadoVisivel());
        assertFalse(bean.isProcessando());
    }

    @Test
    public void testGettersESetters() {
        // Arrange
        String frase = "teste";
        
        // Act
        bean.setFrase(frase);

        // Assert
        assertEquals(frase, bean.getFrase());
        assertTrue(bean.getPalavras().isEmpty()); // Inicialmente vazio
        assertEquals(0, bean.getTotalPalavrasDistintas()); // Inicialmente zero
        assertFalse(bean.isResultadoVisivel()); // Inicialmente falso
        assertFalse(bean.isProcessando()); // Inicialmente falso
    }

    @Test
    public void testGetOcorrenciasOrdenadas() {
        // Arrange
        List<Palavra> palavras = Arrays.asList(
            new Palavra("menos", 1),
            new Palavra("mais", 3),
            new Palavra("medio", 2)
        );
        when(analisadorService.analisarTexto(anyString())).thenReturn(palavras);
        bean.setFrase("teste");
        
        // Act
        bean.analisarFrase();
        List<Palavra> ordenadas = bean.getOcorrenciasOrdenadas();

        // Assert
        assertEquals(3, ordenadas.size());
        assertEquals("mais", ordenadas.get(0).getTexto());
        assertEquals(3, ordenadas.get(0).getOcorrencias());
        assertEquals("medio", ordenadas.get(1).getTexto());
        assertEquals(2, ordenadas.get(1).getOcorrencias());
        assertEquals("menos", ordenadas.get(2).getTexto());
        assertEquals(1, ordenadas.get(2).getOcorrencias());
    }
} 