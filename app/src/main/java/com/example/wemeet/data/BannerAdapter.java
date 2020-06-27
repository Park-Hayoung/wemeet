package com.example.wemeet.data;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.wemeet.R;

import java.util.ArrayList;

public class BannerAdapter extends PagerAdapter {
    private Context context;

    private TextView tv_current;
    private TextView tv_total;

    private TextView tv_match_count;

    private ImageView iv_ad;

    private ArrayList<BannerItem> items = new ArrayList<>();

    public BannerAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ViewGroup root;

        if (position == 0) {
            root = (ViewGroup) inflater.inflate(R.layout.layout_banner_match, container, false);
            tv_match_count = root.findViewById(R.id.textView_match_count);
        } else {
            root = (ViewGroup) inflater.inflate(R.layout.layout_banner_item, container, false);
            iv_ad = root.findViewById(R.id.imageView_ad);
            iv_ad.setBackgroundResource(R.drawable.ic_banner);
        }

        tv_current = root.findViewById(R.id.textView_current_progress);
        tv_current.setText(String.valueOf(position + 1));

        tv_total = root.findViewById(R.id.textView_total_progress);
        tv_total.setText(" / " + getCount());

        container.addView(root);

        return root;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        super.destroyItem(container, position, object);
    }

    @Override
    public int getCount() {
        // 현재는 아이템이 딱 2개만 있으면 되니깐 2로 지정해놨지만
        // 나중에는 배너 갯수에 맞게 동적으로 바뀌도록 해주어야 함
        return 2;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return (view == object);
    }
}
