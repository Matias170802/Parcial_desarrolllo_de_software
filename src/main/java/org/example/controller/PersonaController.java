package org.example.controller;

import org.example.entities.Persona;
import org.example.services.PersonaServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/personas")
public class PersonaController extends BaseControllerImpl<Persona,PersonaServiceImpl> {

    private final PersonaServiceImpl personaService;

    @Autowired
    public PersonaController(PersonaServiceImpl personaService) {
        this.personaService = personaService;
    }

    @PostMapping("/crear")
    public ResponseEntity<?> crearPersona(@RequestBody Persona persona) {
        try {
            // Verifica si es mutante y establece el valor en el objeto persona
            boolean esMutante = personaService.verificarMutante(persona);
            persona.setEsMutante(esMutante);
            return ResponseEntity.status(HttpStatus.OK).body(servicio.save(persona));

        } catch (Exception e) {
            // Manejo de cualquier otra excepción inesperada
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error inesperado: " + e.getMessage());
        }
    }
}
