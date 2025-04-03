package com.example.dung_rot_mon.dialog;

import static com.example.dung_rot_mon.MainActivity.Id_account;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.dung_rot_mon.Fragment.Support;
import com.example.dung_rot_mon.R;
import com.example.dung_rot_mon.Sql.DatabaseHelper;

public class GioiThieuDialogFragment extends DialogFragment {

    public static GioiThieuDialogFragment newInstance() {
        return new GioiThieuDialogFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gioi_thieu_dialog, container, false);

        // Nút đóng dialog
        ImageView btnClose = view.findViewById(R.id.btnClose);
        btnClose.setOnClickListener(v -> dismiss());
view.findViewById(R.id.tvContactSupport).setOnClickListener(v->{
    replaceWithFragment();
});
        DatabaseHelper db=new DatabaseHelper(getContext());
view.findViewById(R.id.btnRegister).setOnClickListener(v->{
    SQLiteDatabase dba = db.openDatabase();
    Cursor cursor = dba.rawQuery("SELECT * FROM account WHERE id = ?", new String[]{String.valueOf(Id_account)});
    int idColumnIndex = cursor.getColumnIndex("taixe");
    if(cursor.moveToFirst())
    {
        int m=cursor.getInt(idColumnIndex);
        if(cursor.getInt(idColumnIndex)==1){
            Toast.makeText(getContext(), "Bạn đã là tài xế" , Toast.LENGTH_SHORT).show();
        }else if(cursor.getInt(idColumnIndex)==2){
            Toast.makeText(getContext(), "Bạn là admin" , Toast.LENGTH_SHORT).show();
        }else if(cursor.getInt(idColumnIndex)==3){
            Toast.makeText(getContext(), "Bạn đang được chờ xét duyệt" , Toast.LENGTH_SHORT).show();
        }else { ContentValues values = new ContentValues();

            values.put("taixe", 3);
            String whereClause = "id = ?";
            String[] whereArgs = {String.valueOf(Id_account)};

            // Thực hiện cập nhật
            int rowsAffected = dba.update("account", values, whereClause, whereArgs);
            if(rowsAffected>0){
                Toast.makeText(getContext(), "Vui lòng chờ xét duyệt" , Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(getContext(), "Đăng ký thất bại" , Toast.LENGTH_SHORT).show();
            }
            dba.close();}
    }

});
        return view;
    }
    public void replaceWithFragment() {
        dismiss(); // Đóng DialogFragment
        Fragment newFragment = new Support(); // Thay bằng Fragment bạn muốn hiển thị

        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, newFragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onStart() {
        super.onStart();
        if (getDialog() != null && getDialog().getWindow() != null) {
            // Set kích thước dialog full màn hình
            Window window = getDialog().getWindow();
            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

            // Ẩn status bar để giao diện full screen đẹp hơn
            window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
    }
}
