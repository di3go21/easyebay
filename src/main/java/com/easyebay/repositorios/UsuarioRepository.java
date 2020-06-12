package com.easyebay.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import com.easyebay.modelo.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{

	Usuario findFirstByEmail(String email);
}
