package com.example.mylibrary.fragments;

import android.app.ProgressDialog;
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

public class MyLibraryFragment extends Fragment {

    public static MyLibraryFragment newInstance() {
        return new MyLibraryFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        inflatedView = inflater.inflate(R.layout.fragment_my_library, container, false);
        return inflatedView;
    }

    private View inflatedView;
    private MainActivityViewModel model;
    private RecyclerView myLibraryRecyclerView;
    private MyLibraryRecyclerViewAdapter myLibraryRecyclerViewAdapter;
    private List<Book> myLibraryBookList = new ArrayList<>();

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        model = new ViewModelProvider(requireActivity()).get(MainActivityViewModel.class);

        MyLibraryRecyclerViewAdapter.OnReadBookButtonClickListener onReadBookButtonClickListener = position -> {
            model.setReadedBook(myLibraryBookList.get(position));
            model.getAddedInLibraryBooks().removeObservers(requireActivity());

            ProgressDialog pd = new ProgressDialog(getContext());
            pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            pd.setCancelable(false);
            pd.setMessage("Loading...");
            pd.show();
            model.setProgressDialog(pd);

            Navigation.findNavController(requireActivity(), R.id.fragmentContainerView).navigate(R.id.action_mainScreenFragment_to_readFragment);
        };

        MyLibraryRecyclerViewAdapter.OnDeleteFromLibraryButtonClickListener onDeleteFromLibraryButtonClickListener = position -> {
            myLibraryBookList.get(position).setAddedInLibrary(false);
            model.updateBook(myLibraryBookList.get(position));
            SharedPreferencesSingleton.getSharedPreferencesSingleton(requireContext().getApplicationContext()).saveReadedBook(null);
        };

        MyLibraryRecyclerViewAdapter.OnCoverClick onCoverClick = new MyLibraryRecyclerViewAdapter.OnCoverClick() {
            @Override
            public void onCoverClick(int position) {
                model.setCurrentBook(myLibraryBookList.get(position));
                Navigation.findNavController(requireActivity(),R.id.fragmentContainerView).navigate(R.id.action_mainScreenFragment_to_bookDetailedFragment2);

            }
        };

        myLibraryRecyclerView = inflatedView.findViewById(R.id.deferredBooksRecyclerView);
        myLibraryRecyclerViewAdapter = new MyLibraryRecyclerViewAdapter(getContext(), myLibraryBookList, onReadBookButtonClickListener, onDeleteFromLibraryButtonClickListener, onCoverClick);
        myLibraryRecyclerView.setAdapter(myLibraryRecyclerViewAdapter);
        myLibraryRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));



        model.getAddedInLibraryBooks().observe(requireActivity(), books -> {
            myLibraryBookList.clear();
            myLibraryBookList.addAll(books);
            myLibraryRecyclerViewAdapter.notifyDataSetChanged();
        });
    }
}