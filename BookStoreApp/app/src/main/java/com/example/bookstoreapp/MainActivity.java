package com.example.bookstoreapp;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookstoreapp.adapter.BookAdapter;
import com.example.bookstoreapp.model.Book;
import com.example.bookstoreapp.network.ApiClient;
import com.example.bookstoreapp.network.BookService;

import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private BookAdapter bookAdapter;

    private BookService bookService;

    private EditText bookIdInput, deleteBookIdInput;
    private Button fetchByIdButton, deleteBookButton;

    private EditText titleInput, authorInput, isbnInput, priceInput;
    private Button addBookButton;

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize Views
        recyclerView = findViewById(R.id.recyclerView);
        bookIdInput = findViewById(R.id.bookIdInput);
        fetchByIdButton = findViewById(R.id.fetchButton);
        deleteBookIdInput = findViewById(R.id.deleteBookIdInput);
        deleteBookButton = findViewById(R.id.deleteButton);

        titleInput = findViewById(R.id.titleInput);
        authorInput = findViewById(R.id.authorInput);
        isbnInput = findViewById(R.id.isbnInput);
        priceInput = findViewById(R.id.priceInput);
        addBookButton = findViewById(R.id.addBookButton);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        bookService = ApiClient.getRetrofit().create(BookService.class);

        // Load books on startup
        loadAllBooks();

        // Fetch by ID
        fetchByIdButton.setOnClickListener(v -> {
            String idStr = bookIdInput.getText().toString().trim();
            Log.d("","idstr"+idStr);
            if (idStr.isEmpty()) {
                Toast.makeText(this, "Enter Book ID", Toast.LENGTH_SHORT).show();
                return;
            }

            try {
                long bookId = Long.parseLong(idStr);
                fetchBookById(bookId);
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Invalid ID format", Toast.LENGTH_SHORT).show();
            }
        });

        // Add new book
        addBookButton.setOnClickListener(v -> {
            String title = titleInput.getText().toString().trim();
            String author = authorInput.getText().toString().trim();
            String isbn = isbnInput.getText().toString().trim();
            String priceStr = priceInput.getText().toString().trim();

            if (title.isEmpty() || author.isEmpty() || isbn.isEmpty() || priceStr.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            double price;
            try {
                price = Double.parseDouble(priceStr);
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Invalid price", Toast.LENGTH_SHORT).show();
                return;
            }

            Book newBook = new Book(title, author, isbn, price);

            bookService.addBook(newBook).enqueue(new Callback<Book>() {
                @Override
                public void onResponse(Call<Book> call, Response<Book> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(MainActivity.this, "Book added!", Toast.LENGTH_SHORT).show();
                        clearInputFields();
                        loadAllBooks();
                    } else {
                        Log.e(TAG, "Add failed: " + response.code());
                        Toast.makeText(MainActivity.this, "Add failed!", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Book> call, Throwable t) {
                    Log.e(TAG, "Add error", t);
                    Toast.makeText(MainActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        });

        // Delete book
        deleteBookButton.setOnClickListener(v -> {
            String idStr = deleteBookIdInput.getText().toString().trim();

            if (idStr.isEmpty()) {
                Toast.makeText(this, "Enter Book ID to delete", Toast.LENGTH_SHORT).show();
                return;
            }

            try {
                long bookId = Long.parseLong(idStr);

                bookService.deleteBook(bookId).enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.isSuccessful()) {
                            Log.d(TAG, "Book deleted successfully.");
                            Toast.makeText(MainActivity.this, "Book deleted!", Toast.LENGTH_SHORT).show();
                            deleteBookIdInput.setText("");
                            loadAllBooks();
                        } else {
                            Log.e(TAG, "Delete failed. HTTP Code: " + response.code());
                            Toast.makeText(MainActivity.this, "Failed to delete! Code: " + response.code(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Log.e(TAG, "Delete error", t);
                        Toast.makeText(MainActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });

            } catch (NumberFormatException e) {
                Toast.makeText(this, "Invalid ID", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadAllBooks() {
        bookService.getAllBooks().enqueue(new Callback<List<Book>>() {
            @Override
            public void onResponse(Call<List<Book>> call, Response<List<Book>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    bookAdapter = new BookAdapter(response.body());
                    recyclerView.setAdapter(bookAdapter);
                } else {
                    Log.e(TAG, "Load failed with code: " + response.code());
                    Toast.makeText(MainActivity.this, "No data found!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Book>> call, Throwable t) {
                Log.e(TAG, "Load error", t);
                Toast.makeText(MainActivity.this, "Failed: " + t.getMessage(), Toast.LENGTH_LONG).show();
                authorInput.setText(t.getMessage());


            }
        });
    }

    private void fetchBookById(long id) {
        bookService.getBookById(id).enqueue(new Callback<Book>() {
            @Override
            public void onResponse(Call<Book> call, Response<Book> response) {

                if (response.isSuccessful() && response.body() != null) {

                    bookAdapter = new BookAdapter(Collections.singletonList(response.body()));
                    recyclerView.setAdapter(bookAdapter);
                } else {
                    Log.w(TAG, "Book not found with id: " + id);
                    Toast.makeText(MainActivity.this, "Book not found!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Book> call, Throwable t) {
                Log.e(TAG, "Fetch error", t);
                Toast.makeText(MainActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void clearInputFields() {
        titleInput.setText("");
        authorInput.setText("");
        isbnInput.setText("");
        priceInput.setText("");
    }
}
