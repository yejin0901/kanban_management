package com.team8.kanban.comment;

import com.team8.kanban.domain.comment.Comment;
import com.team8.kanban.domain.user.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CommentTest {
    @Test
    @DisplayName("")
    void CommentUpdate(){
        //given
        Comment comment = new Comment("hello", new User("user", "1234"));
        String updateContent = "Bye";
        //when
        comment.updateContent(updateContent);
        //then
        assertEquals(updateContent,comment.getContent());
    }
}
