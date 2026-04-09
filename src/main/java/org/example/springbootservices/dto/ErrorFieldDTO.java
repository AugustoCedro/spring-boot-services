package org.example.springbootservices.dto;

import java.util.List;

public record ErrorFieldDTO(
        String field,
        List<String> messages
) {
}
