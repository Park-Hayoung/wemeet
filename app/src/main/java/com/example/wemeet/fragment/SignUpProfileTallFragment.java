package com.example.wemeet.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.wemeet.R;
import com.example.wemeet.data.ScrollViewAdapter;
import com.example.wemeet.data.ScrollViewItem;
import com.example.wemeet.data.SpinnerAdapter;
import com.example.wemeet.data.UserItem;
import com.yarolegovich.discretescrollview.DiscreteScrollView;

import static androidx.recyclerview.widget.RecyclerView.OnClickListener;
import static androidx.recyclerview.widget.RecyclerView.ViewHolder;

public class SignUpProfileTallFragment extends Fragment {
    private DiscreteScrollView scrollViewTall;

    private SeekBar seekBar;

    private Spinner spinnerBody;
    private Spinner spinnerEducation;

    private Button buttonNext;

    private ScrollViewAdapter tallAdapter;

    private int preSeekBarPos;

    private static final String TAG = "SignUpProfileTallEdu";

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.
                inflate(R.layout.fragment_sign_up_profile_tall, container, false);

        scrollViewTall = root.findViewById(R.id.scrollView_tall);
        scrollViewSetting();

        seekBar = root.findViewById(R.id.seekBar);
        // ㅋㅋ디자인 시안 잘못 이해해서 SeekBar로 만들어놨음
        // 그냥 지워버리긴 아까워서 레이아웃만 사용하기로 함
        // 그래서 드래그해도 상태 바뀌지 않게 설정해놓음
        seekBar.setEnabled(false);

        spinnerBody = root.findViewById(R.id.spinner_body);
        String[] bodyList = getResources().getStringArray(R.array.body);
        SpinnerAdapter bodyAdapter =
                new SpinnerAdapter(getContext(), android.R.layout.simple_spinner_item, bodyList);
        spinnerBody.setAdapter(bodyAdapter);

        spinnerEducation = root.findViewById(R.id.spinner_education);
        String[] educationList = getResources().getStringArray(R.array.education);
        SpinnerAdapter educationAdapter =
                new SpinnerAdapter(getContext(), android.R.layout.simple_spinner_item, educationList);
        spinnerEducation.setAdapter(educationAdapter);

        buttonNext = root.findViewById(R.id.button_next);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // 처음 가운데 보여줄 165의 값에 강조효과를 줌
        preSeekBarPos = 35;
        tallAdapter.getItem(preSeekBarPos).setSelected(true);
        scrollViewTall.scrollToPosition(preSeekBarPos);

        scrollViewTall.addOnItemChangedListener(new DiscreteScrollView.OnItemChangedListener<ViewHolder>() {
            @Override
            public void onCurrentItemChanged(@Nullable ViewHolder viewHolder, int i) {
                Log.d(TAG, "preSeekBarPos : " + preSeekBarPos + " |  i = " + i);

                tallAdapter.getItem(preSeekBarPos).setSelected(false);
                tallAdapter.getItem(i).setSelected(true);

                Handler handler = new Handler();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        tallAdapter.notifyDataSetChanged();
                    }
                });

                preSeekBarPos = i;
            }
        });

        buttonNext.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                UserItem user = (UserItem) getArguments().getSerializable("userItem");

                int position = scrollViewTall.getCurrentItem();
                String bodyShape = (String) spinnerBody.getSelectedItem();
                String education = (String) spinnerEducation.getSelectedItem();
                user.setTall(tallAdapter.getItem(position).getValue());
                user.setBodyShape(bodyShape);
                user.setEducation(education);

                Bundle bundle = new Bundle();
                bundle.putSerializable("userItem", user);

                NavHostFragment.findNavController(SignUpProfileTallFragment.this)
                        .navigate(R.id.action_signUpProfileTallFragment_to_signUpProfileAgeFragment, bundle);
            }
        });

        // seekBar.setOnSeekBarChangeListener(new MySeekBarChangeListener());
    }

    private void scrollViewSetting() {
        tallAdapter = new ScrollViewAdapter(getContext());

        // 키는 130 ~ 200로 설정할 수 있도록 함
        // 중앙값은 165
        for (int i = 130; i < 201; i++) {
            ScrollViewItem tallItem = new ScrollViewItem(i, false);
            tallAdapter.addItem(tallItem);
        }

        scrollViewTall.setAdapter(tallAdapter);
        // adapter.notifyDataSetChanged();

        scrollViewTall.setItemTransitionTimeMillis(500);

        scrollViewTall.setSlideOnFling(true); // 빠르게 스크롤링 가능
        scrollViewTall.setSlideOnFlingThreshold(500); // 스크롤 할 수 있는 간격
    }

    /*
    // 삭제된 기능
    class MySeekBarChangeListener implements SeekBar.OnSeekBarChangeListener {

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            Log.d(TAG, preSeekBarPos + " | " + progress);

            adapter.getItem(preSeekBarPos).setSelected(false);
            adapter.getItem(progress).setSelected(true);
            adapter.notifyDataSetChanged();

            int dpi = getContext().getResources().getDisplayMetrics().densityDpi;
            int step = progress - preSeekBarPos;
            int scrollX = (47 * dpi / 160) * step; // 한 뷰가 시작되는 지점에서 다음 뷰의 시작점까지의 거리가 47dp

            recyclerViewTall.smoothScrollBy(scrollX, 0);

            Log.d(TAG, "scrollX = " + scrollX);

            preSeekBarPos = progress;
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    }

     */
}
