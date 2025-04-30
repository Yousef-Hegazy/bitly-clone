package com.yousef.bitlyClone.mappers;

import com.yousef.bitlyClone.dtos.ClickEventResponse;
import com.yousef.bitlyClone.models.ClickEvent;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class ClickEventMapper {

    private ClickEventResponse fromEntryToClickEventResponse(Map.Entry<LocalDate, Long> entry) {
        return ClickEventResponse.builder()
                .clickDate(entry.getKey())
                .count(entry.getValue())
                .build();
    }

    public List<ClickEventResponse> toClickEventResponse(List<ClickEvent> clickEvent) {
        return clickEvent.stream().collect(
                        Collectors.groupingBy(ce -> ce.getClickedAt().toLocalDate(), Collectors.counting())
                ).entrySet()
                .stream()
                .map(this::fromEntryToClickEventResponse)
                .toList();
    }
}
