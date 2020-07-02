package com.neverdaless.wemeet.fragment;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.view.GestureDetectorCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.neverdaless.wemeet.AppHelper;
import com.neverdaless.wemeet.R;
import com.neverdaless.wemeet.data.BannerAdapter;
import com.neverdaless.wemeet.data.UserAdapter;
import com.neverdaless.wemeet.data.UserItem;

public class MainFragment extends Fragment {
    private Context context;

    private ViewPager vp_banner;
    private ViewPager vp_user;

    private BannerAdapter bannerAdapter;
    private UserAdapter userAdapter;

    private UserItem user;

    private ImageView iv_match_start;

    private static final String TAG = "MainFragment";

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
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_main, container, false);

        vp_banner = root.findViewById(R.id.viewPager_banner);
        bannerAdapter = new BannerAdapter(getContext());
        vp_banner.setAdapter(bannerAdapter);
        vp_banner.setPageMargin(70);

        vp_user = root.findViewById(R.id.viewPager_user);
        userAdapter = new UserAdapter(
                getChildFragmentManager(),
                FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);

        UserItem tempUser1 = new UserItem();
        UserItem tempUser2 = new UserItem();

        tempUser1.setUserImage1(String.valueOf(R.drawable.ic_user_temp_1));
        tempUser1.setAge(27);
        tempUser1.setNickname("개발자 박하영");
        tempUser1.setIntroduce("자유롭고 싶어라~");

        tempUser2.setUserImage1(String.valueOf(R.drawable.ic_user_temp_2));
        tempUser2.setAge(24);
        tempUser2.setNickname("건축가 손영수");
        tempUser2.setIntroduce("열정적인 삶을 원해.");

        userAdapter.addItem(new UserFragment(tempUser1, true));
        userAdapter.addItem(new UserFragment(tempUser2, true));

        vp_user.setAdapter(userAdapter);
        vp_user.setPageMargin(50);

        iv_match_start = root.findViewById(R.id.imageView_match_start_up);

        return root;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        try {
            selectFromUser();
        } catch (Exception e) {
            e.printStackTrace();
        }

        final GestureDetectorCompat gestureDetector = new GestureDetectorCompat(context, new MyGestureListener());
        iv_match_start.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                gestureDetector.onTouchEvent(motionEvent);
                return true;
            }
        });
    }

    private void selectFromUser() {
        if (AppHelper.database != null) {
            Cursor cursor = AppHelper.database.rawQuery("select * from user", null);

            user = new UserItem();
            if (cursor.getCount() > 0) {
                cursor.moveToNext();

                user.setId(cursor.getInt(0));
                user.setUserId(cursor.getString(1));
                user.setUserPassword(cursor.getString(2));
                user.setNickname(cursor.getString(3));
                user.setIntroduce(cursor.getString(4));
                user.setTermsUse(cursor.getInt(5));
                user.setTermsNotification(cursor.getInt(6));
                user.setCountry(cursor.getString(7));
                user.setPhone(cursor.getString(8));
                user.setTall(cursor.getInt(9));
                user.setBodyShape(cursor.getString(10));
                user.setEducation(cursor.getString(11));
                user.setAge(cursor.getInt(12));
                user.setJob(cursor.getString(13));
                user.setDrink(cursor.getString(14));
                user.setSmoke(cursor.getString(15));
                user.setUserImage1(cursor.getString(16));
                user.setUserImage2(cursor.getString(17));
                user.setUserImage3(cursor.getString(18));
                user.setUserImage4(cursor.getString(19));

                user.printItems();
            }
        }
    }

    private class MyGestureListener implements GestureDetector.OnGestureListener {

        @Override
        public boolean onDown(MotionEvent motionEvent) {
            return false;
        }

        @Override
        public void onShowPress(MotionEvent motionEvent) {

        }

        @Override
        public boolean onSingleTapUp(MotionEvent motionEvent) {
            return false;
        }

        @Override
        public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
            Log.d(TAG, "onScroll : v -> " + v + " | v1 -> " + v1);
            return false;
        }

        @Override
        public void onLongPress(MotionEvent motionEvent) {

        }

        @Override
        public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
            Log.d(TAG, "onFling : v -> " + v + " | v1 -> " + v1);
            Toast.makeText(context, "게임 화면으로 ㄱㄱ", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    /*
    //------------------- 없어진 기능 --------------------//
    //------------ 눌렀을 버튼을 크게 만들고 -------------//
    //------------ 뗐을때 버튼을 작게 만든다 -------------//
    class MyTouchListener implements View.OnTouchListener {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            switch (motionEvent.getAction()) {
                case MotionEvent.ACTION_DOWN: // 눌렸을때
                    setUpImageBigger();
                    break;
                case MotionEvent.ACTION_MOVE: // 움직일때
                    break;
                case MotionEvent.ACTION_UP: // 뗐을때
                    setUpImageSmaller();
                    break;
                default:
                    break;
            }
            return true;
        }
    }

    private void setUpImageBigger() {
        DisplayMetrics metrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int dpi = metrics.densityDpi;

        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) iv_match_start.getLayoutParams();
        params.width = 40 * dpi / 160;
        params.height = 40 * dpi / 160;
        params.topMargin = 0;

        iv_match_start.setLayoutParams(params);
        iv_match_start.setImageResource(R.drawable.ic_up);
    }

    private void setUpImageSmaller() {
        DisplayMetrics metrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int dpi = metrics.densityDpi;

        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) iv_match_start.getLayoutParams();
        params.width = 24 * dpi / 160;
        params.height = 24 * dpi / 160;
        params.topMargin = 10 * dpi / 160;

        iv_match_start.setLayoutParams(params);
        iv_match_start.setImageResource(R.drawable.ic_up);
    }
    //------------------- 없어진 기능 --------------------//
    */
}
