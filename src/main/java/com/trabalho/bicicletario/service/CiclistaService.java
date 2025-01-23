package com.trabalho.bicicletario.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.trabalho.bicicletario.dto.recuperarDados.RecuperarDadosCiclistaDTO;
import com.trabalho.bicicletario.dto.response.CiclistaResponseDTO;
import com.trabalho.bicicletario.exception.CustomException;
import com.trabalho.bicicletario.model.*;
import com.trabalho.bicicletario.model.Enum.ErrorEnum;
import com.trabalho.bicicletario.model.Enum.StatusCiclistaEnum;
import com.trabalho.bicicletario.model.integracoes.Cobranca;
import com.trabalho.bicicletario.model.integracoes.Email;
import com.trabalho.bicicletario.repository.CiclistaRepository;
import com.trabalho.bicicletario.repository.implementations.CiclistaRepositoryImpl;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

@Service
public class CiclistaService {
    CiclistaRepository ciclistaRepository;
    PassaporteService passaporteService;
    CartaoService cartaoService;
    AluguelService aluguelService;
    Cobranca cobranca;
    Email email;

    @PersistenceContext
    EntityManager entityManager;

    public CiclistaService(CiclistaRepository ciclistaRepository, Email email, Cobranca cobranca, PassaporteService passaporteService, CartaoService cartaoService, AluguelService aluguelService) {
        this.ciclistaRepository = ciclistaRepository;
        this.cobranca = cobranca;
        this.email = email;
        this.passaporteService = passaporteService;
        this.cartaoService = cartaoService;
        this.aluguelService = aluguelService;
    }

    public ResponseEntity<CiclistaResponseDTO> createCiclista(Ciclista ciclista, Cartao cartao, Passaporte passaporte) throws CustomException {
        if(!cartao.checkIfValid() || !ciclista.checkIfValid()){
            throw new CustomException(ErrorEnum.DADOS_INVALIDOS);
        }

        if((ciclista.checkIfBrasileiro() && ciclista.getCpf() == null) || (!ciclista.checkIfBrasileiro() && !passaporte.checkIfValid())){
            throw new CustomException(ErrorEnum.DADOS_INVALIDOS);
        }

        if(!cobranca.validarCartao(cartao)){
            throw new CustomException(ErrorEnum.CARTAO_INVALIDO);
        }

        ResponseEntity<Passaporte> createdPassaporte = null;
        if(passaporte.checkIfValid()){
            createdPassaporte = passaporteService.createPassaporte(passaporte);
            ciclista.setIdPassaporte(createdPassaporte.getBody().getId());
        }

        ResponseEntity<Cartao> createdCartao = cartaoService.createCartao(cartao);

        ciclista.setStatus(StatusCiclistaEnum.INATIVO.getDescricao());
        ciclista.setIdCartao(createdCartao.getBody().getId());

        Ciclista createdCiclista = ciclistaRepository.save( ciclista );

        CiclistaResponseDTO response = new CiclistaResponseDTO(createdCiclista);

        if(createdPassaporte != null){
            response.setPassaporte(createdPassaporte.getBody());
        }

        if(email.enviarEmail(ciclista.getEmail(), "Cadastro realizado!", "Seu cadastro foi realizado com sucesso!") == null){
            deleteCiclistaById(createdCiclista.getId());
            throw new CustomException(ErrorEnum.ERRO_ENVIO_EMAIL);
        }

        return ResponseEntity.ok(response);
    }

    public ResponseEntity<CiclistaResponseDTO> getCiclistaById(int id) throws CustomException {
        if(id <= 0){
            throw new CustomException(ErrorEnum.DADOS_INVALIDOS);
        }

        Optional<Ciclista> optionalCiclista = ciclistaRepository.findById( id );

        if(!optionalCiclista.isPresent()){
            throw new CustomException(ErrorEnum.REQUISICAO_MAL_FORMADA);
        }

        ResponseEntity<Passaporte> passaporte = passaporteService.getPassaporteById(optionalCiclista.get().getIdPassaporte());

        CiclistaResponseDTO response = new CiclistaResponseDTO(optionalCiclista.get());

        if(passaporte != null){
            response.setPassaporte(passaporte.getBody());
        }

        return ResponseEntity.ok(response);
    }

    public ResponseEntity<Ciclista> updateCiclista(int id, Ciclista updateCiclista) throws CustomException {
        if(!updateCiclista.checkIfValid() || id <= 0){
            throw new CustomException(ErrorEnum.DADOS_INVALIDOS);
        }

        Optional<Ciclista> optionalCiclista = ciclistaRepository.findById( id );

        if(!optionalCiclista.isPresent()){
            throw new CustomException(ErrorEnum.REQUISICAO_MAL_FORMADA);
        }

        updateCiclista.setId(optionalCiclista.get().getId());
        updateCiclista.setStatus(optionalCiclista.get().getStatus());
        updateCiclista.setIdCartao(optionalCiclista.get().getIdCartao());
        updateCiclista.setIdPassaporte(optionalCiclista.get().getIdPassaporte());

        Ciclista updatedCiclista = ciclistaRepository.save( updateCiclista );

        return ResponseEntity.ok(updatedCiclista);
    }

    public ResponseEntity<Boolean> emailExists(String email) throws CustomException {
        if(email == null){
            throw new CustomException(ErrorEnum.EMAIL_VAZIO);
        }

        return ResponseEntity.ok(ciclistaRepository.existsByEmail(email));
    }

    public ResponseEntity<Boolean> ciclistaExists(int id) throws CustomException {
        if(id <= 0){
            throw new CustomException(ErrorEnum.DADOS_INVALIDOS);
        }

        Optional<Ciclista> optionalCiclista = ciclistaRepository.findById( id );

        if(!optionalCiclista.isPresent()){
            throw new CustomException(ErrorEnum.REQUISICAO_MAL_FORMADA);
        }

        return ResponseEntity.ok().build();
    }

    public ResponseEntity<CiclistaResponseDTO> ativarCadastro(int id) throws CustomException {
        if(id <= 0){
            throw new CustomException(ErrorEnum.DADOS_INVALIDOS);
        }

        Optional<Ciclista> optionalCiclista = ciclistaRepository.findById( id );

        if(!optionalCiclista.isPresent()){
            throw new CustomException(ErrorEnum.REQUISICAO_MAL_FORMADA);
        }

        optionalCiclista.get().setStatus(StatusCiclistaEnum.ATIVO.getDescricao());

        Ciclista updatedCiclista = ciclistaRepository.save( optionalCiclista.get() );
        ResponseEntity<Passaporte> passaporte = passaporteService.getPassaporteById(optionalCiclista.get().getIdPassaporte());


        CiclistaResponseDTO ciclistaResponseDTO = new CiclistaResponseDTO(updatedCiclista);

        if(passaporte != null){
            ciclistaResponseDTO.setPassaporte(passaporte.getBody());
        }

        return ResponseEntity.ok(ciclistaResponseDTO);
    }

    public boolean isCiclistaAtivo(int id) throws CustomException {
        ResponseEntity<CiclistaResponseDTO> ciclista = this.getCiclistaById(id);

        return ciclista.getBody().getStatus().equals(StatusCiclistaEnum.ATIVO.getDescricao());
    }

    public boolean hasAluguel(int id) throws CustomException {
        ResponseEntity<Aluguel> aluguel = aluguelService.getAluguelAberto(id);

        return aluguel.hasBody();
    }

    public void deleteAllCiclistas() {
        ciclistaRepository.deleteAll();
    }

    @Transactional
    public void createMockCiclista(Ciclista ciclista, Cartao cartao) throws CustomException {
        ResponseEntity<Cartao> createdCartao = cartaoService.createCartao(cartao);
        ciclista.setIdCartao(createdCartao.getBody().getId());

        CiclistaRepositoryImpl ciclistaRepositoryImpl = new CiclistaRepositoryImpl(entityManager);

        ciclistaRepositoryImpl.save(ciclista);

    }

    @Transactional
    public void recuperarDados() throws IOException, CustomException {
        this.deleteAllCiclistas();
        this.cartaoService.deleteAllCartoes();
        this.passaporteService.deleteAllPassaportes();

        ClassPathResource resource = new ClassPathResource("jsons/ciclistas.json");
        String json;
        try (InputStream inputStream = resource.getInputStream()) {
            json = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
        }

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        RecuperarDadosCiclistaDTO[] ciclistas = objectMapper.readValue(json, RecuperarDadosCiclistaDTO[].class);

        for (RecuperarDadosCiclistaDTO ciclista : ciclistas) {
            Ciclista ciclistaInfo = new Ciclista(ciclista);
            Cartao cartao = new Cartao(ciclista.getMeioDePagamento());
            this.createMockCiclista(ciclistaInfo, cartao);
        }
    }

    public Iterable<Ciclista> pegaTodos(){
        return ciclistaRepository.findAll();
    }

    public void deletaTodos(){
        ciclistaRepository.deleteAll();
    }

    public ResponseEntity<Cartao> getCartaoByCiclistaId(int id) throws CustomException {
        if(id <= 0){
            throw new CustomException(ErrorEnum.DADOS_INVALIDOS);
        }

        Optional<Ciclista> optionalCiclista = ciclistaRepository.findById( id );

        if(!optionalCiclista.isPresent()){
            throw new CustomException(ErrorEnum.REQUISICAO_MAL_FORMADA);
        }

        return this.cartaoService.getCartaoById(optionalCiclista.get().getIdCartao());
    }

    public ResponseEntity<Cartao> updateCartaoByCiclistaId(int id, Cartao updateCartao) throws CustomException {
        if(id <= 0){
            throw new CustomException(ErrorEnum.DADOS_INVALIDOS);
        }

        Optional<Ciclista> optionalCiclista = ciclistaRepository.findById( id );

        if(!optionalCiclista.isPresent()){
            throw new CustomException(ErrorEnum.REQUISICAO_MAL_FORMADA);
        }

        return this.cartaoService.updateCartao(optionalCiclista.get().getIdCartao(), updateCartao);
    }

    public void deleteCiclistaById(int id) throws CustomException {
        ciclistaRepository.deleteById( id );
    }
}