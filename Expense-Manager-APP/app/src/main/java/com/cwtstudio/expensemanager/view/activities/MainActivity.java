package com.cwtstudio.expensemanager.view.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.cwtstudio.expensemanager.R;
import com.cwtstudio.expensemanager.databinding.ActivityMainBinding;
import com.cwtstudio.expensemanager.view.fragments.BudgetFragment;

import com.cwtstudio.expensemanager.view.fragments.ExpenseFragment;
import com.cwtstudio.expensemanager.view.fragments.HomeFragment;
import com.cwtstudio.expensemanager.view.fragments.IncomeFragment;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity
{
    public static final int EDIT_TRANSACTION_REQUEST_CODE = 1;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);

        //by default load home fragment

        setFragment(new HomeFragment(), true);

        binding.bottomNav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener()
        {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item)
            {
                if (item.getItemId() == R.id.nav_home)
                {
                    setFragment(new HomeFragment(), false);
                } else if (item.getItemId() == R.id.nav_income)
                {
                    setFragment(new IncomeFragment(), false);
                } else if (item.getItemId() == R.id.nav_expense)
                {
                    setFragment(new ExpenseFragment(), false);
                } else if (item.getItemId() == R.id.nav_budget){
                    setFragment(new BudgetFragment(), false);
                }
                return true;
            }
        });

    }

    private void setFragment(Fragment fragment, boolean isAdd)
    {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if (isAdd)
        {
            transaction.add(binding.frameLayout.getId(), fragment);
        } else
        {
            transaction.replace(binding.frameLayout.getId(), fragment);
        }

        transaction.commit();


    }

}