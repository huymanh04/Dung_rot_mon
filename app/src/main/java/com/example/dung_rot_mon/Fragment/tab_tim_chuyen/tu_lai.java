package com.example.dung_rot_mon.Fragment.tab_tim_chuyen;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.dung_rot_mon.R;
import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link tu_lai#newInstance} factory method to
 * create an instance of this fragment.
 */
public class tu_lai extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Calendar selectedDateTime;
    private TextInputEditText edtAddress, edtRentDate, edtRentTime;

    public tu_lai() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment tu_lai.
     */
    // TODO: Rename and change types and number of parameters
    public static tu_lai newInstance(String param1, String param2) {
        tu_lai fragment = new tu_lai();
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
        View view = inflater.inflate(R.layout.fragment_tu_lai, container, false);

        edtAddress = view.findViewById(R.id.edt_address);
        edtRentDate = view.findViewById(R.id.edt_rent_date);
        edtRentTime = view.findViewById(R.id.edt_rent_time);

        selectedDateTime = Calendar.getInstance();

        // Thiết lập sự kiện chọn địa chỉ
        edtAddress.setOnClickListener(v -> openGoogleMaps());

        // Thiết lập sự kiện chọn ngày
        edtRentDate.setOnClickListener(v -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(
                getContext(),
                (view1, year, month, dayOfMonth) -> {
                    String selectedDate = dayOfMonth + "/" + (month + 1) + "/" + year;
                    edtRentDate.setText(selectedDate);
                    
                    selectedDateTime.set(Calendar.YEAR, year);
                    selectedDateTime.set(Calendar.MONTH, month);
                    selectedDateTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    
                    Calendar currentTime = Calendar.getInstance();
                    if (selectedDateTime.before(currentTime)) {
                        Toast.makeText(getContext(), "Vui lòng chọn thời gian trong tương lai", Toast.LENGTH_SHORT).show();
                        edtRentDate.setText("");
                        return;
                    }
                },
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
            );
            datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
            datePickerDialog.show();
        });

        // Thiết lập sự kiện chọn giờ
        edtRentTime.setOnClickListener(v -> {
            TimePickerDialog timePickerDialog = new TimePickerDialog(
                getContext(),
                (view12, hourOfDay, minute) -> {
                    String selectedTime = String.format(Locale.getDefault(), "%02d:%02d", hourOfDay, minute);
                    edtRentTime.setText(selectedTime);
                    
                    selectedDateTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
                    selectedDateTime.set(Calendar.MINUTE, minute);

                    // Kiểm tra nếu thời gian đã chọn là trong quá khứ
                    Calendar currentTime = Calendar.getInstance();
                    if (selectedDateTime.before(currentTime)) {
                        Toast.makeText(getContext(), "Vui lòng chọn thời gian trong tương lai", Toast.LENGTH_SHORT).show();
                        edtRentTime.setText("");
                    }
                },
                Calendar.getInstance().get(Calendar.HOUR_OF_DAY),
                Calendar.getInstance().get(Calendar.MINUTE),
                true
            );
            timePickerDialog.show();
        });

        Button btnConfirm = view.findViewById(R.id.btn_confirm);
        btnConfirm.setOnClickListener(v -> {
            String date = edtRentDate.getText().toString();
            String time = edtRentTime.getText().toString();
            
            if (date.isEmpty() || time.isEmpty()) {
                Toast.makeText(getContext(), "Vui lòng chọn đầy đủ ngày và giờ thuê", Toast.LENGTH_SHORT).show();
                return;
            }
            
            // Xử lý logic khi người dùng xác nhận thời gian thuê
            Toast.makeText(getContext(), "Đã chọn: " + date + " " + time, Toast.LENGTH_SHORT).show();
        });

        return view;
    }

    private void openGoogleMaps() {
        // TODO: Implement Google Maps intent or fragment
        // Ví dụ:
        // Intent intent = new Intent(this, MapsActivity.class);
        // startActivityForResult(intent, MAP_REQUEST_CODE);
    }

    private void showDatePickerDialog() {
        new DatePickerDialog(
                getActivity(),
                this::onDateSet,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        ).show();
    }

    private void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        selectedDateTime.set(Calendar.YEAR, year);
        selectedDateTime.set(Calendar.MONTH, month);
        selectedDateTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        edtRentDate.setText(dateFormat.format(selectedDateTime.getTime()));
    }

    private void showTimePickerDialog() {
        TimePickerDialog timePickerDialog = new TimePickerDialog(
                requireContext(), // Đảm bảo Fragment đã được gắn vào Activity
                (view, hourOfDay, minute) -> {
                    selectedDateTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
                    selectedDateTime.set(Calendar.MINUTE, minute);

                    SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
                    edtRentTime.setText(timeFormat.format(selectedDateTime.getTime()));
                },
                selectedDateTime.get(Calendar.HOUR_OF_DAY),
                selectedDateTime.get(Calendar.MINUTE),
                true
        );
        timePickerDialog.show();
    }

    private void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        selectedDateTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
        selectedDateTime.set(Calendar.MINUTE, minute);

        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
        edtRentTime.setText(timeFormat.format(selectedDateTime.getTime()));
    }

    private int dpToPx(int dp) {
        return (int) (dp * getResources().getDisplayMetrics().density);
    }
}