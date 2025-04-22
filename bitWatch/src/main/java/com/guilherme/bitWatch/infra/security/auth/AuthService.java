package com.guilherme.bitWatch.infra.security.auth;

import com.guilherme.bitWatch.domain.user.RequestUser;
import com.guilherme.bitWatch.domain.user.User;
import com.guilherme.bitWatch.domain.user.UserRepository;
import com.guilherme.bitWatch.infra.exception.BusinessRuleException;
import com.guilherme.bitWatch.infra.security.auth.email.EmailService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class AuthService {

    private final EmailService mailService;

    private final UserRepository repository;

    private final Map<String, String> verificationCodes = new ConcurrentHashMap<>();
    private final Map<String, LocalDateTime> codesExpirarions= new ConcurrentHashMap<>();

    public AuthService(EmailService mailService, UserRepository repository) {
        this.mailService = mailService;
        this.repository = repository;
    }

    public void RequestCodeAuthentication(RequestUser user){
        repository.findByEmailIgnoreCase(user.email())
                .orElseThrow(() -> new BusinessRuleException("User not Found"));

        String code = UUID.randomUUID().toString().toUpperCase().substring(0, 6);
        LocalDateTime expiration = LocalDateTime.now().plusMinutes(5);

        verificationCodes.put(user.email(), code);
        codesExpirarions.put(user.email(), expiration);

        mailService.sendEmailWithCode(user, code);
    }

    public void validateCode(String email, String code){
        User user = repository.findByEmailIgnoreCase(email)
                .orElseThrow(() -> new BusinessRuleException("User not Found"));

        String storedCode = verificationCodes.get(email);
        LocalDateTime expiration = codesExpirarions.get(email);

        if(storedCode == null || expiration == null){
            throw new BusinessRuleException("Code not found");
        }

        if(LocalDateTime.now().isAfter(expiration)){
            verificationCodes.remove(email);
            codesExpirarions.remove(email);
            throw new BusinessRuleException("Expired code");
        }

        if(!storedCode.equals(code)){
            throw new BusinessRuleException("Invalid code");
        }

        user.setActiveUser(true);
        repository.save(user);

        verificationCodes.remove(email);
        codesExpirarions.remove(email);
    }

}
