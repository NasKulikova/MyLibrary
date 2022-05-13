package com.example.mylibrary.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.mylibrary.R;
import com.example.mylibrary.services.SharedPreferencesSingleton;
import com.example.mylibrary.viewmodels.MainActivityViewModel;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;


public class ReadFragment extends Fragment {

    public static ReadFragment newInstance() {
        return new ReadFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        inflatedView = inflater.inflate(R.layout.fragment_read, container, false);
        return inflatedView;
    }

    private View inflatedView;
    private MainActivityViewModel model;
    private TextView nameOfReadedBookTextView;
    private TextView textOfReadedBookTextView;
    private TextView annotationOfReadedBookTextView;
    private TextView authorOfReadedBookTextView;
    private TextView publisherHouseOfReadedBookTextView;
    private ImageView coverOfReadedBookImageView;
    private SeekBar changeTextSizeSeekBar;
    private int[] textSizeVariants = {8, 9, 10, 11, 12, 14, 16, 18, 20, 22, 24, 26, 28, 36, 48, 72};
    private TextView textSizeTextView;


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        model = new ViewModelProvider(requireActivity()).get(MainActivityViewModel.class);

        SharedPreferencesSingleton.getSharedPreferencesSingleton(getContext().getApplicationContext()).saveReadedBook(model.getReadedBook());

        nameOfReadedBookTextView = inflatedView.findViewById(R.id.nameOfReadedBookTextView);
        textOfReadedBookTextView = inflatedView.findViewById(R.id.textOfReadedBookTextView);
        annotationOfReadedBookTextView = inflatedView.findViewById(R.id.annotationOfReadedBookTextView);
        authorOfReadedBookTextView = inflatedView.findViewById(R.id.authorOfReadedBookTextView);
        publisherHouseOfReadedBookTextView = inflatedView.findViewById(R.id.publisherHouseOfReadedBookTextView);
        coverOfReadedBookImageView = inflatedView.findViewById(R.id.coverOfReadedBookImageView);

        nameOfReadedBookTextView.setText(model.getReadedBook().getNameOfBook());
        textOfReadedBookTextView.setText(getStringFromRawFile(model.getReadedBook().getTextOfBookFileId()));
        annotationOfReadedBookTextView.setText(model.getReadedBook().getAnnotation());
        authorOfReadedBookTextView.setText(model.getReadedBook().getAuthor());
        publisherHouseOfReadedBookTextView.setText(model.getReadedBook().getPublishingHouse());
        coverOfReadedBookImageView.setImageDrawable(AppCompatResources.getDrawable(requireContext(), model.getReadedBook().getCoverDrawableId()));

        textSizeTextView = inflatedView.findViewById(R.id.textSizeTextView);
        textSizeTextView.setText(String.valueOf(textSizeVariants[4]));

        textOfReadedBookTextView.post(() -> model.getProgressDialog().dismiss());


        changeTextSizeSeekBar = inflatedView.findViewById(R.id.changeTextSizeSeekBar);
        changeTextSizeSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                textSizeTextView.setText(String.valueOf(textSizeVariants[seekBar.getProgress()]));
                nameOfReadedBookTextView.setTextSize(textSizeVariants[seekBar.getProgress()]);
                textOfReadedBookTextView.setTextSize(textSizeVariants[seekBar.getProgress()]);
                annotationOfReadedBookTextView.setTextSize(textSizeVariants[seekBar.getProgress()]);
                authorOfReadedBookTextView.setTextSize(textSizeVariants[seekBar.getProgress()]);
                publisherHouseOfReadedBookTextView.setTextSize(textSizeVariants[seekBar.getProgress()]);
            }
        });
    }

    private String getStringFromRawFile(int rawFileId) {
        InputStream is = requireActivity().getResources().openRawResource(rawFileId);
        String myText = "";
        try {
            myText = convertStreamToString(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return myText;
    }

    private String convertStreamToString(InputStream is) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int i = is.read();
        while (i != -1) {
            baos.write(i);
            i = is.read();
        }
        return baos.toString();
    }
}