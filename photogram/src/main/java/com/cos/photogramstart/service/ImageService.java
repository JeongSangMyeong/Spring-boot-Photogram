package com.cos.photogramstart.service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.photogramstart.config.auth.PrincipalDetails;
import com.cos.photogramstart.domain.image.Image;
import com.cos.photogramstart.domain.image.ImageRepository;
import com.cos.photogramstart.web.dto.image.ImageUploadDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ImageService {

	private final ImageRepository imageRepository;

	@Transactional(readOnly = true)
	public List<Image> 인기사진() {

		return imageRepository.mPopular();
	}

	@Transactional(readOnly = true) // 영속성 컨텍스트 변경 감지를 해서, 더티체킹, flush(반영) X
	public List<Image> 이미지스토리(int principalId, Pageable pageable) {
		List<Image> images = imageRepository.mStory(principalId, pageable);

		// 2(cos) 로그인
		// images에 좋아요 상태 담기
		images.forEach((image)-> {
			image.setLikeCount(image.getLikes().size());
			image.getLikes().forEach((like) -> {
				if(like.getUser().getId() == principalId) { // 해당 이미지에 좋아요한 사람들을 찾아서 현재 로그인한 사람이 좋아요 한 것인지 비교
					image.setLikeState(true);
				}
			});
		});

		return images;
	}

	@Value("${file.path}") // application.yml 폴더 안에 file path
	private String uploadFolder;

	@Transactional
	public void 사진업로드(ImageUploadDto imageUploadDto, PrincipalDetails principalDetails) {
		UUID uuid = UUID.randomUUID(); // uuid
		String imageFileName = uuid + "_" + imageUploadDto.getFile().getOriginalFilename(); // 파일이름이 들어옴
		System.out.println("이미지 파일 이름 : " + imageFileName);

		Path imageFilePath = Path.of(uploadFolder + imageFileName);

		// 통신, I/O -> 예외 발생할 수 있음.
		try {
			Files.write(imageFilePath, imageUploadDto.getFile().getBytes());
		} catch (Exception e) {
			e.printStackTrace();
		}

		// image 테이블에 저장
		Image image = imageUploadDto.toEntity(imageFileName, principalDetails.getUser()); // 3d8aea60-701d-462f-985c-32049fede065_wrtFileImageView.jpg
		imageRepository.save(image);

		// System.out.println(imageEntity);
	}
}
