package com.example.sicredidemo.controller;

import com.example.sicredidemo.domain.ErrorDTO;
import com.example.sicredidemo.domain.request.AberturaSessaoRequest;
import com.example.sicredidemo.domain.response.ResultadoPautaResponse;
import com.example.sicredidemo.enums.ValorVotoEnum;
import com.example.sicredidemo.service.SessaoService;
import com.example.sicredidemo.service.VotoService;
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

/**
 * Classe para agrupar as APIs relativas à sessão e aos votos
 */
@RestController
@RequestMapping("/v1/sessao")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SessaoController {

    private final SessaoService sessaoService;
    private final VotoService votoService;

    @Operation(summary = "Abertura da sessão da pauta", tags = "Sessao")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Sessão aberta com sucesso"),
            @ApiResponse(responseCode = "404", description = "Pauta não encontrado",
                    content = @Content(schema = @Schema(implementation = ErrorDTO.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request",
                    content = @Content(schema = @Schema(implementation = ErrorDTO.class)))
    })
    @PostMapping("/pauta/{pautaId}/criarsessao")
    public final ResponseEntity abrirSessao(@Valid @PathVariable String pautaId, @Valid @RequestBody AberturaSessaoRequest request){
        sessaoService.abrirSessao(pautaId, request.getDuracao());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


    @Operation(summary = "Realiza uma votação utilizando o id da pauta, do associado e sua escolha de votação", tags = "Sessao")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Voto realizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Pauta ou associado não encontrado",
                    content = @Content(schema = @Schema(implementation = ErrorDTO.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request",
                    content = @Content(schema = @Schema(implementation = ErrorDTO.class)))
    })
    @PostMapping("/pauta/{pautaId}/associado/{associadoId}/votar")
    public final ResponseEntity votarPauta(
            @Valid @PathVariable String pautaId,
            @Valid @PathVariable String associadoId,
            @Valid @RequestBody ValorVotoEnum voto){
        votoService.votarPauta(pautaId, associadoId, voto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(summary = "Consulta dos votos presentes na pauta procurada pelo parâmetro pautaId", tags = "Sessao")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna a quantidade de votos da pauta passada",
                    content = @Content(schema = @Schema(implementation = ResultadoPautaResponse.class))),
            @ApiResponse(responseCode = "404", description = "Pauta não encontrada",
                    content = @Content(schema = @Schema(implementation = ErrorDTO.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request",
                    content = @Content(schema = @Schema(implementation = ErrorDTO.class)))
    })
    @GetMapping("/pauta/{pautaId}/votos")
    public final ResponseEntity<ResultadoPautaResponse> contarVotosPauta(@PathVariable String pautaId){
        return new ResponseEntity<>(votoService.contarVotosDaPauta(pautaId), HttpStatus.OK);
    }
}
