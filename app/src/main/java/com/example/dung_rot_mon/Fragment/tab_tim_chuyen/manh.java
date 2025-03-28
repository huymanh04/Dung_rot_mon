package com.example.dung_rot_mon.Fragment.tab_tim_chuyen;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
 * Use the {@link manh#newInstance} factory method to
 * create an instance of this fragment.
 */
public class manh extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public manh() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment manh.
     */
    // TODO: Rename and change types and number of parameters
    public static manh newInstance(String param1, String param2) {
        manh fragment = new manh();
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
    private TextInputEditText edtAddress, edtRentDate, edtRentTime;
    private Calendar calendar;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_manh, container, false);
        edtAddress = view.findViewById(R.id.edt_address);
        edtRentDate = view.findViewById(R.id.edt_rent_date);
        edtRentTime = view.findViewById(R.id.edt_rent_time);
        view.findViewById(R.id.ok).setOnClickListener(v->{
            showTimePickerDialog();
        });
        edtAddress.getRootView().setOnTouchListener((v, event) -> {
            View touchedView = v.findViewById(event.getActionIndex());
            if (touchedView != null) {
                Toast.makeText(getContext(), "Touched View ID:"+ touchedView.getId(), Toast.LENGTH_SHORT).show();

                Log.e("DEBUG", "Touched View ID: " + touchedView.getId());
            }
            return false;
        });

        calendar = Calendar.getInstance();

        // Thiết lập sự kiện chọn địa chỉ
        edtAddress.setOnClickListener(v -> openGoogleMaps());

        // Thiết lập sự kiện chọn ngày
        edtRentDate.setOnClickListener(v -> showDatePickerDialog());

        // Thiết lập sự kiện chọn giờ
        edtRentTime.setOnClickListener(v -> showTimePickerDialog());
        return  view;
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
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        ).show();
    }

    private void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        edtRentDate.setText(dateFormat.format(calendar.getTime()));
    }

    private void showTimePickerDialog() {
        TimePickerDialog timePickerDialog = new TimePickerDialog(
                requireContext(),
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        calendar.set(Calendar.MINUTE, minute);

                        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
                        edtRentTime.setText(timeFormat.format(calendar.getTime()));
                    }
                },
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                true
        );
        timePickerDialog.show();
    }

    private void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        calendar.set(Calendar.MINUTE, minute);

        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
        edtRentTime.setText(timeFormat.format(calendar.getTime()));
    }
    private int dpToPx(int dp) {
        return (int) (dp * getResources().getDisplayMetrics().density);
    }
}