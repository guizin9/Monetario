package com.example.monetario

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.TextView
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL

class MainActivity : AppCompatActivity() {
    private lateinit var result: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        result = findViewById<TextView>(R.id.txtResult)

        val  buttonCoverter = findViewById<Button>(R.id.btn_converter)

        buttonCoverter.setOnClickListener {
           converter()
        }

    }
    private fun converter(){
        val selectedCurrency = findViewById<RadioGroup>(R.id.radio_group)

        val checked = selectedCurrency.checkedRadioButtonId

        val currency = when (checked) {
            R.id.radio_usd -> "USD"
            R.id.radio_eur -> "EUR"
            else -> "CLP"

        }

        val editField = findViewById<EditText>(R.id.edit_field)

        val value = editField.text.toString()

        if(value.isEmpty()) return

        result.text = value
        result.visibility = View.VISIBLE

        Thread{
            var url = URL("https://economia.awesomeapi.com.br/json/daily/${currency}")
            val conn = url.openConnection() as HttpURLConnection

            try {
                val data = conn.inputStream.bufferedReader().readText()

                val obj = JSONObject(data)

                runOnUiThread{
                    val res = obj.getDouble("${currency}")

                    result.text = data
                    result.visibility = View.VISIBLE
                }

            }finally {
                conn.disconnect()
            }
        }.start()
    }
}