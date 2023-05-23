package ium.pethub.controller;

import ium.pethub.dto.common.ResponseDto;

import ium.pethub.dto.post.request.PostRequestDto;
import ium.pethub.dto.post.response.PostResponseDto;
import ium.pethub.dto.user.reponse.UserInfoResponseDto;
import ium.pethub.service.PostService;
import ium.pethub.util.UserContext;
import ium.pethub.util.ValidToken;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.util.List;


@Controller
@RequiredArgsConstructor
@RequestMapping("/api/post")
public class PostController {

    //TODO : 코드 공유 가능하도록 깔끔하게 + id 가독성 좋도록, 전체적으로 api구분이 너무 안됨... 

    private final PostService postService;

//    @GetMapping("/save")
//
//    public String saveForm(){
//
//        return "save";
//    }

    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody PostRequestDto postDto){

        PostResponseDto responseDto =postService.save(postDto);

        return ResponseEntity.ok().body(ResponseDto.of(HttpStatus.OK, responseDto));
    }


    // ????
    @GetMapping("/")
    public ResponseEntity<?> findAll(){
        //DB에서 전체 게시글 데이터를 가져와 list.html에 보여줌

        List<PostRequestDto> postDtoList = postService.findAll();
        //게시물 Dto 원소들을 가지는 LIST

        return ResponseEntity.ok().body(ResponseDto.of(HttpStatus.OK, postDtoList));
    }

    //TODO: nickname검색 필요 : gui에서 닉네임으로 접근 시
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id, @PageableDefault(page=1) Pageable pageable){

        //경로상의 데이터를 파싱해올 때에는 PathVariable 어노테이션 사용
        //html에 전달해야 하므로 model객체를 가져옴

        //해당 id의 게시물 조회수를 1올리고 게시물 내용 데이터를 가져와서
        //detail.html에 출력할 것이다.

//        postService.updateHits(id);
        PostResponseDto postDto=postService.findById(id);

        return ResponseEntity.ok().body(ResponseDto.of(HttpStatus.OK, postDto));
    }

    //수정 폼 생성
    @GetMapping("/update/{id}")

    public ResponseEntity<?> updateForm(@PathVariable Long id){

        PostResponseDto postDto = postService.findById(id);


        return ResponseEntity.ok().body(ResponseDto.of(HttpStatus.OK, postDto));

    }

    //수정 api
    //TODO: putmapping
    @PostMapping("/update")

    public ResponseEntity<?> update(@RequestBody PostRequestDto prePostDto){

        PostResponseDto postDto=postService.update(prePostDto);

        return ResponseEntity.ok().body(ResponseDto.of(HttpStatus.OK, postDto));

        //수정시, 수정이 반영된 상세 페이지를 보여줄 것이다.

    }

    @GetMapping("/delete/{id}")

    public ResponseEntity<?> delete(@PathVariable Long id){

        postService.delete(id);

        return ResponseEntity.ok().body(ResponseDto.of("게시물 삭제 성공 하였습니다."));

    }

    @GetMapping("/paging")

    // /post/paging?page=1

    //아래 메서드의 pageable이 쿼리스트링의 페이지 번호를 받아준다. 기본 값은 1이다.
    public ResponseEntity<?> paging(@PageableDefault(page=1) Pageable pageable){

        // pageable.getPageNumber();

        Page<PostRequestDto> postList = postService.paging(pageable);


        return ResponseEntity.ok().body(ResponseDto.of(HttpStatus.OK, postList));

    }

}