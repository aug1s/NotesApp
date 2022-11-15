package app.techland.notesapp;

import androidx.annotation.ColorInt;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

import app.techland.notesapp.Adapter.RecyclerAdapter;
import app.techland.notesapp.Database.DatabaseHelperClass;
import app.techland.notesapp.Model.RecyclerModel;
import app.techland.notesapp.databinding.ActivityExistingNotesBinding;

public class ExistingNotes extends AppCompatActivity {
    ActivityExistingNotesBinding binding;
    RecyclerAdapter adapter;
    DatabaseHelperClass helperClass;
    ArrayList<RecyclerModel> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_existing_notes);

        helperClass = new DatabaseHelperClass(this);
        Cursor data = helperClass.getAllData();
        if (data.getCount() > 0) {
            while (data.moveToNext()) {
                list.add(new RecyclerModel(data.getInt(0), data.getString(1), data.getString(2)));
            }
            adapter = new RecyclerAdapter(this, list);
            LinearLayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, true);
            binding.rvExisting.setLayoutManager(layoutManager);
            binding.rvExisting.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        } else {
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            //title
            String title = "Alert";
            ForegroundColorSpan span = new ForegroundColorSpan(getResources().getColor(R.color.main));
            SpannableStringBuilder titleBuilder = new SpannableStringBuilder(title);
            titleBuilder.setSpan(span, 0, title.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            dialog.setTitle(titleBuilder);
            //description
            String desc = "You don't have any type of Notes.\nFirst Create the Notes then Check.";
            SpannableStringBuilder descBuilder = new SpannableStringBuilder(desc);
            descBuilder.setSpan(span, 0, desc.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            dialog.setIcon(R.drawable.note);
            dialog.setMessage(descBuilder);
            dialog.setCancelable(false);
            dialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Toast.makeText(ExistingNotes.this, "Great", Toast.LENGTH_SHORT).show();
                    dialog.setCancelable(true);
                    finish();
                }
            });
            dialog.show();
        }
    }
}