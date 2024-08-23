package com.cwtstudio.expensemanager.view.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.cwtstudio.expensemanager.R;
import com.cwtstudio.expensemanager.databinding.BottomSheetBinding;
import com.cwtstudio.expensemanager.interfaces.OnDeleteListener;
import com.cwtstudio.expensemanager.interfaces.OnDismissListener;
import com.cwtstudio.expensemanager.model.Transaction;
import com.cwtstudio.expensemanager.repository.DatabaseDAO;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class BottomSheet extends BottomSheetDialogFragment
{
    private BottomSheetBinding binding;
    private DatabaseDAO dao;
    private OnDismissListener listener;

    public BottomSheet(OnDismissListener listener)
    {
        this.listener = listener;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        binding = BottomSheetBinding.inflate(inflater, container, false);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.type));
        binding.spinnerCategory.setAdapter(adapter);
        binding.spinnerCategory.setSelection(0);
        dao = new DatabaseDAO(requireContext());
        binding.btnAdd.setOnClickListener(v ->
        {
            //validate
            if (isValid())
            {
                String note = binding.edtNote.getText().toString();
                String  amount = binding.edtAmount.getText().toString();
                Calendar cal = Calendar.getInstance();
                Date date=cal.getTime();
                DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
                String formattedDate=dateFormat.format(date);
                //insert into database
                dao.insertTransaction(new Transaction(amount,formattedDate,note,binding.spinnerCategory.getSelectedItem().toString()));
                Toast.makeText(requireContext(), "Added successfully", Toast.LENGTH_SHORT).show();

                //close the dialog
                listener.OnAdded();
                dismiss();

            } else
            {
                Toast.makeText(requireContext(), "Please fill all details.", Toast.LENGTH_SHORT).show();
            }
        });
        return binding.getRoot();
    }

    private boolean isValid()
    {
        return !binding.edtNote.getText().toString().isEmpty()
                && !binding.edtAmount.getText().toString().isEmpty();
    }
}
