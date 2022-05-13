package com.example.mylibrary.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.Group;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.mylibrary.R;
import com.example.mylibrary.adapters.MyLibraryRecyclerViewAdapter;
import com.example.mylibrary.adapters.RecommendationRecyclerViewAdapter;
import com.example.mylibrary.model.Book;
import com.example.mylibrary.services.SharedPreferencesSingleton;
import com.example.mylibrary.viewmodels.MainActivityViewModel;

import java.util.ArrayList;
import java.util.List;


public class RecommendationFragment extends Fragment {

    public static RecommendationFragment newInstance() {
        return new RecommendationFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        inflatedView = inflater.inflate(R.layout.fragment_recommendation, container, false);
        return inflatedView;
    }

    private View inflatedView;
    private MainActivityViewModel model;
    private RecyclerView recommendationRecyclerView;
    private RecommendationRecyclerViewAdapter recommendationRecyclerViewAdapter;
    private TextView nameOfLastBookTextView;
    private TextView authorOfLastBookTextView;
    private List<Book> recommendationBooksList = new ArrayList<>();
    private Group lastBookGroup;
    private Button readLastBookButton;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        model = new ViewModelProvider(requireActivity()).get(MainActivityViewModel.class);

      //  model.fillDB();

        nameOfLastBookTextView = inflatedView.findViewById(R.id.nameOfLastBookTextView);
        authorOfLastBookTextView = inflatedView.findViewById(R.id.authorOfLastBookTextView);
        readLastBookButton = inflatedView.findViewById(R.id.readLastBookButton);

        lastBookGroup = inflatedView.findViewById(R.id.lastBookGroup);

        model.setReadedBook(SharedPreferencesSingleton.getSharedPreferencesSingleton(requireContext().getApplicationContext()).loadReadedBook());

        if ((model.getReadedBook() != null) && (model.getReadedBook().isAddedInLibrary())) {
            nameOfLastBookTextView.setText(model.getReadedBook().getNameOfBook());
            authorOfLastBookTextView.setText(model.getReadedBook().getAuthor());
            lastBookGroup.setVisibility(View.VISIBLE);
            readLastBookButton.setOnClickListener(view1 -> {
                ProgressDialog pd = new ProgressDialog(getContext());
                pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                pd.setCancelable(false);
                pd.setMessage("Loading...");
                pd.show();
                model.setProgressDialog(pd);
                Navigation.findNavController(requireActivity(), R.id.fragmentContainerView).navigate(R.id.action_mainScreenFragment_to_readFragment);
            });
        }

        RecommendationRecyclerViewAdapter.OnDeferredButtonClickListener onDeferredButtonClickListener = position -> {
            recommendationBooksList.get(position).setDeferred(!recommendationBooksList.get(position).isDeferred());
            model.updateBook(recommendationBooksList.get(position));
        };

        RecommendationRecyclerViewAdapter.OnAddInLibraryButtonClickListener onAddInLibraryButtonClickListener = position -> {
            recommendationBooksList.get(position).setAddedInLibrary(!recommendationBooksList.get(position).isAddedInLibrary());
            model.updateBook(recommendationBooksList.get(position));
            if(!recommendationBooksList.get(position).isAddedInLibrary()){
                SharedPreferencesSingleton.getSharedPreferencesSingleton(requireContext().getApplicationContext()).saveReadedBook(null);
                lastBookGroup.setVisibility(View.GONE);
            }
        };

        RecommendationRecyclerViewAdapter.OnCoverClick onCoverClick = new RecommendationRecyclerViewAdapter.OnCoverClick() {
            @Override
            public void onCoverClick(int position) {
                model.setCurrentBook(recommendationBooksList.get(position));
                Navigation.findNavController(requireActivity(),R.id.fragmentContainerView).navigate(R.id.action_mainScreenFragment_to_bookDetailedFragment2);

            }
        };

        recommendationRecyclerView = inflatedView.findViewById(R.id.recommendationRecyclerView);
        recommendationRecyclerViewAdapter = new RecommendationRecyclerViewAdapter(getContext(), recommendationBooksList, onDeferredButtonClickListener, onAddInLibraryButtonClickListener, onCoverClick);
        recommendationRecyclerView.setAdapter(recommendationRecyclerViewAdapter);
        recommendationRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        model.getAllBooks().observe(requireActivity(), books -> {
            recommendationBooksList.clear();
            recommendationBooksList.addAll(books);
            recommendationRecyclerViewAdapter.notifyDataSetChanged();
        });


    }

}