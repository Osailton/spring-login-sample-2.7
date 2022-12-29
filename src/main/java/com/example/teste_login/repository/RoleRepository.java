package com.example.teste_login.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.teste_login.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
	
	Role findByName(String name);

}
