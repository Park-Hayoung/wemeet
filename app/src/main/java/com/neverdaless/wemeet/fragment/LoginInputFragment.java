package com.neverdaless.wemeet.fragment;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.neverdaless.wemeet.R;

import java.util.HashMap;

public class LoginInputFragment extends Fragment {
    private Context context;

    private TextView textViewEmail;
    private TextView textViewPassword;
    TextView textViewForgotPassword;

    private EditText editTextEmail;
    private EditText editTextPassword;

    private Button buttonLogin;

    private HashMap<String, String> userInfo;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        this.context = context;

        userInfo = new HashMap<>();
        userInfo.put("bill0941@naver.com", "crush0416");
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        ViewGroup root =
                (ViewGroup) inflater.inflate(R.layout.fragment_login_input, container, false);

        textViewEmail = root.findViewById(R.id.textView_email);
        textViewPassword = root.findViewById(R.id.textView_password);

        editTextEmail = root.findViewById(R.id.editText_email);
        editTextPassword = root.findViewById(R.id.editText_password);

        editTextEmail.addTextChangedListener(new MyTextWatcher());
        editTextPassword.addTextChangedListener(new MyTextWatcher());

        buttonLogin = root.findViewById(R.id.button_login);
        buttonLogin.setEnabled(false); // 회색 버튼에서는 클릭 못하게 막음

        // 텍스트뷰에 밑줄 긋기
        textViewForgotPassword = root.findViewById(R.id.textView_forgot_password);
        SpannableString str = new SpannableString("혹시 비밀번호를 잊으셨나요?");
        str.setSpan(new UnderlineSpan(), 0, str.length(), 0);
        textViewForgotPassword.setText(str);
        textViewForgotPassword.setEnabled(true);
        textViewForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "비밀번호는 \'crush0416\' 입니다.", Toast.LENGTH_SHORT).show();
            }
        });

        return root;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = editTextEmail.getText().toString();
                String password = editTextPassword.getText().toString();

                if (!email.equals("")) {
                    String realPassword = userInfo.get(email);

                    if (realPassword != null) {
                        if (realPassword.equals(password)) {
                            Toast.makeText(getContext(), "로그인 성공.", Toast.LENGTH_SHORT).show();
                            NavHostFragment.findNavController(LoginInputFragment.this)
                                .navigate(R.id.action_loginInputFragment_to_mainFragment);

                        } else {
                            Toast.makeText(getContext(), "비밀번호가 틀렸습니다.", Toast.LENGTH_SHORT).show();
                            new PinkTwoSecondsThread(textViewPassword, editTextPassword).start();
                            setTypefaceBold(textViewForgotPassword);
                            textViewForgotPassword.setTextColor(Color.parseColor("#FA9380"));
                        }
                   } else {
                        Toast.makeText(getContext(), "이메일이 존재하지 않습니다.", Toast.LENGTH_SHORT).show();
                        new PinkTwoSecondsThread(textViewEmail, editTextEmail).start();
                    }
                }
            }
        });
    }

    class MyTextWatcher implements TextWatcher {
        private String email;
        private String password;

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            // 이메일, 비밀번호가 모두 입력되었으면 버튼을 핑크색으로 바꾸고 클릭 가능하게 한다
            email = editTextEmail.getText().toString();
            password = editTextPassword.getText().toString();

            if (!email.equals("") && !password.equals("")) {
                buttonLogin.setBackgroundResource(R.drawable.bg_rect_pink);
                buttonLogin.setEnabled(true);
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {
            // 이메일, 비밀번호중 한 개라도 빈칸이면 로그인 버튼을 회색으로 바꾸고 클릭 못 하게 한다
            email = editTextEmail.getText().toString();
            password = editTextPassword.getText().toString();

            if (email.equals("") || password.equals("")) {
                buttonLogin.setBackgroundResource(R.drawable.bg_rect_gray);
                buttonLogin.setEnabled(false);
            }
        }
    }

    static class PinkTwoSecondsThread extends Thread {
        private TextView textView;
        private EditText editText;

        public PinkTwoSecondsThread(TextView textView, EditText editText) {
            this.textView = textView;
            this.editText = editText;
        }

        @Override
        public void run() {
            // 글자색을 핑크색으로 바꿨다가
            String pinkColor = "#FA9380";
            textView.setTextColor(Color.parseColor(pinkColor));
            editText.setTextColor(Color.parseColor(pinkColor));

            // 3초 후에
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // 다시 검은색으로 바꾼다
            String blackColor = "#000000";
            textView.setTextColor(Color.parseColor(blackColor));
            editText.setTextColor(Color.parseColor(blackColor));
        }
    }

    private void setTypefaceBold(TextView textView) {
        Typeface typeface = ResourcesCompat.getFont(context, R.font.bold);

        textView.setTypeface(typeface);
    }
}