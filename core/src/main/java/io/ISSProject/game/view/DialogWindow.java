package io.ISSProject.game.view;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;

public class DialogWindow extends Table {
    private final Label thoughtLabel;
    private final ScrollPane scrollPane;
    private final Table container;
    private static final float PADDING_RATIO = 0.025f;

    public DialogWindow(Skin skin) {
        super(skin);

        setBackground(skin.getDrawable("window"));

        // Configurazione del label
        thoughtLabel = new Label("", skin);
        thoughtLabel.setWrap(true);

        // Container per il testo con padding
        container = new Table();
        container.add(thoughtLabel).grow().fill().pad(10);

        // ScrollPane configurato per riempire lo spazio disponibile
        scrollPane = new ScrollPane(container, skin);
        scrollPane.setFadeScrollBars(false);
        scrollPane.setScrollingDisabled(true, false);

        // Aggiungi lo ScrollPane alla finestra principale
        add(scrollPane).grow().fill();


    }

    @Override
    public void setSize(float width, float height) {
        super.setSize(width,height);

        // Aggiorna le dimensioni del contenuto interno
        float padding = width * PADDING_RATIO;
        float contentWidth = width - (padding*2);
        thoughtLabel.setWidth(contentWidth);
        container.setWidth(contentWidth);
        scrollPane.setWidth(width);
        container.pad(padding);

        // Forza il ridisegno del layout
        container.invalidate();
        scrollPane.layout();
        this.layout();

    }

    public void updateText(String text) {
        thoughtLabel.setText(text);
        thoughtLabel.setAlignment(Align.left);

        // Forza il layout e lo scroll
        container.invalidate();
        scrollPane.layout();
        scrollPane.setScrollPercentY(1);

    }
}
