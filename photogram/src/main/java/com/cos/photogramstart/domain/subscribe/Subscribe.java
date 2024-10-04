package com.cos.photogramstart.domain.subscribe;

import java.time.LocalDateTime;

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
@Table(uniqueConstraints = {@UniqueConstraint(name="subscribe_uk", columnNames = { "fromUserId", "toUserId" })}) // 실제 데이터의 컬럼 명을 넣어야함.
public class Subscribe {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@JoinColumn(name = "fromUserId")	// 이렇게 컬럼 명을 만들어라. 다르게 만들지 말고
	@ManyToOne
	private User fromUser; // 구독 하는애

	@JoinColumn(name = "toUserId")
	@ManyToOne
	private User toUser; // 구독 받는애

	private LocalDateTime createDate;

	@PrePersist
	public void createDate() {
		this.createDate = LocalDateTime.now();
	}
}
