package com.knu.tubetalk.dto;

import java.util.List;

// 제네릭(<T>)을 써서 댓글뿐만 아니라 나중에도 재사용 가능하게 만듦
public class PageResponse<T> {
    private List<T> content;     // 실제 데이터 (댓글 목록)
    private int currentPage;     // 현재 페이지
    private int totalPages;      // 전체 페이지 수
    private long totalElements;  // 전체 데이터 개수

    public PageResponse(List<T> content, int currentPage, int totalPages, long totalElements) {
        this.content = content;
        this.currentPage = currentPage;
        this.totalPages = totalPages;
        this.totalElements = totalElements;
    }

    // Getters
    public List<T> getContent() { return content; }
    public int getCurrentPage() { return currentPage; }
    public int getTotalPages() { return totalPages; }
    public long getTotalElements() { return totalElements; }
}