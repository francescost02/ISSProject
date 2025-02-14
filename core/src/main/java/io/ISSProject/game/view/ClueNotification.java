package io.ISSProject.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Align;
import io.ISSProject.game.controller.gamePlayController.GameplayController;
import io.ISSProject.game.controller.gameState.GameContext;
import io.ISSProject.game.controller.mediator.GameComponent;
import io.ISSProject.game.controller.mediator.GameMediator;

public class ClueNotification extends Dialog implements GameComponent{
    private Texture paperTexture;
    private Image paperImage;
    private BitmapFont customFont;
    private FreeTypeFontGenerator fontGenerator;
    private GameMediator mediator;

    @Override
    public void setMediator(GameMediator mediator){
        this.mediator = mediator;
    }

    public GameMediator getMediator(){
        return this.mediator;
    }

    @Override
    public void notify(String event, Object...args){
        if (mediator != null) {
            mediator.notify(this, event, args);
            }
    }

    // Costruttore per "Nuovo Indizio" con finestra personalizzata
    public ClueNotification(String content, Skin skin, Stage stage) {
        super("", skin); // Nessun titolo per evitare problemi di stile

        // Carica l'immagine del foglio di carta
        paperTexture = new Texture(Gdx.files.internal("images/paper.png"));
        paperImage = new Image(paperTexture);
        this.setBackground((Drawable) null); //elimina il background della finestra

        // Carica il font personalizzato .ttf
        fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("font/fontNoir.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter fontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        fontParameter.size = 16; // Dimensione del font
        fontParameter.color = Color.BLACK; // Colore del testo
        customFont = fontGenerator.generateFont(fontParameter);

        // Crea uno stile per la label con il font personalizzato
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = customFont;

        // Crea un'etichetta per il testo del messaggio
        Label messageLabel = new Label(content, labelStyle);
        messageLabel.setWrap(true);
        messageLabel.setAlignment(Align.center);

        // Stack per sovrapporre il testo all'immagine del foglio di carta
        Table textTable = new Table();
        textTable.add(messageLabel).width(200).pad(20); // Imposta limiti e margini

        Stack stack = new Stack();
        stack.add(paperImage);
        stack.add(textTable);

        // Pulsante di chiusura
        TextButton closeButton = new TextButton("OK", skin);
        closeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                hide();
            }
        });

        // Struttura della finestra
        Table contentTable = new Table();
        contentTable.add(stack).size(350, 350).expand().center().row();
        contentTable.add(closeButton).pad(10);

        // Aggiunge il contenuto alla finestra
        this.getContentTable().add(contentTable);

        // Posiziona la finestra al centro dello stage
        setPosition((stage.getWidth() - 400) / 2, (stage.getHeight() - 300) / 2);

        show(stage);
    }

    // Costruttore per "Indizio Già Trovato"
    public ClueNotification(String tooltipText, Skin skin, Stage stage, boolean alreadyFound) {
        super("Indizio Già Trovato!", skin);
        text("Hai già trovato questo indizio: " + tooltipText);
        button("OK");

        setPosition((stage.getWidth() - getWidth()) / 2, (stage.getHeight() - getHeight()) / 2);

        show(stage);
    }

    @Override
    public void hide() {
        super.hide();
        if (paperTexture != null) {
            paperTexture.dispose(); // Libera la memoria della texture
        }
        if (fontGenerator != null) {
            fontGenerator.dispose(); // Libera la memoria del font
        }
    }

    public static void showIncompleteSceneNotification(Skin skin, Stage stage) {
        Dialog dialog = new Dialog("Scena Non Completata", skin);
        dialog.text("Non hai ancora completato questa scena.");
        dialog.button("OK");

        dialog.setPosition(
            (stage.getWidth() - dialog.getWidth()) / 2,
            (stage.getHeight() - dialog.getHeight()) / 2
        );

        dialog.addAction(Actions.sequence(
            Actions.fadeIn(0.5f),
            Actions.delay(1.5f),
            Actions.removeActor()
        ));

        dialog.show(stage);
    }

    public static void showChoice(Skin skin, Stage stage, final GameplayController controller) {
        Dialog dialog = new Dialog("Scelta finale", skin) {
            @Override
            protected void result(Object object) {
                String choice = (String) object;
                GameContext gameContext = GameContext.getInstance();
                gameContext.setPlayerFinalChoice(choice);
                boolean isCorrect = choice.equals("Boss");
                controller.getMediator().notify(controller, "FINAL_CHOICE", isCorrect);
            }
        };

        dialog.text("Decidi chi incolpare");
        dialog.button("Boss", "Boss");
        dialog.button("Marco", "Marco");

        dialog.show(stage); // Rimuovi il setPosition, perché lo fa automaticamente show()
    }

}
