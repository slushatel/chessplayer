package figures;

import chessboard.ChessColor;
import chessboard.Coordinates;

import java.util.List;
import java.util.stream.Stream;

public interface Figure {
    ChessColor getColor();
    List<Stream<Coordinates>> getMoves(Coordinates currentPosition);
//    void move(Move move);
    String getAsString();
}
