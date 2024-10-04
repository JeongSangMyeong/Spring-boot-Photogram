package com.cos.photogramstart.web.dto.user;


import com.cos.photogramstart.domain.user.User;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserUpdateDto {
	@NotBlank
	private String name; // 필수
	@NotBlank
	private String password; // 필수
	private String website;
	private String bio;
	private String phone;
	private String gender;
	
	// 조금 위험함. 코드 수정이 필요할 예정
	public User toEntity() {
		return User.builder()
			.name(name)	// 이름 기재 안했으면 문제임, validation 체크
			.password(password)	// 패스워드 기재 안했으면 문제임, validation 체크.
			.website(website)
			.bio(bio)
			.phone(phone)
			.gender(gender)
			.build();
	}
}
