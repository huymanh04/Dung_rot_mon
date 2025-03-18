package com.example.dung_rot_mon.Fragment.tab_account;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.provider.MediaStore;
import android.text.style.UpdateAppearance;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dung_rot_mon.Login.Accountt;
import com.example.dung_rot_mon.R;
import com.example.dung_rot_mon.Sql.DatabaseManager;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link tai_khoan_cua_toi#newInstance} factory method to
 * create an instance of this fragment.
 */
public class tai_khoan_cua_toi extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
String Email;
    public tai_khoan_cua_toi(String email) {
        Email=email;
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment tai_khoan_cua_toi.
     */
    // TODO: Rename and change types and number of parameters
    public static tai_khoan_cua_toi newInstance(String param1, String param2) {
        tai_khoan_cua_toi fragment = new tai_khoan_cua_toi("");
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
ImageView profile_image;
    static TextView textViewName;
    static EditText edit_form_phone;
    static EditText edit_form_email;
    static TextView textTkViewName;
    static TextView textViewName1;
    private FrameLayout editFormContainer;
    private Button editButton;
    private ImageButton closeButton;
    private Button saveButton;
    private Button selectImageButton;
    @SuppressLint({"MissingInflatedId"})
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_tai_khoan_cua_toi, container, false);
        view.findViewById(R.id.btnback).setOnClickListener(v->{
            replaceFragment(new Accountt(Email));
        });
        profile_image=view.findViewById(R.id.profile_image);
        textViewName=view.findViewById(R.id.textViewName);
        edit_form_phone=view.findViewById(R.id.edit_sdt);
        edit_form_email=view.findViewById(R.id.edit_Email);
        edit_form_email.setText(Email);
        textTkViewName=view.findViewById(R.id.textTkViewName);
        textViewName1=view.findViewById(R.id.edit_name);
        readData(Email);
        editFormContainer = view.findViewById(R.id.edit_form_container);
        editButton = view.findViewById(R.id.btn_edit_profile);
        closeButton = view.findViewById(R.id.close_button);
        saveButton = view.findViewById(R.id.save_button);
        selectImageButton = view.findViewById(R.id.select_image_button);
        editButton.setOnClickListener(v -> {
            editFormContainer.setVisibility(View.VISIBLE); // Hiển thị form chỉnh sửa
        });

         closeButton.setOnClickListener(v -> {
            editFormContainer.setVisibility(View.GONE); // Ẩn form chỉnh sửa
        });
        view.findViewById(R.id.phone_layout).setOnClickListener(v -> {
            view.findViewById(R.id.edit_form_sdt).setVisibility(View.VISIBLE); // Hiển thị form chỉnh sửa phone
        });
        view.findViewById(R.id.close_button_sdt).setOnClickListener(v -> {
            view.findViewById(R.id.edit_form_sdt).setVisibility(View.GONE); // Ẩn form chỉnh sửa
        });



        view.findViewById(R.id.gmail_layout).setOnClickListener(v -> {
            view.findViewById(R.id.edit_form_email).setVisibility(View.VISIBLE); // Hiển thị form chỉnh sửa email
        });
        view.findViewById(R.id.close_email).setOnClickListener(v -> {
            view.findViewById(R.id.edit_form_email).setVisibility(View.GONE); // Ẩn form chỉnh sửa
        });

   view.findViewById(R.id.save_button_sdt).setOnClickListener(v->{
            EditText manh=view.findViewById(R.id.edit_sdt);
            updateUserData(Email,"",manh.getText().toString(),null,null);// Hiển thị form chỉnh sửa email
        });
        view.findViewById(R.id.select_image_button).setOnClickListener(v->{
            bitmap=null;
            openImagePicker();
        });
        view.findViewById(R.id.save_button).setOnClickListener(v->{
            Bitmap bmm = null;
            updateUserData(Email,textViewName1.getText().toString(),"",bitmap,bmm);// Hiển thị form chỉnh sửa name
            editFormContainer.setVisibility(View.GONE);
        });
        view.findViewById(R.id.driver_license_layout).setOnClickListener(v -> {

            view.findViewById(R.id.edit_form_gplx).setVisibility(View.VISIBLE); // Hiển thị form chỉnh sửa blx
        });
        view.findViewById(R.id.close_gplx).setOnClickListener(v -> {
            view.findViewById(R.id.edit_form_gplx).setVisibility(View.GONE); // Ẩn form chỉnh sửa
        });
        view.findViewById(R.id.select_image_gplx).setOnClickListener(v->{
            bitmap=null;
            openImagePicker();
        });  view.findViewById(R.id.save_gplx).setOnClickListener(v->{
            Bitmap bmm = null;
            updateUserData(Email,"","",bmm,bitmap);
        });
        return view;
    }
    private void replaceFragment(Fragment fragment) {
        // Lấy Activity chứa FrameLayout
        Activity activity = getActivity();
        if (activity != null) {
            FragmentTransaction fragmentTransaction = ((FragmentActivity) activity).getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame_layout, fragment); // Đảm bảo đúng ID của container trong Activity A
            fragmentTransaction.commit();
        }
    }  Bitmap cmnd;
    int sochuyen;
    private static final int PICK_IMAGE_REQUEST = 1;
    static String Name;

    public void updateUserData(String email, String newName, String newPhone,Bitmap anhmoi,Bitmap anh_gplx) {
        SQLiteDatabase db = Accountt.dbManager.getWritableDatabase();

        ContentValues values = new ContentValues();
        if(newName!=""&&newName!=null) {
            values.put("name", newName);
        }// Cập nhật tên người dùng
        if(newPhone!=""&&newPhone!=null) {
            values.put("phone", newPhone);
        }
     // Cập nhật số điện thoạ
        if(anhmoi!=null) {
            values.put("image", getBitmapAsByteArray(anhmoi));
        }
        if(anh_gplx!=null) {
            values.put("image_blx", getBitmapAsByteArray(anh_gplx));
        }
        // Cập nhật bản ghi trong bảng "users" nơi id = userId
        int rowsUpdated = db.update("account", values, "email = ?", new String[]{String.valueOf(email)});

        if (rowsUpdated > 0) {
            Toast.makeText(getContext(), "Cập nhật thành công", Toast.LENGTH_SHORT).show();
            reloadFragment();
        } else {
            Toast.makeText(getContext(), "Không tìm thấy người dùng để cập nhật", Toast.LENGTH_SHORT).show();
        }

        db.close();
    }
    public byte[] getBitmapAsByteArray(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, stream);  // Chuyển Bitmap thành mảng byte
        return stream.toByteArray();
    }

    public  void readData(String email) {
        // Modify the query to select based on the email
        SQLiteDatabase db = DatabaseManager.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM account WHERE email = ?", new String[]{email});

        // Get column indices
        int idColumnIndex = cursor.getColumnIndex("id");
        int emailColumnIndex = cursor.getColumnIndex("email");
        int phonelColumnIndex = cursor.getColumnIndex("phone");
        int nameColumnIndex = cursor.getColumnIndex("name");
        int imgColumnIndex = cursor.getColumnIndex("image");
        int ngaythamgiaColumnIndex = cursor.getColumnIndex("ngaythamgia");
        int sochuyendhamgiaColumnIndex = cursor.getColumnIndex("sochuyen");
        int image_blxdhamgiaColumnIndex = cursor.getColumnIndex("image_blx");
        // Kiểm tra nếu các chỉ số cột hợp lệ (lớn hơn hoặc bằng 0)
        if (idColumnIndex == -1 || emailColumnIndex == -1 || nameColumnIndex == -1 || imgColumnIndex == -1) {
            Log.e("Database", "Column not found!");
            cursor.close();
            return;
        }

        // Kiểm tra nếu có dữ liệu trong Cursor
        if (cursor.moveToFirst()) {
            int id = cursor.getInt(idColumnIndex);
            Bitmap bitmap = BitmapFactory.decodeByteArray(cursor.getBlob(imgColumnIndex), 0, cursor.getBlob(imgColumnIndex).length);
              if(bitmap==null) {
            }else {        profile_image.setImageBitmap(bitmap);}
            textViewName.setText(cursor.getString(nameColumnIndex));
            edit_form_phone.setText(cursor.getString(phonelColumnIndex));
            textViewName1.setText(cursor.getString(nameColumnIndex));
            textTkViewName.setText("Ngày tham gia :"+cursor.getString(ngaythamgiaColumnIndex));
            sochuyen= Integer.parseInt(cursor.getString(sochuyendhamgiaColumnIndex));
        }

        cursor.close(); // Đóng con trỏ sau khi sử dụng
    }
    public void reloadFragment() {
        // Tạo fragment mới
        Fragment newFragment = new tai_khoan_cua_toi(Email); // Thay YourFragment bằng tên fragment của bạn

        // Lấy FragmentManager

        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        // Thực hiện một transaction để thay thế fragment cũ

        transaction.replace(R.id.frame_layout, newFragment);  // Thay thế fragment hiện tại bằng fragment mới
        transaction.addToBackStack(null);  // Thêm vào back stack nếu muốn quay lại
        transaction.commit();
    }

    public void openImagePicker() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*"); // Chỉ chọn ảnh
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }
    Bitmap bitmap=null;
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == PICK_IMAGE_REQUEST) {
            if (data != null) {
                Uri selectedImageUri = data.getData(); // Lấy URI của ảnh đã chọn
                try {
                    // Chuyển đổi URI thành Bitmap
                     bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), selectedImageUri);

                    Toast.makeText(getActivity(), "lấy ảnh thành cong", Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(getActivity(), "Không thể lấy ảnh", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }





}