package com.example.dung_rot_mon.Adapter;

import static com.example.dung_rot_mon.MainActivity.Id_account;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.dung_rot_mon.R;
import com.example.dung_rot_mon.tab_car.tim_xe;
import com.google.android.material.textfield.TextInputEditText;
import java.util.Calendar;
import java.util.Locale;

public class TabFragment extends Fragment {

    private static final String ARG_POSITION = "position";
    private Calendar selectedDateTime;
    private TextInputEditText edtRentDate;
    private AutoCompleteTextView edt_address;
    private TextInputEditText edtRentTime;

    public static TabFragment newInstance(int position) {
        TabFragment fragment = new TabFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_POSITION, position);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        int position = getArguments().getInt(ARG_POSITION);
        if (position == 1) {
            View v = inflater.inflate(R.layout.fragment_tu_lai, container, false);
            return v;
        } else {
            return inflater.inflate(R.layout.fragment_co_tai_xe, container, false);
        }
    }

    Button btn_confirm;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        selectedDateTime = Calendar.getInstance();
        edt_address = view.findViewById(R.id.edt_address);
        TextInputEditText edt_address1 = view.findViewById(R.id.edt_pickup_address);
        edtRentDate = view.findViewById(R.id.edt_rent_date);
        edtRentTime = view.findViewById(R.id.edt_rent_date1);
        btn_confirm = view.findViewById(R.id.btn_confirm);



        if (edtRentDate != null) {
            edtRentDate.setOnClickListener(v -> {
                view.getLayoutParams().height = dpToPx(586);
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                    getContext(),
                    (view1, year, month, dayOfMonth) -> {
                        String selectedDate = dayOfMonth + "/" + (month + 1) + "/" + year;
                        edtRentDate.setText(selectedDate);
                        
                        selectedDateTime.set(Calendar.YEAR, year);
                        selectedDateTime.set(Calendar.MONTH, month);
                        selectedDateTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        
                        Calendar currentTime = Calendar.getInstance();

                    },
                    Calendar.getInstance().get(Calendar.YEAR),
                    Calendar.getInstance().get(Calendar.MONTH),
                    Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
                );
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                datePickerDialog.show();
            });
        }

        if (edtRentTime != null) {
            edtRentTime.setOnClickListener(v -> {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        getContext(),
                        (view1, year, month, dayOfMonth) -> {
                            String selectedDate = dayOfMonth + "/" + (month + 1) + "/" + year;
                            edtRentTime.setText(selectedDate);

                            selectedDateTime.set(Calendar.YEAR, year);
                            selectedDateTime.set(Calendar.MONTH, month);
                            selectedDateTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                            Calendar currentTime = Calendar.getInstance();

                        },
                        Calendar.getInstance().get(Calendar.YEAR),
                        Calendar.getInstance().get(Calendar.MONTH),
                        Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
                );
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                datePickerDialog.show();
            });
        }
        if(edt_address!=null){
        String[] carSeat1 = getResources().getStringArray(R.array.dia_diem);

        // Dùng ArrayAdapter
        ArrayAdapter<String> adapter12 = new ArrayAdapter<>(
                getActivity(), android.R.layout.simple_dropdown_item_1line, carSeat1
        );
        edt_address.setAdapter(adapter12);

        // Hiển thị danh sách khi nhấn vào
        edt_address.setOnClickListener(v -> edt_address.showDropDown());
        btn_confirm.setOnClickListener(v->{
            String rentTime = edtRentTime.getText().toString().trim();
            String edtRentDatea = edtRentDate.getText().toString().trim();
            String edt_addressa = edt_address.getText().toString().trim();
            String edt_addressa1 = edt_address1.getText().toString().trim();

            if (!edt_addressa1.isEmpty() && edt_addressa1 != null&&!rentTime.isEmpty() && rentTime != null&&!edtRentDatea.isEmpty() && edtRentDatea != null&&!edt_addressa.isEmpty() && edt_addressa != null) {
                replaceFragment(new tim_xe(Id_account,edt_addressa1,edt_address.getText().toString(),edtRentTime.getText().toString(),edtRentDate.getText().toString()));

            } else {
                Toast.makeText(getContext(), "Giá trị không hợp lệ!", Toast.LENGTH_SHORT).show();
            }
               });
        }
    }
    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }
    private int dpToPx(int dp) {
        return (int) (dp * getResources().getDisplayMetrics().density);
    }
}
