package com.example.dung_rot_mon.tab_car;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.dung_rot_mon.R;
import com.example.dung_rot_mon.Sql.DatabaseManager;
import com.google.android.material.textfield.TextInputEditText;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Edit_car extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
Car cars;
    public Edit_car(Car car,int id, Fragment a) {
        // Required empty public constructor
        Id_name=id;
     cars=car;
        this.a=a;
    }
    private static final int PICK_IMAGES_REQUEST = 1;
    private ArrayList<Uri> imageUris = new ArrayList<>();
    private LinearLayout carImagesContainer;
    List<Bitmap> bitmapList;
    DatabaseManager db;
    AutoCompleteTextView ettypeCar,etNguyenLieu,etLocation;
    TextInputEditText etCarName,etDetails,etPriceOld,etPriceNew,etSoChoNgoi,etViTri;

    static int Id_name;Fragment a;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_edit_car, container, false);
        db=new DatabaseManager(getContext());
        bitmapList=new ArrayList<>();
        etCarName = view.findViewById(R.id.etCarName);
        ettypeCar = view.findViewById(R.id.actvTypeCar);
        etDetails = view.findViewById(R.id.etDetails);
        etPriceOld = view.findViewById(R.id.etPriceOld);
        etPriceNew = view.findViewById(R.id.etPriceNew);
        etNguyenLieu = view.findViewById(R.id.actvNguyenLieu);

        etSoChoNgoi = view.findViewById(R.id.etSoChoNgoi);
        etLocation = view.findViewById(R.id.etViTri);
        etViTri = view.findViewById(R.id.etLocation);
        carImagesContainer = view.findViewById(R.id.carImagesContainer);

        String giacu = cars.getPriceOld().toString().trim(); // Lấy giá trị nhập vào
        int giac = giacu.isEmpty() ? 0 : Integer.parseInt(giacu);

        String giamoi = cars.getPriceNew().toString().trim(); // Lấy giá trị nhập vào
        int giamoii = giamoi.isEmpty() ? 0 : Integer.parseInt(giamoi);

        etCarName.setText(cars.getCarName());
        ettypeCar.setText(cars.getType());
        etDetails.setText(cars.getDetails());
        etPriceOld.setText(String.valueOf(giac));
        etPriceNew.setText(String.valueOf(giamoii));
        etNguyenLieu.setText(cars.getNguyenlieu());

        etSoChoNgoi.setText(String.valueOf(cars.getSochongoi()));
        etLocation.setText(cars.getVitri());
        etViTri.setText(cars.getLocation());
        final String[] loaixe = new String[1];
        final String[] Vitri = new String[1];
        final String[] nhienlieu = new String[1];
        List<String> loaiXeItems = Arrays.asList("Số tự động", "Số sàn");
        ArrayAdapter<String> loaiXeAdapter = new ArrayAdapter<>(
                requireContext(),
                R.layout.list_item,
                loaiXeItems
        );
        ettypeCar.setAdapter(loaiXeAdapter);
        List<Bitmap> images = getCarImages(cars.getIDcarr());
        view.findViewById(R.id.btnAddCarImage).setOnClickListener(v->{
            if (carImagesContainer.getChildCount() > 1) {
                carImagesContainer.removeViews(1, carImagesContainer.getChildCount() - 1);
            }

            openGallery();
        });

        // Add item click listener for Loại xe (optional)
        ettypeCar.setOnItemClickListener((parent, viewa, position, id) -> {
            String selectedLoaiXe = loaiXeItems.get(position);
            loaixe[0]=selectedLoaiXe;          // Xử lý khi chọn loại xe
        });

        // Setup Nguyên liệu dropdown
        List<String> nguyenLieuItems = Arrays.asList("Xăng", "Điện", "Dầu");
        ArrayAdapter<String> nguyenLieuAdapter = new ArrayAdapter<>(
                requireContext(),R.layout.list_item,
                nguyenLieuItems
        );
        etNguyenLieu.setAdapter(nguyenLieuAdapter);

        // Add item click listener for Nguyên liệu (optional)
        etNguyenLieu.setOnItemClickListener((parent, viewa, position, id) -> {
            String selectedNguyenLieu = nguyenLieuItems.get(position);
            nhienlieu[0]=selectedNguyenLieu;
        });
        List<String> vitri = Arrays.asList("Sài Gòn", "Đà Nẵng", "Phú Yên","Hà Nội","Đồng Nai","Huế","Vũng Tàu");
        ArrayAdapter<String> vitriAdapter = new ArrayAdapter<>(
                requireContext(),R.layout.list_item,
                vitri
        );
        etLocation.setAdapter(vitriAdapter);
        Vitri[0]= etLocation.getText().toString();
        etLocation.setOnItemClickListener((parent, viewa, position, id) -> {
            String selectedNguyenLieu = vitri.get(position);
            Vitri[0]=selectedNguyenLieu;
        });
        view.findViewById(R.id.btnSave).setOnClickListener(v->{
            Car car=new Car(getContext(),cars.getIDcarr(),Id_name,etCarName.getText().toString(),ettypeCar.getText().toString(),etDetails.getText().toString(),etViTri.getText().toString(),etPriceOld.getText().toString(),etPriceNew.getText().toString(),bitmapList,cars.getCarImage().get(0),etNguyenLieu.getText().toString(),Integer.parseInt(etSoChoNgoi.getText().toString()),etDetails.getText().toString(),Vitri[0],cars.get_tong());
           int ktra= update_car(car);
            if(ktra>0)
            {
                Toast.makeText(getContext(), "Chỉnh xe thành công" , Toast.LENGTH_SHORT).show();
                replaceFragment(a);
            }else{    Toast.makeText(getContext(), "Chỉnh xe thất bại" , Toast.LENGTH_SHORT).show();}
        });
        List<Bitmap> bm=cars.getCarImage();
        for (int i=0;i<bm.size();i++){
            addImageToScrollView1(bm.get(i));
        }
        return  view;
    }
    public List<Bitmap> getCarImages(int carId) {
        List<Bitmap> images = new ArrayList<>();
        SQLiteDatabase dba = db.getWritableDatabase();
        Cursor cursor = dba.rawQuery("SELECT image, image2, image3, image4 FROM cars WHERE id = ?", new String[]{String.valueOf(carId)});
        if (cursor.moveToFirst()) {
            for (int i = 0; i < 4; i++) {
                byte[] imageData = cursor.getBlob(i);
                if (imageData != null) {
                    Bitmap bitmap = BitmapFactory.decodeByteArray(imageData, 0, imageData.length);
                    images.add(bitmap);
                }
            }
        }
        cursor.close();
        dba.close();
        return images;
    }

    public int update_car(Car car) {
        SQLiteDatabase dba = db.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("car_name", car.getCarName());
        values.put("car_type", car.getType());
        values.put("nhine_lieu", car.getNguyenlieu());
        values.put("so_cho_ngoi", car.getSochongoi());
        values.put("vu_tri", car.getVitri());
        values.put("Location", car.getLocation());
        values.put("bio", car.getBio());
        values.put("gia_cu", car.getPriceOld());
        values.put("gia_moi", car.getPriceNew());

        int totalImages = car.getCarImage().size();
        int maxImages = 4;

//         Cập nhật hình ảnh mới
        if(totalImages>0) {
            for (int i = 0; i < totalImages; i++) {
                String columnName = "image" + (i == 0 ? "" : (i + 1)); // image, image2, image3, image4
                Bitmap originalBitmap = BitmapFactory.decodeByteArray(
                        imageToByteArray(car.getCarImage().get(i)), 0,
                        imageToByteArray(car.getCarImage().get(i)).length
                );
                byte[] compressedImage = processImageForSQLite(originalBitmap, 200);
                values.put(columnName, compressedImage);
            }
            for (int i = totalImages + 1; i <= maxImages; i++) {
                String columnName = "image" + (i == 1 ? "" : i);
                values.putNull(columnName);
            }
        }
        // Điều kiện WHERE để cập nhật xe dựa trên ID
        String whereClause = "id = ?";
        String[] whereArgs = {String.valueOf(car.getIDcarr())};

        // Thực hiện cập nhật
        int rowsAffected = dba.update("cars", values, whereClause, whereArgs);
        dba.close();

        return rowsAffected;
    }

    private byte[] imageToByteArray(Bitmap imagePath) {
        Bitmap bmp =imagePath;
        ByteArrayOutputStream strem=new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG,80,strem);
        byte[] anh=strem.toByteArray();
        return anh;
    }
    public static byte[] processImageForSQLite(Bitmap bitmap, int maxSizeKB) {
        int quality = 100; // Bắt đầu với chất lượng cao nhất
        ByteArrayOutputStream stream = new ByteArrayOutputStream();

        // Nén ảnh xuống dưới maxSizeKB
        do {
            stream.reset(); // Xóa dữ liệu cũ
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, stream);
            quality -= 30; // Giảm chất lượng dần (-10 mỗi lần)
        } while (stream.toByteArray().length / 1024 > maxSizeKB && quality > 10);

        return stream.toByteArray();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGES_REQUEST && resultCode == getActivity().RESULT_OK) {
            if (data.getClipData() != null) { // Khi chọn nhiều ảnh
                int count = data.getClipData().getItemCount();
                for (int i = 0; i < count; i++) {
                    Uri imageUri = data.getClipData().getItemAt(i).getUri();
                    imageUris.add(imageUri); // Lưu Uri vào list
                    Bitmap bitmap = uriToBitmap(imageUri);
                    if (bitmap != null) {
                        bitmapList.add(bitmap); // Lưu Bitmap vào list
                        addImageToScrollView(imageUri);
                    }
                }
            } else if (data.getData() != null) { // Khi chọn 1 ảnh
                Uri imageUri = data.getData();
                imageUris.add(imageUri);
                Bitmap bitmap = uriToBitmap(imageUri);
                if (bitmap != null) {
                    bitmapList.add(bitmap);
                    addImageToScrollView(imageUri);
                }
            }
        }
    }
    private Bitmap uriToBitmap(Uri uri) {
        try {
            return MediaStore.Images.Media.getBitmap(requireActivity().getContentResolver(), uri);
        } catch (IOException e) {
            Log.e("uriToBitmap", "Lỗi chuyển đổi Uri -> Bitmap: " + e.getMessage());
            return null;
        }
    }

    private void addImageToScrollView(Uri imageUri) {
        ImageView imageView = new ImageView(getContext());
        int size = 200; // Kích thước ảnh
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(size, size);
        params.setMargins(10, 0, 10, 0);
        imageView.setLayoutParams(params);
        imageView.setImageURI(imageUri);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

        // Thêm vào container
        carImagesContainer.addView(imageView);
    }
    private void addImageToScrollView1(Bitmap imageUri) {
        ImageView imageView = new ImageView(getContext());
        int size = 200; // Kích thước ảnh
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(size, size);
        params.setMargins(10, 0, 10, 0);
        imageView.setLayoutParams(params);
        imageView.setImageBitmap(imageUri);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

        // Thêm vào container
        carImagesContainer.addView(imageView);
    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        startActivityForResult(Intent.createChooser(intent, "Chọn ảnh"), PICK_IMAGES_REQUEST);
    }
}