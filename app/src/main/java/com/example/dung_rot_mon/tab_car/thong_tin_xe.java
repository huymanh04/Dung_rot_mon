package com.example.dung_rot_mon.tab_car;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dung_rot_mon.R;

import org.w3c.dom.Text;


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
public thong_tin_xe(Car car){
    this.car = car;
}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_thong_tin_xe, container, false);

        ImageView imgCar = view.findViewById(R.id.imgCara);
        TextView txtCarName = view.findViewById(R.id.tvCarName);
        TextView sochongoi = view.findViewById(R.id.sochongoi);
        TextView nametaixe =  view.findViewById(R.id.taixeid);
        ImageView imgTaiXe = view.findViewById(R.id.imgTaiXe);
        TextView txtnguyenlieu = view.findViewById(R.id.txtNguyenLieu);
        TextView biocar = view.findViewById(R.id.biocar);
        TextView txtVitri = view.findViewById(R.id.txtVitri);

        nametaixe.setText(car.getOwnerName());
        txtCarName.setText(car.getCarName());
        sochongoi.setText(car.getLocation());
        imgCar.setImageResource(car.getCarImage());
        imgTaiXe.setImageResource(car.getOwnerImage());
        txtnguyenlieu.setText(car.getNguyenlieu());
        biocar.setText(car.getBio());
        txtVitri.setText(car.getVitri());


       return  view;
    }
}