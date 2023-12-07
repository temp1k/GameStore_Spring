package com.example.gamestorespring.config;

import com.example.gamestorespring.entity.UserEntity;
import com.example.gamestorespring.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepo userRepo;

    public CustomUserDetailsService() {
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        UserEntity user = userRepo.findByLogin(login)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));

        Set<GrantedAuthority> authorities = user.getRoles()
                .stream().map(role -> new SimpleGrantedAuthority(role.getRole()))
                .collect(Collectors.toSet());

        return new CustomUserDetails(user.getLogin(), user.getPassword(), authorities);
    }
}
