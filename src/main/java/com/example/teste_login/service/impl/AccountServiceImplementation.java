package com.example.teste_login.service.impl;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.teste_login.dto.AccountDto;
import com.example.teste_login.model.Account;
import com.example.teste_login.model.Role;
import com.example.teste_login.repository.AccountRepository;
import com.example.teste_login.repository.RoleRepository;
import com.example.teste_login.service.AccountService;

@Service
public class AccountServiceImplementation implements AccountService{
	
	private AccountRepository accountRepository;
	private RoleRepository roleRepository;
	private PasswordEncoder encoder;
	
	public AccountServiceImplementation(AccountRepository accountRepository, RoleRepository roleRepository,
			PasswordEncoder encoder) {
		this.accountRepository = accountRepository;
		this.roleRepository = roleRepository;
		this.encoder = encoder;
	}

	@Override
	public void saveAccount(AccountDto accountDto) {
		Account account = new Account();
		account.setCpf(accountDto.getCpf());
		account.setName(accountDto.getName());
		account.setEmail(accountDto.getEmail());
		account.setPassword(this.encoder.encode(accountDto.getPassword()));
		
		Role role = this.roleRepository.findByName("ROLE_ADMIN");
		if(role == null) {
			role = checkRoleExist();
		}
		
		account.setRoles(Arrays.asList(role));
		
		this.accountRepository.save(account);
	}

	@Override
	public Account findUserByCpf(String cpf) {
		return this.accountRepository.findByCpf(cpf);
	}

	@Override
	public List<AccountDto> findAllAccounts() {
		List<Account> accounts = this.accountRepository.findAll();
		return accounts.stream()
				.map((account) -> mapToAccountDto(account))
				.collect(Collectors.toList());
	}
	
	private AccountDto mapToAccountDto(Account account) {
		AccountDto accountDto = new AccountDto();
		accountDto.setCpf(account.getCpf());
		accountDto.setEmail(account.getEmail());
		accountDto.setName(account.getName());
		return accountDto;
	}
	
	private Role checkRoleExist() {
		Role role = new Role();
		role.setName("ROLE_ADMIN");
		return roleRepository.save(role);
	}

}
