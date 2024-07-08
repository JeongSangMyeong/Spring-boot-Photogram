package com.cos.photogramstart.web;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.handler.ex.CustomValidationException;
import com.cos.photogramstart.service.AuthService;
import com.cos.photogramstart.web.dto.auth.SignupDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor // final 필드를 DI할 때 사용
@Controller // 1.IoC 2.파일을 리턴하는 컨트롤러
public class AuthController {

	private static final Logger log = LoggerFactory.getLogger(AuthController.class);

	private final AuthService authService;

	// 컨트롤러가 붙어있으면 스프링이 컨테이너에 관리하고 있는 메모리에 로드하는데, 객체 생성을 위해서는 생성자를 실행시켜야됨 그래서 됨.
	// 생성자를 실행 하려고 하는데 자기가 이미 IoC에 등록한 애들 중에서 Service 타입이 있는지 확인하고 있으면 집어넣어줌. 그걸 의존성
	// 주입 근데 좀 귀찮아서 위에 private AuthService authService;를 조작함
//	public AuthController(AuthService authService) {
//		this.authService = authService;
//	}

	@GetMapping("/auth/signin")
	public String signinForm() {

		return "auth/signin";
	}

	@GetMapping("/auth/signup")
	public String signupForm() {

		return "auth/signup";
	}

	// 회원가입 버튼 -> /auth/signup -> auth/signin
	// 회원가입 버튼 x
	@PostMapping("/auth/signup")
	public String signup(@Valid SignupDto signupDto, BindingResult bindingResult) { // key = value
		// (x-www-form-urlencoded)

		if (bindingResult.hasErrors()) {
			Map<String, String> errorMap = new HashMap<>();

			for (FieldError error : bindingResult.getFieldErrors()) {
				errorMap.put(error.getField(), error.getDefaultMessage());
				System.out.println("=========================");
				System.out.println(error.getDefaultMessage());
				System.out.println("=========================");
			}
			throw new CustomValidationException("유효성 검사 실패함", errorMap);
		} else {
			// User <- SignupDto
			User user = signupDto.toEntity();
			log.info(user.toString());
			User userEntity = authService.회원가입(user);
			System.out.println(userEntity);
			return "auth/signin";

		}

	}

}