package com.example.mylibrary.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mylibrary.R;
import com.example.mylibrary.adapters.DeferredBooksRecyclerViewAdapter;
import com.example.mylibrary.adapters.MyLibraryRecyclerViewAdapter;
import com.example.mylibrary.model.Book;
import com.example.mylibrary.services.SharedPreferencesSingleton;
import com.example.mylibrary.viewmodels.MainActivityViewModel;

import java.util.ArrayList;
import java.util.List;


public class DeferredBooksFragment extends Fragment {

    public static DeferredBooksFragment newInstance() {
        return new DeferredBooksFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        inflatedView = inflater.inflate(R.layout.fragment_deferred_books, container, false);
        return inflatedView;
    }

    private View inflatedView;
    private MainActivityViewModel model;
    private RecyclerView deferredBooksRecyclerView;
    private DeferredBooksRecyclerViewAdapter deferredBooksRecyclerViewAdapter;
    private List<Book> deferredBooksList = new ArrayList<>();

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        model = new ViewModelProvider(requireActivity()).get(MainActivityViewModel.class);

        DeferredBooksRecyclerViewAdapter.OnAddInLibraryButtonClickListener onAddInLibraryButtonClickListener = position -> {
            deferredBooksList.get(position).setAddedInLibrary(!deferredBooksList.get(position).isAddedInLibrary());
            model.updateBook(deferredBooksList.get(position));
            if(!deferredBooksList.get(position).isAddedInLibrary()){
                SharedPreferencesSingleton.getSharedPreferencesSingleton(requireContext().getApplicationContext()).saveReadedBook(null);
            }
        };

        DeferredBooksRecyclerViewAdapter.OnDeleteFromDeferredButtonClickListener onDeleteFromDeferredButtonClickListener = position -> {
            deferredBooksList.get(position).setDeferred(false);
            model.updateBook(deferredBooksList.get(position));
        };
        DeferredBooksRecyclerViewAdapter.OnCoverClick onCoverClick = new DeferredBooksRecyclerViewAdapter.OnCoverClick() {
            @Override
            public void onCoverClick(int position) {
                model.setCurrentBook(deferredBooksList.get(position));
                Navigation.findNavController(requireActivity(),R.id.fragmentContainerView).navigate(R.id.action_mainScreenFragment_to_bookDetailedFragment2);

            }
        };

        deferredBooksRecyclerView = inflatedView.findViewById(R.id.deferredBooksRecyclerView);
        deferredBooksRecyclerViewAdapter = new DeferredBooksRecyclerViewAdapter(getContext(), deferredBooksList, onAddInLibraryButtonClickListener, onDeleteFromDeferredButtonClickListener, onCoverClick);
        deferredBooksRecyclerView.setAdapter(deferredBooksRecyclerViewAdapter);
        deferredBooksRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));



        model.getDeferredBooks().observe(requireActivity(), books -> {
            deferredBooksList.clear();
            deferredBooksList.addAll(books);
            deferredBooksRecyclerViewAdapter.notifyDataSetChanged();
        });
    }
}