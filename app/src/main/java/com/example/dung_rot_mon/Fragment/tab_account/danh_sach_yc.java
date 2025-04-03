package com.example.dung_rot_mon.Fragment.tab_account;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dung_rot_mon.Adapter.Lich_su_adapter;
import com.example.dung_rot_mon.Adapter.duyet_tai_xe_adapter;
import com.example.dung_rot_mon.R;
import com.example.dung_rot_mon.Sql.DatabaseHelper;
import com.example.dung_rot_mon.admin.duyet_taixe;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link danh_sach_yc#newInstance} factory method to
 * create an instance of this fragment.
 */
public class danh_sach_yc extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public danh_sach_yc() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment danh_sach_yc.
     */
    // TODO: Rename and change types and number of parameters
    public static danh_sach_yc newInstance(String param1, String param2) {
        danh_sach_yc fragment = new danh_sach_yc();
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
    private duyet_tai_xe_adapter lic_suAdapter;
    DatabaseHelper db;
    private List<duyet_taixe> list_taxe;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_danh_sach_yc, container, false);
        list_taxe=new ArrayList<>();
        db=new DatabaseHelper(getContext());
        lic_suAdapter=new duyet_tai_xe_adapter(getContext(),list_taxe);
        recyclerView = view.findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        fetchData();
        recyclerView.setAdapter(lic_suAdapter);
        return  view;
    }
    public void fetchData() {
        SQLiteDatabase dba = db.openDatabase();
        Cursor cursor = dba.rawQuery("SELECT * FROM account WHERE taixe = ?", new String[]{String.valueOf(3)});
        int idColumnIndex1 = cursor.getColumnIndex("id");
        int idColumnIndex = cursor.getColumnIndex("phone");
        int nameColumnIndex = cursor.getColumnIndex("name");
        int typeColumnIndex = cursor.getColumnIndex("image");

        while (cursor.moveToNext()) {
            String phone = cursor.getString(idColumnIndex);
            String id = cursor.getString(idColumnIndex1);
            String name=cursor.getString(nameColumnIndex);
            byte[] id_car=cursor.getBlob(typeColumnIndex);
            list_taxe.add(new duyet_taixe(id,name,phone,id_car));
        }

        cursor.close(); // Đóng con trỏ sau khi sử dụng
        lic_suAdapter.notifyDataSetChanged();
    }
}