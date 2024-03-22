package com.team8.kanban.domain.section;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class SectionProjection {
    private Long sectionId;
    private String sectionName;
    private Long next;
}
