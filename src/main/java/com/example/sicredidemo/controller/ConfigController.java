package com.example.sicredidemo.controller;

import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;


/**
 * Controller para redirecionar para a página de documentação
 */
@RestController
@RequestMapping("/")
@Hidden
public class ConfigController {

	/**
	 * Método para redirecionar para a página de documentação automaticamente
	 * @return
	 */
	@GetMapping(value = "/")
	public ResponseEntity<Void> redirect() {
		return ResponseEntity.status(HttpStatus.FOUND)
				.location(URI.create("/swagger-ui/index.html?configUrl=/v3/api-docs/swagger-config#/")).build();
	}
}
