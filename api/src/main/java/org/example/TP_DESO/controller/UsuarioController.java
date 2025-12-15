package org.example.TP_DESO.controller;

import org.example.TP_DESO.domain.RefreshToken;
import org.example.TP_DESO.dto.UsuarioDTO;
import org.example.TP_DESO.exceptions.FracasoOperacion;
import org.example.TP_DESO.service.GestorUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class UsuarioController {

    private final AuthenticationManager authenticationManager;
    private final GestorUsuario gestorUsuario;

    @Autowired
    public UsuarioController(AuthenticationManager authenticationManager,  GestorUsuario gestorUsuario) {
        this.authenticationManager = authenticationManager;
        this.gestorUsuario = gestorUsuario;
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody UsuarioDTO usuarioDTO) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(usuarioDTO.getUsuario(), usuarioDTO.getContrasenna())
        );

        try {
            String acessToken = gestorUsuario.generarAcessToken(usuarioDTO.getUsuario());
            RefreshToken refreshToken = gestorUsuario.generarRefreshToken(usuarioDTO.getUsuario());
            Map<String, String> response = new HashMap<>();
            response.put("accessToken", acessToken);
            response.put("refreshToken", refreshToken.getToken());
            return ResponseEntity.ok(response);
        } catch (FracasoOperacion e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> regisrarUsuario(@RequestBody UsuarioDTO usuarioDTO) {
        try {
            gestorUsuario.altaUsuario(usuarioDTO.getUsuario(), usuarioDTO.getContrasenna());

            String acessToken = gestorUsuario.generarAcessToken(usuarioDTO.getUsuario());
            RefreshToken refreshToken = gestorUsuario.generarRefreshToken(usuarioDTO.getUsuario());
            Map<String, String> response = new HashMap<>();
            response.put("accessToken", acessToken);
            response.put("refreshToken", refreshToken.getToken());
            return ResponseEntity.ok(response);
        } catch (FracasoOperacion e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @PostMapping("/refresh")
    public ResponseEntity<Map<String, String>> refreshToken(@RequestBody TokenRefreshRequest refreshToken) {
        try {
            Map<String, String> response = new HashMap<>();
            response.put("accessToken", gestorUsuario.refreshToken(refreshToken.refreshToken));
            return ResponseEntity.ok(response);
        } catch (FracasoOperacion e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

    }

    @PostMapping("/logout")
    public ResponseEntity<Map<String, String>> logout(@RequestBody TokenRefreshRequest refreshToken) {
        gestorUsuario.logout(refreshToken.refreshToken);
        return ResponseEntity.ok(new HashMap<>());
    }

    public record TokenRefreshRequest(String refreshToken) {}
}