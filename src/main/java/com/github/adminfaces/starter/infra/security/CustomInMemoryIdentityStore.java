package com.github.adminfaces.starter.infra.security;

import javax.enterprise.context.ApplicationScoped;
import javax.security.enterprise.credential.Credential;
import javax.security.enterprise.credential.UsernamePasswordCredential;
import javax.security.enterprise.identitystore.CredentialValidationResult;
import javax.security.enterprise.identitystore.IdentityStore;
import java.util.Arrays;
import java.util.HashSet;

@ApplicationScoped
public class CustomInMemoryIdentityStore implements IdentityStore {

    @Override
    public CredentialValidationResult validate(Credential credential) {

        UsernamePasswordCredential login = (UsernamePasswordCredential) credential;

        if (login.getCaller().equals("admin@faces.com") && login.getPasswordAsString().equals("admin")) {
            return new CredentialValidationResult("admin", new HashSet<>(Arrays.asList("ADMIN")));
        } else if (login.getCaller().equals("user@faces.com") && login.getPasswordAsString().equals("user")) {
            return new CredentialValidationResult("user", new HashSet<>(Arrays.asList("USER")));
        } else {
            return CredentialValidationResult.INVALID_RESULT;
        }
    }
}
