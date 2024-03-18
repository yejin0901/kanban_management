package com.team8.kanban.global.security;


import com.team8.kanban.domain.user.User;
import com.team8.kanban.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }
    public UserDetailsImpl getUserDetails(String username) throws UsernameNotFoundException{
        User user = userRepository.findByUsername(username).orElseThrow();
        return new UserDetailsImpl(user);
    }
}
