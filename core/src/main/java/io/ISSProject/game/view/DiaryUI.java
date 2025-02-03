
package io.ISSProject.game.view;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import io.ISSProject.game.model.Diary.DetectiveDiary;
import io.ISSProject.game.model.Diary.DiaryEntry;
import io.ISSProject.game.model.Diary.DiaryObservers;

import java.util.ArrayList;
import java.util.List;

public class DiaryUI extends Window implements DiaryObservers {
    private final Table contentTable;
    private final TextButton closeButton;
    private final TextButton prevButton;
    private final TextButton nextButton;
    private final DetectiveDiary diary;

    private final List<List<DiaryEntry>> pages = new ArrayList<>();
    private int currentPage = 0;


    public DiaryUI(Skin skin) {
        super("Detective's Diary", skin);

        diary = DetectiveDiary.getInstance();
        // Register as observer for diary updates
        diary.addObserver(this);

        // Basic window configuration
        setMovable(false);
        setModal(true);
        setResizable(false);

        setBackground(skin.newDrawable("highlight"));

        // Create content table for the two columns
        contentTable = new Table(skin);
        contentTable.defaults().pad(10);

        // Create close button
        closeButton = new TextButton("Chiudi", skin);
        closeButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                setVisible(false);
            }
        });

        prevButton = new TextButton("Pagina precedente", skin);
        prevButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (currentPage > 0) {
                    currentPage--;
                    updateEntriesList();
                }
            }
        });

        nextButton = new TextButton("Pagina Successiva", skin);
        nextButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (currentPage < pages.size() - 1) {
                    currentPage++;
                    updateEntriesList();
                }
            }
        });

        Table navigationTable = new Table();
        navigationTable.add(prevButton).size(150,60).padRight(50);
        navigationTable.add(closeButton).size(80,70).center();
        navigationTable.add(nextButton).size(150,60).padLeft(50);

        Table mainTable = new Table();
        mainTable.add(contentTable).expand().fill().pad(20).row();
        mainTable.add(navigationTable).pad(10).bottom();

        add(mainTable).expand().fill();
        setSize(600, 400);
        setVisible(false);

        organizeEntriesIntoPages();
        updateEntriesList();
    }
    /**
     * Organizza gli indizi in pagine di massimo 4 elementi (2 a sinistra e 2 a destra)
     */
    private void organizeEntriesIntoPages() {
        pages.clear();
        List<DiaryEntry> allEntries = diary.getEntriesChronological();
        List<DiaryEntry> currentPageEntries = new ArrayList<>();

        for (DiaryEntry entry : allEntries) {
            if (currentPageEntries.size() == 4) {
                pages.add(currentPageEntries);
                currentPageEntries = new ArrayList<>();
            }
            currentPageEntries.add(entry);
        }
        if (!currentPageEntries.isEmpty()) {
            pages.add(currentPageEntries);
        }

        currentPage = Math.max(0, pages.size() - 1);  // Apri sempre sull'ultima pagina
    }

    private void updateEntriesList() {
        contentTable.clear();

        if (pages.isEmpty()) return;

        List<DiaryEntry> entries = pages.get(currentPage);

        Table leftColumn = new Table();
        Table rightColumn = new Table();

        for (int i = 0; i < entries.size(); i++) {
            Table targetColumn = (i < 2) ? leftColumn : rightColumn;
            addEntryToColumn(targetColumn, entries.get(i));
        }

        contentTable.add(leftColumn).expand().fill().uniform();
        contentTable.add(rightColumn).expand().fill().uniform();

        prevButton.setVisible(currentPage > 0);
        nextButton.setVisible(currentPage < pages.size() - 1);
    }

    private void addEntryToColumn(Table column, DiaryEntry entry) {
        // Entry container
        Table entryTable = new Table();

        // Create labels
        Table headerTable = new Table();

        Label titleLabel = new Label(entry.getTitle(), getSkin(), "default");
        titleLabel.setWrap(true);
        //titleLabel.setAlignment(Align.center);

        Label dateLabel = new Label(entry.getTimestamp(), getSkin());
        //dateLabel.setAlignment(Align.center);

        headerTable.add(titleLabel).expandX().fillX().left();
        headerTable.add(dateLabel).right();

        Label descLabel = new Label(entry.getDescription(), getSkin());
        descLabel.setWrap(true);

        // Add to entry table
        entryTable.add(headerTable).expandX().fillX().pad(5).row();
        //entryTable.add(dateLabel).expandX().fillX().pad(5).row();
        entryTable.add(descLabel).expand().fill().pad(5).row();

        // Add entry to column
        column.add(entryTable).expandX().fillX().pad(5).row();
    }

    public void show() {
        setVisible(true);
        toFront();
        setPosition(
            (getStage().getWidth() - getWidth()) / 2,
            (getStage().getHeight() - getHeight()) / 2
        );
    }

    public void hide(){
        setVisible(false);
    }

    @Override
    public void onDiaryUpdated(DiaryEntry newEntry) {
        organizeEntriesIntoPages();
        updateEntriesList();
    }
}
