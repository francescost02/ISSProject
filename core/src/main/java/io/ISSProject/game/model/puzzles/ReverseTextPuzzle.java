package io.ISSProject.game.model.puzzles;

public class ReverseTextPuzzle implements PuzzleStrategy {
    private final String originalText = "ENIGMA";
    private String reversedText;
    private boolean completed = false;

    @Override
    public void initialize() {
        this.reversedText = new StringBuilder(originalText).reverse().toString();
    }

    @Override
    public boolean solve(Object input) {
        if (!(input instanceof String)) return false;
        String userInput = ((String) input).trim();
        completed = userInput.equalsIgnoreCase(originalText);
        return completed;
    }

    @Override
    public String getDescription() {
        return "Decodifica questo messaggio misterioso:\n" + reversedText;
    }

    @Override
    public String getHint() {
        return "Prova a leggere il testo al contrario...";
    }

    @Override
    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}
