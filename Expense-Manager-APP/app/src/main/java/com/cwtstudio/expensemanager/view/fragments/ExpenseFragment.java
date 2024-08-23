package com.cwtstudio.expensemanager.view.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.cwtstudio.expensemanager.adapter.BudgetAdapter;
import com.cwtstudio.expensemanager.adapter.BudgetAdapter;
import com.cwtstudio.expensemanager.adapter.ExpenseAdapter;
import com.cwtstudio.expensemanager.databinding.FragmentExpenseBinding;




public class ExpenseFragment extends Fragment
{
    private FragmentExpenseBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        binding = FragmentExpenseBinding.inflate(inflater, container, false);
        ExpenseAdapter adapter = new ExpenseAdapter(requireContext());
        binding.rvExpense.setAdapter(adapter);


        return binding.getRoot();
    }
}
