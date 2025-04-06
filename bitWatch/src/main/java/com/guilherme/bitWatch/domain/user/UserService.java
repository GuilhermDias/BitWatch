package com.guilherme.bitWatch.domain.user;

import com.guilherme.bitWatch.domain.account.Account;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    private final PasswordEncoder encoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmailIgnoreCase(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found."));
    }

    public void registerUser(RequestUser requestUser){
        //Do Password enconder
        String encryptedPassword = encoder.encode(requestUser.password());

        //Register new user with encrypted password
        User newUser = new User(
                requestUser.name(),
                requestUser.lastName(),
                requestUser.email(),
                requestUser.document(),
                encryptedPassword
        );

        //Register new account with same data to user
        Account newAccount = new Account(newUser);
        newUser.setAccount(newAccount);

        //Register new user on Data Base
        userRepository.save(newUser);

    }


}
