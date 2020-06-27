package com.example.wemeet.fragment;

import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.wemeet.R;
import com.example.wemeet.data.BannerAdapter;
import com.example.wemeet.data.UserAdapter;
import com.example.wemeet.data.UserItem;

public class MainFragment extends Fragment {
    private ViewPager vp_banner;
    private ViewPager vp_user;

    BannerAdapter bannerAdapter;
    UserAdapter userAdapter;

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

        UserItem user1 = new UserItem();
        UserItem user2 = new UserItem();

        user1.setUserImage1(getURLForResource(R.drawable.ic_user_temp_1));
        user1.setAge(27);
        user1.setNickname("개발자 박하영");
        user1.setIntroduce("자유롭고 싶어라~");

        user2.setUserImage1(getURLForResource(R.drawable.ic_user_temp_1));
        user2.setAge(24);
        user2.setNickname("건축가 손영수");
        user2.setIntroduce("열정적인 삶을 원해.");

        userAdapter.addItem(new UserFragment(user1));
        userAdapter.addItem(new UserFragment(user2));

        vp_user.setAdapter(userAdapter);
        vp_user.setPageMargin(50);

        return root;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private String getURLForResource(int resId) {
        return Uri.parse("android.resource://" + R.class.getPackage().getName() + "/" + resId).toString();
    }
}
