package com.example.mylibrary.viewmodels;

import android.app.Application;
import android.app.ProgressDialog;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.mylibrary.model.Book;
import com.example.mylibrary.model.Comment;
import com.example.mylibrary.model.User;
import com.example.mylibrary.repositories.MyLibraryRepository;

import java.util.ArrayList;
import java.util.List;

public class MainActivityViewModel extends AndroidViewModel {

    private List<Book> books = new ArrayList<>();
    private MyLibraryRepository myLibraryRepository;
    private Book readedBook;
    private ProgressDialog progressDialog;
    private User currentUser;
    private Book currentBook;

    public MainActivityViewModel(@NonNull Application application) {
        super(application);
        myLibraryRepository = new MyLibraryRepository(getApplication().getApplicationContext());
    }

    public ProgressDialog getProgressDialog() {
        return progressDialog;
    }

    public void setProgressDialog(ProgressDialog progressDialog) {
        this.progressDialog = progressDialog;
    }

    public Book getCurrentBook() {
        return currentBook;
    }

    public void setCurrentBook(Book currentBook) {
        this.currentBook = currentBook;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public Book getReadedBook() {
        return readedBook;
    }

    public void setReadedBook(Book readedBook) {
        this.readedBook = readedBook;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public LiveData<List<Book>> getAllBooks() {
        return myLibraryRepository.getAllBooks();
    }

    public LiveData<List<Book>> getDeferredBooks() {
        return myLibraryRepository.getDeferredBooks();
    }

    public LiveData<List<Book>> getAddedInLibraryBooks() {
        return myLibraryRepository.getAddedInLibraryBooks();
    }

    public LiveData<List<Book>> getSearchResultByBookName(String query, boolean isFreeOnly) {
        return myLibraryRepository.getSearchResultByBookName(query, isFreeOnly);
    }

    public LiveData<List<Book>> getSearchResultByAuthor(String query, boolean isFreeOnly) {
        return myLibraryRepository.getSearchResultByAuthor(query, isFreeOnly);
    }
    public LiveData<List<Book>> getSearchResultByGenre(String query, boolean isFreeOnly) {
        return myLibraryRepository.getSearchResultByGenre(query, isFreeOnly);
    }

    public LiveData<List<User>> getUser(String email, String password) {
        return myLibraryRepository.getUser(email, password);
    }

    public void updateBook(Book book) {
        myLibraryRepository.updateBook(book);
    }

    public LiveData<User> getUserById(int userId){
        return myLibraryRepository.getUserById(userId);
    }

    public LiveData<Book> getBookById(int bookId){
        return myLibraryRepository.getBookById(bookId);
    }

    public void insertUser(String password, String login, String userName ) {
         myLibraryRepository.insertUser(new User(login, password, userName));
    }

    public void insertComment(String commentText){
        myLibraryRepository.insertComment(new Comment(currentUser.getId(), currentBook.getId(), commentText));
    }

    public LiveData<List<Comment>> getCommentsByBook(int bookId){
        return myLibraryRepository.getCommentsByBook(bookId);
    }

    public LiveData<List<User>> getAllUsers() {
        return myLibraryRepository.getAllUsers();
    }

}
