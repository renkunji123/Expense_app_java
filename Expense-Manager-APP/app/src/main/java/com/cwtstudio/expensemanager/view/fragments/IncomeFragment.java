package com.cwtstudio.expensemanager.view.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.cwtstudio.expensemanager.adapter.IncomeAdapter;
import com.cwtstudio.expensemanager.databinding.FragmentIncomeBinding;


public class IncomeFragment extends Fragment
{
    private FragmentIncomeBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        binding = FragmentIncomeBinding.inflate(inflater, container, false);
        IncomeAdapter adapter = new IncomeAdapter(requireContext());
        binding.rvIncome.setAdapter(adapter);


        return binding.getRoot();
    }
}
