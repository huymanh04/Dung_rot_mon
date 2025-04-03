package com.example.dung_rot_mon.tab_car;

import static com.example.dung_rot_mon.MainActivity.Id_account;
import static com.example.dung_rot_mon.tab_car.tim_xe.time_thue;
import static com.example.dung_rot_mon.tab_car.tim_xe.time_tra;
import static com.example.dung_rot_mon.MainActivity.email12;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dung_rot_mon.Adapter.cmt_adapter;
import com.example.dung_rot_mon.Fragment.tab_account.Comen;
import com.example.dung_rot_mon.Fragment.tab_account.Lich_su_thue;
import com.example.dung_rot_mon.Fragment.tab_account.lic_su;
import com.example.dung_rot_mon.R;
import com.example.dung_rot_mon.Sql.DatabaseHelper;

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
    }cmt_adapter  cmadapter;
    Fragment fragment1;
    RecyclerView anh;
    List<Comen> cmta;
    RecyclerView cmt;
    private CarAdapter carAdapter;
    DatabaseHelper db;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_thong_tin_xe, container, false);
        anh = view.findViewById(R.id.recyclerViewCarImages1);
        cmt = view.findViewById(R.id.recyclerviewa);
        anh.setLayoutManager(new LinearLayoutManager(getContext()));
        cmt.setLayoutManager(new LinearLayoutManager(getContext()));
        List<Bitmap> images = new ArrayList<>();
       cmta= new ArrayList<>();
        db=new DatabaseHelper(getContext());
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
         cmadapter=new cmt_adapter(getContext(),cmta);

        cmt.setAdapter(cmadapter);
        typecarr.setText(car.getType());
        CarImageAdapter imageAdaptera = new CarImageAdapter(getContext(), images);
        anh.setAdapter(imageAdaptera);
        view.findViewById(R.id.btnthue).setOnClickListener(v->{

            var t=thue_xe(new lic_su("","Đang thuê",car.getType(),time_thue,time_tra,car.getCarName(),Integer.parseInt(car.getPriceNew()),car.getIDcarr()));
        if(t>0){
            SQLiteDatabase dba = db.openDatabase();
            ContentValues values = new ContentValues();

            values.put("status", 1);
            String whereClause = "id = ?";
            String[] whereArgs = {String.valueOf(car.getIDcarr())};

            // Thực hiện cập nhật
            int rowsAffected = dba.update("cars", values, whereClause, whereArgs);
            dba.close();
            Toast.makeText(getContext(), "Thuê xe thành công" , Toast.LENGTH_SHORT).show();
            replaceFragment(new Lich_su_thue(email12));
        }else{    Toast.makeText(getContext(), "Thuê xe thất bại" , Toast.LENGTH_SHORT).show();}
        });
        fetchData(String.valueOf(car.getIDcarr()));
        return  view;
    }
    public void fetchData(String id_car) {
        SQLiteDatabase dba = db.openDatabase();
        Cursor cursor = dba.rawQuery("SELECT * FROM coment WHERE id_car = ?", new String[]{String.valueOf(id_car)});
        int idColumnIndex = cursor.getColumnIndex("text");
        int idColumnIndex1 = cursor.getColumnIndex("so_sao");
        int idColumnIndex2= cursor.getColumnIndex("id_account");
        while (cursor.moveToNext()) {
      {
            String cmt = cursor.getString(idColumnIndex);
            double ss = cursor.getDouble(idColumnIndex1);
            String idacc = cursor.getString(idColumnIndex2);

        cmta.add(new Comen(idacc,id_car,cmt,ss,getContext()));
        }

     }
        cursor.close(); // Đóng con trỏ sau khi sử dụng
        cmadapter.notifyDataSetChanged();
    }
    long thue_xe(lic_su lc){
        SQLiteDatabase dba = db.openDatabase();

        ContentValues values =new ContentValues();
        ContentValues values1 =new ContentValues();
        values.put("id_account",Id_account);
        values.put("id_car",car.getIDcarr());
        values.put("thoi_gian_thue",lc.getNgay_thue());
        values.put("thoi_gian_tra",lc.getNgay_tra());
        values.put("so_tien",lc.get_tong_tien());
        values.put("trang_thai",lc.getStatus());
        Cursor cursor = dba.rawQuery("SELECT tong_chuyen FROM cars WHERE id = ?", new String[]{String.valueOf(car.getIDcarr())});

        if (cursor != null && cursor.moveToFirst()) {
            int b = cursor.getInt(cursor.getColumnIndexOrThrow("tong_chuyen"));
            values1.put("tong_chuyen", ++b);
        } else {
            Log.e("DB_ERROR", "Không tìm thấy dữ liệu với ID: " + car.getIDcarr());
        }

        if (cursor != null) {
            cursor.close(); // Đóng cursor để tránh rò rỉ bộ nhớ
        }


        String whereClause = "id = ?";
        String[] whereArgs = {String.valueOf(car.getIDcarr())};

        // Thực hiện cập nhật
        int rowsAffected = dba.update("cars", values1, whereClause, whereArgs);
        var  fddf= dba.insert("lich_su_thue_xe",null,values);
        return fddf;
    }
    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }
}