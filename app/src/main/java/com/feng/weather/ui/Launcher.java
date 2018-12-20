package com.feng.weather.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.feng.weather.MainActivity;
import com.feng.weather.R;

/**
 * author 丰
 * date   2018/12/5
 * desc
 */
public class Launcher extends AppCompatActivity {

    private TextView tvDelay;
    private TextView tvSkip;

    private Intent intent;
    private CountDownTask task;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);

        tvDelay = findViewById(R.id.tv_delay);
        tvSkip = findViewById(R.id.tv_skip);

        tvSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent);
                finish();
            }
        });

        checkGuide();

        task = new CountDownTask();
        intent = new Intent(this, MainActivity.class);
    }

    @Override
    protected void onResume() {
        super.onResume();
        task.execute();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        task.cancel(task.isCancelled());
    }

    private void checkGuide() {
        SharedPreferences pref = getSharedPreferences("guide", MODE_PRIVATE);
        if (pref.getAll() == null || pref.getBoolean("isFirst", true)) {
            intent = new Intent(this, GuideActivity.class);
            startActivity(intent);
            finish();
        }
    }

    class CountDownTask extends AsyncTask<Void, Integer, Boolean> {

        @Override
        protected Boolean doInBackground(Void... voids) {
            try {
                for (int i = 3; i >= 0; i--) {
                    Thread.sleep(1000);
                    publishProgress(i);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
                return true;
            }
            return true;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            tvDelay.setText(String.valueOf(values[0]));
        }

        @Override
        protected void onPostExecute(Boolean result) {
            if (result) {
                startActivity(intent);
                finish();
            }
        }
    }
}
