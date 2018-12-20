package com.feng.weather.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.feng.weather.MainActivity;
import com.feng.weather.R;

/**
 * author 丰
 * date   2018/12/12
 * desc
 */
public class Fragment3 extends Fragment {

    private Button btnStart;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_guide_3, container, false);

        btnStart = view.findViewById(R.id.btn_guide_start);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSharedPreferences("guide", Context.MODE_PRIVATE)
                        .edit().putBoolean("isFirst", false).apply();
                Intent intent = new Intent(getActivity(), MainActivity.class);
                getActivity().startActivity(intent);
            }
        });

        return view;
    }
}
