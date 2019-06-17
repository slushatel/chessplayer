package figures;

import chessboard.ChessColor;
import chessboard.Coordinates;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

import static java.lang.Math.abs;

public class Knight extends FigureAbstract {

    public Knight(ChessColor color) {
        super(color);
    }

    @Override
    public List<Stream<Coordinates>> getMoves(Coordinates currentPosition) {
        List<Stream<Coordinates>> res = new LinkedList<>();
        int[] directionsX = new int[]{-2, -1, 1, 2};
        for (int i : directionsX) {
            final int dx = i;
            final int dy = 3 - abs(i);
            Stream<Coordinates> moves1 = Stream
                    .iterate(currentPosition, position -> new Coordinates(position.getX() + dx, position.getY() + dy)).limit(2).skip(1);
            res.add(moves1);
            Stream<Coordinates> moves2 = Stream
                    .iterate(currentPosition, position -> new Coordinates(position.getX() + dx, position.getY() - dy)).limit(2).skip(1);
            res.add(moves2);
        }
        return res;
    }

    @Override
    public String getAsString() {
        return "Knt";
    }
}
