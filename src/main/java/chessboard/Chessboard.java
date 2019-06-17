package chessboard;

import figures.Figure;
import figures.King;
import utils.ConsoleColors;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Chessboard {
    private final int WIDTH = 8;
    private final int HEIGHT = 8;
    private final int SIZE = WIDTH * HEIGHT;
    private final List<ChessboardSquare> squares = new ArrayList<>(SIZE);
    private ChessBoardState chessBoardState = new ChessBoardState();

    public ChessBoardState getChessBoardState() {
        return chessBoardState.copyState();
    }

    public void setChessBoardState(ChessBoardState chessBoardState) {
        this.chessBoardState = chessBoardState.copyState();
    }

    public Chessboard() {
        for (int i = 0; i < SIZE; i++) {
            squares.add(
                    new ChessboardSquare(new Coordinates((byte) (i % WIDTH + 1), (byte) (HEIGHT - i / WIDTH)))
            );
        }
    }

//    private void changeState(Figure piece, Coordinates coordinates){
//        chessBoardState.getFigurePositions().forcePut(piece, coordinates);
//
//
//    }

    public void addFigure(Figure figure, Coordinates position) {
        if (chessBoardState.getFigurePositions().containsKey(figure)) {
            throw new RuntimeException("The figure " + figure + " already exists");
        }
        chessBoardState.getFigurePositions().put(figure, position);
    }

    public void moveFigureTo(boolean onlyCheck, Figure figure, Coordinates newCoordinates) {
        if (!chessBoardState.getFigurePositions().containsKey(figure)) {
            throw new RuntimeException("The figure " + figure + " does not exist");
        }
        if (squareContainsSameColorPiece(figure, newCoordinates)) {
            throw new RuntimeException("New square is not empty: " + newCoordinates);
        }

        // check rules
        List<Stream<Coordinates>> moves = figure.getMoves(getCoordinates(figure));
        AtomicBoolean done = new AtomicBoolean(false);
        moves.forEach(moveSeries -> {
                    if (done.get()) return;
                    moveSeries
                            .peek(coord -> {
                                if (coord.equals(newCoordinates)
                                        && (onlyCheck || !(figure instanceof King && squareIsUnderStrike(coord, figure)))
                                        ) {
                                    if (!onlyCheck) chessBoardState.changeState(figure, newCoordinates);
                                    done.set(true);
                                }
                            })
                            .allMatch(coordinates -> getFigure(coordinates) == null
                                    && cordinateIsOnBoard(coordinates)
                            );
                }
        );

        if (!done.get()) {
            throw new RuntimeException("The move is incorrect " + newCoordinates);
        }
    }

    public synchronized void doAllPossibleMoves(int n, ChessColor pieceColor, Runnable doAfter) {
        ChessBoardState state = getChessBoardState();
        getFiguresAsStream()
                .filter(piece -> piece.getColor() == pieceColor)
                .forEach(piece -> {
                    setChessBoardState(state);
                    List<Stream<Coordinates>> moves = piece.getMoves(getCoordinates(piece));

                    moves.forEach(moveSeries -> {
                                moveSeries
                                        .peek(coordinates -> {
                                            setChessBoardState(state);
                                            if ((getFigure(coordinates) == null || getFigure(coordinates).getColor() != pieceColor)
                                                    && cordinateIsOnBoard(coordinates)) {
                                                ChessBoardState currentState = getChessBoardState();
                                                try {
                                                    chessBoardState.changeState(piece, coordinates);
//                                                    System.out.println(getAsString() + n);
//                                                    Thread.sleep(200);
//                                                } catch (InterruptedException e) {
                                                } catch (Exception e) {
                                                    System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!! " + piece + coordinates);
                                                    System.out.println(getAsString());
                                                    throw e;
                                                }
                                                if (checkSituation(pieceColor)) {
                                                    setChessBoardState(currentState);
                                                } else {
                                                    doAfter.run();
                                                }
                                            }
                                        })
                                        .allMatch(coordinates ->
                                                (getFigure(coordinates) == null || getFigure(coordinates) == piece)
                                                        && cordinateIsOnBoard(coordinates)
                                        );
                            }
                    );
                });
    }

    public boolean checkSituation(ChessColor chessColor) {
        AtomicBoolean res = new AtomicBoolean(false);
        Coordinates kingsCoordinates = getCoordinates(findKing(chessColor));
        getFiguresAsStream()
                .filter(piece -> piece.getColor() != chessColor)
                .forEach(piece -> {
                    try {
                        moveFigureTo(true, piece, kingsCoordinates);
                        res.set(true);
                    } catch (Exception e) {

                    }
                });
        return res.get();
    }

    private Figure findKing(ChessColor chessColor) {
        return getFiguresAsStream().filter(piece -> piece.getColor() == chessColor && piece instanceof King).findFirst().get();
    }

    private boolean squareIsUnderStrike(Coordinates coordinates, Figure excludedPiece) {
        return chessBoardState.getFigurePositions().keySet().parallelStream().anyMatch(piece ->
                piece.getColor() != excludedPiece.getColor() && pieceCanStrikeSquare(piece, coordinates, excludedPiece));
    }

    public Stream<Figure> getFiguresAsStream() {
        return chessBoardState.getFigurePositions().keySet().stream();
    }

    private boolean pieceCanStrikeSquare(Figure figure, Coordinates newCoordinates, Figure excludedPiece) {
        List<Stream<Coordinates>> moves = figure.getMoves(getCoordinates(figure));
        AtomicBoolean done = new AtomicBoolean(false);
        moves.forEach(moveSeries -> {
                    if (done.get()) return;
                    moveSeries
                            .peek(coord -> {
                                if (coord.equals(newCoordinates)) {
                                    done.set(true);
                                }
                            })
                            .allMatch(coordinates ->
                                    (getFigure(coordinates) == null || getFigure(coordinates) == excludedPiece)
                                            && cordinateIsOnBoard(coordinates)
                            );
                }
        );
        return done.get();
    }

    private boolean squareContainsSameColorPiece(Figure figure, Coordinates coordinates) {
        return getFigure(coordinates) != null
                && getFigure(coordinates).getColor() == figure.getColor();
    }

    private boolean cordinateIsOnBoard(Coordinates coordinates) {
        return coordinates.getX() > 0
                && coordinates.getX() <= WIDTH
                && coordinates.getY() > 0
                && coordinates.getY() <= HEIGHT;
    }

    public String getAsString() {
        return squares.stream().map(
                square -> getSquareAsString(square) + (square.getCoordinates().getX() == WIDTH ? "\n" : "")
        ).collect(Collectors.joining(""));
    }

    public String getAsStringInversed() {
        return IntStream.range(0, SIZE).map(i -> SIZE - i - 1).mapToObj(squares::get)
                .map(
                        square -> getSquareAsString(square) + (square.getCoordinates().getX() == 1 ? "\n" : "")
                ).collect(Collectors.joining(""));
    }

    private String getSquareAsString(ChessboardSquare square) {
        String res = Optional.ofNullable(getFigure(square.getCoordinates()))
                .map(figure -> (figure.getColor() == ChessColor.WHITE ? ConsoleColors.YELLOW_BOLD_BRIGHT : ConsoleColors.BLUE_BOLD_BRIGHT) + figure.getAsString())
                .orElse(square.getAsString());
        res = (square.getColor() == ChessColor.WHITE ? ConsoleColors.RED_BACKGROUND : "") + res + ConsoleColors.RESET;
        return res;
    }

    public ChessboardSquare getSquareByName(String name) {
        return squares.stream().filter(square -> name.equals(square.getName())).findFirst()
                .orElseThrow(() -> new RuntimeException("Could not find a square with name " + name));
    }

    private Figure getFigure(Coordinates coordinates) {
        return chessBoardState.getFigurePositions().inverse().get(coordinates);
    }

    public Coordinates getCoordinates(Figure figure) {
        return chessBoardState.getFigurePositions().get(figure);
    }

}
