package com.example.wemeet.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.wemeet.R;
import com.example.wemeet.data.SpinnerAdapter;
import com.example.wemeet.data.UserItem;

public class SignUpAuthFragment extends Fragment {
    private Spinner spinnerCountry;
    private EditText editTextPhoneNum;

    private Button buttonRequestAuthNum;
    private Button buttonNext;

    private TextView textViewAuthNum;
    private EditText editTextAuthNum;
    private TextView textViewAuthNumResend;
    private TextView textViewTimeLeft;

    private int realAuthNumber;

    private SpinnerAdapter tallAdapter;

    private String country;
    private String phone;

    private Handler handler = new Handler();

    private boolean flag;

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        ViewGroup root =
                (ViewGroup) inflater.inflate(R.layout.fragment_sign_up_auth, container, false);

        spinnerCountry = root.findViewById(R.id.spinner_country);
        editTextPhoneNum = root.findViewById(R.id.editText_phone_number);
        buttonRequestAuthNum = root.findViewById(R.id.button_request_auth_number);

        // 인증번호 요청 후 보여질 뷰 참조
        textViewAuthNum = root.findViewById(R.id.textView_auth_num);
        editTextAuthNum = root.findViewById(R.id.editText_auth_num);
        textViewAuthNumResend = root.findViewById(R.id.textView_auth_num_resend);
        textViewTimeLeft = root.findViewById(R.id.textView_time_left);
        buttonNext = root.findViewById(R.id.button_next);

        // 인증번호 요청 전까지 안 보이게 설정
        textViewAuthNum.setVisibility(View.INVISIBLE);
        editTextAuthNum.setVisibility(View.INVISIBLE);
        textViewAuthNumResend.setVisibility(View.INVISIBLE);
        textViewTimeLeft.setVisibility(View.INVISIBLE);
        buttonNext.setVisibility(View.GONE);
        buttonNext.setEnabled(false);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // 스피너 어댑터 설정
        String[] countryList = getResources().getStringArray(R.array.country);
        tallAdapter = new SpinnerAdapter(getContext(), android.R.layout.simple_spinner_item, countryList);
        spinnerCountry.setAdapter(tallAdapter);

        editTextPhoneNum.addTextChangedListener(new PhoneTextWatcher());

        buttonRequestAuthNum.setOnClickListener(new RequestAuthNumButtonClickListener());

        textViewAuthNumResend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flag = false;

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                realAuthNumber = (int) ((Math.random() * 1000000) + 100000);
                if (realAuthNumber > 1000000)
                    realAuthNumber -= 100000;

                Toast.makeText(getContext(), "인증번호는 \'" + realAuthNumber + "\' 입니다.", Toast.LENGTH_SHORT).show();

                flag = true;
                new Thread(new MyRunnable()).start();
            }
        });
    }

    private void setTextUnderlined(TextView textView) {
        String text = textView.getText().toString();
        SpannableString str = new SpannableString(text);
        str.setSpan(new UnderlineSpan(), 0, str.length(), 0);
        textView.setText(str);
        textView.setEnabled(true);
    }

    // 국가와 휴대폰 번호가 입력되었는지 확인하여 '인증번호 받기' 버튼을 활성화하는 리스너
    class PhoneTextWatcher implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            // 인증번호가 입력되었으면 버튼을 핑크색으로 바꾸고 클릭 가능하게 한다
            country = spinnerCountry.getSelectedItem().toString();
            phone = editTextPhoneNum.getText().toString();

            if (!country.equals("") && !phone.equals("")) {
                buttonRequestAuthNum.setBackgroundResource(R.drawable.bg_rect_pink);
                buttonRequestAuthNum.setEnabled(true);
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {
            // 이메일, 비밀번호중 한 개라도 빈칸이면 로그인 버튼을 회색으로 바꾸고 클릭 못 하게 한다
            country = spinnerCountry.getSelectedItem().toString();
            phone = editTextPhoneNum.getText().toString();

            if (country.equals("") || phone.equals("")) {
                buttonRequestAuthNum.setBackgroundResource(R.drawable.bg_rect_gray);
                buttonRequestAuthNum.setEnabled(false);
            }
        }
    }

    // '인증번호 받기' 버튼 클릭리스너
    class RequestAuthNumButtonClickListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            // 인증번호 받기 버튼을 비활성화하고
            buttonRequestAuthNum.setVisibility(View.GONE);
            buttonRequestAuthNum.setEnabled(false);

            // 인증번호 입력관련 뷰들을 보이게 한다
            textViewAuthNum.setVisibility(View.VISIBLE);
            textViewTimeLeft.setVisibility(View.VISIBLE);
            editTextAuthNum.setVisibility(View.VISIBLE);
            textViewAuthNumResend.setVisibility(View.VISIBLE);
            buttonNext.setVisibility(View.VISIBLE);
            buttonNext.setEnabled(true);

            setTextUnderlined(textViewAuthNumResend);

            // 인증번호 입력란에 포커스를 요청함
            editTextAuthNum.requestFocus();

            // 인증번호 입력여부에 따른 '다음' 버튼 활성화 리스너
            editTextAuthNum.addTextChangedListener(new AuthNumberTextWatcher());

            // '다음' 버튼 클릭리스너
            buttonNext.setOnClickListener(new NextButtonClickListener());

            // 100000 ~ 999999 사이의 난수 생성
            realAuthNumber = (int) ((Math.random() * 1000000) + 100000);
            if (realAuthNumber > 1000000)
                realAuthNumber -= 100000;
            Toast.makeText(getContext(), "인증번호는 \'" + realAuthNumber + "\' 입니다.", Toast.LENGTH_SHORT).show();

            // 남은 시간 텍스트뷰 스레드
            flag = true;
            new Thread(new MyRunnable()).start();
        }
    }

    // 인증번호 입력 남은 시간 타이머 스레드
    class MyRunnable implements Runnable {
        int left = 300; // 5분
        StringBuilder param;

        @Override
        public void run() {
            while (true) {
                if (flag == false) {
                    break;
                }

                int minute = left / 60;
                int seconds = left % 60;

                param = new StringBuilder("남은 시간   ");
                if (minute / 10 == 0) {
                    param.append("0" + minute + " : ");
                } else {
                    param.append(minute + " : ");
                }

                if (seconds / 10 == 0) {
                    param.append("0" + seconds);
                } else {
                    param.append(seconds);
                }

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        textViewTimeLeft.setText(param.toString());

                        // 시간초과시
                        if (left == 0) {
                            Toast.makeText(getContext(), "다시 시도해주세요.", Toast.LENGTH_SHORT).show();
                            realAuthNumber = -1;
                        }
                    }
                });

                if (left == 0) {
                    break;
                }

                try {
                    Thread.sleep(1000);
                    left--;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // 인증번호 입력여부에 따라 '다음' 버튼 활성화, 비활성화
    class AuthNumberTextWatcher implements TextWatcher {
        String authNumber;

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            authNumber = editTextAuthNum.getText().toString();

            if (!authNumber.equals("")) { // '다음' 버튼 활성화
                buttonNext.setBackgroundResource(R.drawable.bg_rect_pink_border);
                buttonNext.setTextColor(Color.parseColor("#FA9380"));
                buttonNext.setEnabled(true);
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {
            authNumber = editTextAuthNum.getText().toString();

            if (authNumber != null) {
                if (authNumber.equals("")) { // 인증번호란이 빈칸이면 '다음' 버튼 비활성화
                    buttonNext.setBackgroundResource(R.drawable.bg_rect_gray);
                    buttonNext.setEnabled(false);
                }
            }

        }
    }

    // 인증 완료 후 활성화된 '다음' 버튼 클릭리스너
    class NextButtonClickListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            String authNumber = editTextAuthNum.getText().toString();

            if (authNumber != null) {
                String strRealAuthNumber = String.valueOf(realAuthNumber);

                if (authNumber.equals(strRealAuthNumber)) {
                    Toast.makeText(getContext(), "인증 성공.", Toast.LENGTH_SHORT).show();
                    flag = false; // 남은 시간 스레드 종료하도록 함

                    UserItem user = (UserItem) getArguments().getSerializable("userItem");
                    user.setCountry(country);
                    user.setPhone(phone);

                    Bundle bundle = new Bundle();
                    bundle.putSerializable("userItem", user);

                    // 다음 화면으로 이동
                    NavHostFragment.findNavController(SignUpAuthFragment.this)
                            .navigate(R.id.action_signUpAuthFragment_to_signUpProfileTallFragment, bundle);
                } else {
                    Toast.makeText(getContext(), "인증번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}
