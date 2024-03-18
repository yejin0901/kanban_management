package com.team8.kanban.domain.section;


public class SectionResponseDto {
    private final String sectionName;

    public SectionResponseDto(Section section) {
        sectionName = section.getColumnName();
    }
}
