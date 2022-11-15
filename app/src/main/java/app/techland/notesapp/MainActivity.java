package app.techland.notesapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Checkable;
import android.widget.Toast;

import app.techland.notesapp.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    String getData;
    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        binding.btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, NewNotes.class);
                intent.putExtra("main", "new");
                startActivity(intent);
            }
        });

        binding.btnSee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, ExistingNotes.class));
            }
        });

        binding.btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, MaintainNotes.class));
            }
        });

        getData = getIntent().getStringExtra("splash");
        if (getData.equalsIgnoreCase("main")) {
            Toast.makeText(this, "Home Page", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "not", Toast.LENGTH_SHORT).show();
        }
    }
}