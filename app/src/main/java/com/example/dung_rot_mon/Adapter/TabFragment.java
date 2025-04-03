package com.example.dung_rot_mon.Adapter;

import static com.example.dung_rot_mon.MainActivity.Id_account;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
    private AutoCompleteTextView edt_address,edt_address1a;
    private TextInputEditText edtRentTime;
    private TextInputEditText edtRentTimea;
    EditText edt_pickups_time;

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

    Button btn_confirm,btn_confirm1;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        selectedDateTime = Calendar.getInstance();
        edt_address = view.findViewById(R.id.edt_address);
        edt_address1a = view.findViewById(R.id.edt_address1);
        TextInputEditText edt_address1 = view.findViewById(R.id.edt_pickup_address);
        TextInputEditText edt_address12 = view.findViewById(R.id.edt_pickup_addresas);
        edtRentDate = view.findViewById(R.id.edt_rent_date);
        edtRentTime = view.findViewById(R.id.edt_rent_date1);
        btn_confirm = view.findViewById(R.id.btn_confirm);
        edt_pickups_time = view.findViewById(R.id.edt_pickups_time);
        btn_confirm1 = view.findViewById(R.id.btn_confirm1);

if(edt_pickups_time!=null){
    edt_pickups_time.setOnClickListener(v->{
        showDatePicker(); // Chọn ngày trước

    });

}

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
               });}
        final String[] selectedText = {""};
        if(btn_confirm1!=null){
            String[] carSeat11 = getResources().getStringArray(R.array.dia_diem);

            // Dùng ArrayAdapter
            ArrayAdapter<String> adapter121 = new ArrayAdapter<>(
                    getActivity(), android.R.layout.simple_dropdown_item_1line, carSeat11
            );
            edt_address1a.setAdapter(adapter121);

            // Hiển thị danh sách khi nhấn vào
            edt_address1a.setOnClickListener(v -> edt_address1a.showDropDown());

            RadioGroup radioGroup = view.findViewById(R.id.rg_journey_type);
            if(radioGroup!=null){
                radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
                    RadioButton radioButton = view.findViewById(checkedId);
                    if (radioButton != null) {
                        selectedText[0] = radioButton.getText().toString();

                    }
                });}
        btn_confirm1.setOnClickListener(v->{
            String rentTime = edt_pickups_time.getText().toString().trim();
            String edtRentDatea = edt_address1a.getText().toString().trim();
            String edt_addressa = selectedText[0];
            String edt_addressa1 = edt_address12.getText().toString().trim();

            if (!edt_addressa1.isEmpty() && edt_addressa1 != null&&!rentTime.isEmpty() && rentTime != null&&!edtRentDatea.isEmpty() && edtRentDatea != null&&!edt_addressa.isEmpty() && edt_addressa != null) {
                replaceFragment(new tim_xe(Id_account,edt_addressa1,edtRentDatea,rentTime,selectedText[0]));

            } else {
                Toast.makeText(getContext(), "Giá trị không hợp lệ!", Toast.LENGTH_SHORT).show();
            }
        });}
        }

    private void showDatePicker() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                (view, selectedYear, selectedMonth, selectedDay) -> {
                    String selectedDate = selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear;
                    edt_pickups_time.setText(selectedDate); // Gán ngày vào EditText

                    // Sau khi chọn ngày, mở hộp thoại chọn giờ
                    showTimePicker();
                }, year, month, day);

        datePickerDialog.show();
    }

    private void showTimePicker() {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(),
                (view, selectedHour, selectedMinute) -> {
                    String selectedTime = selectedHour + ":" + String.format("%02d", selectedMinute);
                    edt_pickups_time.setText(edt_pickups_time.getText()+" "+selectedTime); // Gán giờ vào EditText
                }, hour, minute, true); // `true` để hiển thị 24h

        timePickerDialog.show();
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
