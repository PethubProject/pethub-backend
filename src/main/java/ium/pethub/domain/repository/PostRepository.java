package ium.pethub.domain.repository;


import ium.pethub.domain.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post,Long> {

// 수행코자하는 작업의 sql문은 아래와 같다.
// update post_table set post_hits = post_hits+1 where id=?

//
//    @Modifying
//    //수정 쿼리를 작성할 때 붙여줘야하는 어노테이션
//    @Query(value = "update PostEntity b set b.postHits=b.postHits+1 where b.id=:id")
//        //PostEntity를 b로 칭함
//        // :id는 변하는, 파라미터를 의미하고 아래의 @Param("id")에서 받아오는 값임
//    void updateHits(@Param("id") Long id);

}