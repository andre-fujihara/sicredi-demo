package com.example.sicredidemo.controller;

import com.example.sicredidemo.domain.ErrorDTO;
import com.example.sicredidemo.domain.request.AssociadoRequest;
import com.example.sicredidemo.domain.response.AssociadoResponse;
import com.example.sicredidemo.service.AssociadoService;
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

/**
 * Classe para agrupar as APIs relativas ao Associado
 */
@RestController
@RequestMapping("/v1/associado")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AssociadoController {

    private final AssociadoService associadoService;

    @Operation(summary = "Criação de associados", tags = "Associado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Associado criado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Associado não encontrado",
                    content = @Content(schema = @Schema(implementation = ErrorDTO.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request",
                    content = @Content(schema = @Schema(implementation = ErrorDTO.class)))
    })
    @PostMapping
    public final ResponseEntity criarAssociado(@Valid @RequestBody(required = true) AssociadoRequest request) {
        associadoService.criarAssociado(request);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(summary = "Consulta de todos os usuários cadastrados", tags = "Associado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "A lista de associados cadastrados",
                    content = @Content(schema = @Schema(implementation = List.class))),
            @ApiResponse(responseCode = "404", description = "Associado não encontrado",
                    content = @Content(schema = @Schema(implementation = ErrorDTO.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request",
                    content = @Content(schema = @Schema(implementation = ErrorDTO.class)))
    })
    @GetMapping
    public final ResponseEntity<List<AssociadoResponse>> recuperarTodosAssociados() {
        return new ResponseEntity<>(associadoService.recuperarTodos(), HttpStatus.OK);
    }

    @Operation(summary = "Validação se o associado pode votar", tags = "Associado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Frase de validação se o usuãrio pode votar",
                    content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "404", description = "Associado não encontrado",
                    content = @Content(schema = @Schema(implementation = ErrorDTO.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request",
                    content = @Content(schema = @Schema(implementation = ErrorDTO.class)))
    })
    @GetMapping("/{associadoId}")
    public final ResponseEntity<String> associadoPodeVotar(@Valid @PathVariable String associadoId) {
        return new ResponseEntity<>(
                associadoService.podeVotar(associadoId), HttpStatus.OK);
    }



}