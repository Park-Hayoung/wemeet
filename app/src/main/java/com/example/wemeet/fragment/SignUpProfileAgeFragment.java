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
import androidx.recyclerview.widget.RecyclerView;

import com.example.wemeet.R;
import com.example.wemeet.data.ScrollViewAdapter;
import com.example.wemeet.data.ScrollViewItem;
import com.example.wemeet.data.SpinnerAdapter;
import com.example.wemeet.data.UserItem;
import com.yarolegovich.discretescrollview.DiscreteScrollView;

public class SignUpProfileAgeFragment extends Fragment {
    private DiscreteScrollView scrollViewAge;

    private Spinner spinnerJob;
    private Spinner spinnerDrink;
    private Spinner spinnerSmoke;

    private Button buttonNext;

    private ScrollViewAdapter ageAdapter;

    private int preSeekBarPos;

    private static final String TAG = "SignUpProfileAge";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root =
                (ViewGroup) inflater.inflate(R.layout.fragment_sign_up_profile_age, container, false);

        scrollViewAge = root.findViewById(R.id.scrollView_age);
        scrollViewSetting();

        SeekBar seekBar = root.findViewById(R.id.seekBar);
        seekBar.setEnabled(false);

        spinnerJob = root.findViewById(R.id.spinner_job);
        spinnerDrink = root.findViewById(R.id.spinner_drink);
        spinnerSmoke = root.findViewById(R.id.spinner_smoke);

        buttonNext = root.findViewById(R.id.button_next);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // 처음 가운데 보여줄 20의 값에 강조효과를 줌
        preSeekBarPos = 5;
        ageAdapter.getItem(preSeekBarPos).setSelected(true);
        scrollViewAge.scrollToPosition(preSeekBarPos);

        scrollViewAge.addOnItemChangedListener(new DiscreteScrollView.OnItemChangedListener<RecyclerView.ViewHolder>() {
            @Override
            public void onCurrentItemChanged(@Nullable RecyclerView.ViewHolder viewHolder, int i) {
                Log.d(TAG, "preSeekBarPos : " + preSeekBarPos + " |  i = " + i);

                ageAdapter.getItem(preSeekBarPos).setSelected(false);
                ageAdapter.getItem(i).setSelected(true);

                Handler handler = new Handler();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        ageAdapter.notifyDataSetChanged();
                    }
                });

                preSeekBarPos = i;
            }
        });

        String[] jobList = getResources().getStringArray(R.array.job);
        SpinnerAdapter jobAdapter = new SpinnerAdapter(getContext(), android.R.layout.simple_spinner_item, jobList);
        spinnerJob.setAdapter(jobAdapter);

        String[] frequencyList = getResources().getStringArray(R.array.frequency);
        SpinnerAdapter drinkAdapter = new SpinnerAdapter(getContext(), android.R.layout.simple_spinner_item, frequencyList);
        SpinnerAdapter smokeAdapter = new SpinnerAdapter(getContext(), android.R.layout.simple_spinner_item, frequencyList);
        spinnerDrink.setAdapter(drinkAdapter);
        spinnerSmoke.setAdapter(smokeAdapter);

        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserItem user = (UserItem) getArguments().getSerializable("userItem");
                int position = scrollViewAge.getCurrentItem();
                String job = (String) spinnerJob.getSelectedItem();
                String drink = (String) spinnerDrink.getSelectedItem();
                String smoke = (String) spinnerSmoke.getSelectedItem();

                user.setAge(ageAdapter.getItem(position).getValue());
                user.setJob(job);
                user.setDrink(drink);
                user.setSmoke(smoke);

                Bundle bundle = new Bundle();
                bundle.putSerializable("userItem", user);

                NavHostFragment.findNavController(SignUpProfileAgeFragment.this)
                        .navigate(R.id.action_signUpProfileAgeFragment_to_signUpSelfieFragment, bundle);
            }
        });
    }

    private void scrollViewSetting() {
        ageAdapter = new ScrollViewAdapter(getContext());

        // 나이는 15 ~ 40으로 설정할 수 있도록 함
        // 중앙값은 20
        for (int i = 15; i < 41; i++) {
            ScrollViewItem tallItem = new ScrollViewItem(i, false);
            ageAdapter.addItem(tallItem);
        }

        scrollViewAge.setAdapter(ageAdapter);

        scrollViewAge.setItemTransitionTimeMillis(500);

        scrollViewAge.setSlideOnFling(true); // 빠르게 스크롤링 가능
        scrollViewAge.setSlideOnFlingThreshold(100); // 스크롤 할 수 있는 간격
    }
}