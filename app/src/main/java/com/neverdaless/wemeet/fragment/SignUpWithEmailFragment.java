package com.neverdaless.wemeet.fragment;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.neverdaless.wemeet.R;
import com.neverdaless.wemeet.data.UserItem;

public class SignUpWithEmailFragment extends Fragment {
    private EditText editTextNickname;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private Button buttonNext;

    private UserItem user;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_sign_up_with_email, container, false);

        editTextNickname = root.findViewById(R.id.editText_nickname);
        editTextEmail = root.findViewById(R.id.editText_email);
        editTextPassword = root.findViewById(R.id.editText_password);

        editTextNickname.addTextChangedListener(new MyTextWatcher());
        editTextEmail.addTextChangedListener(new MyTextWatcher());
        editTextPassword.addTextChangedListener(new MyTextWatcher());

        buttonNext = root.findViewById(R.id.button_next);
        buttonNext.setEnabled(false);

        return root;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user = new UserItem();

                String email = editTextEmail.getText().toString();
                String password = editTextPassword.getText().toString();
                String nickname = editTextNickname.getText().toString();

                user.setUserId(email);
                user.setUserPassword(password);
                user.setNickname(nickname);

                Bundle bundle = new Bundle();
                bundle.putSerializable("userItem", user);

                NavHostFragment.findNavController(SignUpWithEmailFragment.this)
                        .navigate(R.id.action_signUpWithEmailFragment_to_signUpAgreeFragment, bundle);
            }
        });
    }

    class MyTextWatcher implements TextWatcher {
        private String nickname;
        private String email;
        private String password;

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            nickname = editTextNickname.getText().toString();
            email = editTextEmail.getText().toString();
            password = editTextPassword.getText().toString();

            if (!nickname.equals("") && !email.equals("") && !password.equals("")) {
                buttonNext.setBackgroundResource(R.drawable.bg_rect_pink);
                buttonNext.setEnabled(true);
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {
            nickname = editTextNickname.getText().toString();
            email = editTextEmail.getText().toString();
            password = editTextPassword.getText().toString();

            if (nickname.equals("") || email.equals("") || password.equals("")) {
                buttonNext.setBackgroundResource(R.drawable.bg_rect_gray);
                buttonNext.setEnabled(false);
            }
        }
    }
}
