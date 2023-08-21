package com.example.spring.app.vo;

import com.example.spring.app.repository.model.Comment;
import com.example.spring.app.repository.model.PostEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Date;
import java.util.List;

@Getter
@Builder
@RequiredArgsConstructor
public class PostDetailResponse {
    private final Long postSeq;
    private final String postTitle;
    private final String postContent;
    private final Date createDate;
    private final String createUserId;

    private final List<Comment> comments;

    public static PostDetailResponse of(PostEntity entity, List<Comment> comments) {
        return PostDetailResponse.builder()
                .postSeq(entity.getPostSeq())
                .postTitle(entity.getPostTitle())
                .postContent(entity.getPostContent())
                .createDate(entity.getCreateDate())
                .comments(!comments.isEmpty() ? comments : List.of(defaultComment())).build();
    }

    private static Comment defaultComment() {
        return new Comment(
                null, "there is no Comment", null, null, null
        );
    }
}
