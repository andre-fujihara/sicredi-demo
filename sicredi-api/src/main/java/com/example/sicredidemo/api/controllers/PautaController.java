package com.example.sicredidemo.api.controllers;

import com.example.sicredidemo.api.dto.request.AberturaSessaoRequest;
import com.example.sicredidemo.api.dto.request.PautaRequest;
import com.example.sicredidemo.api.dto.response.AssociadoResponse;
import com.example.sicredidemo.api.dto.response.PautaResponse;
import com.example.sicredidemo.domain.interfaces.PautaGateway;
import com.example.sicredidemo.domain.models.Pauta;
import com.example.sicredidemo.domain.usecases.PautaService;
import com.example.sicredidemo.domain.usecases.SessaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


/**
 * Classe para agrupar as APIs relativas à {@link Pauta}
 */
@RestController
@RequestMapping("/v1/pauta")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PautaController {

    private final PautaService pautaService;
    private final SessaoService sessaoService;


    @Operation(summary = "Criação de uma nova pauta", tags = "Pauta")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Criação realizada com sucesso", content = @Content(schema = @Schema(implementation = PautaResponse.class))),
            @ApiResponse(responseCode = "400", description = "Erro no requisição enviada")
    })
    @PostMapping
    public final ResponseEntity<PautaResponse> criarPauta(@Valid @RequestBody(required = true) PautaRequest request){
        Pauta pauta = pautaService.criarPauta(Pauta.builder()
                .nome(request.getNome())
                .build());
        return new ResponseEntity<>(PautaResponse.makeResponse(pauta), HttpStatus.CREATED);
    }


    @Operation(summary = "Consulta de todos as pautas cadastradas", tags = "Pauta")
    @GetMapping
    public final ResponseEntity<List<PautaResponse>> recuperarTodasPautas(){
        return new ResponseEntity<>(
                pautaService.recuperarTodos().stream()
                        .map(PautaResponse::makeResponse)
                        .collect(Collectors.toList()),
                HttpStatus.OK);
    }

}
