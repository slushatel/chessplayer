package figures;

import chessboard.ChessColor;
import chessboard.Coordinates;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

public class Pawn extends FigureAbstract {

    public Pawn(ChessColor color) {
        super(color);
    }

    @Override
    public List<Stream<Coordinates>> getMoves(Coordinates currentPosition) {
        List<Stream<Coordinates>> res = new LinkedList<>();
        Stream<Coordinates> moves = Stream
                .iterate(currentPosition, position -> new Coordinates(position.getX(), position.getY() + 1)).limit(3).skip(1);
        res.add(moves);
        return res;
    }

    @Override
    public String getAsString() {
        return "Pwn";
    }
}
