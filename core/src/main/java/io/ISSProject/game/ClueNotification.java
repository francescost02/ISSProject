package io.ISSProject.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class ClueNotification extends Dialog {
    private Texture paperTexture;
    private Image paperImage;

    // Costruttore per "Nuovo Indizio" con finestra personalizzata
    public ClueNotification(String tooltipText, Skin skin, Stage stage) {
        super("", skin); // Nessun titolo per evitare problemi di stile

        // Carica l'immagine del foglio di carta
        paperTexture = new Texture(Gdx.files.internal("images/paper.jpg"));
        paperImage = new Image(paperTexture);

        // Crea un'etichetta per il testo del messaggio
        Label messageLabel = new Label("Hai trovato un nuovo indizio: " + tooltipText, skin);
        messageLabel.setWrap(true);
        messageLabel.setFontScale(1.2f); // Aumenta la dimensione del testo

        TextButton closeButton = new TextButton("OK", skin);
        closeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                hide(); // Chiude solo al click effettivo
            }
        });

        // Struttura della finestra
        Table contentTable = new Table();
        contentTable.add(paperImage).size(400, 300).expand().center().row();
        contentTable.add(messageLabel).pad(10).width(350).row();
        contentTable.add(closeButton).pad(10);

        // Aggiunge il contenuto alla finestra
        this.getContentTable().add(contentTable);

        // Posiziona la finestra al centro dello stage
        setPosition(
            (stage.getWidth() - 400) / 2,
            (stage.getHeight() - 300) / 2
        );

        show(stage);
    }

    // Costruttore per "Indizio Già Trovato" (rimasto invariato)
    public ClueNotification(String tooltipText, Skin skin, Stage stage, boolean a) {
        super("Indizio Già Trovato!", skin);
        text("Hai già trovato questo indizio: " + tooltipText);
        button("OK");

        setPosition(
            (stage.getWidth() - getWidth()) / 2,
            (stage.getHeight() - getHeight()) / 2
        );

        setupAnimations();
        show(stage);
    }

    // Effetti di fade-in e rimozione automatica
    private void setupAnimations(){
        getColor().a = 0;
        addAction(Actions.sequence(
            Actions.fadeIn(0.5f),
            Actions.delay(1.5f),
            Actions.removeActor()
        ));
    }

    @Override
    public void hide() {
        super.hide();
        if (paperTexture != null) {
            paperTexture.dispose(); // Libera la memoria della texture
        }
    }
}
