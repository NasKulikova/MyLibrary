package com.example.mylibrary.model;

import androidx.room.TypeConverter;

class GenreConverter {

    @TypeConverter
    public GenreOfBook genreStringToEnum(String genreString) {
        return GenreOfBook.valueOf(genreString);
    }

    @TypeConverter
    public String genreEnumToString(GenreOfBook genre) {
        return genre.name();
    }

}
