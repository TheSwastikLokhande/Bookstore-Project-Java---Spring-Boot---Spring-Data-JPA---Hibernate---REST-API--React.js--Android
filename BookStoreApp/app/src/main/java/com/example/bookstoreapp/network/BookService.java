package com.example.bookstoreapp.network;

import com.example.bookstoreapp.model.Book;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.*;

public interface BookService {

    @GET("books")
    Call<List<Book>> getAllBooks();

    @GET("books/{id}")
    Call<Book> getBookById(@Path("id") long id);

    @POST("books")
    Call<Book> addBook(@Body Book book);

    @DELETE("books/{id}")
    Call<Void> deleteBook(@Path("id") long id);
}
