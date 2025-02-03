package io.ISSProject.game.model.Diary;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import java.time.LocalDateTime;

//classe base per gli elementi del diario
public class DiaryEntry {
    private final String title;
    private final String description;
    private final String timestamp;

    public DiaryEntry(String title, String description){
        this.title = title;
        this.description = description;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        this.timestamp = LocalDateTime.now().format(formatter);
    }

    public String getTitle(){
        return title;
    }

    public String getDescription(){
        return description;
    }

    public String getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return String.format("[%s] %s: %s",
            timestamp,
            title,
            description);
    }
}
