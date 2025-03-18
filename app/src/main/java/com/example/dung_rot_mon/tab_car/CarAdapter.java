package com.example.dung_rot_mon.tab_car;


import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dung_rot_mon.R;


import java.util.List;

public class CarAdapter extends RecyclerView.Adapter<CarAdapter.CarViewHolder> {
    private Context context;
    private List<Car> carList;
    private OnCarClickListener listener; // Interface để xử lý sự kiện click

    public CarAdapter(Context context, List<Car> carList, OnCarClickListener listener) {
        this.context = context;
        this.carList = carList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.xe_dat_xe, parent, false);
        return new CarViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CarViewHolder holder, int position) {
        Car car = carList.get(position);
        holder.ownerName.setText(car.getOwnerName());
        holder.carName.setText(car.getCarName());
        holder.carDetails.setText(car.getDetails());
        holder.carLocation.setText(car.getLocation());
        holder.carPriceOld.setText(car.getPriceOld());
        holder.carPriceNew.setText(car.getPriceNew());
        holder.carImage.setImageResource(car.getCarImage());
        holder.ownerImage.setImageResource(car.getOwnerImage());


        if (holder.nguyenlieu != null) {
            holder.nguyenlieu.setText("Nguyên liệu: " + car.getNguyenlieu());
        } else {
            Log.e("CarAdapter", "nguyenlieu is NULL");
        }

        if (holder.sochongoi != null) {
            holder.sochongoi.setText("Số chỗ ngồi: " + car.getSochongoi());
        } else {
            Log.e("CarAdapter", "sochongoi is NULL");
        }

        if (holder.bio != null) {
            holder.bio.setText("Mô tả: " + car.getBio());
        } else {
            Log.e("CarAdapter", "bio is NULL");
        }

        if (holder.vitri != null) {
            holder.vitri.setText("Vị trí: " + car.getVitri());
        } else {
            Log.e("CarAdapter", "vitri is NULL");
        }


        // Xử lý sự kiện click vào CardView
        holder.cardView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onCarClick(car);
            }
        });
    }

    @Override
    public int getItemCount() {
        return carList.size();
    }

    public static class CarViewHolder extends RecyclerView.ViewHolder {
        TextView ownerName, carName, carDetails, carLocation, carPriceOld, carPriceNew , nguyenlieu,sochongoi,bio, vitri;
        ImageView carImage, ownerImage;
        CardView cardView;

        public CarViewHolder(@NonNull View itemView) {
            super(itemView);
            ownerName = itemView.findViewById(R.id.txtOwnerName);
            carName = itemView.findViewById(R.id.txtCarName);
            carDetails = itemView.findViewById(R.id.txtCarDetails);
            carLocation = itemView.findViewById(R.id.txtCarLocation);
            carPriceOld = itemView.findViewById(R.id.txtCarPriceOld);
            carPriceNew = itemView.findViewById(R.id.txtCarPriceNew);
            carImage = itemView.findViewById(R.id.imgCar);
            ownerImage = itemView.findViewById(R.id.imgOwner);
            cardView = itemView.findViewById(R.id.cardViewCar);


            nguyenlieu = itemView.findViewById(R.id.txtNguyenLieu);
            sochongoi = itemView.findViewById(R.id.sochongoi);
            bio = itemView.findViewById(R.id.biocar);
            vitri = itemView.findViewById(R.id.txtVitri);

        }
    }

    // Interface để xử lý sự kiện click
    public interface OnCarClickListener {
        void onCarClick(Car car);
    }
}
