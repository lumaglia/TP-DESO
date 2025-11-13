package org.example.TP_DESO.controller;

import org.example.TP_DESO.dto.UsuarioDTO;
import org.example.TP_DESO.exceptions.FracasoOperacion;
import org.example.TP_DESO.service.GestorUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UsuarioController {

    GestorUsuario gestorUsuario;

    @Autowired
    public UsuarioController(GestorUsuario gestorUsuario) {
        this.gestorUsuario = gestorUsuario;
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/Alta/Usuario")
    public ResponseEntity<UsuarioDTO> altaHuesped(@RequestBody UsuarioDTO usuarioDTO) {
        try {
            gestorUsuario.altaUsuario(usuarioDTO.getUsuario(), usuarioDTO.getContrasenna());
        } catch (FracasoOperacion e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok(usuarioDTO);
    }

}
