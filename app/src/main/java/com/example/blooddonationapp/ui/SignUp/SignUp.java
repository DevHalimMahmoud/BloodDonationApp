package com.example.blooddonationapp.ui.SignUp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import com.example.blooddonationapp.R;
import com.example.blooddonationapp.ui.login.Login;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class SignUp extends AppCompatActivity {
    private FirebaseAuth mAuth;
    CheckBox checkBox1, checkBox2, checkBox3, checkBox4, checkBox5, checkBox6, checkBox7, checkBox8, checkBox9, checkBox10;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    EditText textbox_Email, textbox_Password, textbox_Name, textbox_Age, textbox_National, textbox_Address;
    Spinner Spinner_BloodType1, Spinner_BloodType2;
    RadioGroup Group1;
    RadioButton male, female;
    Button sign_up;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        dataBinding();
        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (NotEmpty()) {
                    mAuth.createUserWithEmailAndPassword(textbox_Email.getText().toString(), textbox_Password.getText().toString())
                            .addOnCompleteListener((Activity) v.getContext(), new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {

                                        String id = mAuth.getCurrentUser().getUid();

                                        db.collection("users").document(id)
                                                .set(data_map())
                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {
                                                        Toast.makeText(getApplicationContext(), "User Created Successfully", Toast.LENGTH_LONG).show();

                                                        Intent in = new Intent(SignUp.this, Login.class);
                                                        startActivity(in);
                                                    }
                                                })
                                                .addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        Toast.makeText(getApplicationContext(), "Please try Again", Toast.LENGTH_SHORT).show();


                                                        mAuth.getCurrentUser().delete();

                                                    }
                                                });

                                    } else {
                                        Toast.makeText(getApplicationContext(), "Please try Again", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                } else {
                    Toast.makeText(getApplicationContext(), "Something Went Wrong, Please Enter All Your Data", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    boolean NotEmpty() {

        if (textbox_Name.getText().toString().equals("") || textbox_Address.getText().toString().equals("") || textbox_Age.getText().toString().equals("") || textbox_National.getText().toString().equals("")
        ) {
            return false;
        } else {
            return true;
        }
    }


    Map<String, Object> data_map() {
        Map<String, Object> city = new HashMap<>();
        city.put("user_type", "user");
        city.put("name", textbox_Name.getText().toString());
        city.put("age", textbox_Age.getText().toString());
        city.put("national_id", textbox_National.getText().toString());
        city.put("address", textbox_Address.getText().toString());
        city.put("blood_type", Spinner_BloodType1.getSelectedItem().toString() + Spinner_BloodType2.getSelectedItem().toString());

        if (male.isChecked()) {
            city.put("gender", "male");
        } else {
            city.put("gender", "female");
        }
        if (checkBox1.isChecked() || checkBox2.isChecked() || checkBox3.isChecked() || checkBox4.isChecked() ||
                checkBox5.isChecked() || checkBox6.isChecked() || checkBox7.isChecked() || checkBox8.isChecked() || checkBox9.isChecked() || checkBox10.isChecked()) {
            city.put("can_donate", false);

        } else {
            city.put("can_donate", true);
        }
        return city;
    }

    void dataBinding() {
        checkBox1 = findViewById(R.id.checkBox);
        checkBox2 = findViewById(R.id.checkBox2);
        checkBox3 = findViewById(R.id.checkBox3);
        checkBox4 = findViewById(R.id.checkBox4);
        checkBox5 = findViewById(R.id.checkBox5);
        checkBox6 = findViewById(R.id.checkBox6);
        checkBox7 = findViewById(R.id.checkBox7);
        checkBox8 = findViewById(R.id.checkBox8);
        checkBox9 = findViewById(R.id.checkBox9);
        checkBox10 = findViewById(R.id.checkBox10);
        mAuth = FirebaseAuth.getInstance();
        sign_up = findViewById(R.id.sign_up);
        textbox_Email = (EditText) findViewById(R.id.TextEmail);
        textbox_Password = (EditText) findViewById(R.id.TextPassword);
        textbox_Name = (EditText) findViewById(R.id.TextName);
        textbox_Age = (EditText) findViewById(R.id.numberAge);
        textbox_National = (EditText) findViewById(R.id.TextNational);
        textbox_Address = (EditText) findViewById(R.id.TextAddress);
        Group1 = (RadioGroup) findViewById(R.id.Group1);
        male = (RadioButton) findViewById(R.id.radioButton);
        female = (RadioButton) findViewById(R.id.radioButton1);

        String arr_BloodType_data1[] = {"A", "B", "AB", "O"};
        Spinner_BloodType1 = (Spinner) findViewById(R.id.BloodType1);
        ArrayAdapter<String> Adapter_Data = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, arr_BloodType_data1);
        Adapter_Data.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner_BloodType1.setAdapter(Adapter_Data);
        String arr_BloodType_data2[] = {"+", "-"};
        Spinner_BloodType2 = (Spinner) findViewById(R.id.BloodType2);
        ArrayAdapter<String> data = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, arr_BloodType_data2);
        data.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner_BloodType2.setAdapter(data);
    }
}