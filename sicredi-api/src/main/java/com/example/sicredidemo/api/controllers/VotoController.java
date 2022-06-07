package com.example.sicredidemo.api.controllers;

import com.example.sicredidemo.api.dto.request.AberturaSessaoRequest;
import com.example.sicredidemo.api.dto.response.PautaResponse;
import com.example.sicredidemo.domain.models.Pauta;
import com.example.sicredidemo.domain.models.ResultadoPauta;
import com.example.sicredidemo.domain.models.enums.ValorVotoEnum;
import com.example.sicredidemo.domain.usecases.SessaoService;
import com.example.sicredidemo.domain.usecases.VotoService;
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
import java.util.UUID;

/**
 * Classe para agrupar as APIs relativas à {@link com.example.sicredidemo.domain.models.Voto}
 */
@RestController
@RequestMapping("/v1/sessao")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class VotoController {

    private final VotoService votoService;
    private final SessaoService sessaoService;


    @Operation(summary = "Realiza uma votação utilizando o id da pauta, do associado e sua escolha de votação", tags = "Sessao")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Votação realizada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro no requisição enviada")
    })
    @PostMapping("/pauta/{pautaId}/associado/{associadoId}/votar")
    public final ResponseEntity<String> votarPauta(
            @Valid @PathVariable String pautaId,
            @Valid @PathVariable String associadoId,
            @Valid @RequestBody ValorVotoEnum voto){
        votoService.votarPauta(UUID.fromString(pautaId), UUID.fromString(associadoId), voto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(summary = "Abertura da sessão da pauta", tags = "Sessao")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Criação da pauta realizada com sucesso", content = @Content(schema = @Schema(implementation = Pauta.class))),
            @ApiResponse(responseCode = "400", description = "Erro no requisição enviada")
    })
    @PostMapping("/pauta/{pautaId}/criarsessao")
    public final ResponseEntity<Pauta> abrirSessao(@Valid @PathVariable (required = true) String pautaId, @Valid @RequestBody(required = true) AberturaSessaoRequest request){
        Date now = new Date();
        int duracao = request.getDuracao() == null ? 1 : request.getDuracao();
        return new ResponseEntity<>(sessaoService.abrirSessao(UUID.fromString(pautaId), now, DateUtils.addMinutes(now, duracao)), HttpStatus.CREATED);
    }

    @Operation(summary = "Consulta dos votos presentes na pauta procurada pelo parâmetro pautaId", tags = "Sessao")
    @GetMapping("/pauta/{pautaId}/votos")
    public final ResponseEntity<ResultadoPauta> contarVotosPauta(@PathVariable String pautaId){
        return new ResponseEntity<>(votoService.contarVotosDaPauta(UUID.fromString(pautaId)), HttpStatus.OK);
    }
}
