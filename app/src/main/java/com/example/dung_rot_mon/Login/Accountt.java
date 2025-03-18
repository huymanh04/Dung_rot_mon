package com.example.dung_rot_mon.Login;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dung_rot_mon.Fragment.tab_account.MyAddress;
import com.example.dung_rot_mon.Fragment.tab_account.SecondFragment;
import com.example.dung_rot_mon.Fragment.tab_account.tai_khoan_cua_toi;
import com.example.dung_rot_mon.R;
import com.example.dung_rot_mon.Sql.DatabaseHelper;
import com.example.dung_rot_mon.Sql.DatabaseManager;


public class Accountt extends Fragment {
    public static DatabaseManager dbManager;
String email1="";
public  Accountt(String email){
    this.email1=email;
}
    private ImageView imageView;
    CardView cardMyAccount,ChangePass,cardMyAddress;
    CardView FavoriteCars;
    static DatabaseHelper dba;
    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, container, false);
        cardMyAccount = view.findViewById(R.id.cardMyAccount);
        imageView = view.findViewById(R.id.imageViewa);
        dbManager = new DatabaseManager(getContext());
dba =new DatabaseHelper(getActivity());
        readData(email1);
        Bitmap bitmap = BitmapFactory.decodeByteArray(imga, 0, imga.length);
        if(bitmap==null) {
        }else {        imageView.setImageBitmap(bitmap);}
        TextView na = view.findViewById(R.id.textViewNamea);
        na.setText(Name);
        cardMyAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                replaceFragment(new tai_khoan_cua_toi(email1));
            }
        });
        FavoriteCars = view.findViewById(R.id.cardFavoriteCars);
        FavoriteCars.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent m = new Intent(getActivity(), com.example.dung_rot_mon.Fragment.tab_account.FavoriteCars.class);
                startActivity(m);
            }
        });
        ChangePass = view.findViewById(R.id.cardChangePassword);
        ChangePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent m = new Intent(getActivity(), com.example.dung_rot_mon.Fragment.tab_account.ChangePass.class);
                startActivity(m);
            }
        });
        cardMyAddress = view.findViewById(R.id.cardMyAddress);
        cardMyAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent m = new Intent(getActivity(), MyAddress.class);
                startActivity(m);
            }
        });


        return view;
    }
    static String Name;
    static String Email;
    static byte[] imga;

    public static void readData(String email) {
        // Modify the query to select based on the email
        SQLiteDatabase db = dba.openDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM account WHERE email = ?", new String[]{email});

        // Get column indices
        int idColumnIndex = cursor.getColumnIndex("id");
        int emailColumnIndex = cursor.getColumnIndex("email");
        int nameColumnIndex = cursor.getColumnIndex("name");
        int imgColumnIndex = cursor.getColumnIndex("image");
        int ngaythamgiaColumnIndex = cursor.getColumnIndex("ngaythamgia");
        int cmndhamgiaColumnIndex = cursor.getColumnIndex("cmnd");

        // Kiểm tra nếu các chỉ số cột hợp lệ (lớn hơn hoặc bằng 0)
        if (idColumnIndex == -1 || emailColumnIndex == -1 || nameColumnIndex == -1 || imgColumnIndex == -1) {
            Log.e("Database", "Column not found!");
            cursor.close();
            return;
        }

        // Kiểm tra nếu có dữ liệu trong Cursor
        if (cursor.moveToFirst()) {
            try {
           int id = cursor.getInt(idColumnIndex);

            Email = cursor.getString(emailColumnIndex);
            Name = cursor.getString(nameColumnIndex);
            imga = cursor.getBlob(imgColumnIndex);
String ngaythamgia=cursor.getBlob(ngaythamgiaColumnIndex).toString(); }finally {
            }
        }

        cursor.close(); // Đóng con trỏ sau khi sử dụng
    }
    private void replaceFragment(Fragment fragment) {
        // Lấy Activity chứa FrameLayout
        Activity activity = getActivity();
        if (activity != null) {
            FragmentTransaction fragmentTransaction = ((androidx.fragment.app.FragmentActivity) activity).getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame_layout, fragment); // Đảm bảo đúng ID của container trong Activity A
            fragmentTransaction.commit();
        }
    }

}