package figures;

import chessboard.ChessColor;
import chessboard.Coordinates;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

public class Rock extends FigureAbstract {

    public Rock(ChessColor color) {
        super(color);
    }

    @Override
    public List<Stream<Coordinates>> getMoves(Coordinates currentPosition) {
        List<Stream<Coordinates>> res = new LinkedList<>();
        int[][] directions = new int[][]{{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        for (int[] d : directions) {
            final int dx = d[0];
            final int dy = d[1];
            Stream<Coordinates> moves = Stream
                    .iterate(currentPosition, position -> new Coordinates(position.getX() + dx, position.getY() + dy)).skip(1);
            res.add(moves);
        }

        return res;
    }

    @Override
    public String getAsString() {
        return "Rck ";
    }
}
