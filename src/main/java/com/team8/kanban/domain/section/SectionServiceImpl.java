package com.team8.kanban.domain.section;

import com.team8.kanban.domain.board.entity.Board;
import com.team8.kanban.domain.board.repository.BoardRepository;
import com.team8.kanban.global.exception.customException.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.team8.kanban.global.exception.ErrorCode.SECTION_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class SectionServiceImpl implements SectionService {

    private final SectionRepository sectionRepository;
    private final BoardRepository boardRepository;

    @Transactional
    @Override
    public SectionResponseDto createSection(SectionRequestDto requestDto, Long boardId) {
        Board board = validateBoard(boardId);
        Section lastSection = sectionRepository.findLastSection();
        //처음 섹션인 경우
        if (lastSection == null) {
            Section createSection = new Section(requestDto, board);
            Section firstSection = sectionRepository.save(createSection);
            return new SectionResponseDto(firstSection);
        }
        Section createSection = new Section(requestDto, board);
        Section NotfirstSection = sectionRepository.save(createSection);
        lastSection.updateNextPos(createSection.getId());
        return new SectionResponseDto(NotfirstSection);
    }

    @Transactional
    @Override
    public void deleteSection(Long sectionId, Long boardId) {
        if (!validateSectionInBoard(boardId, sectionId)) {
            throw new IllegalArgumentException("해당 섹션이 보드에 존재하지 않습니다.");
        }
        Section deleteSection = findById(sectionId);
        Section NullSection = findByNext(null);
        Section beforeNullSection = findByNext(NullSection.getId());
        sectionRepository.delete(deleteSection);
        beforeNullSection.updateNextPos(null);
    }

    @Transactional
    @Override
    public SectionResponseDto updateSection(Long sectionId, SectionRequestDto requestDto, Long boardId) {
        if (!validateSectionInBoard(boardId, sectionId)) {
            throw new IllegalArgumentException("해당 섹션이 보드에 존재하지 않습니다.");
        }
        Section section = findById(sectionId);
        section.updateName(requestDto.getSectionName());
        return new SectionResponseDto(section);
    }

    public List<SectionCardResponseDto> sortSection(Long boardId) {
        validateBoard(boardId);
        Long start = sectionRepository.findHeadEntities().getId(); //head
        SectionProjection cur = sectionRepository.findSectionById(start);//head cur
        List<SectionProjection> orderedList = new ArrayList<>();

        while (cur != null) {
            orderedList.add(cur);
            Long nextSection = cur.getNext();
            if (nextSection == null) {
                break;
            }
            cur = sectionRepository.findSectionById(nextSection);
        }
        List<SectionResponseDto> sectionResponseDto = orderedList.stream().map(SectionResponseDto::new).toList();
        return getAllSections(sectionResponseDto);
    }

    public List<SectionCardResponseDto> getAllSections(List<SectionResponseDto> sectionList){
        return sectionRepository.findSectionAndCard(sectionList);
    }

    /* Linked List SWAP CASE(4)
    1. adjacent
    2, selected prev is null
    3. change prev is null
    4. normal
     */
    @Transactional
    public List<SectionCardResponseDto> updateNextpos(Long selectPos, Long changePos, Long boardId) {
        validateBoard(boardId);
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
                selectedPrevSection.updateNextPos(changePos);
            }
            if (changePrevSection != null) {
                changePrevSection.updateNextPos(selectPos);
            } else {
                Long tempNext = selectedSection.getNext();
                changePosSection.updateNextPos(tempNext);
                selectedSection.updateNextPos(null);
            }
            Long tempNext = selectedSection.getNext();
            selectedSection.updateNextPos(changePosSection.getNext());
            changePosSection.updateNextPos(tempNext);
        }
        return sortSection(boardId);
    }

    private void swapAdjacent(Section prev, Section a, Section b) {
        b.updateNextPos(a.getNext());
        prev.updateNextPos(a.getId());
        a.updateNextPos(b.getId());
    }


    private Section findById(Long id) {
        return sectionRepository.findById(id).orElseThrow(() -> new NotFoundException(SECTION_NOT_FOUND));
    }


    private Section findByNext(Long pos) {
        return sectionRepository.findByNext(pos).orElse(null);
    }

    private Board validateBoard(Long boardId) {
        return boardRepository.findById(boardId).orElseThrow(() -> new IllegalArgumentException("해당 보드가 존재하지 않습니다."));
    }

    private Boolean validateSectionInBoard(Long boardId, Long sectionId) {
        return sectionRepository.existsByBoard_BoardIdAndId(boardId, sectionId);
    }

}
