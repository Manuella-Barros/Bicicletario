package com.trabalho.bicicletario.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trabalho.bicicletario.exception.CustomException;
import com.trabalho.bicicletario.model.Enum.ErrorEnum;
import com.trabalho.bicicletario.model.Funcionario;
import com.trabalho.bicicletario.repository.FuncionarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FuncionarioServiceTest {

    private FuncionarioService funcionarioService;

    @Mock
    private FuncionarioRepository funcionarioRepository;

    private Funcionario funcionario;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        funcionarioService = new FuncionarioService(funcionarioRepository);

        funcionario = new Funcionario();
        funcionario.setId(1);
        funcionario.setNome("João Silva");
        funcionario.setEmail("joao.silva@example.com");
        funcionario.setSenha("senha123");
        funcionario.setConfirmacaoSenha("senha123");
        funcionario.setCpf("12345678900");
        funcionario.setFuncao("Gerente");
        funcionario.setIdade(35);
    }

    @Test
    void deveCriarFuncionarioComSucesso() throws CustomException {
        // Arrange
        when(funcionarioRepository.save(any(Funcionario.class))).thenReturn(funcionario);

        // Act
        ResponseEntity<Funcionario> response = funcionarioService.createFuncionario(funcionario);

        // Assert
        assertNotNull(response.getBody());
        assertEquals(funcionario.getNome(), response.getBody().getNome());
        verify(funcionarioRepository, times(1)).save(any(Funcionario.class));
    }

    @Test
    void deveLancarExcecaoSeDadosDoFuncionarioForemInvalidos() {
        // Arrange
        funcionario.setSenha(null);

        // Act & Assert
        CustomException exception = assertThrows(CustomException.class, () -> funcionarioService.createFuncionario(funcionario));
        assertEquals(ErrorEnum.DADOS_INVALIDOS.getMensagem(), exception.getMensagem());
    }

    @Test
    void deveRetornarTodosOsFuncionarios() {
        // Arrange
        List<Funcionario> funcionarios = new ArrayList<>();
        funcionarios.add(funcionario);
        when(funcionarioRepository.findAll()).thenReturn(funcionarios);

        // Act
        ResponseEntity<Iterable<Funcionario>> response = funcionarioService.getFuncionarios();

        // Assert
        assertNotNull(response.getBody());
        assertEquals(1, ((List<Funcionario>) response.getBody()).size());
        verify(funcionarioRepository, times(1)).findAll();
    }

    @Test
    void deveRetornarFuncionarioPorId() throws CustomException {
        // Arrange
        when(funcionarioRepository.findById(funcionario.getId())).thenReturn(Optional.of(funcionario));

        // Act
        ResponseEntity<Funcionario> response = funcionarioService.getFuncionarioById(funcionario.getId());

        // Assert
        assertNotNull(response.getBody());
        assertEquals(funcionario.getId(), response.getBody().getId());
        verify(funcionarioRepository, times(1)).findById(funcionario.getId());
    }

    @Test
    void deveLancarExcecaoSeFuncionarioNaoForEncontradoPorId() {
        // Arrange
        when(funcionarioRepository.findById(anyInt())).thenReturn(Optional.empty());

        // Act & Assert
        CustomException exception = assertThrows(CustomException.class, () -> funcionarioService.getFuncionarioById(999));
        assertEquals(ErrorEnum.NAO_ENCONTRADO.getMensagem(), exception.getMensagem());
    }

    @Test
    void deveAtualizarFuncionarioComSucesso() throws CustomException {
        // Arrange
        when(funcionarioRepository.findById(funcionario.getId())).thenReturn(Optional.of(funcionario));
        when(funcionarioRepository.save(any(Funcionario.class))).thenReturn(funcionario);

        Funcionario updateFuncionario = new Funcionario();
        updateFuncionario.setNome("Novo Nome");
        updateFuncionario.setEmail("novo.email@example.com");
        updateFuncionario.setSenha("novaSenha123");
        updateFuncionario.setConfirmacaoSenha("novaSenha123");
        updateFuncionario.setCpf("98765432100");
        updateFuncionario.setFuncao("Supervisor");
        updateFuncionario.setIdade(40);

        // Act
        ResponseEntity<Funcionario> response = funcionarioService.updateFuncionario(funcionario.getId(), updateFuncionario);

        // Assert
        assertNotNull(response.getBody());
        assertEquals(updateFuncionario.getNome(), response.getBody().getNome());
        verify(funcionarioRepository, times(1)).save(any(Funcionario.class));
    }

    @Test
    void deveRemoverFuncionarioPorId() throws CustomException {
        // Arrange
        when(funcionarioRepository.findById(funcionario.getId())).thenReturn(Optional.of(funcionario));
        doNothing().when(funcionarioRepository).deleteById(funcionario.getId());

        // Act
        ResponseEntity<Void> response = funcionarioService.removeFuncionario(funcionario.getId());

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        verify(funcionarioRepository, times(1)).deleteById(funcionario.getId());
    }

    @Test
    void deveDeletarTodosOsFuncionarios() {
        // Act
        funcionarioService.deleteAllFuncionarios();

        // Assert
        verify(funcionarioRepository, times(1)).deleteAll();
    }

    @Test
    void deveRecuperarDadosDeArquivoJson() throws Exception {
        // Arrange
        ObjectMapper objectMapper = new ObjectMapper();
        Funcionario[] funcionarios = {funcionario};
        String json = objectMapper.writeValueAsString(funcionarios);
        doNothing().when(funcionarioRepository).deleteAll();
        when(funcionarioRepository.save(any(Funcionario.class))).thenReturn(funcionario);

        // Mock InputStream
        ClassLoader classLoader = getClass().getClassLoader();
        try (InputStream inputStream = classLoader.getResourceAsStream("jsons/funcionarios.json")) {
            assert inputStream != null;
            String fileContent = new String(inputStream.readAllBytes());
            assertNotNull(fileContent);
        }

        // Act
        funcionarioService.recuperarDados();

        // Assert
        verify(funcionarioRepository, times(1)).deleteAll();
        verify(funcionarioRepository, atLeastOnce()).save(any(Funcionario.class));
    }
}
