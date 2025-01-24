package com.trabalho.bicicletario.service;

import com.trabalho.bicicletario.exception.CustomException;
import com.trabalho.bicicletario.model.Aluguel;
import com.trabalho.bicicletario.model.Ciclista;
import com.trabalho.bicicletario.model.Enum.ErrorEnum;
import com.trabalho.bicicletario.model.integracoes.Bicicleta;
import com.trabalho.bicicletario.model.integracoes.Cobranca;
import com.trabalho.bicicletario.model.integracoes.Email;
import com.trabalho.bicicletario.model.integracoes.Tranca;
import com.trabalho.bicicletario.model.integracoes.dtos.EmailDTO;
import com.trabalho.bicicletario.repository.AluguelRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AluguelServiceTest {

    private AluguelService aluguelService;

    @Mock
    private AluguelRepository aluguelRepository;

    @Mock
    private Tranca tranca;

    @Mock
    private Email email;

    @Mock
    private Cobranca cobranca;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        aluguelService = new AluguelService(aluguelRepository, tranca, mock(Bicicleta.class), email, cobranca);
    }

    @Test
    void deveCriarAluguelComSucesso() throws CustomException {
        // Arrange
        Aluguel aluguel = new Aluguel();
        aluguel.setTrancaInicio(1);
        aluguel.setCiclista(2);
        aluguel.setBicicleta(3);
        aluguel.setCobranca(10.0);

        Ciclista ciclista = new Ciclista();
        ciclista.setEmail("ciclista@example.com");

        when(aluguelRepository.save(any(Aluguel.class))).thenReturn(aluguel);
        when(cobranca.enviarCobranca(anyInt(), anyDouble())).thenReturn(true);

        // Act
        ResponseEntity<Aluguel> response = aluguelService.createAluguel(aluguel, ciclista);

        // Assert
        assertNotNull(response.getBody());
        assertEquals(aluguel.getCiclista(), response.getBody().getCiclista());
        verify(cobranca, times(1)).enviarCobranca(anyInt(), anyDouble());
        verify(aluguelRepository, times(1)).save(aluguel);
    }

    @Test
    void deveLancarExcecaoSeDadosDoAluguelForemInvalidos() {
        // Arrange
        Aluguel aluguel = new Aluguel();
        Ciclista ciclista = new Ciclista();

        // Act & Assert
        CustomException exception = assertThrows(CustomException.class, () -> aluguelService.createAluguel(aluguel, ciclista));
        assertEquals(ErrorEnum.DADOS_INVALIDOS.getMensagem(), exception.getMensagem());
    }

    @Test
    void deveLancarExcecaoSePagamentoNaoForAutorizado() throws CustomException {
        // Arrange
        Aluguel aluguel = new Aluguel();
        aluguel.setTrancaInicio(1);
        aluguel.setCiclista(2);
        aluguel.setBicicleta(3);
        aluguel.setCobranca(10.0);

        Ciclista ciclista = new Ciclista();
        ciclista.setEmail("ciclista@example.com");

        when(cobranca.enviarCobranca(anyInt(), anyDouble())).thenReturn(false);

        // Act & Assert
        CustomException exception = assertThrows(CustomException.class, () -> aluguelService.createAluguel(aluguel, ciclista));
        assertEquals(ErrorEnum.PAGAMENTO_NAO_AUTORIZADO, exception.getMensagem());
    }

    @Test
    void deveRetornarAluguelAbertoParaCiclista() throws CustomException {
        // Arrange
        Aluguel aluguel1 = new Aluguel();
        aluguel1.setTrancaFim(0);

        Aluguel aluguel2 = new Aluguel();
        aluguel2.setTrancaFim(1);

        Aluguel[] alugueis = {aluguel1, aluguel2};

        when(aluguelRepository.findAllByCiclista(1)).thenReturn(alugueis);

        // Act
        ResponseEntity<Aluguel> response = aluguelService.getAluguelAberto(1);

        // Assert
        assertNotNull(response.getBody());
        assertEquals(0, response.getBody().getTrancaFim());
        verify(aluguelRepository, times(1)).findAllByCiclista(1);
    }

    @Test
    void deveLancarExcecaoSeAluguelAbertoNaoExistir() throws CustomException {
        // Arrange
        when(aluguelRepository.findAllByCiclista(anyInt())).thenReturn(new Aluguel[]{});

        // Act
        ResponseEntity<Aluguel> response = aluguelService.getAluguelAberto(1);

        // Assert
        assertFalse(response.hasBody());
        verify(aluguelRepository, times(1)).findAllByCiclista(anyInt());
    }

    @Test
    void devePostarDevolucaoComSucesso() throws CustomException {
        // Arrange
        Aluguel aluguel = new Aluguel();
        aluguel.setId(1);
        aluguel.setHoraInicio(LocalDateTime.now().minusHours(3));

        Aluguel[] alugueis = {aluguel};

        when(aluguelRepository.findByBicicletaAndTrancaInicio(1, 2)).thenReturn(alugueis);
        when(aluguelRepository.save(any(Aluguel.class))).thenReturn(aluguel);

        // Act
        ResponseEntity<Aluguel> response = aluguelService.postDevolucao(1, 2);

        // Assert
        assertNotNull(response.getBody());
        assertEquals(aluguel.getId(), response.getBody().getId());
        verify(aluguelRepository, times(1)).save(any(Aluguel.class));
    }

    @Test
    void deveLancarExcecaoSeDevolucaoFalhar() {
        // Arrange
        when(aluguelRepository.findByBicicletaAndTrancaInicio(1, 2)).thenReturn(new Aluguel[]{});

        // Act & Assert
        CustomException exception = assertThrows(CustomException.class, () -> aluguelService.postDevolucao(1, 2));
        assertEquals(ErrorEnum.DADOS_INVALIDOS, exception.getMensagem());
    }
}
