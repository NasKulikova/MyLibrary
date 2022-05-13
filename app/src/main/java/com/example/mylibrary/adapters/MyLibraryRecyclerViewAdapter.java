package com.example.mylibrary.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mylibrary.R;
import com.example.mylibrary.model.Book;

import java.util.ArrayList;
import java.util.List;

public class MyLibraryRecyclerViewAdapter extends RecyclerView.Adapter<MyLibraryRecyclerViewAdapter.ViewHolder> {

    private List<Book> bookList = new ArrayList<>();
    private final LayoutInflater inflater;
    private final Context context;
    private OnReadBookButtonClickListener onReadBookButtonClickListener;
    private OnDeleteFromLibraryButtonClickListener onDeleteFromLibraryButtonClickListener;
    private OnCoverClick coverClick;


    public MyLibraryRecyclerViewAdapter(Context context, List<Book> bookList, OnReadBookButtonClickListener onReadBookButtonClickListener, OnDeleteFromLibraryButtonClickListener onDeleteFromLibraryButtonClickListener, OnCoverClick coverClick) {
        this.bookList = bookList;
        this.inflater = LayoutInflater.from(context);
        this.context = context;
        this.onReadBookButtonClickListener = onReadBookButtonClickListener;
        this.onDeleteFromLibraryButtonClickListener = onDeleteFromLibraryButtonClickListener;
        this.coverClick = coverClick;
    }

    @NonNull
    @Override
    public MyLibraryRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.books_recycler_view_item, parent, false);
        return new MyLibraryRecyclerViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyLibraryRecyclerViewAdapter.ViewHolder holder, int position) {
        holder.nameOfBookTextView.setText(bookList.get(position).getNameOfBook());
        holder.genreOfBookTextView.setText(bookList.get(position).getGenreOfBook().getGenreName());
        holder.authorTextView.setText(bookList.get(position).getAuthor());
        holder.coverOfBookImageView.setImageDrawable(AppCompatResources.getDrawable(context, bookList.get(position).getCoverDrawableId()));
        holder.readBookButton.setText(R.string.read);
        holder.deleteFromLibraryButton.setText(R.string.delete_from_library);
        if(bookList.get(position).getCost()==0){
            holder.priceTextView.setText(holder.priceTextView.getContext().getString(R.string.free));
        } else {
            holder.priceTextView.setText(holder.priceTextView.getContext().getString(R.string.price, String.valueOf(bookList.get(position).getCost())));
        }
    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }

    public interface OnReadBookButtonClickListener {
        public void onReadBookButtonClick(int position);
    }
    public interface OnDeleteFromLibraryButtonClickListener {
        public void onDeleteFromLibraryButtonClick(int position);
    }

    public interface OnCoverClick {
        public void onCoverClick(int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView nameOfBookTextView;
        private TextView genreOfBookTextView;
        private TextView authorTextView;
        private ImageView coverOfBookImageView;
        private Button readBookButton;
        private Button deleteFromLibraryButton;
        private TextView priceTextView;

        ViewHolder(View view) {
            super(view);
            nameOfBookTextView = view.findViewById(R.id.nameOfBookTextView);
            genreOfBookTextView = view.findViewById(R.id.genreOfBookTextView);
            authorTextView = view.findViewById(R.id.authorTextView);
            coverOfBookImageView = view.findViewById(R.id.coverOfBookImageView);
            readBookButton = view.findViewById(R.id.addInLibraryButton);
            deleteFromLibraryButton = view.findViewById(R.id.deferButton);
            priceTextView = view.findViewById(R.id.priceTextView);

            readBookButton.setOnClickListener(this);
            deleteFromLibraryButton.setOnClickListener(this);
            coverOfBookImageView.setOnClickListener(this);
            nameOfBookTextView.setOnClickListener(this);
            genreOfBookTextView.setOnClickListener(this);
            authorTextView.setOnClickListener(this);
            priceTextView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if ((view.getId() != readBookButton.getId()) && (view.getId() != deleteFromLibraryButton.getId())) {
                coverClick.onCoverClick(getAdapterPosition());
            }
            if(view.getId() == readBookButton.getId()) {
                if (onReadBookButtonClickListener != null) {
                    onReadBookButtonClickListener.onReadBookButtonClick(getAdapterPosition());
                }
            }

            if(view.getId() == deleteFromLibraryButton.getId()) {
                if (onDeleteFromLibraryButtonClickListener != null) {
                    onDeleteFromLibraryButtonClickListener.onDeleteFromLibraryButtonClick(getAdapterPosition());
                }
            }
        }
    }

}
