package com.cwtstudio.expensemanager.adapter;

import static com.cwtstudio.expensemanager.view.activities.MainActivity.EDIT_TRANSACTION_REQUEST_CODE;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cwtstudio.expensemanager.R;
import com.cwtstudio.expensemanager.databinding.ItemTransBinding;
import com.cwtstudio.expensemanager.interfaces.OnDeleteListener;
import com.cwtstudio.expensemanager.model.Transaction;
import com.cwtstudio.expensemanager.repository.DatabaseDAO;
import com.cwtstudio.expensemanager.view.activities.EditTransactionActivity;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.List;

public class TransAdapter extends RecyclerView.Adapter<TransAdapter.TransViewHolder>
{
    private Context context;
    private DatabaseDAO dao;
    private List<Transaction> list;
    private OnDeleteListener listener;

    public TransAdapter(Context context, OnDeleteListener listener)
    {
        this.context = context;
        dao = new DatabaseDAO(context);
        list = dao.getAllTransactions();
        this.listener = listener;
    }

    @NonNull
    @Override
    public TransViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        return new TransViewHolder(LayoutInflater.from(context).inflate(R.layout.item_trans, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TransViewHolder holder, int position) {
        Transaction transaction = list.get(position);
        holder.binding.chipType.setText(transaction.getType());
        holder.binding.txtAmount.setText(transaction.getAmount());
        holder.binding.txtNote.setText(transaction.getNote());
        holder.binding.txtDate.setText(transaction.getDate());

        holder.binding.btnEdit.setOnClickListener(v -> {
            Intent intent = new Intent(context, EditTransactionActivity.class);
            intent.putExtra("TRANSACTION_ID", transaction.getId()); // Pass the ID to the EditActivity
            ((Activity) context).startActivityForResult(intent, EDIT_TRANSACTION_REQUEST_CODE);
        });

        // Set up the click listener for the delete button
        holder.binding.button.setOnClickListener(v -> {
            new MaterialAlertDialogBuilder(context)
                    .setTitle("Are you sure you want to delete?")
                    .setMessage("This action cannot be undone.")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        dao.deleteTransaction(transaction.getId());
                        listener.OnDelete();
                        refresh();
                    })
                    .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss()).show();
        });

    }

    @Override
    public int getItemCount()
    {
        return list.size();
    }

    public static class TransViewHolder extends RecyclerView.ViewHolder
    {
        public ItemTransBinding binding;

        public TransViewHolder(View itemView)
        {
            super(itemView);
            binding = ItemTransBinding.bind(itemView);
        }
    }

    public void refresh()
    {
        list = dao.getAllTransactions();
        notifyDataSetChanged();
    }


}