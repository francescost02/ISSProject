package io.ISSProject.game.model.puzzles;

public interface PuzzleStrategy {
    void initialize();
    boolean solve(Object input);
    String getDescription();
    String getHint();
    boolean isCompleted();
}
