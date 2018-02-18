package com.orders.persistence.model.mysql.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.orders.persistence.model.mysql.entity.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {


	Usuario findByUsernameAndPass(String username, String pass);

}