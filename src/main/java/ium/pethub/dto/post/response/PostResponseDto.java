package ium.pethub.dto.post.response;

import ium.pethub.domain.entity.Post;


import lombok.*;

import java.time.LocalDateTime;

// Dto (Data Transfer Object)
@Getter
@Setter
@ToString
@NoArgsConstructor //기본 생성자
@AllArgsConstructor // 모든 필드를 매개변수로 가지는 생성자
public class PostResponseDto {



    private Long postId;
//    private String postWriter;
//    private String postPass;
    private String postTitle;
    private String postContents;
//    private int postHits;  //조회수
//    private LocalDateTime postCreatedTime;
//    private LocalDateTime postUpdatedTime;

    public PostResponseDto(Post post) {
//        this.id = post.getId();
//        this.postWriter = post.getPostWriter();
        this.postTitle = post.getPostTitle();
        this.postContents = post.getPostContents();
//        this.postHits = post.getPostHits();

    }



}
