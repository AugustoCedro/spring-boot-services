package org.example.springbootservices.dto;

import java.util.List;

public record PagedResponseDTO<T>(
        Integer page,
        Integer size,
        Integer total,
        Integer totalPages,
        List<T> content
) {

    public PagedResponseDTO(Integer page,Integer size,Integer total, List<T> content){
        this(
                page,
                size,
                total,
                size <= 0 ? 0 : (int) Math.ceil((double) total / size),
                content
        );
    }
}