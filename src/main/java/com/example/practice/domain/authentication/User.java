package com.example.practice.domain.authentication;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class User {
    private String username;
    private String password;
    private Authority authority;
    //ユーザーの権限を表すための列挙型フィールド
    public enum Authority {
        ADMIN, USER
    }
}