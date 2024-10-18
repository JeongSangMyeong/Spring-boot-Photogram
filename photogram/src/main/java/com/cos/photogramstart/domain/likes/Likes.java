package com.cos.photogramstart.domain.likes;

import java.time.LocalDateTime;

import com.cos.photogramstart.domain.image.Image;
import com.cos.photogramstart.domain.user.User;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(uniqueConstraints = {
        @UniqueConstraint(name = "likes_uk", columnNames = { "imageId", "userId" }) // 유저가 이미지 하나만 like 할 수 있음
})
public class Likes { // N

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @JoinColumn(name = "imageId")
    @ManyToOne
    private Image image; // 1

    // 오류가 터지고
    @JoinColumn(name = "userId")
    @ManyToOne
    private User user; // 1

    private LocalDateTime createDate;

    @PrePersist
    public void createDate() {
        this.createDate = LocalDateTime.now();
    }

}
