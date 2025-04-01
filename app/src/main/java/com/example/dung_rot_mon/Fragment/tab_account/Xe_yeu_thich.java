package com.example.dung_rot_mon.Fragment.tab_account;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.example.dung_rot_mon.MainActivity.Id_account;


import android.app.AlertDialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
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
import android.widget.Toast;

import com.example.dung_rot_mon.R;
import com.example.dung_rot_mon.Sql.DatabaseHelper;
import com.example.dung_rot_mon.tab_car.Car;
import com.example.dung_rot_mon.tab_car.CarAdapter;
import com.example.dung_rot_mon.tab_car.Edit_car;
import com.example.dung_rot_mon.tab_car.Quan_ly_xe;
import com.example.dung_rot_mon.tab_car.thong_tin_xe;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Xe_yeu_thich#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Xe_yeu_thich extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Xe_yeu_thich() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Xe_yeu_thich.
     */
    // TODO: Rename and change types and number of parameters
    public static Xe_yeu_thich newInstance(String param1, String param2) {
        Xe_yeu_thich fragment = new Xe_yeu_thich();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
    private RecyclerView recyclerView;
    private CarAdapter carAdapter;
    private List<Car> carList;
    DatabaseHelper db;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_xe_yeu_thich, container, false);
        db=new DatabaseHelper(getContext());
        SQLiteDatabase dba=db.openDatabase();
        Cursor cursor = dba.rawQuery("SELECT Xe_yeu_thich FROM account WHERE id = ?", new String[]{String.valueOf(Id_account)});
        String idxea = "";
        recyclerView = view.findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        if (cursor.moveToFirst()) {

            int columnIndex = cursor.getColumnIndex("Xe_yeu_thich");
            if (columnIndex >= 0) {
                idxea = cursor.getString(columnIndex).replaceAll("null","");
            }

        }
        String[] idxeaArray = idxea.split(",");
        final boolean[] isFavorite = {false};
        List<String> idc=new ArrayList<>();
        carList=new ArrayList<>();
        // Xóa danh sách trước khi cập nhật
        carList.clear();
        view.findViewById(R.id.emptyStateLayout).setVisibility(VISIBLE);
        view.findViewById(R.id.recyclerview).setVisibility(GONE);
        for (String v : idxeaArray) {


            if(!Objects.equals(v, ""))
            {
            idc.add(v);
            }
        }


    if(idc.size()>0){
    view.findViewById(R.id.emptyStateLayout).setVisibility(GONE);
    view.findViewById(R.id.recyclerview).setVisibility(VISIBLE);
        carAdapter = new CarAdapter(getContext(), carList, new CarAdapter.OnCarClickListener() {
            @Override
            public void onCarClick(Car car) {
                replaceFragment(new thong_tin_xe(car,Xe_yeu_thich.this));
            }

            @Override
            public void onEditClick(Car car) {

            }

            @Override
            public void onDeleteClick(Car car) {
                // Hiển thị hộp thoại xác nhận rồi xóa xe

            }
        });
        for (String v : idc) {
          fetchData(v);
        }
        recyclerView.setAdapter(carAdapter);}
        return view;
    }
    public void fetchData(String accountId) {
        SQLiteDatabase dba = db.openDatabase();
        Cursor cursor = dba.rawQuery("SELECT * FROM cars WHERE id = ?", new String[]{String.valueOf(accountId)});


        // Lấy index của các cột
        int idColumnIndex = cursor.getColumnIndex("id");
        int nameColumnIndex = cursor.getColumnIndex("car_name");
        int typeColumnIndex = cursor.getColumnIndex("car_type");
        int fuelColumnIndex = cursor.getColumnIndex("nhine_lieu");
        int seatsColumnIndex = cursor.getColumnIndex("so_cho_ngoi");
        int vuTriColumnIndex = cursor.getColumnIndex("vu_tri");
        int priceOldColumnIndex = cursor.getColumnIndex("gia_cu");
        int priceNewColumnIndex = cursor.getColumnIndex("gia_moi");
        int imageColumnIndex = cursor.getColumnIndex("image");
        int image2ColumnIndex = cursor.getColumnIndex("image2");
        int image3ColumnIndex = cursor.getColumnIndex("image3");
        int image4ColumnIndex = cursor.getColumnIndex("image4");
        int statusColumnIndex = cursor.getColumnIndex("status");
        int LocationColumnIndex = cursor.getColumnIndex("Location");
        int bio = cursor.getColumnIndex("bio");
        int idtx = cursor.getColumnIndex("account_id");
        int tongs = cursor.getColumnIndex("tong_chuyen");

        // Kiểm tra nếu các cột hợp lệ
        if (idColumnIndex == -1 || nameColumnIndex == -1 || imageColumnIndex == -1) {
            Log.e("Database", "Column not found!");
            return;
        }
        while (cursor.moveToNext()) {
            int id = cursor.getInt(idColumnIndex);
            String name = cursor.getString(nameColumnIndex);
            int idcarr = cursor.getInt(0);
            String type = cursor.getString(typeColumnIndex);
            String fuel = cursor.getString(fuelColumnIndex);
            int seats = cursor.getInt(seatsColumnIndex);
            String location = cursor.getString(LocationColumnIndex);
            String vitri = cursor.getString(vuTriColumnIndex);
            String priceOld = cursor.getString(priceOldColumnIndex);
            String priceNew = cursor.getString(priceNewColumnIndex);
            String biao = cursor.getString(bio);
            int status = cursor.getInt(statusColumnIndex);
            int idt = cursor.getInt(idtx);
            int tongsa = cursor.getInt(tongs);
            Cursor cursor1 = dba.rawQuery("SELECT * FROM account WHERE id = ?", new String[]{String.valueOf(idt)});
            Bitmap anhchuxe=null;
            while (cursor1.moveToNext()) {
                int imageColumnIndexa = cursor1.getColumnIndex("image");
                byte[] image0 = cursor1.getBlob(imageColumnIndexa);
                anhchuxe =convertByteArrayToBitmap(image0);
            }
            List<Bitmap> ok=new ArrayList<>();
            // Đọc ảnh dưới dạng byte array

            byte[] image1 = cursor.getBlob(imageColumnIndex);
            byte[] image2 = cursor.isNull(image2ColumnIndex) ? new byte[0] : cursor.getBlob(image2ColumnIndex);
            byte[] image3 = cursor.isNull(image3ColumnIndex) ? new byte[0] : cursor.getBlob(image3ColumnIndex);
            byte[] image4 = cursor.isNull(image4ColumnIndex) ? new byte[0] : cursor.getBlob(image4ColumnIndex);
            ok.add(convertByteArrayToBitmap(image1));
            ok.add(convertByteArrayToBitmap(image2));
            ok.add(convertByteArrayToBitmap(image3));
            ok.add(convertByteArrayToBitmap(image4));

            // Thêm vào danh sách
            carList.add(new Car(getContext(),idcarr,idt,name, type,biao,location ,priceOld,priceNew,ok, anhchuxe, fuel,seats,biao,vitri,tongsa));
        }

        cursor.close(); // Đóng con trỏ sau khi sử dụng
        carAdapter.notifyDataSetChanged(); // Cập nhật RecyclerView
    }
    public static Bitmap convertByteArrayToBitmap(byte[] byteArray) {
        try{
            return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
        }catch(Exception sss){return null;}
    }
    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }
}