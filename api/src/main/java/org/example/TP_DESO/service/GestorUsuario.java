package org.example.TP_DESO.service;

import jakarta.transaction.Transactional;
import org.example.TP_DESO.dao.Mappers.UsuarioMapper;
import org.example.TP_DESO.dao.UsuarioDAO;
import org.example.TP_DESO.dao.UsuarioDAOMySQL;
import org.example.TP_DESO.domain.RefreshToken;
import org.example.TP_DESO.dto.UsuarioDTO;
import org.example.TP_DESO.domain.Usuario;
import org.example.TP_DESO.exceptions.FracasoOperacion;
import org.example.TP_DESO.repository.RefreshTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Service
public class GestorUsuario implements UserDetailsService {
    private static GestorUsuario singleton_instance;

    @Autowired
    private UsuarioDAO dao;
    private UsuarioDAOMySQL dao;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RefreshTokenRepository refreshTokenRepository;
    @Autowired
    private JwtEncoder jwtEncoder;

    @Value("${jwt.refresh.expiration}")
    private Long refreshTokenDuration;

    public GestorUsuario() throws FracasoOperacion {
    }

    private synchronized static GestorUsuario getInstance() throws FracasoOperacion {
        if(singleton_instance == null) singleton_instance = new GestorUsuario();
        return singleton_instance;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            UsuarioDTO usuario = dao.ObtenerUsuario(new UsuarioDTO(username, ""));
            if(usuario == null) {
                throw new UsernameNotFoundException("Usuario no encontrado: " + username);
            }
            return User.builder()
                    .username(usuario.getUsuario())
                    .password(usuario.getContrasenna())
                    .roles("USER")
                    .build();
        } catch (FracasoOperacion e) {
            throw new RuntimeException(e);
        }
    }

    public void altaUsuario(String usuario, String contrasenna) throws FracasoOperacion {
        Usuario u = new Usuario(usuario, passwordEncoder.encode(contrasenna));
        dao.CrearUsuario(u);
    }

    public boolean autenticar(String usuario, String contrasenna) throws FracasoOperacion {
        UsuarioDTO dto = new UsuarioDTO(usuario, contrasenna);

        return dao.ObtenerUsuario(dto) != null;

    }

    public void modificar(String usuario, String contrasenna) throws FracasoOperacion {
        Usuario u = new Usuario(usuario, contrasenna);
        dao.ModificarUsuario(usuario, u);
    }

    public void eliminar(String usuario) throws FracasoOperacion {
        dao.EliminarUsuario(usuario);
    }

    public String generarAcessToken(String usuario){
        Instant now = Instant.now();

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                .expiresAt(now.plus(5, ChronoUnit.MINUTES))
                .subject(usuario)
                .claim("scope", "ROLE_USER")
                .build();

        var encoderParameters = JwtEncoderParameters.from(
                JwsHeader.with(MacAlgorithm.HS256).build(),
                claims
        );

        return jwtEncoder.encode(encoderParameters).getTokenValue();

    }

    private void verificarExpiracion(RefreshToken token) throws FracasoOperacion {
        if (token.getFechaExpiracion().compareTo(Instant.now()) < 0) {
            refreshTokenRepository.delete(token);
            throw new FracasoOperacion("El Refresh Token ha expirado.");
        }
    }

    public RefreshToken generarRefreshToken(String usuario) throws FracasoOperacion {
        return refreshTokenRepository.save(RefreshToken.builder()
                        .usuario(UsuarioMapper.toDomain(dao.ObtenerUsuario(new UsuarioDTO(usuario, ""))))
                        .fechaExpiracion(Instant.now().plusSeconds(refreshTokenDuration))
                        .token(UUID.randomUUID().toString())
                .build());
    }

    public String refreshToken(String tokenValue) throws FracasoOperacion {
        RefreshToken token = refreshTokenRepository.findByToken(tokenValue)
                .orElseThrow(() -> new FracasoOperacion("Token invalido"));

        verificarExpiracion(token);

        Usuario usuario = token.getUsuario();
        return generarAcessToken(usuario.getUsuario());

    }

    @Transactional
    public void logout(String token) {
        refreshTokenRepository.deleteByToken(token);
    }

    @Transactional
    public void logoutAll(String tokenValue) throws FracasoOperacion {
        RefreshToken token = refreshTokenRepository.findByToken(tokenValue)
                .orElseThrow(() -> new FracasoOperacion("Token inválido"));

        Usuario usuario = token.getUsuario();

        refreshTokenRepository.deleteAllByUsuario(usuario);
    }

}
