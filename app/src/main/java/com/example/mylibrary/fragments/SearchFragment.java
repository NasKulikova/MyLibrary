package com.example.mylibrary.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.SearchView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mylibrary.R;
import com.example.mylibrary.adapters.RecommendationRecyclerViewAdapter;
import com.example.mylibrary.model.Book;
import com.example.mylibrary.services.SharedPreferencesSingleton;
import com.example.mylibrary.viewmodels.MainActivityViewModel;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment {

    public static SearchFragment newInstance() {
        return new SearchFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        inflatedView = inflater.inflate(R.layout.fragment_search, container, false);
        return inflatedView;
    }

    private View inflatedView;
    private MainActivityViewModel model;
    private SearchView searchView;
    private Spinner searchSpinner;
    private CheckBox isFreeOnlyCheckBox;
    private RecyclerView searchResultRecyclerView;
    private RecommendationRecyclerViewAdapter recommendationRecyclerViewAdapter;
    private List<Book> searchResultBookList = new ArrayList<>();

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        model = new ViewModelProvider(requireActivity()).get(MainActivityViewModel.class);

        searchSpinner = inflatedView.findViewById(R.id.searchSpinner);
        fillSearchSpinner();
        isFreeOnlyCheckBox = inflatedView.findViewById(R.id.isFreeOnlyCheckBox);

        RecommendationRecyclerViewAdapter.OnDeferredButtonClickListener onDeferredButtonClickListener = position -> {
            searchResultBookList.get(position).setDeferred(!searchResultBookList.get(position).isDeferred());
            recommendationRecyclerViewAdapter.notifyItemChanged(position);
            model.updateBook(searchResultBookList.get(position));

            if (searchSpinner.getSelectedItemPosition() == 0) {
                searchByBookName(searchView.getQuery().toString());
            } else if (searchSpinner.getSelectedItemPosition() == 1) {
                searchByAuthor(searchView.getQuery().toString());
            } else if (searchSpinner.getSelectedItemPosition() == 2) {
                searchByGenre(searchView.getQuery().toString());
            }
        };

        RecommendationRecyclerViewAdapter.OnAddInLibraryButtonClickListener onAddInLibraryButtonClickListener = position -> {
            searchResultBookList.get(position).setAddedInLibrary(!searchResultBookList.get(position).isAddedInLibrary());
            recommendationRecyclerViewAdapter.notifyItemChanged(position);
            model.updateBook(searchResultBookList.get(position));
            if (!searchResultBookList.get(position).isAddedInLibrary()) {
                SharedPreferencesSingleton.getSharedPreferencesSingleton(requireContext().getApplicationContext()).saveReadedBook(null);
            }

            if (searchSpinner.getSelectedItemPosition() == 0) {
                searchByBookName(searchView.getQuery().toString());
            } else if (searchSpinner.getSelectedItemPosition() == 1) {
                searchByAuthor(searchView.getQuery().toString());
            } else if (searchSpinner.getSelectedItemPosition() == 2) {
                searchByGenre(searchView.getQuery().toString());
            }
        };

        RecommendationRecyclerViewAdapter.OnCoverClick onCoverClick = new RecommendationRecyclerViewAdapter.OnCoverClick() {
            @Override
            public void onCoverClick(int position) {
                model.setCurrentBook(searchResultBookList.get(position));
                Navigation.findNavController(requireActivity(),R.id.fragmentContainerView).navigate(R.id.action_mainScreenFragment_to_bookDetailedFragment2);

            }
        };

        searchResultRecyclerView = inflatedView.findViewById(R.id.searchResultRecyclerView);
        recommendationRecyclerViewAdapter = new RecommendationRecyclerViewAdapter(getContext(), searchResultBookList, onDeferredButtonClickListener, onAddInLibraryButtonClickListener, onCoverClick);
        searchResultRecyclerView.setAdapter(recommendationRecyclerViewAdapter);
        searchResultRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        searchByBookName("");

        searchView = inflatedView.findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                if (newText.isEmpty()) {
                    searchByBookName(newText);
                }

                if (searchSpinner.getSelectedItemPosition() == 0) {
                    searchByBookName(newText);
                } else if (searchSpinner.getSelectedItemPosition() == 1) {
                    searchByAuthor(newText);
                } else if (searchSpinner.getSelectedItemPosition() == 2) {
                    searchByGenre(newText);
                }

                return false;
            }
        });

        searchSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if (searchView.getQuery().toString().isEmpty()) {
                    searchByBookName(searchView.getQuery().toString());
                }

                if (searchSpinner.getSelectedItemPosition() == 0) {
                    searchByBookName(searchView.getQuery().toString());
                } else if (searchSpinner.getSelectedItemPosition() == 1) {
                    searchByAuthor(searchView.getQuery().toString());
                } else if (searchSpinner.getSelectedItemPosition() == 2) {
                    searchByGenre(searchView.getQuery().toString());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        isFreeOnlyCheckBox.setOnCheckedChangeListener((compoundButton, b) -> {
            if (searchView.getQuery().toString().isEmpty()) {
                searchByBookName(searchView.getQuery().toString());
            }

            if (searchSpinner.getSelectedItemPosition() == 0) {
                searchByBookName(searchView.getQuery().toString());
            } else if (searchSpinner.getSelectedItemPosition() == 1) {
                searchByAuthor(searchView.getQuery().toString());
            } else if (searchSpinner.getSelectedItemPosition() == 2) {
                searchByGenre(searchView.getQuery().toString());
            }
        });

    }

    private void searchByBookName(String query) {
        model.getSearchResultByBookName(query, isFreeOnlyCheckBox.isChecked()).observe(getViewLifecycleOwner(), books -> {
            model.getSearchResultByBookName(query, isFreeOnlyCheckBox.isChecked()).removeObservers(getViewLifecycleOwner());
            searchResultBookList.clear();
            searchResultBookList.addAll(books);
            recommendationRecyclerViewAdapter.notifyDataSetChanged();
        });
    }

    private void searchByAuthor(String query) {
        model.getSearchResultByAuthor(query, isFreeOnlyCheckBox.isChecked()).observe(getViewLifecycleOwner(), books -> {
            model.getSearchResultByAuthor(query, isFreeOnlyCheckBox.isChecked()).removeObservers(getViewLifecycleOwner());
            searchResultBookList.clear();
            searchResultBookList.addAll(books);
            recommendationRecyclerViewAdapter.notifyDataSetChanged();
        });
    }

    private void searchByGenre(String query) {
        model.getSearchResultByGenre(query, isFreeOnlyCheckBox.isChecked()).observe(getViewLifecycleOwner(), books -> {
            model.getSearchResultByGenre(query, isFreeOnlyCheckBox.isChecked()).removeObservers(getViewLifecycleOwner());
            searchResultBookList.clear();
            searchResultBookList.addAll(books);
            recommendationRecyclerViewAdapter.notifyDataSetChanged();
        });
    }


    private void fillSearchSpinner() {
        List<String> searchVariantsList = new ArrayList<>();
        searchVariantsList.add("По названию");
        searchVariantsList.add("По автору");
        searchVariantsList.add("По жанру");
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, searchVariantsList);
        searchSpinner.setAdapter(arrayAdapter);
        searchSpinner.setSelection(0);
    }
}