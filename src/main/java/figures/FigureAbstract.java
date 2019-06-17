package figures;

import chessboard.ChessColor;

public abstract class FigureAbstract implements Figure{
    private ChessColor color;

    public FigureAbstract(ChessColor color) {
        this.color = color;
    }

    public ChessColor getColor() {
        return color;
    }
}
