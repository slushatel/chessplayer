package chessboard;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class ChessboardSquare {
    private Coordinates coordinates;
    private String name;
    private ChessColor color;
    private static List<Character> Letters = new ArrayList<>();
    private static final String EMPTY_PRESENTATION = "   ";

    static {
        IntStream.rangeClosed('A', 'Z').forEach(c -> Letters.add((char) c));
    }

    public ChessboardSquare(Coordinates coordinates) {
        this.coordinates = coordinates;
        color = (coordinates.getX() + coordinates.getY()) % 2 == 0 ? ChessColor.BLACK : ChessColor.WHITE;
        name = "" + Letters.get(coordinates.getX() - 1) + coordinates.getY();
    }

    @Override
    public String toString() {
        return name;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public String getName() {
        return name;
    }

    public ChessColor getColor() {
        return color;
    }

    public String getAsString() {
        return EMPTY_PRESENTATION;
    }
}
