package com.example.practice.domain.authentication;

import lombok.RequiredArgsConstructor;  // lombokのRequiredArgsConstructorをインポート
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;  // Spring SecurityのUserDetailsをインポート
import org.springframework.security.core.userdetails.UserDetailsService;  // Spring SecurityのUserDetailsServiceをインポート
import org.springframework.security.core.userdetails.UsernameNotFoundException;  // ユーザーが見つからなかった場合の例外をインポート
import org.springframework.stereotype.Service;  // SpringのServiceアノテーションをインポート
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;
import java.util.List;

@Service  // サービスクラスであることを示す@Serviceアノテーション
@RequiredArgsConstructor  // コンストラクタインジェクションのためのアノテーション
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;  // UserRepositoryの依存を注入

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)  // ユーザー名でユーザー情報を検索
                .map(  // Optional型からカスタムなユーザー情報を作成
                        user -> new CustomUserDetails(
                                user.getUsername(),
                                user.getPassword(),
                                toGrantedAuthorityList(user.getAuthority())
                        )
                )
                .orElseThrow(  // ユーザーが見つからない場合は例外をスロー
                        () -> new UsernameNotFoundException(
                                "ユーザー情報の認証に失敗しました。 (username = '" + username + "')"
                        )
                );
    }
    private List<GrantedAuthority> toGrantedAuthorityList(User.Authority authority) {
        return Collections.singletonList(new SimpleGrantedAuthority(authority.name()));
    }
}
