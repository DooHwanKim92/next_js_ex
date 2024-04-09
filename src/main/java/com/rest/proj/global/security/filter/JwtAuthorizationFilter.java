package com.rest.proj.global.security.filter;

import com.rest.proj.domain.member.entity.Member;
import com.rest.proj.domain.member.service.MemberService;
import com.rest.proj.global.RsData.RsData;
import com.rest.proj.global.jwt.JwtProvider;
import com.rest.proj.global.rq.Rq;
import com.rest.proj.global.security.SecurityUser;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {

//    private final JwtProvider jwtProvider;

    private final MemberService memberService;

    private final Rq rq;

//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException, IOException {
//        // 헤더에서 Authorization 값을 가져온다.
//        String bearerToken = request.getHeader("Authorization");
//
//        if (bearerToken != null) {
//            String token = bearerToken.substring("Bearer ".length());
//
//            if (jwtProvider.verify(token)) {
//                Map<String, Object> claims = jwtProvider.getClaims(token);
//                long id = (int)claims.get("id");
//
//                Member member = memberService.findById(id).orElseThrow();
//
//                forceAuthentication(member);
//            }
//        }
//
//        filterChain.doFilter(request, response);
//    }
//
//    // 강제로 로그인 처리하는 메소드
//    private void forceAuthentication(Member member) {
//        User user = new User(member.getUsername(), member.getPassword(), member.getAuthorities());
//
//        // 스프링 시큐리티 객체에 저장할 authentication 객체를 생성
//        UsernamePasswordAuthenticationToken authentication =
//                UsernamePasswordAuthenticationToken.authenticated(
//                        user,
//                        null,
//                        member.getAuthorities()
//                );
//
//        // 스프링 시큐리티 내에 우리가 만든 authentication 객체를 저장할 context 생성
//        SecurityContext context = SecurityContextHolder.createEmptyContext();
//        // context에 authentication 객체를 저장
//        context.setAuthentication(authentication);
//        // 스프링 시큐리티에 context를 등록
//        SecurityContextHolder.setContext(context);
//    }

    @SneakyThrows
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) {
        // 로그인, 로그아웃 제외
        if (request.getRequestURI().equals("/api/v1/members/login") || request.getRequestURI().equals("/api/v1/members/logout")) {
            filterChain.doFilter(request, response);
            return;
        }
        String accessToken = rq.getCookieValue("accessToken", "");

        if (!accessToken.isBlank()) {
            if (!memberService.validateToken(accessToken)) {
                String refreshToken = rq.getCookieValue("refreshToken", "");

                RsData<String> rs = memberService.refreshAccessToken(refreshToken);
                accessToken = rs.getData();
                rq.setCrossDomainCookie("accessToken", accessToken);
            }
            SecurityUser securityUser = memberService.getUserFromAccessToken(accessToken);
            rq.setLogin(securityUser);
        }

        filterChain.doFilter(request, response);
    }
}
