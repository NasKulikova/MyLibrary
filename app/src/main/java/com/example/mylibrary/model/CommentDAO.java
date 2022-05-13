package com.example.mylibrary.model;

import static androidx.room.OnConflictStrategy.IGNORE;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface CommentDAO {

    @Insert(onConflict = IGNORE)
    void insertComments(List<Comment> comments);

    @Query("Select * from commentTable where IDNameOfBook = :bookId")
    LiveData<List<Comment>> getCommentsByBook(int bookId);

    @Insert(onConflict = IGNORE)
    void insertComment(Comment comment);

}
