package com.example.dung_rot_mon.Fragment.tab_account;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.content.ContentValues;
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

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dung_rot_mon.Adapter.Lich_su_adapter;
import com.example.dung_rot_mon.Fragment.Chat_.tab_chat;
import com.example.dung_rot_mon.R;
import com.example.dung_rot_mon.Sql.DatabaseHelper;
import com.example.dung_rot_mon.tab_car.Car;
import com.example.dung_rot_mon.tab_car.CarAdapter;
import com.example.dung_rot_mon.tab_car.thong_tin_xe;
import com.example.dung_rot_mon.tab_car.tim_xe;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;


public class Lich_su_thue extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    String email;

    public Lich_su_thue(String email) {
        // Required empty public constructor
        this.email=email;
    }




    private RecyclerView recyclerView;
    private Lich_su_adapter lic_suAdapter;
    private List<lic_su> lic_suList;
    DatabaseHelper db;
    String ida;

TextView textCustomerId,textCustomerName,textCustomerPhone,textCustomerEmail,textTotalRentals,textTotalSpent;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_lich_su_thue, container, false);
        textCustomerId=view.findViewById(R.id.textCustomerId);
        lic_suList=new ArrayList<>();
        tong_lanthue=0;
        tong_lanthue1=0;
        textCustomerName=view.findViewById(R.id.textCustomerName);
        textTotalRentals=view.findViewById(R.id.textTotalRentals);
        textTotalSpent=view.findViewById(R.id.textTotalSpent);
        textCustomerPhone=view.findViewById(R.id.textCustomerPhone);
        textCustomerEmail=view.findViewById(R.id.textCustomerEmail);
        db=new DatabaseHelper(getContext());
        SQLiteDatabase dba=db.openDatabase();
        Cursor cursor = dba.rawQuery("SELECT * FROM account WHERE email = ?", new String[]{email});
        if(cursor.moveToFirst()){
        int emailColumnIndex1 = cursor.getColumnIndex("id");
        int emailColumnIndex = cursor.getColumnIndex("email");
        int phonelColumnIndex = cursor.getColumnIndex("phone");
        int nameColumnIndex = cursor.getColumnIndex("name");
        textCustomerId.setText("KH"+cursor.getInt(0));
        textCustomerName.setText(cursor.getString(nameColumnIndex));
        textCustomerPhone.setText(cursor.getString(phonelColumnIndex));
        textCustomerEmail.setText(cursor.getString(emailColumnIndex));
        }
        final lic_su[] lc = {new lic_su()};
        lic_suAdapter =new Lich_su_adapter(getContext(),lic_suList,new Lich_su_adapter.OnCarClickListener(){

            @Override
            public void onlicsuClick(lic_su car) {
                SQLiteDatabase dba=db.openDatabase();
                Cursor cursor1 = dba.rawQuery("SELECT * FROM cars WHERE id = ?", new String[]{String.valueOf(car.getidcarr())});
                int idColumnIndex = cursor1.getColumnIndex("id");
                int nameColumnIndex = cursor1.getColumnIndex("car_name");
                int typeColumnIndex = cursor1.getColumnIndex("car_type");
                int fuelColumnIndex = cursor1.getColumnIndex("nhine_lieu");
                int seatsColumnIndex = cursor1.getColumnIndex("so_cho_ngoi");
                int vuTriColumnIndex = cursor1.getColumnIndex("vu_tri");
                int priceOldColumnIndex = cursor1.getColumnIndex("gia_cu");
                int priceNewColumnIndex = cursor1.getColumnIndex("gia_moi");
                int imageColumnIndex = cursor1.getColumnIndex("image");
                int image2ColumnIndex = cursor1.getColumnIndex("image2");
                int image3ColumnIndex = cursor1.getColumnIndex("image3");
                int image4ColumnIndex = cursor1.getColumnIndex("image4");
                int statusColumnIndex = cursor1.getColumnIndex("status");
                int LocationColumnIndex = cursor1.getColumnIndex("Location");
                int bio = cursor1.getColumnIndex("bio");
                int idtx = cursor1.getColumnIndex("account_id");
                int tongs = cursor1.getColumnIndex("tong_chuyen");
                while (cursor1.moveToNext()) {
                    int idt = cursor1.getInt(idtx);
                    int id = cursor1.getInt(idColumnIndex);
                    String name = cursor1.getString(nameColumnIndex);
                    int idcarr = cursor1.getInt(0);
                    String type = cursor1.getString(typeColumnIndex);
                    String fuel = cursor1.getString(fuelColumnIndex);
                    int seats = cursor1.getInt(seatsColumnIndex);
                    String location = cursor1.getString(LocationColumnIndex);
                    String vitri = cursor1.getString(vuTriColumnIndex);
                    String priceOld = cursor1.getString(priceOldColumnIndex);
                    String priceNew = cursor1.getString(priceNewColumnIndex);
                    String biao = cursor1.getString(bio);
                    List<Bitmap> ok=new ArrayList<>();
                    Cursor cursor1a = dba.rawQuery("SELECT * FROM account WHERE id = ?", new String[]{String.valueOf(idt)});
                    Bitmap anhchuxe=null;
                    while (cursor1a.moveToNext()) {
                        int imageColumnIndexa = cursor1a.getColumnIndex("image");
                        byte[] image0 = cursor1a.getBlob(imageColumnIndexa);
                        anhchuxe =convertByteArrayToBitmap(image0);
                    }
                    // Đọc ảnh dưới dạng byte array
                    int tongsa = cursor1.getInt(tongs);
                    byte[] image1 = cursor1.getBlob(imageColumnIndex);
                    byte[] image2 = cursor1.isNull(image2ColumnIndex) ? new byte[0] : cursor1.getBlob(image2ColumnIndex);
                    byte[] image3 = cursor1.isNull(image3ColumnIndex) ? new byte[0] : cursor1.getBlob(image3ColumnIndex);
                    byte[] image4 = cursor1.isNull(image4ColumnIndex) ? new byte[0] : cursor1.getBlob(image4ColumnIndex);
                    ok.add(convertByteArrayToBitmap(image1));
                    ok.add(convertByteArrayToBitmap(image2));
                    ok.add(convertByteArrayToBitmap(image3));
                    ok.add(convertByteArrayToBitmap(image4));

                    replaceFragment(new thong_tin_xe(new Car(getContext(),idcarr,idt,name, type,biao,location ,priceOld,priceNew,ok, anhchuxe, fuel,seats,biao,vitri,tongsa), Lich_su_thue.this));
            }
            }
            @Override
            public void danhgia_click(lic_su car) {
                lc[0] =car;
              view.findViewById(R.id.form_danhgia).setVisibility(VISIBLE);


            }
        });
        view.findViewById(R.id.close_button).setOnClickListener(v->{
            view.findViewById(R.id.form_danhgia).setVisibility(GONE);
        });

        view.findViewById(R.id.save_button).setOnClickListener(v->{
            Button nb= view.findViewById(R.id.save_button);
            if(nb.getText().equals("Đánh giá")){
            TextInputEditText edt_mk2=view.findViewById(R.id.edt_mk2);
                EditText edt_mk1=view.findViewById(R.id.edt_mk1);

                Cursor cursora = dba.rawQuery(
                        "SELECT * FROM coment WHERE id_account = ? AND id_car = ?",
                        new String[]{String.valueOf(ida), String.valueOf(lc[0].getidcarr())}
                );
                if (cursora.moveToNext())
                {
                    Toast.makeText(getContext(), "Bạn đã đánh giá rồi" , Toast.LENGTH_SHORT).show();
                    view.findViewById(R.id.form_danhgia).setVisibility(GONE);
                    edt_mk1.setText("");
                    edt_mk2.setText("");
                }
                else{
                    ContentValues valuesa =new ContentValues();
            valuesa.put("id_account",ida);
            valuesa.put("id_car",String.valueOf(lc[0].getidcarr()));
            valuesa.put("text",edt_mk2.getText().toString());
            valuesa.put("so_sao",Integer.parseInt(edt_mk1.getText().toString()));
                var  fddf= dba.insert("coment",null,valuesa);
            if(fddf>0) {
                Toast.makeText(getContext(), "Đánh giá thành công" , Toast.LENGTH_SHORT).show();
                view.findViewById(R.id.form_danhgia).setVisibility(GONE);

            }
        dba.close();
            }
            }
        });

        recyclerView = view.findViewById(R.id.recyclerviewa);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        ida=cursor.getString(0);
        fetchData(cursor.getString(0));
        recyclerView.setAdapter(lic_suAdapter);
        return view;
    }
    int tong_lanthue=0;
    int tong_lanthue1=0;
    public static Bitmap convertByteArrayToBitmap(byte[] byteArray) {
        try{
            return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
        }catch(Exception sss){return null;}
    }
    public void fetchData(String accountId) {
        SQLiteDatabase dba = db.openDatabase();
        Cursor cursor = dba.rawQuery("SELECT * FROM lich_su_thue_xe WHERE id_account = ?", new String[]{String.valueOf(accountId)});
        int idColumnIndex = cursor.getColumnIndex("id");
        int nameColumnIndex = cursor.getColumnIndex("id_account");
        int typeColumnIndex = cursor.getColumnIndex("id_car");
        int fuelColumnIndex = cursor.getColumnIndex("thoi_gian_thue");
        int seatsColumnIndex = cursor.getColumnIndex("thoi_gian_tra");
        int vuTriColumnIndex = cursor.getColumnIndex("so_tien");
        int priceOldColumnIndex = cursor.getColumnIndex("trang_thai");
        while (cursor.moveToNext()) {
tong_lanthue++;
            String id = cursor.getString(idColumnIndex);
            String id_account=cursor.getString(nameColumnIndex);
            String id_car=cursor.getString(typeColumnIndex);
            String thoi_gian_thue=cursor.getString(fuelColumnIndex);
            String thoi_gian_tra=cursor.getString(seatsColumnIndex);
            String so_tien=cursor.getString(vuTriColumnIndex);
            tong_lanthue1+=Integer.parseInt(so_tien);
            String trang_thai=cursor.getString(priceOldColumnIndex);
            Cursor cursor1 = dba.rawQuery("SELECT * FROM cars WHERE id = ?", new String[]{String.valueOf(id_car)});
            while (cursor1.moveToNext()) {

int bjjj=cursor1.getInt(0);
                lic_suList.add(new lic_su(id,trang_thai,cursor1.getString(2),thoi_gian_thue,thoi_gian_tra,cursor1.getString(1),Integer.parseInt(so_tien),Integer.parseInt(id_car)));
            }
            cursor1.close();
        }
        textTotalRentals.setText(String.valueOf(tong_lanthue));
        textTotalSpent.setText(String.format("%,d.000 VNĐ", tong_lanthue1));
        cursor.close(); // Đóng con trỏ sau khi sử dụng
        lic_suAdapter.notifyDataSetChanged();
    }
        private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }
}