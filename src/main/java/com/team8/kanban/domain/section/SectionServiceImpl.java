package com.team8.kanban.domain.section;

import com.team8.kanban.global.exception.NotFoundException;
import com.team8.kanban.global.exception.error.SectionErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.function.LongToIntFunction;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SectionServiceImpl implements SectionService {

    private final SectionRepository sectionRepository;

    @Transactional
    @Override
    public SectionResponseDto createSection(SectionRequestDto requestDto) {
        Section section = sectionRepository.save(new Section(requestDto));
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
        section.updateName(requestDto.getSectionName());
        return new SectionResponseDto(section);
    }

    public List<SectionResponseDto> sortPos(Long sectionId, Integer pos) {
        long start = 0;
        Optional<Section> section = sectionRepository.findByPrev(null);
        if(section.isPresent()){
            start = section.get().getId();

        }

    }

    // 1 2 3 4 5 -> 1 2 5 3 4
    public List<SectionResponseDto> updatePos(Long sectionId, Integer pos) {
        Section originSection = findById(sectionId);
        Section changeSection = findByPos(pos);

        Long prev = changeSection.getPrev();
        Long next = changeSection.getNext();

        sectionRepository.save(originSection.)

    }

    @Override
    public List<SectionResponseDto> getAllSection() {
        return sectionRepository.findAll().stream().map(SectionResponseDto::new).toList();
    }

    private Section findById(Long id) {
        return sectionRepository.findById(id).orElseThrow(() -> new NotFoundException(SectionErrorCode.SECTION_NOT_FOUND));
    }

    private Section findByPos(Integer pos) {
        return sectionRepository.findByPrev(pos).orElseThrow(() -> new NotFoundException(SectionErrorCode.SECTION_NOT_FOUND));
    }

}
