package com.example.daniellitvakproject;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class FragmentLogin extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    private String language;

    public FragmentLogin() {
    }
    public static FragmentLogin newInstance(String param1, String param2) {
        FragmentLogin fragment = new FragmentLogin();
        Bundle args = new Bundle();

        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
            language = getArguments().getString("language");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        Button buttonLOGIN = view.findViewById(R.id.buttonLOGIN);
        Button buttonREGISTER = view.findViewById(R.id.buttonREGISTER);

        EditText textEmail = view.findViewById(R.id.textEmail);
        EditText textPassword = view.findViewById(R.id.textPassword);

        if ("Hebrew".equals(language)) {
            buttonLOGIN.setText("להתחבר");
            buttonREGISTER.setText("להרשם");
            textEmail.setHint("אימייל");
            textPassword.setHint("סיסמה");
            textEmail.setGravity(Gravity.RIGHT);
            textPassword.setGravity(Gravity.RIGHT);
        }

        buttonLOGIN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity mainActivity = (MainActivity) getActivity();
                mainActivity.login(language);
            }
        });

        buttonREGISTER.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("language", language);
                Navigation.findNavController(view).navigate(R.id.action_fragmentLogin_to_fragmentRegister, bundle);
            }
        });

        return view;
    }
}