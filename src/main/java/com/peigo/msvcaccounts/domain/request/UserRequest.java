package com.peigo.msvcaccounts.domain.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {

    private String user;
    private String pwd;
    private String token;
}
