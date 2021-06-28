package com.example.blooddonationapp.ui.Settings;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.blooddonationapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class SettingsFragment extends Fragment {

    private FirebaseAuth mAuth;
    CheckBox checkBox1, checkBox2, checkBox3, checkBox4, checkBox5, checkBox6, checkBox7, checkBox8, checkBox9, checkBox10;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    EditText textbox_Email, textbox_Name, textbox_Age, textbox_National, textbox_Address;
    Spinner Spinner_BloodType1, Spinner_BloodType2;
    RadioGroup Group1;
    RadioButton male, female;
    Button update;
    TextView password, email, setname;
    private String m_Text = "";


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_settings, container, false);


        dataBinding(root);
        ResetPassword(root);
        ReAuth(root);
        ChangeName(root);
        updateInfo(root);
        return root;
    }

    void ReAuth(View root) {
        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(root.getContext());
                builder.setTitle("Write Your Password To Change Email");

// Set up the input
                final EditText input = new EditText(root.getContext());
// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
                input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                builder.setView(input);

// Set up the buttons
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        if (!input.getText().toString().isEmpty()) {

                            AuthCredential credential = EmailAuthProvider
                                    .getCredential(mAuth.getCurrentUser().getEmail().toString(), input.getText().toString());

                            mAuth.getCurrentUser().reauthenticate(credential)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            Log.d(TAG, "User re-authenticated.");
                                            ResetEmail(root);
                                        }
                                    });

                            m_Text = input.getText().toString();
                        } else {

                            Toast.makeText(root.getContext(), "Please Enter Your Password", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();
            }

        });

    }

    void ResetEmail(View root) {

        if (!textbox_Email.getText().toString().isEmpty()) {

            mAuth.getCurrentUser().updateEmail(textbox_Email.getText().toString()).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    mAuth.getCurrentUser().sendEmailVerification()
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(root.getContext(), "Email Changed, please verify the new email", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull @NotNull Exception e) {
                    Toast.makeText(root.getContext(), "ERROR: " + e, Toast.LENGTH_SHORT).show();

                }
            });


        } else {

            Toast.makeText(root.getContext(), "Please Enter a Valid Email", Toast.LENGTH_SHORT).show();


        }


    }


    void ResetPassword(View root) {

        password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mAuth.getCurrentUser().isEmailVerified()) {


                    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which) {
                                case DialogInterface.BUTTON_POSITIVE:
                                    //Yes button clicked


                                    mAuth.sendPasswordResetEmail(mAuth.getCurrentUser().getEmail().toString())
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                        Toast.makeText(root.getContext(), "Password Reset Email Sent, Check Your Email", Toast.LENGTH_SHORT).show();

                                                    }
                                                }

                                            });


                                    break;

                                case DialogInterface.BUTTON_NEGATIVE:
                                    //No button clicked
                                    break;
                            }
                        }
                    };

                    AlertDialog.Builder builder = new AlertDialog.Builder(root.getContext());
                    builder.setMessage("Are you sure?").setPositiveButton("Yes", dialogClickListener)
                            .setNegativeButton("No", dialogClickListener).show();


                } else {

                    Toast.makeText(root.getContext(), "Please Verify Your Email First", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    void ChangeName(View root) {

        setname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!textbox_Name.getText().toString().isEmpty()) {

                    Map<String, Object> Name = new HashMap<>();
                    Name.put("name", textbox_Name.getText().toString());

                    db.collection("users").document(mAuth.getCurrentUser().getUid().toString()).update(Name)

                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(root.getContext(), "Name Updated.", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(root.getContext(), "Error Try again", Toast.LENGTH_SHORT).show();
                                }
                            });

                } else {
                    Toast.makeText(root.getContext(), "Please Enter The New Name", Toast.LENGTH_SHORT).show();
                }


            }
        });


    }


    boolean NotEmpty() {

        if (textbox_Address.getText().toString().isEmpty() || textbox_Age.getText().toString().isEmpty() || textbox_National.getText().toString().isEmpty()
        ) {
            return false;
        } else {
            return true;
        }
    }

    void updateInfo(View root) {

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (NotEmpty()) {

                    ;

                    db.collection("users").document(mAuth.getCurrentUser().getUid().toString()).update(data_map())

                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(root.getContext(), "Name Updated.", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(root.getContext(), "Error Try again", Toast.LENGTH_SHORT).show();
                                }
                            });

                } else {
                    Toast.makeText(root.getContext(), "Please Enter The New Name", Toast.LENGTH_SHORT).show();
                }


            }
        });


    }

    Map<String, Object> data_map() {
        Map<String, Object> UserData = new HashMap<>();


        UserData.put("age", textbox_Age.getText().toString());
        UserData.put("national_id", textbox_National.getText().toString());
        UserData.put("address", textbox_Address.getText().toString());
        UserData.put("blood_type", Spinner_BloodType1.getSelectedItem().toString() + Spinner_BloodType2.getSelectedItem().toString());

        if (male.isChecked()) {
            UserData.put("gender", "male");
        } else {
            UserData.put("gender", "female");
        }
        if (checkBox1.isChecked() || checkBox2.isChecked() || checkBox3.isChecked() || checkBox4.isChecked() ||
                checkBox5.isChecked() || checkBox6.isChecked() || checkBox7.isChecked() || checkBox8.isChecked() || checkBox9.isChecked() || checkBox10.isChecked()) {
            UserData.put("can_donate", false);

        } else {
            UserData.put("can_donate", true);
        }
        return UserData;
    }

    void dataBinding(View root) {
        checkBox1 = root.findViewById(R.id.checkBox);
        checkBox2 = root.findViewById(R.id.checkBox2);
        checkBox3 = root.findViewById(R.id.checkBox3);
        checkBox4 = root.findViewById(R.id.checkBox4);
        checkBox5 = root.findViewById(R.id.checkBox5);
        checkBox6 = root.findViewById(R.id.checkBox6);
        checkBox7 = root.findViewById(R.id.checkBox7);
        checkBox8 = root.findViewById(R.id.checkBox8);
        checkBox9 = root.findViewById(R.id.checkBox9);
        checkBox10 = root.findViewById(R.id.checkBox10);
        mAuth = FirebaseAuth.getInstance();
        update = root.findViewById(R.id.update);
        textbox_Email = root.findViewById(R.id.TextEmail);
        password = root.findViewById(R.id.set_password);
        email = root.findViewById(R.id.set_email);
        textbox_Name = root.findViewById(R.id.TextName);
        textbox_Age = root.findViewById(R.id.numberAge);
        textbox_National = root.findViewById(R.id.TextNational);
        textbox_Address = root.findViewById(R.id.TextAddress);
        Group1 = root.findViewById(R.id.Group1);
        male = root.findViewById(R.id.radioButton);
        female = root.findViewById(R.id.radioButton1);
        setname = root.findViewById(R.id.set_name);
        String arr_BloodType_data1[] = {"A", "B", "AB", "O"};
        Spinner_BloodType1 = (Spinner) root.findViewById(R.id.BloodType1);
        ArrayAdapter<String> Adapter_Data = new ArrayAdapter<String>(root.getContext(),
                android.R.layout.simple_list_item_1, arr_BloodType_data1);
        Adapter_Data.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner_BloodType1.setAdapter(Adapter_Data);
        String arr_BloodType_data2[] = {"+", "-"};
        Spinner_BloodType2 = (Spinner) root.findViewById(R.id.BloodType2);
        ArrayAdapter<String> data = new ArrayAdapter<String>(root.getContext(),
                android.R.layout.simple_list_item_1, arr_BloodType_data2);
        data.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner_BloodType2.setAdapter(data);
    }
}