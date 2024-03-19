package com.team8.kanban.domain.section;


import lombok.*;

@Getter
@AllArgsConstructor
@Builder
public class SectionResponseDto {
    private final String sectionName;

    public SectionResponseDto(Section section) {
        sectionName = section.getSectionName();
    }
}
