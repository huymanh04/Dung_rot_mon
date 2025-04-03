package com.example.dung_rot_mon.Adapter;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dung_rot_mon.Fragment.tab_account.Comen;
import com.example.dung_rot_mon.Fragment.tab_account.lic_su;
import com.example.dung_rot_mon.R;
import com.example.dung_rot_mon.Sql.DatabaseHelper;

import java.util.List;

public class cmt_adapter extends RecyclerView.Adapter<cmt_adapter.ViewHolder> {
    private List<Comen> rentalRecords;
    private Context context;
    DatabaseHelper db;
    public cmt_adapter(Context context, List<Comen> rentalRecords) {
        this.context = context;
        this.rentalRecords = rentalRecords;

    }


    @NonNull
    @Override
    public cmt_adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_cmt, parent, false);
        return new cmt_adapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Comen cmt = rentalRecords.get(position);
        Bitmap bitmap = BitmapFactory.decodeByteArray(cmt.get_anh(), 0, cmt.get_anh().length);
        holder.profile_image.setImageBitmap(bitmap);
        holder.txtUsername.setText(cmt.getname());
        holder.txtReviewContent.setText(cmt.getText());
        holder.ratingBar.setRating((float) cmt.getSoSao());

    }


    @Override
    public int getItemCount() {
        return rentalRecords.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
       ImageView profile_image;
        TextView txtUsername,txtReviewContent;
        RatingBar ratingBar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            profile_image = itemView.findViewById(R.id.profile_image);
            txtUsername = itemView.findViewById(R.id.txtUsername);
            txtReviewContent = itemView.findViewById(R.id.txtReviewContent);
            ratingBar = itemView.findViewById(R.id.ratingBar);

        }
    }
}
