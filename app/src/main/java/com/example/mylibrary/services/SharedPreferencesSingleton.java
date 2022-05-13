package com.example.mylibrary.services;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.mylibrary.model.Book;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

public class SharedPreferencesSingleton {

    private static SharedPreferencesSingleton sharedPreferencesSingleton;
    private SharedPreferences sharedPreferences;
    private Context context;
    private Gson gson = new Gson();
    private SharedPreferences.Editor editor;

    private SharedPreferencesSingleton(Context context) {
        this.context = context;
    }

    public static SharedPreferencesSingleton getSharedPreferencesSingleton(Context context) {
        if (sharedPreferencesSingleton == null) {
            sharedPreferencesSingleton = new SharedPreferencesSingleton(context);
        }
        return sharedPreferencesSingleton;
    }

    public void saveReadedBook(Book book) {
        sharedPreferences = context.getSharedPreferences("ReadedBook", MODE_PRIVATE);
        String bookString = gson.toJson(book);
        editor = sharedPreferences.edit().clear();
        editor.commit();
        editor.putString("ReadedBookString", bookString);
        editor.apply();
    }

    public Book loadReadedBook() {
        sharedPreferences = context.getSharedPreferences("ReadedBook", MODE_PRIVATE);
        String bookString = sharedPreferences.getString("ReadedBookString", null);
        if (bookString != null) {
            Type type = new TypeToken<Book>() {
            }.getType();
            return gson.fromJson(bookString, type);
        }
        return null;
    }

}
