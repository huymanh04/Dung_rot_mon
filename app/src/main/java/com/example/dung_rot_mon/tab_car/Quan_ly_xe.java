package com.example.dung_rot_mon.tab_car;

import android.app.AlertDialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import com.example.dung_rot_mon.Sql.DatabaseManager;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Quan_ly_xe#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Quan_ly_xe extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Quan_ly_xe(String email) {
        // Required empty public constructor
        this.email=email;
    }
static String email;
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Quan_ly_xe.
     */
    // TODO: Rename and change types and number of parameters
    public static Quan_ly_xe newInstance(String param1, String param2) {
        Quan_ly_xe fragment = new Quan_ly_xe(email);
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
    private List<Car> carList;    DatabaseManager db;
    int account_id;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
   View view=inflater.inflate(R.layout.fragment_quan_ly_xe, container, false);
        db=new DatabaseManager(getContext());
        SQLiteDatabase dba=db.getWritableDatabase();
        Cursor cursor = dba.rawQuery("SELECT id FROM account WHERE email = ?", new String[]{String.valueOf(email)});
        if (cursor.moveToFirst()) {
            account_id = cursor.getInt(0); // Lấy cột đầu tiên (tên tài xế)
        }

        recyclerView = view.findViewById(R.id.recyclerViewCars);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        carList=new ArrayList<>();
        List<Bitmap> imageList = new ArrayList<>();


        carAdapter = new CarAdapter(getContext(), carList, new CarAdapter.OnCarClickListener() {
            @Override
            public void onCarClick(Car car) {
                replaceFragment(new thong_tin_xe(car,Quan_ly_xe.this));
            }

            @Override
            public void onEditClick(Car car) {
                replaceFragment(new Edit_car(car,account_id,Quan_ly_xe.this));

                // Mở Activity chỉnh sửa
//                Intent intent = new Intent(Quan_ly_xe.this, EditCarActivity.class);
//                intent.putExtra("CAR_ID", car.getId());
//                startActivity(intent);
            }

            @Override
            public void onDeleteClick(Car car) {
                // Hiển thị hộp thoại xác nhận rồi xóa xe
                new AlertDialog.Builder(getContext())
                        .setTitle("Xác nhận xóa")
                        .setMessage("Bạn có chắc chắn muốn xóa xe này không?"+car.getIDcarr())
                        .setPositiveButton("Xóa", (dialog, which) -> {
                          deleteCarFromDatabase(car.getIDcarr());
                            fetchData(account_id);
                            Toast.makeText(getContext(), "Xóa xe thành công" , Toast.LENGTH_SHORT).show();
                        })
                        .setNegativeButton("Hủy", null)
                        .show();
            }
        });
       fetchData(account_id);
        recyclerView.setAdapter(carAdapter);
   view.findViewById(R.id.fabAddCar).setOnClickListener(v->{
       replaceFragment(new add_xe(account_id,Quan_ly_xe.this));
   });

   return  view;
    }
    void deleteCarFromDatabase(int id){
        SQLiteDatabase dba=db.getWritableDatabase();
        dba.delete("cars", "id=?", new String[]{String.valueOf(id)});
        dba.close();
    }
    public void fetchData(int accountId) {
        SQLiteDatabase dba = db.getWritableDatabase();
        Cursor cursor = dba.rawQuery("SELECT * FROM cars WHERE account_id = ?", new String[]{String.valueOf(accountId)});


        // Lấy index của các cột
        int idColumnIndex = cursor.getColumnIndex("id");
        int nameColumnIndex = cursor.getColumnIndex("car_name");
        int typeColumnIndex = cursor.getColumnIndex("car_type");
        int fuelColumnIndex = cursor.getColumnIndex("nhine_lieu");
        int seatsColumnIndex = cursor.getColumnIndex("so_cho_ngoi");
        int locationColumnIndex = cursor.getColumnIndex("vu_tri");
        int priceOldColumnIndex = cursor.getColumnIndex("gia_cu");
        int priceNewColumnIndex = cursor.getColumnIndex("gia_moi");
        int imageColumnIndex = cursor.getColumnIndex("image");
        int image2ColumnIndex = cursor.getColumnIndex("image2");
        int image3ColumnIndex = cursor.getColumnIndex("image3");
        int image4ColumnIndex = cursor.getColumnIndex("image4");
        int statusColumnIndex = cursor.getColumnIndex("status");
        int bio = cursor.getColumnIndex("bio");
        int idtx = cursor.getColumnIndex("account_id");

        // Kiểm tra nếu các cột hợp lệ
        if (idColumnIndex == -1 || nameColumnIndex == -1 || imageColumnIndex == -1) {
            Log.e("Database", "Column not found!");
            return;
        }

        // Xóa danh sách trước khi cập nhật
        carList.clear();

        // Duyệt qua tất cả các bản ghi
        while (cursor.moveToNext()) {
            int id = cursor.getInt(idColumnIndex);
            String name = cursor.getString(nameColumnIndex);
            int idcarr = cursor.getInt(0);
            String type = cursor.getString(typeColumnIndex);
            String fuel = cursor.getString(fuelColumnIndex);
            int seats = cursor.getInt(seatsColumnIndex);
            String location = cursor.getString(locationColumnIndex);
            String priceOld = cursor.getString(priceOldColumnIndex);
            String priceNew = cursor.getString(priceNewColumnIndex);
            String biao = cursor.getString(bio);
            int status = cursor.getInt(statusColumnIndex);
            int idt = cursor.getInt(idtx);
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
            carList.add(new Car(getContext(),idcarr,idt,name, type,biao,location ,priceOld,priceNew,ok, ok.get(0), fuel,seats,biao,location));
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