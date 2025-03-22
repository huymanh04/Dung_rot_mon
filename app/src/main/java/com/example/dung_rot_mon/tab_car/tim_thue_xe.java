package com.example.dung_rot_mon.tab_car;

import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dung_rot_mon.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link tim_thue_xe#newInstance} factory method to
 * create an instance of this fragment.
 */
public class tim_thue_xe extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;



    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment tim_thue_xe.
     */
    // TODO: Rename and change types and number of parameters
    public static tim_thue_xe newInstance(String param1, String param2) {
        tim_thue_xe fragment = new tim_thue_xe();
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
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_tim_thue_xe, container, false);
        view.findViewById(R.id.button2).setOnClickListener(v->{
            recyclerView = view.findViewById(R.id.recyclerView);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

            carList = getCarList();
            carAdapter = new CarAdapter(getContext(), carList, car -> {
                // Khi click vào xe, thay đổi sang màn hình chi tiết xe
                replaceFragment(new thong_tin_xe(car));
            });
            recyclerView.setAdapter(carAdapter);


        });
        if(recyclerView!=null) {

            recyclerView.setAdapter(carAdapter);
        }
        return view;
    }
    private void replaceFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.addToBackStack(null); // Cho phép quay lại Fragment trước
        fragmentTransaction.commit();
    }
    List<Car> list = new ArrayList<>();
    private List<Car> getCarList() {

        list.add(new Car("Nguyễn Văn A", "Porsche 911", "Số tự động", "Quận 1, TP.HCM",
                "1.2M", "999K/ngày", R.drawable.porsche, R.drawable.bao_hiem, "Xăng",7,"xe mới","gần trung tâm"));
        list.add(new Car("Trần Minh B", "Mercedes-Benz G63", "Số tự động", "Quận 3, TP.HCM",
                "1.8M", "1.1M/ngày", R.drawable.porsche, R.drawable.bao_hiem, "Xăng", 5, "xe sang", "nội thất đẹp"));

        list.add(new Car("Lê Hoàng C", "BMW X7", "Số tự động", "Quận 5, TP.HCM",
                "2.0M", "1.3M/ngày", R.drawable.porsche, R.drawable.bao_hiem, "Xăng", 7, "xe mới", "rộng rãi"));

        list.add(new Car("Ngô Thanh D", "Lexus RX 500h", "Số tự động", "Quận 7, TP.HCM",
                "1.6M", "1.0M/ngày", R.drawable.porsche, R.drawable.bao_hiem, "Hybrid", 5, "tiết kiệm nhiên liệu", "nội thất cao cấp"));

        list.add(new Car("Đặng Văn E", "Range Rover Sport", "Số tự động", "Bình Thạnh, TP.HCM",
                "2.2M", "1.5M/ngày", R.drawable.porsche, R.drawable.bao_hiem, "Xăng", 7, "xe sang", "cách âm tốt"));

        list.add(new Car("Phạm Quốc F", "Audi Q8", "Số tự động", "Quận 2, TP.HCM",
                "1.9M", "1.2M/ngày", R.drawable.porsche, R.drawable.bao_hiem, "Xăng", 5, "hiệu suất cao", "công nghệ hiện đại"));



        return list;
    }
    class BannerViewHolder extends RecyclerView.ViewHolder {

        CardView cr;

        BannerViewHolder(View itemView) {
            super(itemView);
            cr = itemView.findViewById(R.id.cardViewCar);

        }
    }
}