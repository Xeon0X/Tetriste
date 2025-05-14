package model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Shape {
    I(new boolean[][]{
            {true, true, true, true}
    }),
    O(new boolean[][]{
            {true, true},
            {true, true}
    }),
    T(new boolean[][]{
            {false, true, false},
            {true, true, true}
    }),
    L(new boolean[][]{
            {true, false},
            {true, false},
            {true, true}
    }),
    J(new boolean[][]{
            {false, true},
            {false, true},
            {true, true}
    });

    private final boolean[][] pattern;
}
