package com.mesamaster.mesamaster.repositorios;

import com.mesamaster.mesamaster.entidades.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UsuarioRepository extends JpaRepository<Usuario, UUID> {
}
