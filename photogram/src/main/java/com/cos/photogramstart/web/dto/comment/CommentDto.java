package com.cos.photogramstart.web.dto.comment;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

// NotNull = null값 체크
// NotEmpty = 빈값이거나 null 체크
// NotBlank = 빈값이거나 null, 공백 체크
@Data
public class CommentDto {

    @NotBlank
    private String content;
    @NotNull
    private int imageId;

    // toEntity 필요 없음.

}
