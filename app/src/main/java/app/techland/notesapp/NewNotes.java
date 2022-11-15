package app.techland.notesapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import app.techland.notesapp.Database.DatabaseHelperClass;
import app.techland.notesapp.Model.RecyclerModel;
import app.techland.notesapp.databinding.ActivityNewNotesBinding;

public class NewNotes extends AppCompatActivity {
    ActivityNewNotesBinding binding;
    String title, description;
    DatabaseHelperClass helperClass;
    String getData;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_new_notes);
        helperClass = new DatabaseHelperClass(this);

        // For New Notes
        getData = getIntent().getStringExtra("main");
        if (getData.equalsIgnoreCase("new")) {
            binding.btnCreate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    title = binding.etTitle.getText().toString();
                    description = binding.etDesc.getText().toString();
                    if (title.isEmpty()) {
                        binding.etTitle.setError("Required");
                    } else if (description.isEmpty()) {
                        binding.etDesc.setError("Required");
                    } else {

                        boolean status = helperClass.INSERT_IN_LAB(title, description);
                        if (status) {
                            Toast.makeText(NewNotes.this, "Inserted Successfully", Toast.LENGTH_LONG).show();
                            binding.etTitle.setText("");
                            binding.etDesc.setText("");
                        } else {
                            Toast.makeText(NewNotes.this, "Insertion Failed", Toast.LENGTH_SHORT).show();
                        }

                    }
                }
            });


        }

        // For Update Notes
        else if (getData.equalsIgnoreCase("update")) {
            binding.btnCreate.setText("Update Notes");
            String title1 = getIntent().getStringExtra("title");
            String description1 = getIntent().getStringExtra("desc");
            Integer position = getIntent().getIntExtra("id", 0);

            binding.etTitle.setText(title1);
            binding.etDesc.setText(description1);

            binding.btnCreate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    title = binding.etTitle.getText().toString();
                    description = binding.etDesc.getText().toString();
                    if (title.isEmpty()) {
                        binding.etTitle.setError("Required");
                    } else if (description.isEmpty()) {
                        binding.etDesc.setError("Required");
                    } else {

                        boolean status = helperClass.Update_IN_LAB(position, title, description);
                        if (status) {
                            Toast.makeText(NewNotes.this, "Updated Successfully", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(NewNotes.this, MaintainNotes.class));
                        } else {
                            Toast.makeText(NewNotes.this, "Updating Failed", Toast.LENGTH_SHORT).show();
                        }

                    }
                }
            });

        }

    }

}