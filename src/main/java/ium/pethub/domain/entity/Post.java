
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(nullable = false, name = "post_id")
//    private Long id;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "user_id")
//    private User user;
//



package ium.pethub.domain.entity;
//DB의 테이블 역할을 하는 클래스

        import ium.pethub.dto.post.request.PostRequestDto;
        import ium.pethub.dto.post.request.PostRequestDto;
        import lombok.Getter;
        import lombok.Setter;

        import javax.persistence.*;
@Entity
@Getter
@Setter
@Table(name= "post_table")

public class Post extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
//    @Column(length = 20,nullable = false)
//    private String postWriter;
    @Column
    private String postTitle;
//    @Column
//    private Integer postHits;

    @Column(length =500)
    private String postContents;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;


    public static Post toSaveEntity(PostRequestDto postRequestDto){

        Post postEntity = new Post();
//        postEntity.setPostWriter(postRequestDto.getPostWriter());
        postEntity.setPostTitle(postRequestDto.getPostTitle());
        postEntity.setPostContents(postRequestDto.getPostContents());
//        postEntity.setPostHits(0);
        return postEntity;
    }

    public static Post toUpdateEntity(PostRequestDto postRequestDto){

        Post postEntity = new Post();
//        postEntity.setId(postRequestDto.getId()); //아이디 항목 추가
//        postEntity.setPostWriter(postRequestDto.getPostWriter());
        postEntity.setPostTitle(postRequestDto.getPostTitle());
        postEntity.setPostContents(postRequestDto.getPostContents());
//        postEntity.setPostHits(postRequestDto.getPostHits()); //조회수 유지용
        return postEntity;
    }

}

