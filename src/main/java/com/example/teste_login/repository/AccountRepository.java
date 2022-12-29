package com.example.teste_login.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.teste_login.model.Account;

public interface AccountRepository extends JpaRepository<Account, Long> {
	
	Account findByCpf(String cpf);

}
