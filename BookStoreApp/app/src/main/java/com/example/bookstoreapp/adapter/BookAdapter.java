package com.example.bookstoreapp.adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.bookstoreapp.R;
import com.example.bookstoreapp.model.Book;
import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder> {
    private List<Book> bookList;
    // ViewHolder
    public static class BookViewHolder extends RecyclerView.ViewHolder {
        TextView titleText, authorText, priceText;

        public BookViewHolder(@NonNull View view) {
            super(view);
            titleText = view.findViewById(R.id.titleText);
            authorText = view.findViewById(R.id.authorText);
            priceText = view.findViewById(R.id.priceText);
        }
    }

    // Constructor
    public BookAdapter(List<Book> bookList) {
        this.bookList = bookList;
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.book_item, parent, false);
        return new BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        Book book = bookList.get(position);
        holder.titleText.setText(book.getTitle());
        holder.authorText.setText(book.getAuthor());
        holder.priceText.setText("₹ " + book.getPrice());
    }

    @Override
    public int getItemCount() {
        return bookList != null ? bookList.size() : 0;
    }
}
