package com.team8.kanban.domain.section;

import com.team8.kanban.global.exception.NotFoundException;
import com.team8.kanban.global.exception.error.SectionErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
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

    public List<SectionResponseDto> sortPos() {
        long start = sectionRepository.findHeadEntities().getId(); //head
        Section current = findById(start);//head cur
        List<Section> orderedList = new ArrayList<>();

        while (current != null) {
            orderedList.add(current);
            Long nextSection = current.getNext(); // getNext()는 다음 section 값을 반환하는 메소드
            if (nextSection == null) {
                break;
            }
            current = findById(nextSection);
        }
        return orderedList.stream().map(SectionResponseDto::new).toList();
    }

    // 2 3 4 5 6 -> 2 4 3 5 6
    @Transactional
    public List<SectionResponseDto> updatePos(Long selectedSectionId, Long changePos) {
        Section selectedSection = findById(selectedSectionId); //4
        Section changePosSection = findById(changePos);//3

        Section selectedPrevSection = findByNext(selectedSectionId);
        System.out.println(selectedPrevSection.getId());
        Section changePrevSection = findByNext(changePos);
        System.out.println(changePrevSection.getId());

        selectedPrevSection.updatePos(changePos);
        changePrevSection.updatePos(selectedSectionId);

        Long selectedSectionNext = selectedSection.getNext();
        Long changePosSectionNext = changePosSection.getNext();

        //update
        selectedSection.updatePos(changePosSectionNext);
        changePosSection.updatePos(selectedSectionNext);


        return sectionRepository.findAll().stream().map(SectionResponseDto::new).toList();
//        return sortPos();
    }

    @Override
    public List<SectionResponseDto> getAllSection() {
        return sectionRepository.findAll().stream().map(SectionResponseDto::new).toList();
    }

    private Section findById(Long id) {
        return sectionRepository.findById(id).orElseThrow(() -> new NotFoundException(SectionErrorCode.SECTION_NOT_FOUND));
    }

    private Section findByNext(Long pos) {
        System.out.println("없음");
        return sectionRepository.findByNext(pos).orElseThrow(() -> new NotFoundException(SectionErrorCode.SECTION_NOT_FOUND));
    }

}
