package ium.pethub.dto.post.request;

import ium.pethub.domain.entity.Post;


import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

// Dto (Data Transfer Object)
@Getter
@Setter
@ToString
@NoArgsConstructor //기본 생성자
@AllArgsConstructor // 모든 필드를 매개변수로 가지는 생성자
public class PostRequestDto {


//
    private Long id;
//    private String postWriter;
    private String postTitle;
    private String postContents;
////    private int postHits;  //조회수
//    private LocalDateTime postCreatedTime;
//    private LocalDateTime postUpdatedTime;
//
//    private MultipartFile boardFile; // 사용자의 파일을 담는 용도
//    private String originalFileName; //원본 파일 이름
//    private String storedFileName; // 사용자 저장용 파일 이름
//    private int fileAttached; //파일 첨부 여부 (첨부 - 1 , 미첨부 - 0)

    public PostRequestDto(Post post) {
//        this.id = post.getId();
//        this.postWriter = post.getPostWriter();
        this.postTitle = post.getPostTitle();
        this.postContents = post.getPostContents();
//        this.postHits = post.getPostHits();

    }

    public static PostRequestDto toPostDto (Post post){

        PostRequestDto postRequestDto = new PostRequestDto();
        postRequestDto.setId(post.getId());
//        postRequestDto.setPostWriter(post.getPostWriter());

        postRequestDto.setPostTitle(post.getPostTitle());
        postRequestDto.setPostContents(post.getPostContents());
//        postRequestDto.setPostHits(post.getPostHits());

//        postRequestDto.setPostCreatedTime(post.getCreatedAt());
//        postRequestDto.setPostUpdatedTime(post.getModifiedAt());

        return postRequestDto;
    }

}
