package com.easyebay.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.easyebay.modelo.Compra;
import com.easyebay.modelo.Usuario;

public interface CompraRepository extends JpaRepository<Compra, Long> {
	
	List<Compra> findByPropietario(Usuario propietario);

}
