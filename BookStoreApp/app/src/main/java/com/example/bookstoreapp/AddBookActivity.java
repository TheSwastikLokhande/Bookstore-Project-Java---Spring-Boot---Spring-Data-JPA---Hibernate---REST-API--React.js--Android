package com.example.bookstoreapp;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bookstoreapp.model.Book;
import com.example.bookstoreapp.network.BookService;
import com.example.bookstoreapp.network.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddBookActivity extends AppCompatActivity {

    private EditText titleInput, authorInput, isbnInput, priceInput;
    private Button submitButton;
    private BookService bookService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);

        titleInput = findViewById(R.id.titleInput);
        authorInput = findViewById(R.id.authorInput);
        isbnInput = findViewById(R.id.isbnInput);
        priceInput = findViewById(R.id.priceInput);
        submitButton = findViewById(R.id.submitButton);

        bookService = RetrofitClient.getClient("http://192.168.1.27:8080/bookstore-api/")
                .create(BookService.class);

        submitButton.setOnClickListener(v -> {
            Book book = new Book();
            book.setTitle(titleInput.getText().toString());
            book.setAuthor(authorInput.getText().toString());
            book.setIsbn(isbnInput.getText().toString());
            book.setPrice(Double.parseDouble(priceInput.getText().toString()));

            bookService.addBook(book).enqueue(new Callback<Book>() {
                @Override
                public void onResponse(Call<Book> call, Response<Book> response) {
                    Toast.makeText(AddBookActivity.this, "Book Added", Toast.LENGTH_SHORT).show();
                    finish(); // go back
                }

                @Override
                public void onFailure(Call<Book> call, Throwable t) {
                    Toast.makeText(AddBookActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });
    }
}
