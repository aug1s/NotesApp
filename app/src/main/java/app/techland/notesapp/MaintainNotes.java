package app.techland.notesapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;

import app.techland.notesapp.Adapter.RecyclerAdapter;
import app.techland.notesapp.Adapter.UpdateAdapter;
import app.techland.notesapp.Database.DatabaseHelperClass;
import app.techland.notesapp.Model.RecyclerModel;
import app.techland.notesapp.databinding.ActivityExistingNotesBinding;
import app.techland.notesapp.databinding.ActivityMaintainNotesBinding;
import app.techland.notesapp.databinding.ActivityMaintainNotesBindingImpl;

public class MaintainNotes extends AppCompatActivity {
    ActivityMaintainNotesBinding binding;
    DatabaseHelperClass helperClass;
    UpdateAdapter adapter;
    ArrayList<RecyclerModel> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_maintain_notes);
        helperClass = new DatabaseHelperClass(this);
        Cursor data = helperClass.getAllData();

        if (data.getCount() > 0) {
            while (data.moveToNext()) {
                list.add(new RecyclerModel(data.getInt(0), data.getString(1), data.getString(2)));
            }
            adapter = new UpdateAdapter(this, list);
            LinearLayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, true);
            binding.rvExisting.setLayoutManager(layoutManager);
            binding.rvExisting.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
        else {
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            //title
            String title = "Alert";
            ForegroundColorSpan span = new ForegroundColorSpan(getResources().getColor(R.color.main));
            SpannableStringBuilder titleBuilder = new SpannableStringBuilder(title);
            titleBuilder.setSpan(span, 0, title.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            dialog.setTitle(titleBuilder);
            //description
            String desc = "You don't have any Notes.";
            SpannableStringBuilder descBuilder = new SpannableStringBuilder(desc);
            descBuilder.setSpan(span, 0, desc.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            dialog.setIcon(R.drawable.note);
            dialog.setMessage(descBuilder);
            dialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Toast.makeText(MaintainNotes.this, "Successful", Toast.LENGTH_SHORT).show();
                    dialog.setCancelable(true);
                    finish();
                }
            });
            dialog.show();
        }


    }
}