import chessboard.ChessColor;
import chessboard.Chessboard;
import chessboard.Coordinates;
import figures.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Stream;


public class ChessBoardTest {
    @Test
    void ChessboardTest() {
        Chessboard chessboard = new Chessboard();
        Figure figure = new Pawn(ChessColor.WHITE);
        chessboard.addFigure(figure, chessboard.getSquareByName("B2").getCoordinates());
        System.out.println(chessboard.getAsString());
        System.out.println();
        System.out.println(chessboard.getAsStringInversed());
    }

    @Test
    void ChessboardPawnMoveTest() {
        Chessboard chessboard = new Chessboard();
        Figure figure = new Pawn(ChessColor.WHITE);
        chessboard.addFigure(figure, chessboard.getSquareByName("B2").getCoordinates());
        List<Stream<Coordinates>> moves = figure.getMoves(chessboard.getCoordinates(figure));

        System.out.println(chessboard.getAsString());
        System.out.println();

        moves.forEach(moveSeries -> moveSeries.forEach(coord -> {
            chessboard.moveFigureTo(false, figure, coord);
            System.out.println(chessboard.getAsString());
            System.out.println();
        }));
    }

    @Test
    void ChessboardPawnMoveToTest() {
        Chessboard chessboard = new Chessboard();
        Figure figure1 = new Pawn(ChessColor.WHITE);
        Figure figure2 = new Pawn(ChessColor.BLACK);
        chessboard.addFigure(figure1, chessboard.getSquareByName("B2").getCoordinates());
        chessboard.addFigure(figure2, chessboard.getSquareByName("B3").getCoordinates());

        Figure figure3 = new Pawn(ChessColor.WHITE);
        Figure figure4 = new Pawn(ChessColor.BLACK);
        chessboard.addFigure(figure3, chessboard.getSquareByName("E2").getCoordinates());
        chessboard.addFigure(figure4, chessboard.getSquareByName("E3").getCoordinates());

        System.out.println(chessboard.getAsString());
        System.out.println();

        Assertions.assertThrows(RuntimeException.class, () ->
                chessboard.moveFigureTo(false, figure1, chessboard.getSquareByName("B4").getCoordinates())
        );
        System.out.println(chessboard.getAsString());
        System.out.println();
    }

    @Test
    void ChessboardGameTest19() {
        Chessboard chessboard = new Chessboard();
        Figure figure;
        figure = new King(ChessColor.BLACK);
        chessboard.addFigure(figure, chessboard.getSquareByName("E3").getCoordinates());

        figure = new King(ChessColor.WHITE);
        chessboard.addFigure(figure, chessboard.getSquareByName("E6").getCoordinates());

        figure = new Bishop(ChessColor.WHITE);
        chessboard.addFigure(figure, chessboard.getSquareByName("C5").getCoordinates());

        figure = new Knight(ChessColor.WHITE);
        chessboard.addFigure(figure, chessboard.getSquareByName("D4").getCoordinates());

        figure = new Queen(ChessColor.WHITE);
        chessboard.addFigure(figure, chessboard.getSquareByName("C2").getCoordinates());

        System.out.println(chessboard.getAsString());
        System.out.println();


        chessboard.doAllPossibleMoves(1, ChessColor.WHITE,
                () -> chessboard.doAllPossibleMoves(2, ChessColor.BLACK,
                        () -> chessboard.doAllPossibleMoves(3, ChessColor.WHITE,
                                () -> {
                                    boolean check = chessboard.checkSituation(ChessColor.BLACK);
                                    if (check) {
                                        AtomicBoolean willMate = new AtomicBoolean(true);
                                        chessboard.doAllPossibleMoves(4, ChessColor.BLACK, () -> willMate.set(false));
                                        if (willMate.get())
                                            System.out.println(chessboard.getAsString()
                                                    + "\n" + chessboard.getChessBoardState().getHistory());
                                    }
                                }
                        )
                )
        );

        System.out.println(chessboard.getAsString());
        System.out.println();
    }

    @Test
    void ChessboardGameTest26() {
        Chessboard chessboard = new Chessboard();
        Figure figure;
        figure = new King(ChessColor.BLACK);
        chessboard.addFigure(figure, chessboard.getSquareByName("G3").getCoordinates());

        figure = new King(ChessColor.WHITE);
        chessboard.addFigure(figure, chessboard.getSquareByName("G5").getCoordinates());

        figure = new Rock(ChessColor.WHITE);
        chessboard.addFigure(figure, chessboard.getSquareByName("C8").getCoordinates());

        figure = new Queen(ChessColor.WHITE);
        chessboard.addFigure(figure, chessboard.getSquareByName("B1").getCoordinates());

        System.out.println(chessboard.getAsString());
        System.out.println();


        chessboard.doAllPossibleMoves(1, ChessColor.WHITE,
                () -> chessboard.doAllPossibleMoves(2, ChessColor.BLACK,
                        () -> chessboard.doAllPossibleMoves(3, ChessColor.WHITE,
                                () -> {
                                    boolean check = chessboard.checkSituation(ChessColor.BLACK);
                                    if (check) {
                                        AtomicBoolean willMate = new AtomicBoolean(true);
                                        chessboard.doAllPossibleMoves(4, ChessColor.BLACK, () -> willMate.set(false));
                                        if (willMate.get())
                                            System.out.println(chessboard.getAsString()
                                                    + "\n" + chessboard.getChessBoardState().getHistory());
                                    }
                                }
                        )
                )
        );

        System.out.println(chessboard.getAsString());
        System.out.println();
    }

    @Test
    void ChessboardGameTest32() {
        Chessboard chessboard = new Chessboard();
        Figure figure;
        figure = new King(ChessColor.BLACK);
        chessboard.addFigure(figure, chessboard.getSquareByName("G6").getCoordinates());

        figure = new King(ChessColor.WHITE);
        chessboard.addFigure(figure, chessboard.getSquareByName("F4").getCoordinates());

        figure = new Rock(ChessColor.WHITE);
        chessboard.addFigure(figure, chessboard.getSquareByName("H5").getCoordinates());

        figure = new Queen(ChessColor.WHITE);
        chessboard.addFigure(figure, chessboard.getSquareByName("F3").getCoordinates());

        System.out.println(chessboard.getAsString());
        System.out.println();


        chessboard.doAllPossibleMoves(1, ChessColor.WHITE,
                () -> chessboard.doAllPossibleMoves(2, ChessColor.BLACK,
                        () -> chessboard.doAllPossibleMoves(3, ChessColor.WHITE,
                                () -> {
                                    boolean check = chessboard.checkSituation(ChessColor.BLACK);
                                    if (check) {
                                        AtomicBoolean willMate = new AtomicBoolean(true);
                                        chessboard.doAllPossibleMoves(4, ChessColor.BLACK, () -> willMate.set(false));
                                        if (willMate.get())
                                            System.out.println(chessboard.getAsString()
                                                    + "\n" + chessboard.getChessBoardState().getHistory());
                                    }
                                }
                        )
                )
        );

        System.out.println(chessboard.getAsString());
        System.out.println();
    }

    @Test
    void ChessboardGameTest34() {
        Chessboard chessboard = new Chessboard();
        Figure figure;
        figure = new King(ChessColor.BLACK);
        chessboard.addFigure(figure, chessboard.getSquareByName("G3").getCoordinates());

        figure = new King(ChessColor.WHITE);
        chessboard.addFigure(figure, chessboard.getSquareByName("D6").getCoordinates());

        figure = new Rock(ChessColor.WHITE);
        chessboard.addFigure(figure, chessboard.getSquareByName("H4").getCoordinates());

        figure = new Queen(ChessColor.WHITE);
        chessboard.addFigure(figure, chessboard.getSquareByName("B7").getCoordinates());

        System.out.println(chessboard.getAsString());
        System.out.println();


        chessboard.doAllPossibleMoves(1, ChessColor.WHITE,
                () -> chessboard.doAllPossibleMoves(2, ChessColor.BLACK,
                        () -> chessboard.doAllPossibleMoves(3, ChessColor.WHITE,
                                () -> chessboard.doAllPossibleMoves(4, ChessColor.BLACK,
                                        () -> {
                                            AtomicBoolean noMate = new AtomicBoolean(true);
                                            chessboard.doAllPossibleMoves(5, ChessColor.WHITE,
                                                    () -> {
                                                        boolean check = chessboard.checkSituation(ChessColor.BLACK);
                                                        if (check) {
                                                            AtomicBoolean willMate = new AtomicBoolean(true);
                                                            chessboard.doAllPossibleMoves(6, ChessColor.BLACK, () -> willMate.set(false));
                                                            if (willMate.get()) {
                                                                noMate.set(false);
                                                                System.out.println(chessboard.getAsString()
                                                                        + "\n" + chessboard.getChessBoardState().getHistory());
                                                            }
                                                        }
                                                    }

                                            );
                                            if (noMate.get()) {
                                                
                                            }
                                        }
                                )
                        )
                ));

        System.out.println(chessboard.getAsString());
        System.out.println();
    }
}
