package com.cos.photogramstart.web.dto.subscribe;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class SubscribeDto {

    private int id;
    private String username;
    private String profileImageUrl;
    private Integer subscribeState; // Maria DB에서 int 리턴을 못받음.
    private Integer equalUserState;

}
