package com.example.sicredidemo.domain.usecases;

import com.example.sicredidemo.domain.models.enums.UsuarioStatusEnum;
import com.example.sicredidemo.domain.models.externalrequest.UsuarioStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collections;
import java.util.InputMismatchException;

/**
 * Serviço que realiza as manipulações para os CPFs
 */
@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CPFManagerService {


    @Value("${external.validador-usuario.url}")
    private String validadorUsuarioUrl;
    private final RestTemplate restTemplate;

    private static final String URL_USUARIO = "/users/";

    /**
     * Método que realiza uma chamada externa para validar se o usuário pode votar
     * Esse método poderia ser cacheado, mas foi verificado que a resposta do sistema externo não é constantte
     *
     * @param cpf
     * @return
     */
    public boolean isUsuarioPodeVotar(String cpf) {
        final HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        final HttpEntity<String> httpEntity = new HttpEntity<>(headers);
        UriComponents uri = UriComponentsBuilder.fromHttpUrl(validadorUsuarioUrl)
                .path(URL_USUARIO + cpf).build();
        try {
            ResponseEntity<UsuarioStatus> response = restTemplate.exchange(
                    uri.toString(), HttpMethod.GET, httpEntity, UsuarioStatus.class);
            if(UsuarioStatusEnum.ABLE_TO_VOTE.equals(response.getBody().getStatus()))
                return true;
        } catch (Exception ex) {
            log.error("Erro ao processar request: {} - erro: {}", uri.toString(), ex.getMessage());
        }
        return false;
    }



    /**
     * Lógica de validação de CPF importada
     * @param CPF
     * @return
     */
    public boolean isCPF(String CPF) {
        // considera-se erro CPF's formados por uma sequencia de numeros iguais
        if (CPF.equals("00000000000") ||
                CPF.equals("11111111111") ||
                CPF.equals("22222222222") || CPF.equals("33333333333") ||
                CPF.equals("44444444444") || CPF.equals("55555555555") ||
                CPF.equals("66666666666") || CPF.equals("77777777777") ||
                CPF.equals("88888888888") || CPF.equals("99999999999") ||
                (CPF.length() != 11))
            return(false);

        char dig10, dig11;
        int sm, i, r, num, peso;

        // "try" - protege o codigo para eventuais erros de conversao de tipo (int)
        try {
            // Calculo do 1o. Digito Verificador
            sm = 0;
            peso = 10;
            for (i=0; i<9; i++) {
                // converte o i-esimo caractere do CPF em um numero:
                // por exemplo, transforma o caractere '0' no inteiro 0
                // (48 eh a posicao de '0' na tabela ASCII)
                num = (int)(CPF.charAt(i) - 48);
                sm = sm + (num * peso);
                peso = peso - 1;
            }

            r = 11 - (sm % 11);
            if ((r == 10) || (r == 11))
                dig10 = '0';
            else dig10 = (char)(r + 48); // converte no respectivo caractere numerico

            // Calculo do 2o. Digito Verificador
            sm = 0;
            peso = 11;
            for(i=0; i<10; i++) {
                num = (int)(CPF.charAt(i) - 48);
                sm = sm + (num * peso);
                peso = peso - 1;
            }

            r = 11 - (sm % 11);
            if ((r == 10) || (r == 11))
                dig11 = '0';
            else dig11 = (char)(r + 48);

            // Verifica se os digitos calculados conferem com os digitos informados.
            if ((dig10 == CPF.charAt(9)) && (dig11 == CPF.charAt(10)))
                return(true);
            else return(false);
        } catch (InputMismatchException erro) {
            return(false);
        }
    }
}
