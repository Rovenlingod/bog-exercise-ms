package com.example.bogexercisems.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageDTO<T> {
    private Integer currentPage;
    private Integer totalPages;
    private Long totalElements;
    private List<T> content;
}
