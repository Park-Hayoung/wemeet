package com.example.wemeet.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.wemeet.R;

public class LoginHomeFragment extends Fragment {
    Button buttonLogin;
    Button buttonEmail;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_login_home, container, false);

        buttonLogin = root.findViewById(R.id.button_login);
        buttonEmail = root.findViewById(R.id.button_email);

        return root;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(LoginHomeFragment.this)
                        .navigate(R.id.action_loginHomeFragment_to_loginInputFragment);
            }
        });

        buttonEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(LoginHomeFragment.this)
                        .navigate(R.id.action_loginHomeFragment_to_signUpWithEmailFragment);
            }
        });
    }
}