package com.example.practice.web.user;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
public class UserForm {
    @NotBlank(message = "ユーザー名を入力してください")
    private String username;
    @NotBlank(message = "パスワードを入力してください")
    @Size(min = 4, max = 12, message = "パスワードは4文字以上12文字以下で登録してください")
    private String password;
    @NotBlank(message = "権限を入力してください")
    private String authority;
}
