package com.neverdaless.wemeet.data;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.neverdaless.wemeet.R;

import java.util.ArrayList;

public class ScrollViewAdapter extends RecyclerView.Adapter<ScrollViewAdapter.ViewHolder> {
    static Context context;

    private ArrayList<ScrollViewItem> items = new ArrayList<>();

    private static final String TAG = "ScrollViewAdapter";

    public ScrollViewAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    // ViewHolder가 만들어지는 시점에 호출
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater =
                (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // layout_tall_item을 parent에 붙여준다
        View itemView = inflater.inflate(R.layout.layout_tall_item, parent, false);

        return new ViewHolder(itemView);
    }

    // 데이터와 ViewHolder가 서로 결합되는 시점에 호출
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ScrollViewItem item = items.get(position);

        holder.setItem(item);
    }

    public void addItem(ScrollViewItem item) {
        items.add(item);
    }

    public void addItems(ArrayList<ScrollViewItem> items) {
        this.items = items;
    }

    public ScrollViewItem getItem(int position) {
        return items.get(position);
    }

    // ViewHolder 클래스 정의
    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewTall;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewTall = itemView.findViewById(R.id.textView_tall_item);
        }

        public void setItem(ScrollViewItem item) {
            int tall = item.getValue();
            boolean isSelected = item.isSelected();

            // onBindViewHolder에서 넘겨 받은 값을 적용
            textViewTall.setText(String.valueOf(tall));

            int dpi = context.getResources().getDisplayMetrics().densityDpi;
            if (isSelected) {
                textViewTall.setTextSize(18);
                Typeface typeface = ResourcesCompat.getFont(context, R.font.bold);
                textViewTall.setTypeface(typeface); // 폰트 bold 설정
                textViewTall.setTextColor(Color.parseColor("#000000"));
            } else {
                textViewTall.setTextSize(16);
                Typeface typeface = ResourcesCompat.getFont(context, R.font.medium);
                textViewTall.setTypeface(typeface); // 폰트 medium 설정
                textViewTall.setTextColor(Color.parseColor("#939393"));
            }
        }
    }
}
