package com.example.mylibrary.fragments;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.mylibrary.R;
import com.example.mylibrary.model.Comment;
import com.example.mylibrary.model.User;
import com.example.mylibrary.viewmodels.MainActivityViewModel;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;


public class EntranceFragment extends Fragment {

    Button buttonSignIn, buttonRegister;
    private View inflatedView;
    RelativeLayout root;


    private MainActivityViewModel viewModel;


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(requireActivity()).get(MainActivityViewModel.class);
        buttonSignIn = inflatedView.findViewById(R.id.buttonSignIn);
        buttonRegister = inflatedView.findViewById(R.id.buttonRegister);
        root = inflatedView.findViewById(R.id.root_element);


        buttonSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowOnSignInWindow();
            }
        });
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowRegisterWindow();
            }
        });
    }

    private void ShowOnSignInWindow() {

        LayoutInflater inflater = LayoutInflater.from(requireContext());
        View sign_in_window = inflater.inflate(R.layout.sign_in_window, null);


        EditText email = sign_in_window.findViewById(R.id.emailField);
        EditText password = sign_in_window.findViewById(R.id.passField);

        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setView(sign_in_window);
        builder.setMessage("Введите данные для входа");
        builder.setNegativeButton("Отменить", (dialog, which) -> dialog.cancel());
        builder.setPositiveButton("Войти", null);
        AlertDialog alertDialog = builder.create();
        alertDialog.setOnShowListener(dialog -> alertDialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener(v -> {

            if (TextUtils.isEmpty(email.getText().toString())) {
                Snackbar.make(root, "Введите почту", Snackbar.LENGTH_SHORT).show();
                return;
            }

            if (password.getText().toString().length() < 6) {
                Snackbar.make(root, "Введите пароль длиной более 6 символов", Snackbar.LENGTH_SHORT).show();
                return;
            }

            viewModel.getUser(email.getText().toString(), password.getText().toString()).observe(getViewLifecycleOwner(), new Observer<List<User>>() {
                @Override
                public void onChanged(List<User> users) {
                    if (users.size() != 0) {
                        viewModel.setCurrentUser(users.get(0));
                        Navigation.findNavController(requireActivity(), R.id.fragmentContainerView).navigate(R.id.action_entranceFragment_to_mainScreenFragment);
                        dialog.dismiss();
                    }
                }
            });


        }));
        alertDialog.show();
//        AlertDialog.Builder dialog = new AlertDialog.Builder(requireContext());
//        dialog.setTitle("Войти");
//        dialog.setMessage("Введите данные для входа");
//
//        LayoutInflater inflater = LayoutInflater.from(requireContext());
//        View sign_in_window = inflater.inflate(R.layout.sign_in_window, null);
//        dialog.setView(sign_in_window);
//
//        EditText email = sign_in_window.findViewById(R.id.emailField);
//        EditText password = sign_in_window.findViewById(R.id.passField);
//
//
//        dialog.setNegativeButton("Отменить", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int which) {
//                dialogInterface.dismiss();
//            }
//        });
//        dialog.setPositiveButton("Войти", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int which) {
//                if (TextUtils.isEmpty(email.getText().toString())) {
//                    Snackbar.make(root, "Введите почту", Snackbar.LENGTH_SHORT).show();
//                    return;
//                }
//
//                if (password.getText().toString().length() < 6) {
//                    Snackbar.make(root, "Введите пароль длиной более 6 символов", Snackbar.LENGTH_SHORT).show();
//                    return;
//                }
//
//                viewModel.getUser(email.getText().toString(),password.getText().toString()).observe(getViewLifecycleOwner(), new Observer<List<User>>() {
//                    @Override
//                    public void onChanged(List<User> users) {
//                        if (users.size() != 0) {
//                            viewModel.setCurrentUser(users.get(0));
//                            Navigation.findNavController(requireActivity(), R.id.fragmentContainerView).navigate(R.id.action_entranceFragment_to_mainScreenFragment);
//                        }
//                    }
//                });
//
//
//            }
        //  });

        // dialog.show();

    }

    private void ShowRegisterWindow() {
        LayoutInflater inflater = LayoutInflater.from(requireContext());
        View register_window = inflater.inflate(R.layout.register_window, null);


        EditText email = register_window.findViewById(R.id.emailField);
        EditText password = register_window.findViewById(R.id.passField);
        EditText name = register_window.findViewById(R.id.nameField);

        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setView(register_window);
        builder.setMessage(R.string.registrationData);
        builder.setNegativeButton("Отменить", (dialog, which) -> dialog.cancel());
        builder.setPositiveButton("Зарегистрироваться", null);
        AlertDialog alertDialog = builder.create();
        alertDialog.setOnShowListener(dialog -> alertDialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener(v -> {

            if (TextUtils.isEmpty(email.getText().toString())) {
                Snackbar.make(root, "Введите почту", Snackbar.LENGTH_SHORT).show();
                return;
            }

            if (password.getText().toString().length() < 6) {
                Snackbar.make(root, "Введите пароль длиной более 6 символов", Snackbar.LENGTH_SHORT).show();
                return;
            }
            ;

            //регистрация пользователя

            viewModel.insertUser (password.getText().toString(),email.getText().toString(),name.getText().toString());
            viewModel.setCurrentUser(new User(email.getText().toString(),password.getText().toString(),name.getText().toString()));
            Navigation.findNavController(requireActivity(), R.id.fragmentContainerView).navigate(R.id.action_entranceFragment_to_mainScreenFragment);
            dialog.dismiss();

        }));
        alertDialog.show();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        inflatedView = inflater.inflate(R.layout.fragment_entrance, container, false);
        return inflatedView;

    }


}