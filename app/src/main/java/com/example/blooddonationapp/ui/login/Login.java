package com.example.blooddonationapp.ui.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.blooddonationapp.FirebaseAuthSingleton;
import com.example.blooddonationapp.MainActivity;
import com.example.blooddonationapp.R;
import com.example.blooddonationapp.ui.SignUp.SignUp;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class Login extends AppCompatActivity {
    TextView new_user;
    Button Done_login;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    EditText email, password;
    private final FirebaseAuthSingleton mAuth = FirebaseAuthSingleton.INSTANCE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        new_user = findViewById(R.id.new_user);
        Done_login = findViewById(R.id.Done_login);
        email = findViewById(R.id.TextEmail);

        password = findViewById(R.id.set_password);
        Done_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (email.getText().toString().isEmpty() || password.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please Enter Email And Password", Toast.LENGTH_SHORT).show();


                } else {

                    tryLogin(v);

                }
            }
        });

        new_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent in = new Intent(Login.this, SignUp.class);
                startActivity(in);

            }
        });
    }

    private void tryLogin(View v) {

        mAuth.getInstance().signInWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                .addOnCompleteListener((Activity) v.getContext(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information

                            checkUserType();


                        } else {
                            // If sign in fails, display a message to the user.


                            Toast.makeText(v.getContext(), "Authentication failed." + task.getException().toString(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void checkUserType() {

        DocumentReference docRef = db.collection("users").document(mAuth.getInstance().getUid().toString());
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        if (document.get("user_type").toString().equals("user")) {


                            Intent in = new Intent(Login.this, MainActivity.class);
                            startActivity(in);
                            finish();

                        } else {
                            Toast.makeText(getApplicationContext(), "User type not supported", Toast.LENGTH_SHORT).show();
                            mAuth.getInstance().signOut();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "User type not supported", Toast.LENGTH_SHORT).show();
                        mAuth.getInstance().signOut();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Please try Again" + task.getException(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();
        moveTaskToBack(true);
        finish();
        System.runFinalizersOnExit(true);
    }

}