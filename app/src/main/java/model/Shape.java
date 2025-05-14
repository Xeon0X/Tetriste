package model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Shape {
    I(new boolean[][]{
            {false, false, false, false},
            {false, false, false, false},
            {true, true, true, true},
            {false, false, false, false},
    }, 4),

    O(new boolean[][]{
            {false, false, false, false},
            {false, true, true, false},
            {false, true, true, false},
            {false, false, false, false},
    }, 4),

    T(new boolean[][]{
            {false, true, false},
            {true, true, true},
            {false, false, false}
    }, 3),

    L(new boolean[][]{
            {false, true, false},
            {false, true, false},
            {false, true, true}
    }, 3),

    J(new boolean[][]{
            {false, true, false},
            {false, true, false},
            {true, true, false}
    }, 3);

    private final boolean[][] pattern;
    private final int size;
}
