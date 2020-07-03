package com.neverdaless.wemeet.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.kakao.auth.IApplicationConfig;
import com.kakao.auth.ISessionCallback;
import com.kakao.auth.KakaoAdapter;
import com.kakao.auth.KakaoSDK;
import com.kakao.auth.Session;
import com.kakao.usermgmt.LoginButton;
import com.kakao.util.exception.KakaoException;
import com.neverdaless.wemeet.R;

public class LoginHomeFragment extends Fragment {
    private Context context;

    private Button btn_Login;

    private RelativeLayout btn_gmail;
    private Button btn_facebook;
    private Button btn_Email;

    private static final String TAG = "LoginHome";

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        this.context = context;
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_login_home, container, false);

        // SDK 초기화
        KakaoSDK.init(new KakaoAdapter() {

            @Override
            public IApplicationConfig getApplicationConfig() {
                return new IApplicationConfig() {
                    @Override
                    public Context getApplicationContext() {
                        return context.getApplicationContext();
                    }
                };
            }
        });

        btn_Login = root.findViewById(R.id.button_login);
        // 상대 레이아웃으로 구현한 버튼이라 편의상 버튼이라고 작명함
        btn_gmail = root.findViewById(R.id.linearLayout_gmail);
        
        btn_facebook = root.findViewById(R.id.button_facebook);
        btn_Email = root.findViewById(R.id.button_email);

        return root;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // 로그인 버튼
        btn_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(LoginHomeFragment.this)
                        .navigate(R.id.action_loginHomeFragment_to_loginInputFragment);
            }
        });
        
        btn_gmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "지메일로 회원가입하기.", Toast.LENGTH_SHORT).show();
            }
        });

        // 카카오 로그인 세션 콜백 등록
        // 버튼에 리스너를 부착하는 것이 아니고
        // 카카오에서 자체적으로 제공하는 콜백메소드를 활용
        Session.getCurrentSession().addCallback(sessionCallback);
        
        btn_facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "페이스북으로 회원가입하기.", Toast.LENGTH_SHORT).show();
            }
        });

        // 이메일 버튼
        btn_Email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(LoginHomeFragment.this)
                        .navigate(R.id.action_loginHomeFragment_to_signUpWithEmailFragment);
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        // 세션 콜백 삭제
        Session.getCurrentSession().removeCallback(sessionCallback);
    }

    // 세션 콜백 구현
    private ISessionCallback sessionCallback = new ISessionCallback() {
        @Override
        public void onSessionOpened() {
            Log.i("KAKAO_SESSION", "로그인 성공");
        }

        @Override
        public void onSessionOpenFailed(KakaoException exception) {
            Log.e("KAKAO_SESSION", "로그인 실패", exception);
        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        // 카카오톡|스토리 간편로그인 실행 결과를 받아서 SDK로 전달
        if (Session.getCurrentSession().handleActivityResult(requestCode, resultCode, data)) {
            Log.d(TAG, requestCode + " | " + resultCode + " | " + data);
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}