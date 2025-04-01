package com.example.dung_rot_mon.tab_car;

import android.graphics.Bitmap;
import android.graphics.Paint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dung_rot_mon.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;


public class thong_tin_xe extends Fragment {

    String ownerName, carName, carDetails, carLocation, carPriceOld, carPriceNew;
    ImageView carImage, ownerImage;
    //    public  thong_tin_xe(String ownerName, String carName, String carDetails, String carLocation, String carPriceOld, String carPriceNew, ImageView carImage, ImageView ownerImage){
//        this.ownerName = ownerName;
//        this.carName = carName;
//        this.carDetails = carDetails;
//        this.carLocation = carLocation;
//        this.carPriceOld = carPriceOld;
//        this.carPriceNew = carPriceNew;
//        this.carImage = carImage;
//        this.ownerImage = ownerImage;
//    }
    private Car car;
    public thong_tin_xe(Car car,Fragment fragment1){
        this.fragment1=fragment1;
        this.car = car;
    }
    Fragment fragment1;
    RecyclerView anh;
    private CarAdapter carAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_thong_tin_xe, container, false);
        anh = view.findViewById(R.id.recyclerViewCarImages1);
        anh.setLayoutManager(new LinearLayoutManager(getContext()));
        List<Bitmap> images = new ArrayList<>();
        ImageAdapter imageAdapter = new ImageAdapter(car.getCarImage());
        anh.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
      anh.setAdapter(imageAdapter);
        TextView txtCarName = view.findViewById(R.id.tvCarName);
        TextView sochongoi = view.findViewById(R.id.sochongoi);
        TextView nametaixe =  view.findViewById(R.id.taixeid);
        ImageView imgTaiXe = view.findViewById(R.id.imgTaiXe);
        TextView txtnguyenlieu = view.findViewById(R.id.txtNguyenLieu);
        TextView biocar = view.findViewById(R.id.biocar);
        TextView typecarr = view.findViewById(R.id.typecarr);
        TextView txtCarPriceOld = view.findViewById(R.id.txtCarPriceOld);
        TextView txtCarPriceNew = view.findViewById(R.id.txtCarPriceNew);
        TextView txtCarPriceNewa = view.findViewById(R.id.txtCarPriceNewa);
        TextView txtVitri = view.findViewById(R.id.txtVitri);
        txtCarPriceOld.setText(car.getPriceOld()+"K");
        txtCarPriceNew.setText(car.getPriceNew()+"K/Ngày");
        txtCarPriceNewa.setText("Giá Tổng : "+car.getPriceNew()+"K/Ngày");
        txtCarPriceNewa.setPaintFlags(txtCarPriceNewa.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        txtCarPriceOld.setPaintFlags( txtCarPriceOld.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        nametaixe.setText(car.getOwnerName());
        txtCarName.setText(car.getCarName());
        sochongoi.setText(String.valueOf(car.getSochongoi())+" Chỗ");
 /*       imgCar.setImageResource(car.getCarImage());*/
        imgTaiXe.setImageBitmap(car.getOwnerImage());
        txtnguyenlieu.setText(car.getNguyenlieu());
        biocar.setText(car.getDetails());
        txtVitri.setText(car.getLocation());
        view.findViewById(R.id.btnExit).setOnClickListener(v->{
            replaceFragment(fragment1);
        });

        //set adapter ảnh vào recycalview
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
        typecarr.setText(car.getType());
        CarImageAdapter imageAdaptera = new CarImageAdapter(getContext(), images);
        anh.setAdapter(imageAdaptera);
        return  view;
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }
}