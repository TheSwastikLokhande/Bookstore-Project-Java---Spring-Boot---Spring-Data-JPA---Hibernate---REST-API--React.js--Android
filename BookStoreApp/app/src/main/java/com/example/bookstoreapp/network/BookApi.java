package com.example.bookstoreapp.network;

import com.example.bookstoreapp.model.Book;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface BookApi {

    @GET("/bookstore-api/books") // Make sure it matches your Spring endpoint
    Call<List<Book>> getBooks();
}
