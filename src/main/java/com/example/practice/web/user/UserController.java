package com.example.practice.web.user;

import com.example.practice.domain.authentication.UserRepository;
import com.example.practice.domain.authentication.UserService;
import com.example.practice.web.order.OrderForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collections;

@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    // UserServiceのインスタンスを注入
    private final UserService userService;

    // ユーザーリストを表示するGETリクエスト
    @GetMapping
    public String showList(Model model) {
        // UserServiceを使って全てのユーザー情報を取得し、"userList"という名前でモデルに追加
        model.addAttribute("userList", userService.findAll());
        return "users/list"; // "users/list" テンプレートを返す
    }

    // ユーザー作成フォームを表示するGETリクエスト
    @GetMapping("/creationForm")
    public String showCreationForm(@ModelAttribute UserForm form) {
        return "users/creationForm"; // "users/creationForm" テンプレートを返す
    }

    // ユーザーを作成するPOSTリクエスト
    @PostMapping
    public String create(@Validated UserForm form, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return showCreationForm(form);
        }
        // ユーザー名の重複をチェック
        if (userService.isUsernameTaken(form.getUsername())) {
            bindingResult.rejectValue("username", "duplicate", "ユーザー名が既に使用されています");
            return "redirect:/users/creationForm?error"; // エラーパラメータを追加
        }
        userService.create(form.getUsername(), form.getPassword(), form.getAuthority());
        return "redirect:/users";
    }

}
