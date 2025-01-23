package io.ISSProject.game.model.Diary;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import java.time.LocalDateTime;

//classe base per gli elementi del diario
public class DiaryEntry {
    private final String title;
    private final String description;
    private final LocalDateTime timestamp;

    public DiaryEntry(String title, String description){
        this.title = title;
        this.description = description;
        this.timestamp = LocalDateTime.now();
    }

    public String getTitle(){
        return title;
    }

    public String getDescription(){
        return description;
    }

    public String getFormattedTimestamp() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        return timestamp.format(formatter);
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return String.format("[%s] %s: %s",
            getFormattedTimestamp(),
            title,
            description);
    }
}
