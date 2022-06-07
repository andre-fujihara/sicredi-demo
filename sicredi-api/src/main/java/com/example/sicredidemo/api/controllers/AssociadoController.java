package com.example.sicredidemo.api.controllers;

import com.example.sicredidemo.api.dto.request.AssociadoRequest;
import com.example.sicredidemo.api.dto.response.AssociadoResponse;
import com.example.sicredidemo.domain.models.Associado;
import com.example.sicredidemo.domain.usecases.AssociadoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Classe para agrupar as APIs relativas ao {@link Associado}
 */
@RestController
@RequestMapping("/v1/associado")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AssociadoController {

    private final AssociadoService associadoService;


    @Operation(summary = "Criação de associados", tags = "Associado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Criação realizada com sucesso", content = @Content(schema = @Schema(implementation = AssociadoResponse.class))),
            @ApiResponse(responseCode = "400", description = "Erro no requisição enviada")
    })
    @PostMapping
    public final ResponseEntity<AssociadoResponse> criarAssociado(@Valid @RequestBody(required = true) AssociadoRequest request){
        Associado associado = associadoService.criarAssociado(Associado.builder().cpf(request.getCpf()).build());
        return new ResponseEntity<>(AssociadoResponse.makeResponse(associado), HttpStatus.CREATED);
    }


    @Operation(summary = "Validação se o associado pode votar", tags = "Associado")
    @GetMapping("/{associadoId}")
    public final ResponseEntity<String> associadoPodeVotar(@Valid @RequestParam String associadoId){
        return new ResponseEntity<>(
                associadoService.podeVotar(UUID.fromString(associadoId)),
                HttpStatus.OK);
    }

    @Operation(summary = "Consulta de todos os usuários cadastrados", tags = "Associado")
    @GetMapping
    public final ResponseEntity<List<AssociadoResponse>> recuperarTodosAssociados(){
        return new ResponseEntity<>(
                associadoService.recuperarTodosAssociados().stream()
                        .map(AssociadoResponse::makeResponse)
                        .collect(Collectors.toList()),
                HttpStatus.OK);
    }

}

