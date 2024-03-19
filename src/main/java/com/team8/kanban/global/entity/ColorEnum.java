package com.team8.kanban.global.entity;

import lombok.Getter;

@Getter
public enum ColorEnum {
    YELLOW(0),
    RED(1),
    GREEN(2),
    BLUE(3),
    GRAY(4),
    CYAN(5),
    BLACK(6),
    MAGENTA(7);

    final int colorCode;

    ColorEnum(int colorCode) {
        this.colorCode = colorCode;
    }
}