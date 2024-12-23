/**
	2. 스토리 페이지
	(1) 스토리 로드하기
	(2) 스토리 스크롤 페이징하기
	(3) 좋아요, 안좋아요
	(4) 댓글쓰기
	(5) 댓글삭제
 */

// (0) 현재 로그인한 사용자 아이디
let principalId = $("#principalId").val();

// (1) 스토리 로드하기
let page = 0;
function storyLoad() {
	$.ajax({
		url: `/api/image?page=${page}`,
		dataType: "json"
	}).done(res => {
		res.data.forEach(image => {
			let storyItem = getStoryItem(image);
			$("#storyList").append(storyItem);
		});
	}).fail(error => {
		console.log("오류", error);
	})
}

storyLoad();

function getStoryItem(image) {
	let item = `<div class="story-list__item">
	<div class="sl__item__header">
		<div>
			<img class="profile-image" src="/upload/${image.user.profileImageUrl}"
				onerror="this.src='/images/person.jpeg'" />
		</div>
		<div onClick="location.href='/user/${image.user.id}';" style="cursor:pointer;">${image.user.username}</div>
	</div>

	<div class="sl__item__img">
		<img src="/upload/${image.postImageUrl}" />
	</div>

	<div class="sl__item__contents">
		<div class="sl__item__contents__icon">
			<button>
				<i class="${image.likeState ? "fas fa-heart active" : "far fa-heart"}" id="storyLikeIcon-${image.id}" onclick="toggleLike(${image.id})"></i>
			</button>
		</div>

		<span class="like"><b id="storyLikeCount-${image.id}">${image.likeCount} </b>likes</span>

		<div class="sl__item__contents__content">
			<p>${image.caption}</p>
		</div>

		<div id="storyCommentList-${image.id}">`;

	image.comments.forEach((comment) => {
		item += `<div class="sl__item__contents__comment" id="storyCommentItem-${comment.id}">
					<p>
						<b onClick="location.href='/user/${comment.user.id}';" style="cursor:pointer;">${comment.user.name} :</b> ${comment.content}
					</p>`;
					
					if(principalId == comment.user.id) {
						item += `<button onClick="deleteComment(${comment.id})">
									<i class="fas fa-times"></i>
								</button>`;
					}

		item += `</div>`;
	});

	item += `
		</div>

		<div class="sl__item__input">
			<input type="text" placeholder="댓글 달기..." id="storyCommentInput-${image.id}" />
			<button type="button" onClick="addComment(${image.id})">게시</button>
		</div>

	</div>
</div>`;

	return item;
}

// (2) 스토리 스크롤 페이징하기
$(window).scroll(() => {
	// 문서의 높이($(document).height()) - 윈도우 높이($(window).height()) = 스크롤 맨 아래!!! $(window).scrollTop()
	// console.log("윈도우 스크롤 탑", $(window).scrollTop());
	// console.log("문서 높이", $(document).height());
	// console.log("윈도우 높이", $(window).height());

	let checkNum = $(window).scrollTop() - ($(document).height() - $(window).height());
	// console.log(checkNum);

	if (checkNum < 1 && checkNum > -1) {
		page++;
		storyLoad();
	}
});


// (3) 좋아요, 좋아요 취소
function toggleLike(imageId) {
	let likeIcon = $(`#storyLikeIcon-${imageId}`);
	if (likeIcon.hasClass("far")) { // 좋아요

		$.ajax({
			type: "POST",
			url: `/api/image/${imageId}/likes`,
			dataType: "json",
		}).done(res => {

			let likeCounStr = $(`#storyLikeCount-${imageId}`).text();
			let likeCount = Number(likeCounStr) + 1;
			$(`#storyLikeCount-${imageId}`).text(likeCount);


			likeIcon.addClass("fas");
			likeIcon.addClass("active");
			likeIcon.removeClass("far");
		}).fail(error => {
			console.log("오류", error);
		});

	} else {	// 좋아요 취소

		$.ajax({
			type: "DELETE",
			url: `/api/image/${imageId}/likes`,
			dataType: "json",
		}).done(res => {

			let likeCounStr = $(`#storyLikeCount-${imageId}`).text();
			let likeCount = Number(likeCounStr) - 1;
			$(`#storyLikeCount-${imageId}`).text(likeCount);


			likeIcon.removeClass("fas");
			likeIcon.removeClass("active");
			likeIcon.addClass("far");
		}).fail(error => {
			console.log("오류", error);
		});

	}
}

// (4) 댓글쓰기
function addComment(imageId) {

	let commentInput = $(`#storyCommentInput-${imageId}`);
	let commentList = $(`#storyCommentList-${imageId}`);

	let data = {
		imageId: imageId,
		content: commentInput.val()
	}

	if (data.content === "") {
		alert("댓글을 작성해주세요.");
		return;
	}

	$.ajax({
		type: "POST",
		url: "/api/comment",
		data: JSON.stringify(data),
		contentType: "application/json; charset=utf-8",
		dataType: "JSON",
	}).done(res => {

		let comment = res.data;

		let content =
			`<div class="sl__item__contents__comment" id="storyCommentItem-${comment.id}"> 
				<p>
					<b>${comment.user.username} :</b>
					${comment.content}
				</p>
				<button onClick="deleteComment(${comment.id})"><i class="fas fa-times"></i></button>
			</div>`;

		commentList.prepend(content);
	}).fail(error => {
		console.log("오류", error.responseJSON.data.content);
		alert(error.responseJSON.data.content);
		
	})

	commentInput.val("");
}

// (5) 댓글 삭제
function deleteComment(commentId) {

	$.ajax({
		type:"DELETE",
		url:`/api/comment/${commentId}`,
		dataType:"JSON"
	}).done(res => {
		console.log("성공", res);
		$(`#storyCommentItem-${commentId}`).remove();
	}).fail(error => {
		console.log("오류", error);
	});

}







