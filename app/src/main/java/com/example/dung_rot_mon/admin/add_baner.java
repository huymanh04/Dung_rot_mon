package com.example.dung_rot_mon.admin;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dung_rot_mon.R;
import com.example.dung_rot_mon.Sql.DatabaseManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link add_baner#newInstance} factory method to
 * create an instance of this fragment.
 */
public class add_baner extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private static final int PICK_IMAGE_REQUEST = 1;

    private Uri imageUri;
    private ImageView imageView;
    private DatabaseManager dbManager;

    private TextView tvBase64String;

    public add_baner() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment add_baner.
     */
    // TODO: Rename and change types and number of parameters
    public static add_baner newInstance(String param1, String param2) {
        add_baner fragment = new add_baner();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_add_baner, container, false);
        imageView = view.findViewById(R.id.imageView);


        // Xử lý chọn ảnh từ thiết bị
        Button btnChooseImage = view.findViewById(R.id.btnChooseImage);
        Button open = view.findViewById(R.id.openshow);
        FloatingActionButton btnChooseImage1 = view.findViewById(R.id.btnChooseImagde2);

        btnChooseImage.setOnClickListener(v -> {
            openImageChooser();
        });

        // Đảm bảo khởi tạo đúng đối tượng DatabaseManager
        dbManager = new DatabaseManager(getContext());

        btnChooseImage1.setOnClickListener(v -> {
            Frg_baner.bannerList.clear();
            Frg_baner.fetchData();
            replaceFragment(new Frg_baner());

        });

        open.setOnClickListener(av -> {
            EditText v= view.findViewById(R.id.etName);

            var numbe=   dbManager.insertData(v.getText().toString(),imageView);
            if(numbe==-1){
                Toast.makeText(getContext(), "Add erro" , Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(getContext(), "Add ok" , Toast.LENGTH_SHORT).show();

            }

            v.setText("");
            requireActivity().recreate();

        });
        return view;
    }
    private void openImageChooser() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }
    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == getActivity().RESULT_OK && data != null) {
            Uri imageUri = data.getData();
            imageView.setImageURI(imageUri); // Hiển thị ảnh
        }
    }




}