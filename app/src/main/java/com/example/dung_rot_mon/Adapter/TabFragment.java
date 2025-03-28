package com.example.dung_rot_mon.Adapter;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.dung_rot_mon.R;
import com.google.android.material.textfield.TextInputEditText;
import java.util.Calendar;
import java.util.Locale;

public class TabFragment extends Fragment {

    private static final String ARG_POSITION = "position";
    private Calendar selectedDateTime;
    private TextInputEditText edtRentDate;
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
            return inflater.inflate(R.layout.fragment_tu_lai, container, false);
        } else {
            return inflater.inflate(R.layout.fragment_co_tai_xe, container, false);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        
        selectedDateTime = Calendar.getInstance();
        edtRentDate = view.findViewById(R.id.edt_rent_date);
        edtRentTime = view.findViewById(R.id.edt_rent_time);

        if (edtRentDate != null) {
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
                        }
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
                TimePickerDialog timePickerDialog = new TimePickerDialog(
                    getContext(),
                    (view12, hourOfDay, minute) -> {
                        String selectedTime = String.format(Locale.getDefault(), "%02d:%02d", hourOfDay, minute);
                        edtRentTime.setText(selectedTime);
                        
                        selectedDateTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        selectedDateTime.set(Calendar.MINUTE, minute);

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
        }
    }
}
