package org.example.TP_DESO.controller;

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

    private final JwtEncoder jwtEncoder;
    private final AuthenticationManager authenticationManager;
    private final GestorUsuario gestorUsuario;

    @Autowired
    public UsuarioController(JwtEncoder jwtEncoder, AuthenticationManager authenticationManager,  GestorUsuario gestorUsuario) {
        this.jwtEncoder = jwtEncoder;
        this.authenticationManager = authenticationManager;
        this.gestorUsuario = gestorUsuario;
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody UsuarioDTO usuarioDTO) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(usuarioDTO.getUsuario(), usuarioDTO.getContrasenna())
        );

        String jwt = generarJWT(usuarioDTO.getUsuario());

        // 4. RESPUESTA
        Map<String, String> response = new HashMap<>();
        response.put("token", jwt);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> regisrarUsuario(@RequestBody UsuarioDTO usuarioDTO) {
        try {
            gestorUsuario.altaUsuario(usuarioDTO.getUsuario(), usuarioDTO.getContrasenna());
            String jwt = generarJWT(usuarioDTO.getUsuario());

            Map<String, String> response = new HashMap<>();
            response.put("token", jwt);
            return ResponseEntity.status(201).body(response);
        } catch (FracasoOperacion e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    private String generarJWT(String usuario){
        Instant now = Instant.now();

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                .expiresAt(now.plus(1, ChronoUnit.HOURS))
                .subject(usuario)
                .claim("scope", "ROLE_USER")
                .build();

        var encoderParameters = JwtEncoderParameters.from(
                JwsHeader.with(MacAlgorithm.HS256).build(),
                claims
        );

        return jwtEncoder.encode(encoderParameters).getTokenValue();

    }
}