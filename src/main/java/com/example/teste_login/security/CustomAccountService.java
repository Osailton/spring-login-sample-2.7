package com.example.teste_login.security;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.teste_login.model.Account;
import com.example.teste_login.model.Role;
import com.example.teste_login.repository.AccountRepository;

@Service
public class CustomAccountService implements UserDetailsService {
	
	private AccountRepository accountRepository;
	
	public CustomAccountService(AccountRepository accountRepository) {
		this.accountRepository = accountRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String cpf) throws UsernameNotFoundException {
		Account account = this.accountRepository.findByCpf(cpf);
		if(account != null) {
			return new User(account.getCpf(),
					account.getPassword(),
					mapRolesToAuthorities(account.getRoles()));
		} else {
			throw new UsernameNotFoundException("O CPF ou senha informados são inválidos");
		}
	}
	
	private Collection< ? extends GrantedAuthority> mapRolesToAuthorities(Collection <Role> roles) {
        Collection < ? extends GrantedAuthority> mapRoles = roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
        return mapRoles;
    }

}
