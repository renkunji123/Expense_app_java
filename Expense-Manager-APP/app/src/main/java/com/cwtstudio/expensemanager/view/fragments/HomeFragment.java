package com.cwtstudio.expensemanager.view.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.anychart.AnyChart;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Pie;

import com.cwtstudio.expensemanager.adapter.TransAdapter;
import com.cwtstudio.expensemanager.databinding.FragmentHomeBinding;
import com.cwtstudio.expensemanager.model.Transaction;
import com.cwtstudio.expensemanager.repository.DatabaseDAO;
import com.cwtstudio.expensemanager.interfaces.OnDeleteListener;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private DatabaseDAO dao;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        Pie pie = AnyChart.pie();
        dao = new DatabaseDAO(requireContext());
        showData(pie);
        TransAdapter transAdapter = new TransAdapter(requireContext(), new OnDeleteListener() {
            @Override
            public void OnDelete() {
                showData(pie);
            }
        });

        binding.rvHome.setAdapter(transAdapter);
        binding.fabAdd.setOnClickListener(v -> {
            BottomSheet bottomSheet = new BottomSheet(() ->
            {
                transAdapter.refresh();
                showData(pie);
            });

            bottomSheet.show(getParentFragmentManager(), null);
        });

        return binding.getRoot();
    }

    private void showData(Pie p) {
        List<Transaction> income = new ArrayList<>();
        List<Transaction> expense = new ArrayList<>();
        List<Transaction> budgets = new ArrayList<>();
        long i = 0;
        long e = 0;
        long b = 0;

        for (Transaction t : dao.getAllTransactions()) {
            if (t.getType().equals("Income")) {
                income.add(t);
                i += Long.parseLong(t.getAmount());
            } else if (t.getType().equals("Expense")) {
                expense.add(t);
                e += Long.parseLong(t.getAmount());
            } else if (t.getType().equals("Budget")) {
                budgets.add(t);
                b += Long.parseLong(t.getAmount());
            }
        }

        List<DataEntry> data = new ArrayList<>();
        data.add(new ValueDataEntry("Income", i));
        data.add(new ValueDataEntry("Expense", e));
        p.data(data);
        binding.pieChart.setChart(p);
        binding.txtTotalExp.setText("Total Expense: " + e + " VND");
        binding.txtTotalInc.setText("Total Income: " + i + " VND");
        binding.txtTotalBgt.setText("Budget: " + b + " VND");
        if (e - i > b) {
            binding.txtTotalBgt.setText("Budget: " + b + " (You have gone over budget.)");
            binding.txtTotalBgt.setTextColor(Color.RED);
        } else {
            binding.txtTotalBgt.setTextColor(Color.BLACK);
        }
    }
}
