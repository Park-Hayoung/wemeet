package com.example.wemeet;

import android.animation.ObjectAnimator;
import android.animation.StateListAnimator;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;

import com.example.wemeet.fragment.LoginHomeFragment;
import com.google.android.material.appbar.AppBarLayout;

import java.sql.Time;

public class MainActivity extends AppCompatActivity {
    private NavController navController;

    private TextView textViewTitle; // 툴바 타이틀 출력 텍스트뷰

    private MenuItem menuItem_msg;

    private long previousTimeMillis = -1;

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewTitle = findViewById(R.id.textView_title);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // 툴바 뒤로가기 활성화
        getSupportActionBar().setDisplayShowTitleEnabled(false); // 앱이름 표시 안 하기

        // 툴바 엘리베이션 효과 없애기
        AppBarLayout appBarLayout = findViewById(R.id.app_bar);
        StateListAnimator stateListAnimator = new StateListAnimator();
        stateListAnimator.addState(new int[0], ObjectAnimator.ofFloat(appBarLayout,
                "elevation", 0));
        appBarLayout.setStateListAnimator(stateListAnimator);

        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        navController.addOnDestinationChangedListener(new MyDestinationChangedListener());

        AppHelper.openDatabase(getApplicationContext(), "user");
        AppHelper.createTableUser();

        navigateToLoginFragmentIfNotLoggedIn();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        menuItem_msg = menu.findItem(R.id.menu_message);
        Log.d(TAG, "메시지 메뉴 생성 완료.");

        int dstId = navController.getCurrentDestination().getId();
        if (dstId == R.id.loginHomeFragment) { // 초기화면에서 로그인 안 되어 있어 로그인 그래프로 이동하는 경우
            menuItem_msg.setVisible(false); // 메시지 메뉴를 안 보이게 설정해줌
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home: // toolbar up indicator
                int dstId = navController.getCurrentDestination().getId();

                switch (dstId) {
                    case R.id.mainFragment:
                        Toast.makeText(this, "마이페이지 클릭됨.", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.loginInputFragment:
                    case R.id.signUpWithEmailFragment:
                    case R.id.signUpAgreeFragment:
                        navController.navigate(R.id.loginHomeFragment);
                        break;
                    default:
                        navController.navigateUp();
                        break;
                }

                break;
            case R.id.menu_message:
                Toast.makeText(this, "쪽지 메뉴 클릭됨.", Toast.LENGTH_SHORT).show();

                break;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    // 프래그먼트 바뀔때마다 타이틀, 뒤로가기 버튼 설정
    class MyDestinationChangedListener implements NavController.OnDestinationChangedListener {

        @Override
        public void onDestinationChanged(@NonNull NavController controller,
                                         @NonNull NavDestination destination,
                                         @Nullable Bundle arguments) {
            String title = destination.getLabel().toString();
            textViewTitle.setText(title);

            int resId = -1;
            int dstId = destination.getId();
            switch (dstId) {
                case R.id.mainFragment:
                    resId = R.drawable.ic_my_page;
                    if (menuItem_msg != null) {
                        if (!menuItem_msg.isVisible()) {
                            menuItem_msg.setVisible(true);
                        }
                    }
                    break;
                case R.id.loginHomeFragment: // 유일하게 툴바에 아무것도 없는 프래그먼트
                    if (menuItem_msg != null) {
                        menuItem_msg.setVisible(false);
                    }
                    getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                    break;
                case R.id.loginInputFragment:
                case R.id.signUpWithEmailFragment:
                case R.id.signUpAgreeFragment:
                    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                    resId = R.drawable.ic_exit;
                    break;
                case R.id.signUpAuthFragment:
                case R.id.signUpProfileTallFragment:
                case R.id.signUpProfileAgeFragment:
                case R.id.signUpSelfieFragment:
                    resId = R.drawable.ic_back;
                    break;
                default:
                    break;
            }

            if (resId != -1) {
                getSupportActionBar().setHomeAsUpIndicator(resId);
            }
        }
    }

    @Override
    public void onBackPressed() {
        int dstId = navController.getCurrentDestination().getId();
        if (dstId == R.id.mainFragment) {
            if (previousTimeMillis == -1) {
                previousTimeMillis = System.currentTimeMillis();
                Toast.makeText(this, "한 번 더 누르면 앱이 종료됩니다.", Toast.LENGTH_SHORT).show();
            } else {
                long currentTimeMillis = System.currentTimeMillis();
                Log.d(TAG, "preTimeMillis : " + previousTimeMillis + " | curTimeMillis : " + currentTimeMillis);
                if (currentTimeMillis - previousTimeMillis < 2000) { // 뒤로가기를 2초 안에 2번 누르면 앱이 종료
                    finish();
                } else {
                    previousTimeMillis = currentTimeMillis;
                    Toast.makeText(this, "한 번 더 누르면 앱이 종료됩니다.", Toast.LENGTH_SHORT).show();
                }
            }
        } else {
            super.onBackPressed();
        }
    }

    private void navigateToLoginFragmentIfNotLoggedIn() {
        SharedPreferences userInfo = getSharedPreferences("userInfo", MODE_PRIVATE);

        String userId = userInfo.getString("userId", null);
        String userPwd = userInfo.getString("userPwd", null);

        // 저장된 아이디와 비밀번호가 없으면 로그인 그래프로 이동
        if (userId == null && userPwd == null) {
            navController.navigate(R.id.graph_login);
        }
    }
}