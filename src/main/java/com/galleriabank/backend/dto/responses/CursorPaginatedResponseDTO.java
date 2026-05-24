package com.galleriabank.backend.dto.responses;

import java.time.LocalDateTime;
import java.util.List;

public record CursorPaginatedResponseDTO<T>(
        List<T> items,
        LocalDateTime nextCursor,
        Boolean hasNext
) {
}
