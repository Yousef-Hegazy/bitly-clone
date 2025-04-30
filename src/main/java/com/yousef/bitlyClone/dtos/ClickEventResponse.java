package com.yousef.bitlyClone.dtos;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record ClickEventResponse(
        LocalDate clickDate,
        Long count
) {
}
