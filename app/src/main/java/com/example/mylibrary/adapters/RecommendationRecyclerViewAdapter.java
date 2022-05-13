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

public class RecommendationRecyclerViewAdapter extends RecyclerView.Adapter<RecommendationRecyclerViewAdapter.ViewHolder> {

    private List<Book> bookList = new ArrayList<>();
    private final LayoutInflater inflater;
    private final Context context;
    private OnDeferredButtonClickListener onDeferredButtonClickListener;
    private OnAddInLibraryButtonClickListener onAddInLibraryButtonClickListener;
    private OnCoverClick coverClick;


    public RecommendationRecyclerViewAdapter(Context context, List<Book> bookList, OnDeferredButtonClickListener onDeferredButtonClickListener, OnAddInLibraryButtonClickListener onAddInLibraryButtonClickListener, OnCoverClick coverClick) {
        this.bookList = bookList;
        this.inflater = LayoutInflater.from(context);
        this.context = context;
        this.onDeferredButtonClickListener = onDeferredButtonClickListener;
        this.onAddInLibraryButtonClickListener = onAddInLibraryButtonClickListener;
        this.coverClick = coverClick;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.books_recycler_view_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.nameOfBookTextView.setText(bookList.get(position).getNameOfBook());
        holder.genreOfBookTextView.setText(bookList.get(position).getGenreOfBook().getGenreName());
        holder.authorTextView.setText(bookList.get(position).getAuthor());
        holder.coverOfBookImageView.setImageDrawable(AppCompatResources.getDrawable(context, bookList.get(position).getCoverDrawableId()));
        if (bookList.get(position).getCost() == 0) {
            holder.priceTextView.setText(holder.priceTextView.getContext().getString(R.string.free));
        } else {
            holder.priceTextView.setText(holder.priceTextView.getContext().getString(R.string.price, String.valueOf(bookList.get(position).getCost())));
        }

        if (bookList.get(position).isDeferred()) {
            holder.deferButton.setBackground(AppCompatResources.getDrawable(context, R.drawable.button_background_selected));
            holder.deferButton.setText(R.string.deferred);
        } else {
            holder.deferButton.setBackground(AppCompatResources.getDrawable(context, R.drawable.button_background_unselected));
            holder.deferButton.setText(R.string.defer);
        }
        if (bookList.get(position).isAddedInLibrary()) {
            holder.addInLibraryButton.setBackground(AppCompatResources.getDrawable(context, R.drawable.button_background_selected));
            holder.addInLibraryButton.setText(R.string.added_in_library);
        } else if (bookList.get(position).getCost() == 0) {
            holder.addInLibraryButton.setBackground(AppCompatResources.getDrawable(context, R.drawable.button_background_unselected));
            holder.addInLibraryButton.setText(R.string.add_in_library);
        } else {
            holder.addInLibraryButton.setBackground(AppCompatResources.getDrawable(context, R.drawable.button_background_unselected));
            holder.addInLibraryButton.setText(R.string.buy);
        }

    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }

    public interface OnDeferredButtonClickListener {
        public void onDeferredButtonClick(int position);
    }

    public interface OnAddInLibraryButtonClickListener {
        public void onAddInLibraryButtonClick(int position);
    }

    public interface OnCoverClick {
        public void onCoverClick(int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView nameOfBookTextView;
        private TextView genreOfBookTextView;
        private TextView authorTextView;
        private ImageView coverOfBookImageView;
        private Button deferButton;
        private Button addInLibraryButton;
        private TextView priceTextView;

        ViewHolder(View view) {
            super(view);
            nameOfBookTextView = view.findViewById(R.id.nameOfBookTextView);
            genreOfBookTextView = view.findViewById(R.id.genreOfBookTextView);
            authorTextView = view.findViewById(R.id.authorTextView);
            coverOfBookImageView = view.findViewById(R.id.coverOfBookImageView);
            deferButton = view.findViewById(R.id.deferButton);
            addInLibraryButton = view.findViewById(R.id.addInLibraryButton);
            priceTextView = view.findViewById(R.id.priceTextView);

            deferButton.setOnClickListener(this);
            addInLibraryButton.setOnClickListener(this);
            coverOfBookImageView.setOnClickListener(this);
            nameOfBookTextView.setOnClickListener(this);
            genreOfBookTextView.setOnClickListener(this);
            authorTextView.setOnClickListener(this);
            priceTextView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if ((view.getId() != addInLibraryButton.getId()) && (view.getId() != deferButton.getId())) {
                coverClick.onCoverClick(getAdapterPosition());
            }
            if (view.getId() == deferButton.getId()) {
                if (onDeferredButtonClickListener != null) {
                    onDeferredButtonClickListener.onDeferredButtonClick(getAdapterPosition());
                }
            }

            if (view.getId() == addInLibraryButton.getId()) {
                if (onAddInLibraryButtonClickListener != null) {
                    onAddInLibraryButtonClickListener.onAddInLibraryButtonClick(getAdapterPosition());
                }
            }
        }
    }

}
