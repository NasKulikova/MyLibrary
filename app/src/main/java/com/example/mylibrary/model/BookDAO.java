package com.example.mylibrary.model;

import static androidx.room.OnConflictStrategy.IGNORE;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface BookDAO {

    @Insert(onConflict = IGNORE)
    void insertBooks(List<Book> books);

    @Query("Select * from bookTable where id = :id")
    LiveData<Book> getBookById(int id);

    @Query("Select * from bookTable")
    LiveData<List<Book>> getAllBooks();

    @Query("Select * from bookTable where isDeferred == true ")
    LiveData<List<Book>> getDeferredBooks();

    @Query("Select * from bookTable where isAddedInLibrary == true ")
    LiveData<List<Book>> getAddedInLibraryBooks();

    @Query("Select * from bookTable where nameOfBook like '%' || :query  || '%'")
    LiveData<List<Book>> getSearchResultByBookName(String query);

    @Query("Select * from bookTable where author like '%' || :query  || '%'")
    LiveData<List<Book>> getSearchResultByAuthor(String query);

    @Query("Select * from bookTable where genreOfBook = :genre")
    LiveData<List<Book>> getSearchResultByGenre(GenreOfBook genre);

    @Query("Select * from bookTable where cost = 0 and nameOfBook like '%' || :query  || '%'")
    LiveData<List<Book>> getSearchResultByBookNameFreeOnly(String query);

    @Query("Select * from bookTable where cost = 0 and author like '%' || :query  || '%'")
    LiveData<List<Book>> getSearchResultByAuthorFreeOnly(String query);

    @Query("Select * from bookTable where cost = 0 and genreOfBook = :genre")
    LiveData<List<Book>> getSearchResultByGenreFreeOnly(GenreOfBook genre);


    @Update
    void updateBook(Book book);


}
