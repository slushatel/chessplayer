package chessboard;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import figures.Figure;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class ChessBoardState {
    private BiMap<Figure, Coordinates> figurePositions = HashBiMap.create();
    private List<HystoryEntry> history = new LinkedList<>();

    public BiMap<Figure, Coordinates> getFigurePositions() {
        return figurePositions;
    }

    public String getHistory(){
        return history.stream().map(Object::toString).collect(Collectors.joining("\n"));
    }

    public ChessBoardState() {

    }

    public ChessBoardState(BiMap<Figure, Coordinates> figurePositions, List<HystoryEntry> history) {
        this.figurePositions = figurePositions;
        this.history = history;
    }

    public ChessBoardState copyState() {
        return new ChessBoardState(HashBiMap.create(figurePositions), new ArrayList<>(history));
    }

    public void changeState(Figure piece, Coordinates coordinates){
        getFigurePositions().forcePut(piece, coordinates);
        history.add(new HystoryEntry(piece, coordinates));
    }
}

class HystoryEntry {
    private Figure figure;
    private Coordinates coordinates;

    public HystoryEntry(Figure figure, Coordinates coordinates) {
        this.figure = figure;
        this.coordinates = coordinates;
    }

    @Override
    public String toString() {
        return "HystoryEntry{" +
                "figure=" + figure +
                ", coordinates=" + coordinates +
                '}';
    }
}
