package org.example.TP_DESO.repository;

import org.example.TP_DESO.domain.RefreshToken;
import org.example.TP_DESO.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByToken(String token);
    void deleteByToken(String token);
    void deleteAllByUsuario(Usuario usuario);
}
