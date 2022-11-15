package app.techland.notesapp.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import app.techland.notesapp.Database.DatabaseHelperClass;
import app.techland.notesapp.ExistingNotes;
import app.techland.notesapp.Model.RecyclerModel;
import app.techland.notesapp.NewNotes;
import app.techland.notesapp.R;

public class UpdateAdapter extends RecyclerView.Adapter<UpdateAdapter.UpdateViewHolder> {
    private Context context;
    ArrayList<RecyclerModel> list = new ArrayList<>();

    public UpdateAdapter(Context context, ArrayList<RecyclerModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public UpdateViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_items, parent, false);
        return new UpdateViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UpdateViewHolder holder, int position) {
        RecyclerModel getData = list.get(position);
        holder.tvTitle.setText(getData.getTitle());
        holder.tvDescription.setText(getData.getDescription());

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                //title
                String title = "Alert";
                ForegroundColorSpan span = new ForegroundColorSpan(context.getResources().getColor(R.color.main));
                SpannableStringBuilder titleBuilder = new SpannableStringBuilder(title);
                titleBuilder.setSpan(span, 0, title.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                dialog.setTitle(titleBuilder);
                //description
                String desc = "Update/Delete your Notes";
                SpannableStringBuilder descBuilder = new SpannableStringBuilder(desc);
                descBuilder.setSpan(span, 0, desc.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                dialog.setIcon(R.drawable.note);
                dialog.setMessage(descBuilder);
                dialog.setCancelable(false);
                dialog.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        RecyclerModel model = list.get(holder.getAdapterPosition());
                        String title = model.getTitle();
                        String description = model.getDescription();
                        Integer id = model.getId();
                        dialog.setCancelable(true);
                        Intent intent = new Intent(context,NewNotes.class);
                        intent.putExtra("main", "update");
                        intent.putExtra("title",title);
                        intent.putExtra("desc",description);
                        intent.putExtra("id",id);
                        context.startActivity(intent);

                    }
                });
                dialog.setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        int position = holder.getAdapterPosition();
                        delete_item(position);

                        dialog.setCancelable(true);
                    }
                });
                dialog.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        dialog.setCancelable(true);
                    }
                });
                dialog.show();
                return true;
            }
        });
    }

    private void delete_item(int position) {

        RecyclerModel model = list.get(position);
        list.remove(position);
        notifyItemRemoved(position);

        DatabaseHelperClass helperClass = new DatabaseHelperClass(context);
        boolean result = helperClass.deleteDataById(model.getId());
        if (result)
            Toast.makeText(context, "Successfully Deleted", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(context, "Not Deleted", Toast.LENGTH_SHORT).show();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    class UpdateViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvDescription;

        public UpdateViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvDescription = itemView.findViewById(R.id.tvDescription);
        }
    }


}
