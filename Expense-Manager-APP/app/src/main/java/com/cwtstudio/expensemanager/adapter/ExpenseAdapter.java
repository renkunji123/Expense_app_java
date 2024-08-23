package com.cwtstudio.expensemanager.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cwtstudio.expensemanager.R;
import com.cwtstudio.expensemanager.databinding.ItemTransBinding;
import com.cwtstudio.expensemanager.model.Transaction;
import com.cwtstudio.expensemanager.repository.DatabaseDAO;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.List;

public class ExpenseAdapter extends RecyclerView.Adapter<ExpenseAdapter.TransViewHolder> {
    private Context context;
    private DatabaseDAO dao;
    private List<Transaction> list;

    public ExpenseAdapter(Context context) {
        this.context = context;
        dao = new DatabaseDAO(context);
        list = dao.getAllExpenses(); // Initialize with expenses
    }

    @NonNull
    @Override
    public TransViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TransViewHolder(LayoutInflater.from(context).inflate(R.layout.item_trans, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TransViewHolder holder, int position) {
        Transaction transaction = list.get(position);
        holder.binding.chipType.setText(transaction.getType());
        holder.binding.txtAmount.setText(transaction.getAmount());
        holder.binding.txtNote.setText(transaction.getNote());
        holder.binding.txtDate.setText(transaction.getDate());
        holder.itemView.setOnLongClickListener(v -> {
            new MaterialAlertDialogBuilder(context)
                    .setTitle("Are you sure you want to delete?")
                    .setMessage("Delete?")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        dao.deleteTransaction(transaction.getId());
                        refresh();
                    })
                    .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss()).show();
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class TransViewHolder extends RecyclerView.ViewHolder {
        public ItemTransBinding binding;

        public TransViewHolder(View itemView) {
            super(itemView);
            binding = ItemTransBinding.bind(itemView);
        }
    }

    public void refresh() {
        list = dao.getAllExpenses(); // Corrected to fetch expenses
        notifyDataSetChanged();
    }
}
