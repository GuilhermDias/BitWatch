package com.guilherme.bitWatch.domain.user;

import com.guilherme.bitWatch.domain.account.Account;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Entity(name = "users")
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String lastName;

    @Email
    private String email;

    @Pattern(regexp = "\\d{3}\\.?\\d{3}\\.?\\d{3}\\-?\\d{2}")
    private String document;
    private String password;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Account account;

    private Boolean isActiveUser = false;

    public User(String name, String lastName, String email, String document, String encryptedPassword) {
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.document = document;
        this.password = encryptedPassword;
    }

    public User(){
    }

    public User(Long id, String name, String lastName, String email, String document, String password, Account account, Boolean isActiveUser) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.document = document;
        this.password = password;
        this.account = account;
        this.isActiveUser = isActiveUser;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Boolean getActiveUser() {
        return isActiveUser;
    }

    public void setActiveUser(Boolean activeUser) {
        isActiveUser = activeUser;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getUsername() {
        return email;
    }
    @Override
    public String getPassword() {
        return password;
    }
}
