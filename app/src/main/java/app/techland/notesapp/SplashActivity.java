package app.techland.notesapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TableLayout;
import android.widget.Toast;

public class SplashActivity extends AppCompatActivity {
    TableLayout tableLayout;
    Animation frombottom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        tableLayout = findViewById(R.id.tableLayout);
        frombottom = AnimationUtils.loadAnimation(this, R.anim.frombottom);
        tableLayout.setAnimation(frombottom);

        final Thread thread = new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    sleep(3000);
                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                    intent.putExtra("splash", "main");
                    startActivity(intent);
                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        thread.start();
    }
}