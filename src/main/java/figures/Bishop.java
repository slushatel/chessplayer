package figures;

import chessboard.ChessColor;
import chessboard.Coordinates;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

public class Bishop extends FigureAbstract {

    public Bishop(ChessColor color) {
        super(color);
    }

    @Override
    public List<Stream<Coordinates>> getMoves(Coordinates currentPosition) {
        List<Stream<Coordinates>> res = new LinkedList<>();
        int[] directions = new int[]{-1, 1};
        for (int i : directions) {
            for (int j : directions) {
                final int dx = i;
                final int dy = j;
                Stream<Coordinates> moves = Stream
                        .iterate(currentPosition, position -> new Coordinates(position.getX() + dx, position.getY() + dy)).skip(1);
                res.add(moves);
            }
        }
        return res;
    }

    @Override
    public String getAsString() {
        return "Bp ";
    }
}
