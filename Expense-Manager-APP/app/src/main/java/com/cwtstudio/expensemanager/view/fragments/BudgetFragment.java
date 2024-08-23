package com.cwtstudio.expensemanager.view.fragments;

import android.graphics.drawable.AdaptiveIconDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.cwtstudio.expensemanager.adapter.BudgetAdapter;
import com.cwtstudio.expensemanager.adapter.BudgetAdapter;
import com.cwtstudio.expensemanager.databinding.FragmentBudgetBinding;


public class BudgetFragment extends Fragment {


    private FragmentBudgetBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        binding = FragmentBudgetBinding.inflate(inflater, container, false);
        BudgetAdapter adapter = new BudgetAdapter(requireContext());
        binding.rvBudget.setAdapter(adapter);
        return binding.getRoot();


    }


}
