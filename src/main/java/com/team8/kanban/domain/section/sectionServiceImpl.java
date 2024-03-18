package com.team8.kanban.domain.section;

import com.team8.kanban.global.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class sectionServiceImpl implements SectionService {

    private final SectionRepository sectionRepository;

    @Transactional
    @Override
    public SectionResponseDto createSection(SectionRequestDto requestDto) {
        Section section = sectionRepository.save(new Section(requestDto.getColumnName()));
        return new SectionResponseDto(section);
    }

    @Transactional
    @Override
    public void deleteSection(Long sectionId) {
        Section section = findById(sectionId);
        sectionRepository.delete(section);
    }

    @Transactional
    @Override
    public SectionResponseDto updateSection(Long sectionId, SectionRequestDto requestDto) {
        Section section = findById(sectionId);
        section.update(requestDto.getColumnName());
        return new SectionResponseDto(section);
    }

    @Override
    public List<SectionResponseDto> updatePos(Long sectionId, Integer pos) {
        return null;
    }

    @Override
    public List<SectionResponseDto> getAllSection() {
        return sectionRepository.findAll().stream().map(SectionResponseDto::new).toList();
    }

    private Section findById(Long id) {
        return sectionRepository.findById(id).orElseThrow(() -> new NotFoundException("해당 섹션이 존재하지 않습니다."));
    }

}
