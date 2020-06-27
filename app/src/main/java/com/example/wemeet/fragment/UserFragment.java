package com.example.wemeet.fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.wemeet.R;
import com.example.wemeet.data.UserItem;

public class UserFragment extends Fragment {
    private UserItem user;

    private ImageView iv_user_image;
    private TextView tv_age;
    private TextView tv_nickname;
    private TextView tv_introduce;

    public UserFragment(UserItem userItem) {
        this.user = userItem;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup)
                inflater.inflate(R.layout.layout_user_item, container, false);

        iv_user_image = root.findViewById(R.id.imageView_user_image);

        Bitmap bitmap = getLowerQualityBitmap();
        iv_user_image.setImageBitmap(bitmap);

        tv_age = root.findViewById(R.id.textView_age);
        tv_age.setText(user.getAge() + " 세");

        tv_nickname = root.findViewById(R.id.textView_nickname);
        tv_nickname.setText(user.getNickname());

        tv_introduce = root.findViewById(R.id.textView_introduce);
        tv_introduce.setText(user.getIntroduce());

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private Bitmap getLowerQualityBitmap() {
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = 2;

        Bitmap bitmap = BitmapFactory.decodeFile(user.getUserImage1(), bmOptions);

        return bitmap;
    }
}
