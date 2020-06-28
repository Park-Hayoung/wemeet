package com.example.wemeet.fragment;

import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.wemeet.AppHelper;
import com.example.wemeet.R;
import com.example.wemeet.data.BannerAdapter;
import com.example.wemeet.data.UserAdapter;
import com.example.wemeet.data.UserItem;

public class MainFragment extends Fragment {
    private ViewPager vp_banner;
    private ViewPager vp_user;

    private BannerAdapter bannerAdapter;
    private UserAdapter userAdapter;

    private UserItem user;

    private static final String TAG = "MainActivity";

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

        return root;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        try {
            selectFromUser();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void selectFromUser() {
        if (AppHelper.database != null) {
            Cursor cursor = AppHelper.database.rawQuery("select * from user", null);

            user = new UserItem();
            if (cursor.getCount() > 0) {
                cursor.moveToNext();
                int id = cursor.getInt(0);
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

                Log.d(TAG, String.valueOf(id)); // auto incremented _id
                user.printItems();
            }
        }
    }
}
