package com.example.blooddonationapp.ui.RequestDonation;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.blooddonationapp.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class RequestForm extends AppCompatActivity {
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    Intent intent;
    Spinner Spinner_BloodType1, Spinner_BloodType2;
    EditText reason, amount;
    Button send;
    CheckBox agree;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_form);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        String[] arr_BloodType_data1 = {"A", "B", "AB", "O"};
        Spinner_BloodType1 = (Spinner) findViewById(R.id.BloodType1);
        ArrayAdapter<String> Adapter_Data = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, arr_BloodType_data1);
        Adapter_Data.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner_BloodType1.setAdapter(Adapter_Data);
        String[] arr_BloodType_data2 = {"+", "-"};
        Spinner_BloodType2 = (Spinner) findViewById(R.id.BloodType2);
        ArrayAdapter<String> data = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, arr_BloodType_data2);
        data.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner_BloodType2.setAdapter(data);
        reason = findViewById(R.id.reason);
        amount = findViewById(R.id.amount);
        send = findViewById(R.id.button);
        agree = findViewById(R.id.checkBox);
        intent = getIntent();

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (agree.isChecked() && !amount.getText().toString().isEmpty() && !reason.getText().toString().isEmpty()) {
                    String type = Spinner_BloodType1.getSelectedItem().toString() + Spinner_BloodType2.getSelectedItem().toString();
                    Map<String, Object> data = new HashMap<>();
                    data.put("amount", amount.getText().toString());
                    data.put("reason", reason.getText().toString());
                    data.put("status", "pending");
                    data.put("user_id", mAuth.getCurrentUser().getUid().toString());
                    data.put("org_id", intent.getStringExtra("org_id"));
                    data.put("hotspot_id", intent.getStringExtra("hotspot_id"));
                    data.put("type", type);

                    db.collection("requests")
                            .add(data)
                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    Toast.makeText(RequestForm.this, "Request Sent", Toast.LENGTH_LONG).show();
                                    clear();
                                    finish();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(RequestForm.this, "ERROR TRY AGAIN", Toast.LENGTH_SHORT).show();
                                    clear();

                                }
                            });
                } else {

                    Toast.makeText(RequestForm.this, "Please Complete the form", Toast.LENGTH_SHORT).show();

                }
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void clear() {
        reason.setText("");
        amount.setText("");

    }
}