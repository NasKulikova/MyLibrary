package com.example.mylibrary.model;

import static androidx.room.OnConflictStrategy.IGNORE;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface UserDAO {
    @Insert(onConflict = IGNORE)
    void insertUsers(List<User> users);

    @Query("Select * from UsersTable where id = :id")
    LiveData<User> getUserById(int id);

    @Query("Select * from UsersTable")
    LiveData<List<User>> getAllUsers();

    @Query("Select * from UsersTable where login == :email AND password == :password")
    LiveData<List<User>> getUser(String email, String password);

    @Insert(onConflict = IGNORE)
    void insertUser( User newUser);

}
