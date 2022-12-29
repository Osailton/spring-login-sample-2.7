package com.example.teste_login.service;

import java.util.List;

import com.example.teste_login.dto.AccountDto;
import com.example.teste_login.model.Account;

public interface AccountService {
	
	void saveAccount(com.example.teste_login.dto.AccountDto accountDto);
	Account findUserByCpf(String cpf);
	List<AccountDto> findAllAccounts();

}
