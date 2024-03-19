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
        Section cur = findById(start);//head cur
        List<Section> orderedList = new ArrayList<>();

        while (cur != null) {
            orderedList.add(cur);
            Long nextSection = cur.getNext();
            if (nextSection == null) {
                break;
            }
            cur = findById(nextSection);
        }
        return orderedList.stream().map(SectionResponseDto::new).toList();
    }

    /* Linked List SWAP CASE(4)
    1. adjacent
    2, selected prev is null
    3. change prev is null
    4. normal
     */
    @Transactional
    public List<SectionResponseDto> updateNextpos(Long selectPos, Long changePos) {
        Section selectedSection = findById(selectPos);
        Section changePosSection = findById(changePos);

        Section selectedPrevSection = findByNext(selectPos);
        Section changePrevSection = findByNext(changePos);

        if (selectedPrevSection != null && selectedPrevSection.getId().equals(changePos)) {
            swapAdjacent(changePrevSection, selectedSection, changePosSection);
        } else if (changePrevSection != null && changePrevSection.getId().equals(selectPos)) {
            swapAdjacent(selectedPrevSection, changePosSection, selectedSection);
        } else {
            if (selectedPrevSection != null) {
                selectedPrevSection.updatePos(changePos);
            }
            if (changePrevSection != null) {
                changePrevSection.updatePos(selectPos);
            } else {
                Long tempNext = selectedSection.getNext();
                changePosSection.updatePos(tempNext);
                selectedSection.updatePos(null);
            }
            Long tempNext = selectedSection.getNext();
            selectedSection.updatePos(changePosSection.getNext());
            changePosSection.updatePos(tempNext);
        }
       return sortPos();
    }

    private void swapAdjacent(Section prev, Section a, Section b) {
        b.updatePos(a.getNext());
        prev.updatePos(a.getId());
        a.updatePos(b.getId());
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
        return sectionRepository.findByNext(pos).orElse(null);
    }



}
