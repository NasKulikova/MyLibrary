package com.example.mylibrary.model;

import java.util.Locale;

public enum GenreOfBook {

    DETECTIVE("Детектив"),
    NOVEL("Роман"),
    FANTASTIC("Фантастика"),
    FOREIGN_LITERATURE("Зарубежная литература"),
    STORY("Рассказ");


    private String genreName;

    GenreOfBook(String genreName) {
        this.genreName = genreName;
    }

    public String getGenreName() {
        return genreName;
    }

    public void setGenreName(String genreName) {
        this.genreName = genreName;
    }

    public GenreOfBook findGenreByGenreName(String query) {
        for (int i = 0; i < GenreOfBook.values().length; i++) {
            for(int j=0; j<GenreOfBook.values()[i].getGenreName().split(" ").length; j++)
            if (GenreOfBook.values()[i].getGenreName().split(" ")[j].toLowerCase().startsWith(query.toLowerCase())) {
                return GenreOfBook.values()[i];
            }
        }
        return null;
    }
}
