package com.example.blooddonationapp.Fragments

import android.app.AlertDialog
import com.example.blooddonationapp.Utils.FirebaseAuthSingleton
import com.google.firebase.firestore.FirebaseFirestore
import android.view.LayoutInflater
import android.view.ViewGroup
import android.os.Bundle
import com.example.blooddonationapp.R
import android.text.InputType
import com.google.firebase.auth.EmailAuthProvider
import android.content.ContentValues
import android.content.DialogInterface
import android.util.Log
import android.view.View
import android.widget.*
import androidx.fragment.app.Fragment
import java.util.HashMap

class SettingsFragment : Fragment() {
    private val mAuth = FirebaseAuthSingleton
    var checkBox1: CheckBox? = null
    var checkBox2: CheckBox? = null
    var checkBox3: CheckBox? = null
    var checkBox4: CheckBox? = null
    var checkBox5: CheckBox? = null
    var checkBox6: CheckBox? = null
    var checkBox7: CheckBox? = null
    var checkBox8: CheckBox? = null
    var checkBox9: CheckBox? = null
    var checkBox10: CheckBox? = null
    var db = FirebaseFirestore.getInstance()
    var textbox_Email: EditText? = null
    var textbox_Name: EditText? = null
    var textbox_Age: EditText? = null
    var textbox_National: EditText? = null
    var textbox_Address: EditText? = null
    var Spinner_BloodType1: Spinner? = null
    var Spinner_BloodType2: Spinner? = null
    var Group1: RadioGroup? = null
    var male: RadioButton? = null
    var female: RadioButton? = null
    var update: Button? = null
    var password: TextView? = null
    var email: TextView? = null
    var setname: TextView? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_settings, container, false)
        dataBinding(root)
        ResetPassword(root)
        ReAuth(root)
        ChangeName(root)
        updateInfo(root)
        return root
    }

    fun ReAuth(root: View) {
        email!!.setOnClickListener {
            val builder = AlertDialog.Builder(root.context)
            builder.setTitle("Write Your Password To Change Email")

            // Set up the input
            val input = EditText(root.context)
            // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
            input.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            builder.setView(input)

            // Set up the buttons
            builder.setPositiveButton("OK") { dialog, which ->
                if (!input.text.toString().isEmpty()) {
                    val credential = EmailAuthProvider
                        .getCredential(
                            mAuth.instance!!.currentUser!!.email.toString(),
                            input.text.toString()
                        )
                    mAuth.instance!!.currentUser!!.reauthenticate(credential)
                        .addOnCompleteListener {
                            Log.d(ContentValues.TAG, "User re-authenticated.")
                            ResetEmail(root)
                        }
                } else {
                    Toast.makeText(root.context, "Please Enter Your Password", Toast.LENGTH_SHORT)
                        .show()
                }
            }
            builder.setNegativeButton("Cancel") { dialog, which -> dialog.cancel() }
            builder.show()
        }
    }

    fun ResetEmail(root: View) {
        if (!textbox_Email!!.text.toString().isEmpty()) {
            mAuth.instance!!.currentUser!!.updateEmail(textbox_Email!!.text.toString())
                .addOnSuccessListener {
                    mAuth.instance!!.currentUser!!.sendEmailVerification()
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                Toast.makeText(
                                    root.context,
                                    "Email Changed, please verify the new email",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                }.addOnFailureListener { e ->
                    Toast.makeText(
                        root.context,
                        "ERROR: $e",
                        Toast.LENGTH_SHORT
                    ).show()
                }
        } else {
            Toast.makeText(root.context, "Please Enter a Valid Email", Toast.LENGTH_SHORT).show()
        }
    }

    fun ResetPassword(root: View) {
        password!!.setOnClickListener {
            if (mAuth.instance!!.currentUser!!.isEmailVerified) {
                val dialogClickListener = DialogInterface.OnClickListener { dialog, which ->
                    when (which) {
                        DialogInterface.BUTTON_POSITIVE ->                                     //Yes button clicked
                            mAuth.instance!!.sendPasswordResetEmail(
                                mAuth.instance!!.currentUser!!.email.toString()
                            )
                                .addOnCompleteListener { task ->
                                    if (task.isSuccessful) {
                                        Toast.makeText(
                                            root.context,
                                            "Password Reset Email Sent, Check Your Email",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                }
                        DialogInterface.BUTTON_NEGATIVE -> {
                        }
                    }
                }
                val builder = AlertDialog.Builder(root.context)
                builder.setMessage("Are you sure?").setPositiveButton("Yes", dialogClickListener)
                    .setNegativeButton("No", dialogClickListener).show()
            } else {
                Toast.makeText(root.context, "Please Verify Your Email First", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    fun ChangeName(root: View) {
        setname!!.setOnClickListener {
            if (!textbox_Name!!.text.toString().isEmpty()) {
                val Name: MutableMap<String, Any> = HashMap()
                Name["name"] = textbox_Name!!.text.toString()
                db.collection("users").document(mAuth.instance!!.currentUser!!.uid).update(Name)
                    .addOnSuccessListener {
                        Toast.makeText(
                            root.context,
                            "Name Updated.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    .addOnFailureListener {
                        Toast.makeText(
                            root.context,
                            "Error Try again",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
            } else {
                Toast.makeText(root.context, "Please Enter The New Name", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun notEmpty(): Boolean {
        return !(textbox_Address!!.text.toString().isEmpty() || textbox_Age!!.text.toString()
            .isEmpty() || textbox_National!!.text.toString().isEmpty())
    }

    private fun updateInfo(root: View) {
        update!!.setOnClickListener {
            if (notEmpty()) {
                db.collection("users").document(mAuth.instance!!.currentUser!!.uid)
                    .update(dataMap())
                    .addOnSuccessListener {
                        Toast.makeText(
                            root.context,
                            "Name Updated.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    .addOnFailureListener {
                        Toast.makeText(
                            root.context,
                            "Error Try again",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
            } else {
                Toast.makeText(root.context, "Please Enter The New Name", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun dataMap(): Map<String, Any> {
        val UserData: MutableMap<String, Any> = HashMap()
        UserData["age"] = textbox_Age!!.text.toString()
        UserData["national_id"] = textbox_National!!.text.toString()
        UserData["address"] = textbox_Address!!.text.toString()
        UserData["blood_type"] =
            Spinner_BloodType1!!.selectedItem.toString() + Spinner_BloodType2!!.selectedItem.toString()
        if (male!!.isChecked) {
            UserData["gender"] = "male"
        } else {
            UserData["gender"] = "female"
        }
        UserData["can_donate"] =
            !(checkBox1!!.isChecked || checkBox2!!.isChecked || checkBox3!!.isChecked || checkBox4!!.isChecked ||
                    checkBox5!!.isChecked || checkBox6!!.isChecked || checkBox7!!.isChecked || checkBox8!!.isChecked || checkBox9!!.isChecked || checkBox10!!.isChecked)
        return UserData
    }

    private fun dataBinding(root: View) {
        checkBox1 = root.findViewById(R.id.checkBox)
        checkBox2 = root.findViewById(R.id.checkBox2)
        checkBox3 = root.findViewById(R.id.checkBox3)
        checkBox4 = root.findViewById(R.id.checkBox4)
        checkBox5 = root.findViewById(R.id.checkBox5)
        checkBox6 = root.findViewById(R.id.checkBox6)
        checkBox7 = root.findViewById(R.id.checkBox7)
        checkBox8 = root.findViewById(R.id.checkBox8)
        checkBox9 = root.findViewById(R.id.checkBox9)
        checkBox10 = root.findViewById(R.id.checkBox10)
        update = root.findViewById(R.id.update)
        textbox_Email = root.findViewById(R.id.TextEmail)
        password = root.findViewById(R.id.set_password)
        email = root.findViewById(R.id.set_email)
        textbox_Name = root.findViewById(R.id.TextName)
        textbox_Age = root.findViewById(R.id.numberAge)
        textbox_National = root.findViewById(R.id.TextNational)
        textbox_Address = root.findViewById(R.id.TextAddress)
        Group1 = root.findViewById(R.id.Group1)
        male = root.findViewById(R.id.radioButton)
        female = root.findViewById(R.id.radioButton1)
        setname = root.findViewById(R.id.set_name)
        val arr_BloodType_data1 = arrayOf("A", "B", "AB", "O")
        Spinner_BloodType1 = root.findViewById<View>(R.id.BloodType1) as Spinner
        val Adapter_Data = ArrayAdapter(
            root.context,
            android.R.layout.simple_list_item_1, arr_BloodType_data1
        )
        Adapter_Data.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        Spinner_BloodType1!!.adapter = Adapter_Data
        val arr_BloodType_data2 = arrayOf("+", "-")
        Spinner_BloodType2 = root.findViewById<View>(R.id.BloodType2) as Spinner
        val data = ArrayAdapter(
            root.context,
            android.R.layout.simple_list_item_1, arr_BloodType_data2
        )
        data.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        Spinner_BloodType2!!.adapter = data
    }
}