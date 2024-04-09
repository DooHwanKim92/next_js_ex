package com.rest.proj.domain.member.service;

import com.rest.proj.domain.member.entity.Member;
import com.rest.proj.domain.member.repository.MemberRepository;
import com.rest.proj.global.RsData.RsData;
import com.rest.proj.global.exceptions.GlobalException;
import com.rest.proj.global.jwt.JwtProvider;
import com.rest.proj.global.security.SecurityUser;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    private final JwtProvider jwtProvider;

    private final PasswordEncoder passwordEncoder;

    public Member join(String username, String password, String email) {
        Member member = Member.builder()
                .username(username)
                .password(password)
                .email(email)
                .build();

        String refreshToken = jwtProvider.genRefreshToken(member);
        member.setRefreshToken(refreshToken);

        memberRepository.save(member);

        return member;
    }

    public Optional<Member> findById (Long id) {
        return this.memberRepository.findById(id);
    }

    @AllArgsConstructor
    @Getter
    public static class AuthAndMakeTokensResponseBody {
        private Member member;
        private String accessToken;
        private String refreshToken;
    }

    @Transactional
    public RsData<AuthAndMakeTokensResponseBody> authAndMakeTokens(String username, String password) {
        // 회원 존재유무,
        Member member = this.memberRepository.findByUsername(username).orElseThrow(() -> new GlobalException("400-1", "해당 유저가 존재하지 않습니다."));

        // 비밀번호 일치 여부
        if (!passwordEncoder.matches(password, member.getPassword())) {
            throw  new GlobalException("400-2", "비밀번호가 일치 하지 않습니다.");
        }

        //리프레시토큰 가지고오기
        String refreshToken = member.getRefreshToken();

        // 회원데이터, 시간 설정 및 토큰 생성
        String accessToken = jwtProvider.genToken(member, 60 * 60 * 5);

        // 토큰 출력
        // System.out.println("accessToken :" + accessToken);
        return RsData.of("200-1", "로그인 성공", new AuthAndMakeTokensResponseBody(member, accessToken, refreshToken));
    }

    public SecurityUser getUserFromAccessToken(String accessToken) {
        Map<String, Object> payloadBody = jwtProvider.getClaims(accessToken);

        long id = (int) payloadBody.get("id");
        String username = (String) payloadBody.get("username");
        List<GrantedAuthority> authorities = new ArrayList<>();

        return new SecurityUser(
                id,
                username,
                "",
                authorities
        );
    }

    public boolean validateToken(String token) {
        return jwtProvider.verify(token);
    }

    public RsData<String> refreshAccessToken(String refreshToken) {
        Member member = memberRepository.findByRefreshToken(refreshToken).orElseThrow(() -> new GlobalException("400-1", "존재하지 않는 리프레시 토큰입니다."));

        String accessToken = jwtProvider.genAccessToken(member);

        return RsData.of("200-1", "토큰 갱신 성공", accessToken);
    }
}
