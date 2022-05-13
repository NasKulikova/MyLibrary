package com.example.mylibrary.model;

import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

@Entity(tableName = "bookTable", indices = @Index(value = {"author", "nameOfBook", "textOfBookFileId", "publishingHouse","cost"}, unique = true))
public class Book {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String author;
    private String nameOfBook;
    private int textOfBookFileId;
    @TypeConverters(GenreConverter.class)
    private GenreOfBook genreOfBook;
    private String annotation;
    private String publishingHouse;
    private long cost;
    private int coverDrawableId;
    private boolean isDeferred;
    private boolean isAddedInLibrary;

    public Book(String author, String nameOfBook, int textOfBookFileId, GenreOfBook genreOfBook, String annotation, String publishingHouse, long cost, int coverDrawableId) {
        this.author = author;
        this.nameOfBook = nameOfBook;
        this.textOfBookFileId = textOfBookFileId;
        this.genreOfBook = genreOfBook;
        this.annotation = annotation;
        this.publishingHouse = publishingHouse;
        this.cost = cost;
        this.coverDrawableId = coverDrawableId;

    }

    public Book() {
    }

    public int getCoverDrawableId() {
        return coverDrawableId;
    }

    public void setCoverDrawableId(int coverDrawableId) {
        this.coverDrawableId = coverDrawableId;
    }

    public String getAnnotation() {
        return annotation;
    }

    public void setAnnotation(String annotation) {
        this.annotation = annotation;
    }

    public String getPublishingHouse() {
        return publishingHouse;
    }

    public void setPublishingHouse(String publishingHouse) {
        this.publishingHouse = publishingHouse;
    }


    public long getCost() { return cost; }

    public void setCost(long cost) { this.cost = cost; }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isDeferred() {
        return isDeferred;
    }

    public void setDeferred(boolean deferred) {
        isDeferred = deferred;
    }

    public boolean isAddedInLibrary() {
        return isAddedInLibrary;
    }

    public void setAddedInLibrary(boolean addedInLibrary) {
        isAddedInLibrary = addedInLibrary;
    }

    public GenreOfBook getGenreOfBook() {
        return genreOfBook;
    }

    public void setGenreOfBook(GenreOfBook genreOfBook) {
        this.genreOfBook = genreOfBook;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getNameOfBook() {
        return nameOfBook;
    }

    public void setNameOfBook(String nameOfBook) {
        this.nameOfBook = nameOfBook;
    }

    public int getTextOfBookFileId() {
        return textOfBookFileId;
    }

    public void setTextOfBookFileId(int textOfBookFileId) {
        this.textOfBookFileId = textOfBookFileId;
    }
}
