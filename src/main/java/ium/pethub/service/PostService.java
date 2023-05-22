package ium.pethub.service;

import ium.pethub.dto.post.request.PostRequestDto;
import ium.pethub.domain.entity.Post;
import ium.pethub.domain.repository.PostRepository;
import ium.pethub.dto.post.response.PostResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

//Dto -> Entity (Entity Class)
//Entity -> Dto (Dto Class)

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public PostResponseDto save(PostRequestDto postRequestDto) {
        Post post = Post.toSaveEntity(postRequestDto);
        postRepository.save(post);
        
        return new PostResponseDto(post);
    }

    public List<PostRequestDto> findAll() {

        List<Post> postList = postRepository.findAll();
        //repository 단에선 dto가 아닌 entity로 데이터 접근!
        //그래서 dto로 변환하여야함

        List<PostRequestDto> postRequestDtoList = new ArrayList<>();

        for (Post post: postList){

            postRequestDtoList.add(PostRequestDto.toPostDto(post));
        }

        return postRequestDtoList;

    }

    @Transactional
    public void updateHits(Long id) {
//jpa에서 지원하지 않는 특수 기능이므로 별도로 메서드를 구현해줄 것

//        postRepository.updateHits(id);

    }

    public PostResponseDto findById(Long id) {
        Optional<Post> optionalPostEntity=postRepository.findById(id);

        if (optionalPostEntity.isPresent()){  //null값이 아니라면
            Post post = optionalPostEntity.get(); //옵셔널 객체에서 데이터 가져옴
            PostRequestDto postRequestDto = PostRequestDto.toPostDto(post); //Entity -> Dto

            return new PostResponseDto(post);

        }else{

            return null;
        }
    }

    public PostResponseDto update(PostRequestDto postRequestDto){

        //spring data jpa에선 save 메서드로 update 기능을 구현 할 수 있다.
        //인자에 id값이 존재하면 update 기능으로 작동한다 (다형성)

        Post post = Post.toUpdateEntity(postRequestDto);
        //id를 포함해 entity로 바꿔주어야 하기에, 변환 메서드를 업데이트 버전으로 만들어준다.

        postRepository.save(post);

        return new PostResponseDto(post);

    }

    public void delete(Long id){

        postRepository.deleteById(id);
    }

    public Page<PostRequestDto> paging(Pageable pageable){

        int page = pageable.getPageNumber()-1; //사용자가 보고 싶은 페이지
        //findAll에서 page는 0부터 시작해서 1빼줌

        int pageLimit = 5; //한 페이지에 보여줄 글 개수

        Page<Post> posts= postRepository.findAll(PageRequest.of(page,pageLimit, Sort.by(Sort.Direction.DESC,"id")));
        //어떤 페이지를, 게시물 몇개를 불러와서, 어떤 정렬상태로

        //한페이지랑 3개의 글을 보여주고 id 기준 내림차순 정렬

        Page<PostRequestDto> postDtoS = posts.map(post->new PostRequestDto(post));

        return postDtoS;
    }

}
