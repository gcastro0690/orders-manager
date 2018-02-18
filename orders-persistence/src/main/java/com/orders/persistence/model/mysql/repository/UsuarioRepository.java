package com.orders.persistence.model.mysql.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.orders.persistence.model.mysql.entity.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

	Usuario findById(Long id);

	Long countById(Long id);

	List<Usuario> findByDocumento(String documento);

	Usuario findOneByDocumento(String documento);

	Long countByDocumento(String documento);

}