package com.example.dung_rot_mon.tab_car;


import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dung_rot_mon.R;


import java.util.ArrayList;
import java.util.List;

public class CarAdapter extends RecyclerView.Adapter<CarAdapter.CarViewHolder> {
    private Context context;
    private List<Car> carList;
    private OnCarClickListener listener; // Interface để xử lý sự kiện click
    public interface OnCarClickListener {
        void onCarClick(Car car);
        void onEditClick(Car car);
        void onDeleteClick(Car car);
    }

    public CarAdapter(Context context, List<Car> carList, OnCarClickListener listener) {
        this.context = context;
        this.carList = carList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.xe_dat_xe, parent, false);
        ImageView imageView = new ImageView(context);
        imageView.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
        ));
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        return new CarViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CarViewHolder holder, int position) {
        Car car = carList.get(position);
        List<Bitmap> images = new ArrayList<>();
        holder.ownerName.setText(car.getOwnerName());
        holder.carName.setText(car.getCarName());
        holder.carDetails.setText(car.getDetails());
        holder.carLocation.setText(car.getLocation());
        holder.carPriceOld.setText(car.getPriceOld() + "K");
        holder.carPriceNew.setText(car.getPriceNew()+ "K/ngày");
        ImageAdapter imageAdapter = new ImageAdapter(car.getCarImage());
        holder.recyclerViewCarImages.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
       holder.recyclerViewCarImages.setAdapter(imageAdapter);

//        holder.carImage.setImageResource(car.getCarImage());
      holder.ownerImage.setImageBitmap(car.getOwnerImage());

if(car.getTaixe()==1)
{
    holder.taixe.setVisibility(VISIBLE);
}else {
    holder.taixe.setVisibility(GONE);
}
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
        if (car.getImage1() != null) {
            images.add(car.getImage1());
        }
        if (car.getImage2() != null) {
            images.add(car.getImage2());
        }
        if (car.getImage3() != null) {
            images.add(car.getImage3());
        }
        if (car.getImage4() != null) {
            images.add(car.getImage4());
        }



        // Gán Adapter vào ViewPager2
        CarImageAdapter imageAdaptera = new CarImageAdapter(holder.itemView.getContext(), images);
        holder.recyclerViewCarImages.setAdapter(imageAdaptera);
        holder.Edit.setOnClickListener(v -> {
            if (listener != null) {
                listener.onEditClick(car);
            }
        });

        holder.Delete.setOnClickListener(v -> {
            if (listener != null) {
                listener.onDeleteClick(car);
            }
        });

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
        CardView cardView;   ImageView imageView;
        ImageButton Edit,Delete;
        LinearLayout taixe;
RecyclerView recyclerViewCarImages;
RecyclerView recyclerViewCarImages1;
        public CarViewHolder(@NonNull View itemView) {
            super(itemView);
            ownerName = itemView.findViewById(R.id.txtOwnerName);
            carName = itemView.findViewById(R.id.txtCarName);
            carDetails = itemView.findViewById(R.id.txtCarDetails);
            carLocation = itemView.findViewById(R.id.txtCarLocation);
            carPriceOld = itemView.findViewById(R.id.txtCarPriceOld);
            carPriceNew = itemView.findViewById(R.id.txtCarPriceNew);
            carImage = itemView.findViewById(R.id.a);
            ownerImage = itemView.findViewById(R.id.imgOwner);
            cardView = itemView.findViewById(R.id.cardViewCar);
            nguyenlieu = itemView.findViewById(R.id.txtNguyenLieu);
            sochongoi = itemView.findViewById(R.id.sochongoi);
            bio = itemView.findViewById(R.id.biocar);
            vitri = itemView.findViewById(R.id.txtVitri);
            Edit=itemView.findViewById(R.id.btnEdit);
            Delete=itemView.findViewById(R.id.btnDelete);
            taixe=itemView.findViewById(R.id.taixe);
            recyclerViewCarImages=itemView.findViewById(R.id.recyclerViewCarImages);

        }

    }

    // Interface để xử lý sự kiện click

}
