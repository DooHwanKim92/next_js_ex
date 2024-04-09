package com.rest.proj.domain.member.controller;

import com.rest.proj.domain.member.dto.MemberDto;
import com.rest.proj.domain.member.entity.Member;
import com.rest.proj.domain.member.service.MemberService;
import com.rest.proj.global.RsData.RsData;
import com.rest.proj.global.rq.Rq;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/members")
@RequiredArgsConstructor
public class ApiV1MemberController {
    private final MemberService memberService;
    private final Rq rq;

    @Getter
    public static class LoginRequestBody {
        @NotBlank
        private String username;
        @NotBlank
        private String password;
    }

    @Getter
    @AllArgsConstructor
    public static class LoginResponseBody {
        private MemberDto memberDto;
    }

    @PostMapping("/login")
    public RsData<LoginResponseBody> login (@Valid @RequestBody LoginRequestBody loginRequestBody) {

        RsData<MemberService.AuthAndMakeTokensResponseBody> authAndMakeTokensRs = memberService.authAndMakeTokens(loginRequestBody.getUsername(), loginRequestBody.getPassword());

        // 토큰 쿠키에 등록
        rq.setCrossDomainCookie("accessToken", authAndMakeTokensRs.getData().getAccessToken());

        return RsData.of(
                authAndMakeTokensRs.getResultCode(),
                authAndMakeTokensRs.getMsg(),
                new LoginResponseBody(new MemberDto(authAndMakeTokensRs.getData().getMember()))
        );
    }

    @AllArgsConstructor
    @Getter
    public static class MeResponse {
        private final MemberDto memberDto;
    }


    @GetMapping("/me")
    public RsData<MeResponse> getMe () {
        Member member = rq.getMember();

        return RsData.of(
                "S-2",
                "성공",
                new MeResponse(new MemberDto(member))
        );
    }
}
