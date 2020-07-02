package com.neverdaless.wemeet.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.neverdaless.wemeet.R;
import com.neverdaless.wemeet.data.UserItem;

public class SignUpAgreeFragment extends Fragment {
    private String nickname;
    private String email;
    private String password;

    private EditText editTextNickname;
    private EditText editTextEmail;
    private EditText editTextPassword;

    private CheckBox checkBoxService;
    private CheckBox checkBoxNotification;

    private Button buttonNext;

    private UserItem user;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_sign_up_agree, container, false);

        editTextNickname = root.findViewById(R.id.editText_nickname);
        editTextEmail = root.findViewById(R.id.editText_email);
        editTextPassword = root.findViewById(R.id.editText_password);
        setUserInputData();

        checkBoxService = root.findViewById(R.id.checkBox_service);
        checkBoxNotification = root.findViewById(R.id.checkBox_notification);

        checkBoxService.setOnCheckedChangeListener(new MyCheckedChangeListener());
        checkBoxNotification.setOnCheckedChangeListener(new MyCheckedChangeListener());
        setTextUnderlined();

        buttonNext = root.findViewById(R.id.button_next);
        buttonNext.setEnabled(false);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("userItem", user);

                int termsUse = checkBoxService.isChecked() ? 1 : 0;
                int termsNotif = checkBoxNotification.isChecked() ? 1 : 0;
                user.setTermsUse(termsUse);
                user.setTermsNotification(termsNotif);

                NavHostFragment.findNavController(SignUpAgreeFragment.this)
                        .navigate(R.id.action_signUpAgreeFragment_to_SignUpAuthFragment, bundle);
            }
        });
    }

    private void setUserInputData() {
        user = (UserItem) getArguments().getSerializable("userItem");
        email = user.getUserId();
        password = user.getUserPassword();
        nickname = user.getNickname();

        if (nickname != null) {
            editTextNickname.setText(nickname);
        }
        if (email != null) {
            editTextEmail.setText(email);
        }
        if (password != null) {
            editTextPassword.setText(password);
        }
    }

    // 체크박스에 밑줄 긋기
    private void setTextUnderlined() {
        SpannableString str = new SpannableString("서비스 이용 동의*");
        str.setSpan(new UnderlineSpan(), 0, str.length(), 0);
        checkBoxService.setText(str);

        str = new SpannableString("알림서비스 이용 동의*");
        str.setSpan(new UnderlineSpan(), 0, str.length(), 0);
        checkBoxNotification.setText(str);
    }

    // 이용 약관에 동의하였는지 알아내는 리스너
    // 두 개의 이용 약관에 모두 동의하였으면 다음 버튼을 활성화
    class MyCheckedChangeListener implements CompoundButton.OnCheckedChangeListener {

        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            boolean serviceChecked = checkBoxService.isChecked();
            boolean notificationChecked = checkBoxNotification.isChecked();

            if (serviceChecked && notificationChecked) {
                buttonNext.setBackgroundResource(R.drawable.bg_rect_pink_border);
                buttonNext.setTextColor(Color.parseColor("#FA9380"));
                buttonNext.setEnabled(true);
            } else {
                buttonNext.setBackgroundResource(R.drawable.bg_rect_gray);
                buttonNext.setTextColor(Color.parseColor("#FFFFFF"));
                buttonNext.setEnabled(false);
            }
        }
    }
}
