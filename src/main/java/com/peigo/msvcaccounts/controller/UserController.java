package com.peigo.msvcaccounts.controller;

import com.peigo.msvcaccounts.common.route.Route;
import com.peigo.msvcaccounts.domain.request.UserRequest;
import com.peigo.msvcaccounts.domain.response.UserResponse;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class UserController {

    @PostMapping(Route.User.Auth)
    public UserResponse authentication(@RequestBody UserRequest request) {
        String token = getJWTToken(request.getUser());
        UserResponse user = new UserResponse();
        user.setToken(token);
        return user;
    }

    private String getJWTToken(String username) {
        String secretKey = "mySecretKey";
        List<GrantedAuthority> grantedAuthorities = AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_ADMIN");

        String token = Jwts
                .builder()
                .setId("1")
                .setSubject(username)
                .claim("authorities",
                        grantedAuthorities.stream()
                                .map(GrantedAuthority::getAuthority)
                                .collect(Collectors.toList()))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 600000))
                .signWith(SignatureAlgorithm.HS512, secretKey.getBytes()).compact();
        return "Bearer " + token;
    }
}
