package com.example.spring.vo;

import com.example.spring.repositories.entities.AccountEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Getter
@Builder
@RequiredArgsConstructor
public class PostInfoResponse {
    private final long maxPageCount;
    private final long postCount;
    private final List<PostInfo> postInfos;

    @Getter
    @Builder
    @RequiredArgsConstructor
    public static class PostInfo {
        private final Long postSeq;
        private final String postTitle;
        private final Date createDate;
        private final String createUserId;
    }

}