package com.team8.kanban.domain.section;


import lombok.*;

@Getter
@AllArgsConstructor
@Builder
public class SectionResponseDto {
    private Long sectionId;
    private String sectionName;

    public SectionResponseDto(Section section) {
        sectionId = section.getId();
        sectionName = section.getSectionName();
    }

    public SectionResponseDto(SectionProjection section) {
        sectionId = section.getSectionId();
        sectionName = section.getSectionName();
    }
}
