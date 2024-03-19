package com.team8.kanban.domain.board.dto;

import java.util.List;
import lombok.Getter;

@Getter
public class BoardInviteRequestDto {

    private List<Long> invitedUserIds;

}
