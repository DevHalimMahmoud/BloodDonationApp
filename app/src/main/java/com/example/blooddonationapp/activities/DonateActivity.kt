package com.example.blooddonationapp.activities

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.blooddonationapp.R
import com.example.blooddonationapp.viewModels.DonateActivityViewModel
import java.util.*

class DonateActivity : AppCompatActivity() {

    var Spinner_BloodType1: Spinner? = null
    var Spinner_BloodType2: Spinner? = null
    var amount: EditText? = null
    var send: Button? = null
    var agree: CheckBox? = null
    private val model: DonateActivityViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_donate_form)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
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
        amount = findViewById(R.id.amount)
        send = findViewById(R.id.button)
        agree = findViewById(R.id.checkBox)
        send?.setOnClickListener(View.OnClickListener {
            if (agree?.isChecked == true && amount?.text.toString().isNotEmpty()) {
                val type =
                    Spinner_BloodType1!!.selectedItem.toString() + Spinner_BloodType2!!.selectedItem.toString()
                val donateData: MutableMap<String, Any?> = HashMap()
                donateData["amount"] = amount?.text.toString()
                donateData["medical_reason"] = "This user is donating"
                donateData["reason"] = "donate"
                donateData["status"] = "pending"
                donateData["user_id"] = model.getCurrentUser()
                donateData["org_id"] = intent.getStringExtra("org_id")
                donateData["hotspot_id"] = intent.getStringExtra("hotspot_id")
                donateData["type"] = type

                model.addData(donateData).addOnSuccessListener {
                    Toast.makeText(this@DonateActivity, "Request Sent", Toast.LENGTH_LONG)
                        .show()
                    clear()
                    finish()

                }.addOnFailureListener {

                    Toast.makeText(this@DonateActivity, "ERROR TRY AGAIN", Toast.LENGTH_SHORT)
                        .show()
                    clear()

                }

            } else {
                Toast.makeText(this@DonateActivity, "Please Complete the form", Toast.LENGTH_SHORT)
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
        amount!!.setText("")
    }
}