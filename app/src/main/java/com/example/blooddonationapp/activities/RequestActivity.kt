package com.example.blooddonationapp.activities

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.blooddonationapp.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*

class RequestActivity : AppCompatActivity() {
    var mAuth = FirebaseAuth.getInstance()
    var db = FirebaseFirestore.getInstance()
    var Spinner_BloodType1: Spinner? = null
    var Spinner_BloodType2: Spinner? = null
    var reason: EditText? = null
    var amount: EditText? = null
    var send: Button? = null
    var agree: CheckBox? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_request_form)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        val arr_BloodType_data1 = arrayOf("A", "B", "AB", "O")
        Spinner_BloodType1 = findViewById(R.id.BloodType1)
        val Adapter_Data = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1, arr_BloodType_data1
        )
        Adapter_Data.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        Spinner_BloodType1?.adapter = Adapter_Data
        val arr_BloodType_data2 = arrayOf("+", "-")
        Spinner_BloodType2 = findViewById(R.id.BloodType2)
        val data = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1, arr_BloodType_data2
        )
        data.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        Spinner_BloodType2?.setAdapter(data)
        reason = findViewById(R.id.reason)
        amount = findViewById(R.id.amount)
        send = findViewById(R.id.button)
        agree = findViewById(R.id.checkBox)
        send?.setOnClickListener(View.OnClickListener {
            if (agree?.isChecked == true && amount?.text.toString().isNotEmpty() && !reason?.text
                    .toString().isEmpty()
            ) {
                val type = Spinner_BloodType1?.selectedItem
                    .toString() + Spinner_BloodType2?.selectedItem.toString()
                val data: MutableMap<String, Any?> = HashMap()
                data["amount"] = amount?.text.toString()
                data["medical_reason"] = reason?.getText().toString()
                data["reason"] = "request"
                data["status"] = "pending"
                data["user_id"] = mAuth.currentUser!!.uid
                data["org_id"] = getIntent().getStringExtra("org_id")
                data["hotspot_id"] = getIntent().getStringExtra("hotspot_id")
                data["type"] = type
                db.collection("requests")
                    .add(data)
                    .addOnSuccessListener {
                        Toast.makeText(this@RequestActivity, "Request Sent", Toast.LENGTH_LONG)
                            .show()
                        clear()
                        finish()
                    }
                    .addOnFailureListener {
                        Toast.makeText(this@RequestActivity, "ERROR TRY AGAIN", Toast.LENGTH_SHORT)
                            .show()
                        clear()
                    }
            } else {
                Toast.makeText(this@RequestActivity, "Please Complete the form", Toast.LENGTH_SHORT)
                    .show()
            }
        })
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

    private fun clear() {
        reason!!.setText("")
        amount!!.setText("")
    }
}