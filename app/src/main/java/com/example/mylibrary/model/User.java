package com.example.mylibrary.model;

import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "UsersTable", indices = @Index(value = {"login", "password","balance", "userName"}, unique = true))
public class User {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String login;
    private String password;
    private long balance;
    private String userName;


    public User(String login, String password, String userName ){
        this.login = login;
        this.password = password;
        this.balance = 0;
        this.userName = userName;
    }

    public String getUserName() { return userName; }

    public void setUserName(String userName) { this.userName = userName; }

    public long getBalance() { return balance; }

    public void setBalance(long balance) { this.balance = balance; }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}