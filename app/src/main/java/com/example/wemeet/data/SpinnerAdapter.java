package com.example.wemeet.data;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.core.content.res.ResourcesCompat;

import com.example.wemeet.R;

public class SpinnerAdapter extends ArrayAdapter<String> {
    private Context context;
    private String[] items;

    private static final String TAG = "SpinnerAdapter";

    public SpinnerAdapter(Context context, int textViewResourceId, String[] items) {
        super(context, textViewResourceId, items);
        this.context = context;
        this.items = items;
    }

    // 스피너 뷰 정의
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(
                    android.R.layout.simple_spinner_item, parent, false);

            // 기본으로 들어가 있는 왼쪽 간격 없애기
            convertView.setPadding(
                    0,
                    convertView.getPaddingTop(),
                    convertView.getPaddingRight(),
                    convertView.getPaddingBottom());
        }

        TextView textView = convertView.findViewById(android.R.id.text1);
        setTextAppearance(textView, items[position]);

        return convertView;
    }

    // 드롭다운 뷰 정의
    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(
                    android.R.layout.simple_spinner_dropdown_item, parent, false);
        }

        TextView textView = convertView.findViewById(android.R.id.text1);

        setTextAppearance(textView, items[position]);

        return convertView;
    }

    private void setTextAppearance (TextView textView, String text) {
        textView.setText(text);
        Typeface typeface = ResourcesCompat.getFont(getContext(), R.font.bold);
        textView.setTypeface(typeface);
        textView.setTextColor(Color.parseColor("#000000")); // 검은색
        textView.setTextSize(18);
        textView.setLetterSpacing(-0.03f);
        textView.setLineSpacing(17, 0);
    }
}
