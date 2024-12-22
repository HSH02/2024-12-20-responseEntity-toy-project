package com.mysite.responseentitytoyproject.domain.user.controller;

import com.mysite.responseentitytoyproject.domain.user.dto.response.UserResponse;
import com.mysite.responseentitytoyproject.domain.user.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest             // 전체 스프링 부트 애플리케이션 컨텍스트를 로드하여 테스트
@AutoConfigureMockMvc       // MockMvc를 자동으로 구성
class UserControllerTest {

    // MockMvc: 웹 애플리케이션을 실제로 실행하지 않고도 컨트롤러를 테스트할 수 있게 해주는 도구
    @Autowired
    private MockMvc mockMvc;                // 테스트용 가짜 브라우저

    // MockitoBean: Mockito를 사용해 가짜 서비스 객체를 만듦
    @MockitoBean
    private UserService userService;        // 가짜 서비스

    @Test
    @DisplayName("전체_사용자_조회_테스트")
    void findAll() throws Exception {
        // given (준비 단계): 테스트에 필요한 데이터와 환경을 설정
        List<UserResponse> userList = new ArrayList<>();  // 사용자 목록을 담을 리스트 생성

        // UserResponse 객체 생성 (빌더 패턴 사용)
        UserResponse user = UserResponse.builder()
                .id(1L)                     // 사용자 ID 설정
                .name("테스트 유저")        // 사용자 이름 설정
                .build();                   // 객체 생성

        userList.add(user);  // 생성한 사용자를 리스트에 추가

        // when: userService.getAllUsers() 메서드가 호출되면
        // 위에서 만든 userList를 반환하도록 미리 설정 (모킹)
        when(userService.getAllUsers()).thenReturn(userList);

        // then (실행 및 검증 단계)
        mockMvc.perform(get("/api/users"))  // GET /api/users 엔드포인트 호출
                .andExpect(status().isOk())     // HTTP 200 OK 상태 코드 확인
                .andDo(print());                // 테스트 결과를 콘솔에 출력

    }


}