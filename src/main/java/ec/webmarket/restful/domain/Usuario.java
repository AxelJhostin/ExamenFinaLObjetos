package ec.webmarket.restful.domain;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String nombreUsuario;

    @Column(nullable = false)
    private String clave; // 🔹 Sin cifrar, como lo pediste

    @Enumerated(EnumType.STRING)
    private Rol tipoUsuario;

    public enum Rol { ODONTOLOGO, PACIENTE }
}
