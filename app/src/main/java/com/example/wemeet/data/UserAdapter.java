package com.example.wemeet.data;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.wemeet.fragment.UserFragment;

import java.util.ArrayList;

public class UserAdapter extends FragmentStatePagerAdapter {
    private ArrayList<UserFragment> items = new ArrayList<>();

    public UserAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    public void addItem(UserFragment item) {
        items.add(item);
    }

    @NonNull
    @Override
    public UserFragment getItem(int position) {
        return items.get(position);
    }

    @Override
    public int getCount() {
        return items.size();
    }
}
