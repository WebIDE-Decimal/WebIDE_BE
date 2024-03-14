package com.example.demo.model;

import jakarta.persistence.*;
import java.time.LocalDateTime; // LocalDateTime을 사용하기 위한 import 추가
import java.util.ArrayList;
import java.util.List;

@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 게시글 ID
    private Long authorId; // 작성자 ID
    private String title; // 제목
    private String content; //내용
    private Integer numberOfParticipants; // 모집 인원
    private String recruitmentStatus; // 모집 상태 (예: "OPEN", "CLOSED")
    private String targetAudience; // 모집 대상
    private LocalDateTime createdAt; // 작성 시각
    private Boolean isDeleted; // 삭제 여부

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAuthorId() { return authorId; }  // 작성자 ID

    public void setAuthorId(Long authorId) { this.authorId = authorId; } // 작성자 ID

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getNumberOfParticipants() { return numberOfParticipants; } // 모집인원 반환

    public void setNumberOfParticipants(Integer numberOfParticipants) {
        this.numberOfParticipants = numberOfParticipants; }                     // 모집인원 설정

    public String getRecruitmentStatus() { return recruitmentStatus; }        // 모집 상태 반환 ex) OPEN / CLOSE

    public void setRecruitmentStatus(String recruitmentStatus)
        { this.recruitmentStatus = recruitmentStatus; }                 // 모집 상태 설정

    public String getTargetAudience() { return targetAudience; }        // 모집 대상 반환

    public void setTargetAudience(String targetAudience) { this.targetAudience = targetAudience; } // 모집대상 설정

    public LocalDateTime getCreatedAt() { return createdAt; } // 게시글 작성 시간 반환

    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; } // 게시글 작성 시각 설정

    public Boolean getIsDeleted() { return isDeleted; } // 게시글 삭제 여부 반환

    public void setIsDeleted(Boolean isDeleted) { this.isDeleted = isDeleted; } // 게시글 삭제 여부 설정


    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}