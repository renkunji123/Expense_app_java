package com.cwtstudio.expensemanager.view.activities;

import android.os.Bundle;
import android.widget.ArrayAdapter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;

import com.cwtstudio.expensemanager.R;
import com.cwtstudio.expensemanager.model.Transaction;
import com.cwtstudio.expensemanager.repository.DatabaseDAO;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class EditTransactionActivity extends AppCompatActivity {

    private TextInputEditText edtAmount;
    private TextInputEditText edtNote;
    private AppCompatSpinner spinnerCategory;
    private MaterialButton btnSave;
    private DatabaseDAO dao;
    private int transactionId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_transaction);

        edtAmount = findViewById(R.id.edtAmount);
        edtNote = findViewById(R.id.edtNote);
        spinnerCategory = findViewById(R.id.spinnerCategory);
        btnSave = findViewById(R.id.btnSave);

        dao = new DatabaseDAO(this);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.type, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategory.setAdapter(adapter);

        // Get the transaction ID from the intent
        transactionId = getIntent().getIntExtra("TRANSACTION_ID", -1);
        if (transactionId != -1) {
            // Load transaction details and populate the fields
            Transaction transaction = dao.getTransactionById(transactionId);
            if (transaction != null) {
                edtAmount.setText(transaction.getAmount());
                edtNote.setText(transaction.getNote());
                // Set spinner category if applicable
                // For example, set spinner selection to the transaction type
            }
        }

        btnSave.setOnClickListener(v -> {
            // Get updated values
            String amount = edtAmount.getText().toString();
            String note = edtNote.getText().toString();
            String category = spinnerCategory.getSelectedItem().toString();

            // Update transaction
            Transaction updatedTransaction = new Transaction(amount, null, transactionId, note, category);
            dao.updateTransaction(updatedTransaction);

            // Finish and return result
            setResult(RESULT_OK);
            finish();
        });
    }
}
