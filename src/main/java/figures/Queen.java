package figures;

import chessboard.ChessColor;
import chessboard.Coordinates;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

public class Queen extends FigureAbstract {

    public Queen(ChessColor color) {
        super(color);
    }

    @Override
    public List<Stream<Coordinates>> getMoves(Coordinates currentPosition) {
        List<Stream<Coordinates>> res = new LinkedList<>();
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i == 0 && j == 0) continue;
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
        return "Qn ";
    }
}
