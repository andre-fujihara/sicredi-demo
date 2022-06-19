package com.example.sicredidemo.controller;

import com.example.sicredidemo.domain.ErrorDTO;
import com.example.sicredidemo.domain.request.PautaRequest;
import com.example.sicredidemo.domain.response.AssociadoResponse;
import com.example.sicredidemo.domain.response.PautaResponse;
import com.example.sicredidemo.service.PautaService;
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
 * Classe para agrupar as APIs relativas às pautas
 */
@RestController
@RequestMapping("/v1/pauta")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PautaController {

    private final PautaService pautaService;

    @Operation(summary = "Criação de uma nova pauta", tags = "Pauta")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Pauta criada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Pauta não encontrada",
                    content = @Content(schema = @Schema(implementation = ErrorDTO.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request",
                    content = @Content(schema = @Schema(implementation = ErrorDTO.class)))
    })
    @PostMapping
    public final ResponseEntity criarPauta(@Valid @RequestBody PautaRequest request) {
        pautaService.criarPauta(request);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(summary = "Consulta de todos as pautas cadastradas", tags = "Pauta")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna a lista de pautas existentes",
                    content = @Content(schema = @Schema(implementation = List.class))),
            @ApiResponse(responseCode = "404", description = "Pauta não encontrada",
                    content = @Content(schema = @Schema(implementation = ErrorDTO.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request",
                    content = @Content(schema = @Schema(implementation = ErrorDTO.class)))
    })
    @GetMapping
    public final ResponseEntity<List<PautaResponse>> recuperarTodasPautas() {
        return new ResponseEntity<>(pautaService.recuperarTodos(), HttpStatus.OK);
    }

}
