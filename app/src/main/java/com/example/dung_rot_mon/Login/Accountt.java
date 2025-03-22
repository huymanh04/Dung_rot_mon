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
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dung_rot_mon.Fragment.tab_account.MyAddress;
import com.example.dung_rot_mon.Fragment.tab_account.SecondFragment;
import com.example.dung_rot_mon.Fragment.tab_account.tai_khoan_cua_toi;
import com.example.dung_rot_mon.MainActivity;
import com.example.dung_rot_mon.R;
import com.example.dung_rot_mon.Sql.DatabaseHelper;
import com.example.dung_rot_mon.Sql.DatabaseManager;
import com.example.dung_rot_mon.admin.Frg_baner;
import com.example.dung_rot_mon.admin.MainAdmin;
import com.example.dung_rot_mon.admin.add;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class Accountt extends Fragment {
    public static DatabaseManager dbManager;
String email1="";
public  Accountt(String email){
    this.email1=email;
}
    private ImageView imageView;
    CardView cardMyAccount,ChangePass,cardMyAddress;
    CardView FavoriteCars;
    FrameLayout manh;

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
if(email1!=""&&email1!=null){
        readData(email1);
        Bitmap bitmap = BitmapFactory.decodeByteArray(imga, 0, imga.length);
        if(bitmap==null) {
        }else {        imageView.setImageBitmap(bitmap);}
        TextView na = view.findViewById(R.id.textViewNamea);
        na.setText(Name);
        if(taixe==1)
        {
            view.findViewById(R.id.textssViewNamea).setVisibility(View.VISIBLE);
        }
}
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
                Intent ma = new Intent(getActivity(), com.example.dung_rot_mon.Fragment.tab_account.FavoriteCars.class);
                startActivity(ma);
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
        if(1!=1){
            view.findViewById(R.id.textssViewNamea).setVisibility(View.VISIBLE);
        }else {view.findViewById(R.id.textssViewNamea).setVisibility(View.GONE);}
        if(1!=1){
            view.findViewById(R.id.addxe).setVisibility(View.VISIBLE);
        }else {view.findViewById(R.id.addxe).setVisibility(View.GONE);}
        view.findViewById(R.id.cardDeleteAccount).setOnClickListener(v->{

            MainActivity.ktralogin=false;
            Intent m = new Intent(getActivity(), MainActivity.class);
            startActivity(m);
            getActivity().finish();
        });
        cardMyAddress = view.findViewById(R.id.cardMyAddress);
        cardMyAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent m = new Intent(getActivity(), MyAddress.class);
                startActivity(m);
            }
        });  view.findViewById(R.id.addxe).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent m = new Intent(getActivity(), MainAdmin.class);
                startActivity(m);
            }
        });
        etOldPassword=view.findViewById(R.id.edt_mk1);
        etNewPassword=view.findViewById(R.id.edt_mk2);
        etConfirmNewPassword=view.findViewById(R.id.edt_mk3);
view.findViewById(R.id.cardChangePassword).setOnClickListener(v->{

    view.findViewById(R.id.edit_form_mk).setVisibility(View.VISIBLE);
});
        manh= view.findViewById(R.id.edit_form_mk);
view.findViewById(R.id.close_button).setOnClickListener(v->{

            view.findViewById(R.id.edit_form_mk).setVisibility(View.GONE);
});
view.findViewById(R.id.save_button).setOnClickListener(v->{

    String oldPassworda = etOldPassword.getText().toString().trim();
    String newPassworda = etNewPassword.getText().toString().trim();
    String confirmNewPassword = etConfirmNewPassword.getText().toString().trim();

    if (newPassworda.equals(confirmNewPassword)) {
        changePassword(oldPassworda, newPassworda);
    } else {
        Toast.makeText(getActivity(), "Mật khẩu mới không khớp", Toast.LENGTH_SHORT).show();

    }
});
        return view;
    }
    EditText etOldPassword,etNewPassword,etConfirmNewPassword;
    private FirebaseAuth mAuth;
    private void changePassword(String oldPassword, String newPassword) {

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        if (user != null) {
            // Lấy email và mật khẩu cũ của người dùng để thực hiện xác thực lại
            AuthCredential credential = EmailAuthProvider.getCredential(user.getEmail(), oldPassword);

            // Xác thực lại với mật khẩu cũ
            user.reauthenticate(credential)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            // Xác thực thành công, tiến hành thay đổi mật khẩu mới
                            user.updatePassword(newPassword)
                                    .addOnCompleteListener(updateTask -> {
                                        if (updateTask.isSuccessful()) {
                                            // Mật khẩu được thay đổi thành công
                                            Toast.makeText(getActivity(), "Mật khẩu đã được thay đổi", Toast.LENGTH_SHORT).show();
                                            manh.setVisibility(View.GONE);
                                        } else {
                                            // Nếu thay đổi mật khẩu thất bại
                                            Toast.makeText(getActivity(), "Lỗi thay đổi mật khẩu: " + updateTask.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        } else {
                            // Xác thực mật khẩu cũ thất bại
                            Toast.makeText(getActivity(), "Mật khẩu cũ không chính xác", Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            Toast.makeText(getActivity(), "Bạn cần đăng nhập để thay đổi mật khẩu", Toast.LENGTH_SHORT).show();
        }
    }
    static String Name;
    static String Email;
    static byte[] imga;
    public static int taixe;
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
        int inttaixe = cursor.getColumnIndex("taixe");

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
            taixe=cursor.getInt(inttaixe);
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