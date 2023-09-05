package com.example.practice.domain.authentication;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final Logger logger = LoggerFactory.getLogger(UserService.class); // ロガーのインスタンスを取得
    // UserRepositoryのインスタンスを注入
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    @PreAuthorize("hasAuthority('ADMIN')")
    // ユーザー一覧を取得するメソッド
    public List<User> findAll() {
        // userRepositoryを使ってデータベースからユーザー一覧を取得し、返す
        return userRepository.findAll();
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    public void create(String username, String password, String authority) {
        logger.debug("create_username:" + username); // ユーザー名をログ出力
        var encodedPassword = passwordEncoder.encode(password);
        logger.debug("create_encodedPassword:" + encodedPassword); // パスワードをエンコードしたものをログで出力
        logger.debug("create_authority:" + authority); // 権限をログ出力
        userRepository.insert(username, encodedPassword, authority);
    }
    // ユーザー名の重複をチェックするメソッド
    public boolean isUsernameTaken(String username) {
        // ユーザー名が重複しているかをチェック
        logger.debug("isUsernameTaken_username:" + username); // ユーザー名をログ出力
        Optional<User> existingUser = userRepository.findByUsername(username); // 既存のユーザーを取得
        logger.debug("isUsernameTaken_existingUser:" + existingUser); // 既存のユーザーをログ出力
        return existingUser.isPresent(); // 既存のユーザーが存在するかを返す
    }
}