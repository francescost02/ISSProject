package io.ISSProject.game.view.Puzzles;

import com.badlogic.gdx.scenes.scene2d.ui.*;
import io.ISSProject.game.controller.Puzzles.PuzzleController;

public abstract class AbstractPuzzleView extends Dialog {
    protected final PuzzleController controller;
    protected Table contentTable;

    public AbstractPuzzleView(String title, Skin skin, PuzzleController controller) {
        super(title, skin);
        this.controller = controller;

        // Imposta lo stile base del dialog
        setModal(true);  // Blocca input sulla scena sottostante
        setMovable(false);  // Dialog non spostabile

        // Crea il layout base
        contentTable = new Table();
        contentTable.pad(20);

        // Aggiungi descrizione puzzle
        Label description = new Label(controller.getPuzzleDescription(), skin);
        description.setWrap(true);  // Permette il wrapping del testo
        contentTable.add(description).width(400f).pad(10).row();

        // Setup specifico per ogni tipo di puzzle
        setupPuzzleUI(contentTable);

        // Aggiungi i bottoni standard
        addStandardButtons();

        getContentTable().add(contentTable);
    }

    // Metodo astratto per UI specifica del puzzle
    protected abstract void setupPuzzleUI(Table contentTable);

    // Metodo astratto per validazione input
    protected abstract boolean validateInput();

    private void addStandardButtons() {
        button("Risolvi", "SOLVE"); // true sarà gestito in result()
        button("Indizio", "HINT");
        button("Chiudi", "CANCEL");
    }

    @Override
    protected void result(Object object) {
        if ("SOLVE".equals(object)){
            Object inputValue = getInputValue();
            controller.handleSolutionAttempt(inputValue, this);
        } else if("HINT".equals(object)){
            controller.showHint(this);
        } else if ("CANCEL".equals(object)){
            hide();
        }
    }

    // Metodo astratto per ottenere il valore dell'input
    protected abstract Object getInputValue();

    public void showSuccessDialog() {
        Dialog successDialog = new Dialog("Successo!", getSkin());
        successDialog.text("Puzzle risolto correttamente!");
        successDialog.button("OK");
        successDialog.show(getStage());
    }

    public void showErrorDialog() {
        Dialog errorDialog = new Dialog("Errore", getSkin());
        errorDialog.text("Soluzione non corretta. Riprova!");
        TextButton okButton = new TextButton("OK", getSkin());
        errorDialog.button(okButton);
        errorDialog.show(getStage());
    }
    public void showHintDialog() {
        Dialog hintDialog = new Dialog("Suggerimento", getSkin());
        hintDialog.text(controller.getPuzzleHint());
        hintDialog.button("OK");
        hintDialog.show(getStage());
    }

    public void showEmptyInputDialog() {
        Dialog emptyDialog = new Dialog("Attenzione", getSkin());
        emptyDialog.text("Inserisci una soluzione prima di procedere!");
        emptyDialog.button("OK");
        emptyDialog.show(getStage());
    }


}

