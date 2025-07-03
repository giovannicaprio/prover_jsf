package com.prover.web.bean;

import com.prover.model.FraseAnalisada;
import com.prover.model.Palavra;
import com.prover.model.PalavraAnalisada;
import com.prover.service.AnalisadorService;
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
        FraseAnalisada fraseAnalisada = new FraseAnalisada(frase, 2, 3);
        List<PalavraAnalisada> palavrasAnalisadas = Arrays.asList(
            new PalavraAnalisada("teste", 2, fraseAnalisada),
            new PalavraAnalisada("exemplo", 1, fraseAnalisada)
        );
        fraseAnalisada.setPalavrasAnalisadas(palavrasAnalisadas);
        
        when(analisadorService.analisarESalvarFrase(frase)).thenReturn(fraseAnalisada);
        when(analisadorService.buscarHistorico()).thenReturn(Arrays.asList(fraseAnalisada));

        bean.setFrase(frase);

        // Act
        bean.analisarFrase();

        // Assert
        assertEquals(2, bean.getPalavras().size());
        assertEquals("teste", bean.getPalavras().get(0).getTexto());
        assertEquals(2, bean.getPalavras().get(0).getOcorrencias());
        assertEquals("exemplo", bean.getPalavras().get(1).getTexto());
        assertEquals(1, bean.getPalavras().get(1).getOcorrencias());
        assertEquals(2, bean.getTotalPalavrasDistintas());
        assertEquals(3, bean.getTotalPalavras());
        verify(analisadorService).analisarESalvarFrase(frase);
        verify(analisadorService).buscarHistorico();
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
        assertEquals(0, bean.getTotalPalavras());
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
        assertEquals(0, bean.getTotalPalavras());
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
        assertEquals(0, bean.getTotalPalavras()); // Inicialmente zero
        assertFalse(bean.isResultadoVisivel()); // Inicialmente falso
        assertFalse(bean.isProcessando()); // Inicialmente falso
    }

    @Test
    public void testGetOcorrenciasOrdenadas() {
        // Arrange
        String frase = "teste";
        FraseAnalisada fraseAnalisada = new FraseAnalisada(frase, 3, 6);
        List<PalavraAnalisada> palavrasAnalisadas = Arrays.asList(
            new PalavraAnalisada("mais", 3, fraseAnalisada),
            new PalavraAnalisada("medio", 2, fraseAnalisada),
            new PalavraAnalisada("menos", 1, fraseAnalisada)
        );
        fraseAnalisada.setPalavrasAnalisadas(palavrasAnalisadas);
        
        when(analisadorService.analisarESalvarFrase(frase)).thenReturn(fraseAnalisada);
        when(analisadorService.buscarHistorico()).thenReturn(Arrays.asList(fraseAnalisada));
        bean.setFrase(frase);
        
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
        assertEquals(6, bean.getTotalPalavras()); // 3 + 2 + 1 = 6 palavras totais
    }
} 