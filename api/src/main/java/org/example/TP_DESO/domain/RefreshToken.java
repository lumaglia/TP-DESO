package org.example.TP_DESO.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity(name = "refreshtoken")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RefreshToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String token;

    @Column(nullable = false)
    private Instant fechaExpiracion;

    @ManyToOne
    @JoinColumn(name = "usuario", referencedColumnName = "usuario")
    private Usuario usuario;
}
