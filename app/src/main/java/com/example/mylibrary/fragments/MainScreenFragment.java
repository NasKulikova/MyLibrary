package com.example.mylibrary.fragments;

import android.graphics.BlendMode;
import android.graphics.PorterDuff;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mylibrary.R;
import com.example.mylibrary.viewmodels.MainActivityViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainScreenFragment extends Fragment {

    public static MainScreenFragment newInstance() {
        return new MainScreenFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        inflatedView = inflater.inflate(R.layout.fragment_main_screen, container, false);
        return inflatedView;
    }

    private View inflatedView;
    private BottomNavigationView bottomNavigationView;
    private NavController navController;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        bottomNavigationView = inflatedView.findViewById(R.id.bottomNavigationView);
        navController = Navigation.findNavController(getActivity(), R.id.fragmentContainerView2);
        NavigationUI.setupWithNavController(bottomNavigationView, navController);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            return NavigationUI.onNavDestinationSelected(item, navController);
        });
    }
}