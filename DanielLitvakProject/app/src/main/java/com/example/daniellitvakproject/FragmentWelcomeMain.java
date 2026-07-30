package com.example.daniellitvakproject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

public class FragmentWelcomeMain extends Fragment {

    RadioButton radioEnglish;
    RadioButton radioHebrew;
    Button buttonStart;

    public FragmentWelcomeMain() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(
                R.layout.fragment_welcome_main,
                container,
                false
        );
        TextView tvWelcome = view.findViewById(R.id.tvWelcome);
        radioEnglish = view.findViewById(R.id.radioEnglish);
        radioHebrew = view.findViewById(R.id.radioHebrew);
        buttonStart = view.findViewById(R.id.buttonStart);
        tvWelcome.setText("Welcome to the Animal World");
        radioEnglish.setChecked(true);
        buttonStart.setOnClickListener(v -> {
            String language;
            if(radioHebrew.isChecked()){
                language = "Hebrew";
            }
            else{
                language = "English";
            }

            Bundle bundle = new Bundle();
            bundle.putString("language", language);
            Navigation.findNavController(view).navigate(R.id.action_fragmentWelcomeMain_to_fragmentLogin, bundle);
        });

        return view;
    }
}