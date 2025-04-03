package com.example.dung_rot_mon.Adapter;

import android.content.ContentValues;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dung_rot_mon.Fragment.tab_account.Comen;
import com.example.dung_rot_mon.R;
import com.example.dung_rot_mon.Sql.DatabaseHelper;
import com.example.dung_rot_mon.admin.duyet_taixe;
import com.google.protobuf.Value;

import java.util.List;

public class duyet_tai_xe_adapter extends RecyclerView.Adapter<duyet_tai_xe_adapter.ViewHolder> {
    @NonNull
    @Override
    public duyet_tai_xe_adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_duyet_tx, parent, false);
        return new duyet_tai_xe_adapter.ViewHolder(view);
    } public duyet_tai_xe_adapter(Context cc, List<duyet_taixe> rentalRecords) {
        contex=cc;

        this.rentalRecords = rentalRecords;

    }
    DatabaseHelper db;
    Context contex;
    private List<duyet_taixe> rentalRecords;
    @Override
    public void onBindViewHolder(@NonNull duyet_tai_xe_adapter.ViewHolder holder, int position) {
        duyet_taixe cmt = rentalRecords.get(position);
        Bitmap bitmap = BitmapFactory.decodeByteArray(cmt.getAnh(), 0, cmt.getAnh().length);
        holder.profile_image.setImageBitmap(bitmap);
        holder.text_driver_name.setText(cmt.getNameTx());
        holder.text_phone_number.setText(cmt.getPhoneTx());
        db=new DatabaseHelper(contex);
        holder.btn_approve.setOnClickListener(v->{
            ContentValues value=new ContentValues();
            value.put("taixe",1);
            String whereClause = "id = ?";
            String[] whereArgs = {String.valueOf(cmt.getIdTx())};
            var dfgdgdg=db.openDatabase().update("account",value,whereClause, whereArgs);
            if(dfgdgdg>0) {
                Toast.makeText(v.getContext(), "Duyệt thành công", Toast.LENGTH_SHORT).show();
                rentalRecords.remove(position);  notifyDataSetChanged();
            }
        }); holder.btn_reject.setOnClickListener(v->{
            ContentValues value=new ContentValues();
            value.put("taixe",0);
            String whereClause = "id = ?";
            String[] whereArgs = {String.valueOf(cmt.getIdTx())};
            var dfgdgdg=db.openDatabase().update("account",value,whereClause, whereArgs);
            if(dfgdgdg>0) {
                Toast.makeText(v.getContext(), "Từ chối thành công", Toast.LENGTH_SHORT).show();
                rentalRecords.remove(position);  notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return rentalRecords.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView profile_image;
        TextView text_driver_name,text_phone_number;
Button btn_reject,btn_approve;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            profile_image = itemView.findViewById(R.id.profile_image);
            text_driver_name = itemView.findViewById(R.id.text_driver_name);
            text_phone_number = itemView.findViewById(R.id.text_phone_number);
            btn_approve = itemView.findViewById(R.id.btn_approve);
            btn_reject = itemView.findViewById(R.id.btn_reject);
        }
    }

}
