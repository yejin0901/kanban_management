package com.team8.kanban.comment;

import com.team8.kanban.domain.comment.CommentService;
import com.team8.kanban.domain.comment.dto.CommentResponse;
import com.team8.kanban.domain.user.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.cache.RedisCacheManager;

import java.util.List;

@SpringBootTest
public class CommentFindAllCacheTest {
    @Autowired
    private CommentService commentService;
    @Autowired
    private RedisCacheManager cacheManager;
    @Test
    @DisplayName("캐시 x")
    void comments(){
        cacheManager.getCache("allComments").clear();
        // 첫 번째 호출: 데이터베이스에서 가져와서 캐시에 저장됨
        List<CommentResponse> first = commentService.getCommentsAllV3();
        // 두 번째 호출: 캐쉬에서 가져옴 - 쿼리가 나가지 않음
        List<CommentResponse> second = commentService.getCommentsAllV3();

        System.out.println(first.size()+" " +second.size());
        Assertions.assertEquals(first.size(),second.size());
    }
    @Test
    @DisplayName("캐쉬 o")
    void commentsCache(){
        cacheManager.getCache("allComments").clear();
        // 첫 번째 호출: 데이터베이스에서 가져와서 캐시에 저장됨
        List<CommentResponse> first = commentService.getCommentsAllV3Cache();
        // 두 번째 호출: 캐쉬에서 가져옴 - 쿼리가 나가지 않음
        List<CommentResponse> second = commentService.getCommentsAllV3Cache();

        System.out.println(first.size()+" " +second.size());
        Assertions.assertEquals(first.size(),second.size());
    }
}
