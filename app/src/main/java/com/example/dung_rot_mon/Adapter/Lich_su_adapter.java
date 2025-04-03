package com.example.dung_rot_mon.Adapter;


import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dung_rot_mon.Fragment.tab_account.lic_su;
import com.example.dung_rot_mon.R;
import com.example.dung_rot_mon.Sql.DatabaseHelper;
import com.example.dung_rot_mon.tab_car.CarAdapter;

import java.util.List;

public class Lich_su_adapter extends RecyclerView.Adapter<Lich_su_adapter.ViewHolder> {
        private List<lic_su> rentalRecords;
        private Context context;

        public Lich_su_adapter(Context context, List<lic_su> rentalRecords, OnCarClickListener onCarClickListener) {
            this.context = context;
            this.rentalRecords = rentalRecords;
            this.listener = onCarClickListener;
        }
    private Lich_su_adapter.OnCarClickListener listener;
    public interface OnCarClickListener {
        void onlicsuClick(lic_su car);
        void danhgia_click(lic_su car);

    }
        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_lich_su, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            lic_su record = rentalRecords.get(position);

            holder.tvRentalId.setText("MHD"+record.getMa_thue());
            holder.tvRentalStatus.setText(record.getStatus());

            // Đặt màu cho trạng thái
            if (record.getStatus().equals("Đã hoàn tất")) {
                holder.Tra_xe.setText("Đánh giá");
                holder.tvRentalStatus.setTextColor(ContextCompat.getColor(context, R.color.gren));
            } else if (record.getStatus().equals("Đang thuê")) {
                holder.tvRentalStatus.setTextColor(ContextCompat.getColor(context, R.color.blue));
            }

            holder.tvRentalDate.setText(record.getNgay_thue());
            holder.tvReturnDate.setText(record.getNgay_tra());
            holder.carname.setText(record.getCar_name());
            holder.loai_xe.setText(record.gettype());
            holder.tvTotalAmount.setText(String.format("%,d.000 VNĐ", record.get_tong_tien()));
            holder.Tra_xe.setOnClickListener(v->{
                if(holder.Tra_xe.getText().equals("Trả xe")){
                    holder.Tra_xe.setText("Đánh giá");
                holder.tvRentalStatus.setText("Đã hoàn tất");
                    DatabaseHelper db=new DatabaseHelper(context);
                    SQLiteDatabase dba=db.openDatabase();
                    ContentValues values = new ContentValues();
                    values.put("trang_thai", "Đã hoàn tất");
                    String whereClause = "id = ?";
                    String[] whereArgs = {String.valueOf(record.getMa_thue())};
                    ContentValues valuesa = new ContentValues();

                    valuesa.put("status", 0);
                    String whereClausea = "id = ?";
                    String[] whereArgsa = {String.valueOf(record.getidcarr())};

                    // Thực hiện cập nhật
                    int rowsAffecteda = dba.update("cars", valuesa, whereClausea, whereArgsa);

                    int rowsAffected = dba.update("lich_su_thue_xe", values, whereClause, whereArgs);
                    dba.close();
                holder.tvRentalStatus.setTextColor(ContextCompat.getColor(context, R.color.gren));

                }
                else
                {
                    if (listener != null) {
                        listener.danhgia_click(record);
                    }
                }
            });
            holder.itemView.setOnClickListener(v->{
                listener.onlicsuClick(record);
            });
        }

        @Override
        public int getItemCount() {
            return rentalRecords.size();
        }

        public static class ViewHolder extends RecyclerView.ViewHolder {
             TextView tvRentalId, tvRentalStatus, tvRentalDate, tvReturnDate;
            TextView carname, loai_xe, tvTotalAmount;
            Button Tra_xe;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                tvRentalId = itemView.findViewById(R.id.tvRentalId);
                tvRentalStatus = itemView.findViewById(R.id.tvRentalStatus);
                tvRentalDate = itemView.findViewById(R.id.tvRentalDate);
                tvReturnDate = itemView.findViewById(R.id.tvReturnDate);
                carname = itemView.findViewById(R.id.carname);
                loai_xe = itemView.findViewById(R.id.loai_xe);
                tvTotalAmount = itemView.findViewById(R.id.tong_tien);
                Tra_xe = itemView.findViewById(R.id.Tra_xe);

            }
        }
    }