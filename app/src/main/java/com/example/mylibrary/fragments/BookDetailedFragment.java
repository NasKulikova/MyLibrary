package com.example.mylibrary.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mylibrary.R;
import com.example.mylibrary.adapters.CommentsRecyclerViewAdapter;
import com.example.mylibrary.adapters.DeferredBooksRecyclerViewAdapter;
import com.example.mylibrary.model.Comment;
import com.example.mylibrary.model.User;
import com.example.mylibrary.viewmodels.MainActivityViewModel;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;


public class BookDetailedFragment extends Fragment {

    private View inflatedView;
    private MainActivityViewModel model;
    private RecyclerView commentsRecyclerView;
    private CommentsRecyclerViewAdapter commentsRecyclerViewAdapter;
    private ImageView coverOfBookImageView;
    private TextView authorTextView;
    private TextView nameOfBookTextView;
    private TextView genreTextView;
    private TextView annotationTextView;
    private List<User> allUsersList = new ArrayList<>();
    private List<Comment> allCommentsList = new ArrayList<>();
    private Button sendButton;
    private EditText enterCommentEditText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        inflatedView = inflater.inflate(R.layout.fragment_book_detailed, container, false);
        return inflatedView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        model = new ViewModelProvider(requireActivity()).get(MainActivityViewModel.class);
        coverOfBookImageView = inflatedView.findViewById(R.id.coverImageView);
        authorTextView = inflatedView.findViewById(R.id.authorOfBookTextView);
        nameOfBookTextView = inflatedView.findViewById(R.id.nameOfTheBookTextView);
        genreTextView = inflatedView.findViewById(R.id.genreOfTheBookTextView);
        annotationTextView = inflatedView.findViewById(R.id.annotationTextView);
        sendButton = inflatedView.findViewById(R.id.sendButton);
        enterCommentEditText = inflatedView.findViewById(R.id.enterCommentEditText);

        coverOfBookImageView.setImageDrawable(AppCompatResources.getDrawable(requireContext(), model.getCurrentBook().getCoverDrawableId()));
        authorTextView.setText(model.getCurrentBook().getAuthor());
        nameOfBookTextView.setText(model.getCurrentBook().getNameOfBook());
        genreTextView.setText(model.getCurrentBook().getGenreOfBook().getGenreName());
        annotationTextView.setText(model.getCurrentBook().getAnnotation());

        commentsRecyclerView = inflatedView.findViewById(R.id.commentsRecyclerView);
        commentsRecyclerViewAdapter = new CommentsRecyclerViewAdapter(getContext(), allCommentsList, allUsersList);
        commentsRecyclerView.setAdapter(commentsRecyclerViewAdapter);
        commentsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        model.getAllUsers().observe(getViewLifecycleOwner(), users -> {
            allUsersList.addAll(users);

            model.getCommentsByBook(model.getCurrentBook().getId()).observe(getViewLifecycleOwner(), comments -> {
                allCommentsList.clear();
                allCommentsList.addAll(comments);
                commentsRecyclerViewAdapter.notifyDataSetChanged();

            });
        });

        sendButton.setOnClickListener(view1 -> {
            if (!enterCommentEditText.getText().toString().isEmpty()) {
                model.insertComment(enterCommentEditText.getText().toString());
                commentsRecyclerViewAdapter.notifyDataSetChanged();
                enterCommentEditText.setText("");
            }
        });

    }


    public BookDetailedFragment() {
    }


    public static BookDetailedFragment newInstance() {
        BookDetailedFragment fragment = new BookDetailedFragment();
        return fragment;
    }


}