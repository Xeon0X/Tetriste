package model;

public class PreconfigMatrix {
    public static final boolean[][] emptyLineLeft(int sizeX, int sizeY) {
        boolean[][] matrix = new boolean[sizeX][sizeY];
        for (int x = 0; x < sizeX - 1; x++) {
            for (int y = sizeY - 4; y < sizeY; y++) {
                matrix[x][y] = true;
            }
        }
        return matrix;
    }
}
