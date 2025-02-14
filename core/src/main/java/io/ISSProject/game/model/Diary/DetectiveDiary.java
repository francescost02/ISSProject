package io.ISSProject.game.model.Diary;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

//classe base per diario

public class DetectiveDiary {
    private static DetectiveDiary instance;
    private final List<DiaryEntry> entries;
    private final List<DiaryObservers> observers;


    private DetectiveDiary(){
        entries = new ArrayList<>();
        observers = new ArrayList<>();
    }


    public static DetectiveDiary getInstance(){
        if(instance == null){
            instance = new DetectiveDiary();
        }
        return instance;
    }

    public void addEntry(String title, String content){
        //Controlliamo se esiste già una entry con lo stesso nome
        boolean entryExists = entries.stream()
            .anyMatch(entry -> entry.getTitle().equals(title));
        if(!entryExists) {
            DiaryEntry entry = new DiaryEntry(title, content);
            entries.add(entry);
            notifyObservers(entry);
        }
    }

    public void notifyObservers(DiaryEntry newEntry){
        for (DiaryObservers observer : observers){
            observer.onDiaryUpdated(newEntry);
        }
    }

    public void addObserver(DiaryObservers observer){
        observers.add(observer);
    }

    public void removeObserver(DiaryObservers observer){
        observers.remove(observer);
    }

    //Metodo per ottenre tutti gli indizi in ordine cronologico
    public List<DiaryEntry> getEntriesChronological(){
        return entries.stream()
            .sorted(Comparator.comparing(DiaryEntry::getTimestamp))
            .collect(Collectors.toList());
    }

    public void clear(){
        entries.clear();
    }
}
