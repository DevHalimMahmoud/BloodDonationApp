package com.example.blooddonationapp.ui.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;

import com.example.blooddonationapp.R;
import com.example.blooddonationapp.ui.signUp.SignUp;
import com.example.blooddonationapp.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Document;

import io.perfmark.Tag;

public class Login extends AppCompatActivity {
    TextView Add_new_user;
    Button Done_login;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    EditText email, password;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
        Add_new_user = findViewById(R.id.Add_new_user);
        Done_login = findViewById(R.id.Done_login);
        email = findViewById(R.id.TextEmail);
        password = findViewById(R.id.TextPassword);
        Done_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (email.getText().toString().isEmpty() || password.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please Enter Email And Password", Toast.LENGTH_SHORT).show();


                } else {
                    mAuth.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                            .addOnCompleteListener((Activity) v.getContext(), new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        DocumentReference docRef = db.collection("users").document(mAuth.getUid().toString());
                                        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                if (task.isSuccessful()) {
                                                    DocumentSnapshot document = task.getResult();
                                                    if (document.exists()) {
                                                        if (document.get("user_type").toString().equals("user")) {
                                                            FirebaseUser user = mAuth.getCurrentUser();

                                                            Intent in = new Intent(Login.this, MainActivity.class);
                                                            startActivity(in);

                                                        } else {
                                                            Toast.makeText(getApplicationContext(), "User type not supported", Toast.LENGTH_SHORT).show();
                                                            mAuth.signOut();
                                                        }
                                                    } else {
                                                        Toast.makeText(getApplicationContext(), "User type not supported", Toast.LENGTH_SHORT).show();
                                                        mAuth.signOut();
                                                    }
                                                } else {
                                                    Toast.makeText(getApplicationContext(), "Please try Again" + task.getException().toString(), Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });

                                    } else {
                                        // If sign in fails, display a message to the user.


                                        Toast.makeText((Activity) v.getContext(), "Authentication failed." + task.getException().toString(),
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });
        Add_new_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent in = new Intent(Login.this, SignUp.class);
                startActivity(in);

            }
        });
    }

//    @Override
//    public void onStart() {
//        super.onStart();
//        // Check if user is signed in (non-null) and update UI accordingly.
//        FirebaseUser currentUser = mAuth.getCurrentUser();
//        if(currentUser != null){
//            Intent i = new Intent(getApplicationContext(), MainActivity.class);
//            startActivity(i);
//            finish();
//        }
//    }
}