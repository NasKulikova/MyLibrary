package com.example.mylibrary.model;

import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.util.ArrayList;
import java.util.List;

@Entity(tableName = "commentTable", indices = @Index(value = {"IDNameOfUser", "IDNameOfBook", "createdComments" }, unique = true))
public class Comment {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private Integer IDNameOfUser;
    private Integer IDNameOfBook;
    private String createdComments;

    public Comment(Integer IDNameOfUser, Integer IDNameOfBook, String createdComment){
        this.IDNameOfUser = IDNameOfUser;
        this.IDNameOfBook = IDNameOfBook;
        this.createdComments = createdComment;
    }
    public Comment(){ }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getIDNameOfUser() {
        return IDNameOfUser;
    }

    public void setIDNameOfUser(Integer IDNameOfUser) {
        this.IDNameOfUser = IDNameOfUser;
    }

    public Integer getIDNameOfBook() {
        return IDNameOfBook;
    }

    public void setIDNameOfBook(Integer IDNameOfBook) {
        this.IDNameOfBook = IDNameOfBook;
    }

    public String getCreatedComments() {
        return createdComments;
    }

    public void setCreatedComments(String createdComments) {
        this.createdComments = createdComments;
    }
}


