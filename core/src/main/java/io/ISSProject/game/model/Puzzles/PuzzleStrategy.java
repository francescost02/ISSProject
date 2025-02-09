package io.ISSProject.game.model.Puzzles;

public interface PuzzleStrategy {
    void initialize();
    boolean solve(Object input);
    String getDescription();
    String getHint();
    boolean isCompleted();
}
