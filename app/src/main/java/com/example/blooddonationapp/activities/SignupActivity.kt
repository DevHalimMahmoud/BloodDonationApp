package com.example.blooddonationapp.activities

import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.blooddonationapp.R
import com.example.blooddonationapp.utils.FirebaseAuthSingleton
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*

class SignupActivity : AppCompatActivity() {
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
    var textbox_Password: EditText? = null
    var textbox_Name: EditText? = null
    var textbox_Age: EditText? = null
    var textbox_National: EditText? = null
    var textbox_Address: EditText? = null
    var Spinner_BloodType1: Spinner? = null
    var Spinner_BloodType2: Spinner? = null
    var Group1: RadioGroup? = null
    var male: RadioButton? = null
    var female: RadioButton? = null
    var sign_up: Button? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        dataBinding()
        signUp()
    }

    private fun signUp() {
        sign_up!!.setOnClickListener { v ->
            if (notEmpty()) {
                mAuth.instance!!.createUserWithEmailAndPassword(
                    textbox_Email!!.text.toString(),
                    textbox_Password!!.text.toString()
                )
                    .addOnCompleteListener((v.context as Activity)) { task ->
                        if (task.isSuccessful) {
                            addUserDataToFirestore()
                        } else {
                            Toast.makeText(
                                applicationContext,
                                "Please try Again",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
            } else {
                Toast.makeText(
                    applicationContext,
                    "Something Went Wrong, Please Enter All Your Data",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun addUserDataToFirestore() {
        val id = mAuth.instance!!.currentUser!!.uid
        db.collection("users").document(id)
            .set(dataMap())
            .addOnSuccessListener {
                verifyEmail()
                startActivity()
            }
            .addOnFailureListener {
                Toast.makeText(applicationContext, "Please try Again", Toast.LENGTH_SHORT).show()
                mAuth.instance!!.currentUser!!.delete()
            }
    }

    private fun startActivity() {
        val `in` = Intent(this@SignupActivity, LoginActivity::class.java)
        startActivity(`in`)
        finish()
    }

    private fun verifyEmail() {
        Toast.makeText(
            applicationContext,
            "User Created Successfully Pleas Verify your E-mail",
            Toast.LENGTH_LONG
        ).show()
        mAuth.instance!!.currentUser!!.sendEmailVerification()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(ContentValues.TAG, "Email sent.")
                }
            }
    }

    private fun notEmpty(): Boolean {
        return !(textbox_Name!!.text.toString() == "" || textbox_Address!!.text.toString() == "" || textbox_Age!!.text.toString() == "" || textbox_National!!.text.toString() == "")
    }

    private fun dataMap(): Map<String, Any> {
        val UserData: MutableMap<String, Any> = HashMap()
        UserData["user_type"] = "user"
        UserData["name"] = textbox_Name!!.text.toString()
        UserData["age"] = textbox_Age!!.text.toString()
        UserData["national_id"] = textbox_National!!.text.toString()
        UserData["address"] = textbox_Address!!.text.toString()
        UserData["blood_type"] =
            Spinner_BloodType1!!.selectedItem.toString() + Spinner_BloodType2!!.selectedItem.toString()
        UserData["num_of_donation"] = 0
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

    private fun dataBinding() {
        checkBox1 = findViewById(R.id.checkBox)
        checkBox2 = findViewById(R.id.checkBox2)
        checkBox3 = findViewById(R.id.checkBox3)
        checkBox4 = findViewById(R.id.checkBox4)
        checkBox5 = findViewById(R.id.checkBox5)
        checkBox6 = findViewById(R.id.checkBox6)
        checkBox7 = findViewById(R.id.checkBox7)
        checkBox8 = findViewById(R.id.checkBox8)
        checkBox9 = findViewById(R.id.checkBox9)
        checkBox10 = findViewById(R.id.checkBox10)
        sign_up = findViewById(R.id.sign_up)
        textbox_Email = findViewById<View>(R.id.TextEmail) as EditText
        textbox_Password = findViewById<View>(R.id.set_password) as EditText
        textbox_Name = findViewById<View>(R.id.TextName) as EditText
        textbox_Age = findViewById<View>(R.id.numberAge) as EditText
        textbox_National = findViewById<View>(R.id.TextNational) as EditText
        textbox_Address = findViewById<View>(R.id.TextAddress) as EditText
        Group1 = findViewById<View>(R.id.Group1) as RadioGroup
        male = findViewById<View>(R.id.radioButton) as RadioButton
        female = findViewById<View>(R.id.radioButton1) as RadioButton
        val arr_BloodType_data1 = arrayOf("A", "B", "AB", "O")
        Spinner_BloodType1 = findViewById<View>(R.id.BloodType1) as Spinner
        val Adapter_Data = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1, arr_BloodType_data1
        )
        Adapter_Data.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        Spinner_BloodType1!!.adapter = Adapter_Data
        val arr_BloodType_data2 = arrayOf("+", "-")
        Spinner_BloodType2 = findViewById<View>(R.id.BloodType2) as Spinner
        val data = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1, arr_BloodType_data2
        )
        data.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        Spinner_BloodType2!!.adapter = data
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}