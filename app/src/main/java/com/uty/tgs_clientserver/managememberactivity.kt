@file:Suppress("DEPRECATION")
package com.uty.tgs_clientserver

import  android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import kotlinx.android.synthetic.main.activity_manage_member.*
import org.json.JSONObject
import javax.xml.transform.Templates

class managememberactivity :AppCompatActivity() {
    lateinit var i: Intent
    lateinit var add: Button
    lateinit var edit: Button
    lateinit var delete: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manage_member)
        add = findViewById(R.id.btnCreate)
        edit = findViewById(R.id.btnUpdate)
        delete = findViewById(R.id.btnDelete)

        i = intent
        if (i.hasExtra("editmode")) {
            if (i.getStringExtra("editmode").equals("1")) {
                onEditMode()
            }
        }
        add.setOnClickListener {
            onCreate()
        }
        edit.setOnClickListener {
            onUpdate()
        }
        delete.setOnClickListener {
            onDelete()
        }
    }

    private fun onEditMode() {
        TODO("not implemented")
        //use File|Settings|File Templates
        txt_nama.setText(i.getStringExtra("txt_idmember"))
        txt_nama.setText(i.getStringExtra("txt_nama"))
        txt_alamat.setText(i.getStringExtra("txt_alamat"))
        txt_password.setText(i.getStringExtra("txt_password"))
        txt_idmember.isEnabled = false

        btnCreate.visibility = View.GONE
        btnUpdate.visibility = View.VISIBLE
        btnDelete.visibility = View.VISIBLE
    }

    private fun onCreate() {
        val loading = ProgressDialog(this)
        loading.setMessage("menambahkan data...")
        loading.show()
        AndroidNetworking.post(apiendpoint.CREATE)
            .addBodyParameter("nama", txt_nama.text.toString())
            .addBodyParameter("alamat", txt_alamat.text.toString())
            .addBodyParameter("password", txt_password.text.toString())
            .setPriority(Priority.MEDIUM)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener {
                override fun onResponse(response: JSONObject?) {
                    loading.dismiss()

                    Toast.makeText(
                        applicationContext,
                        response?.getString("message"),
                        Toast.LENGTH_SHORT
                    ).show()

                    if (response?.getString("message")?.contains("successfully")!!) {
                        this@managememberactivity.finish()
                    }
                }

                override fun onError(anError: ANError?) {
                    loading.dismiss()
                    Log.d("ONERROR", anError?.errorDetail?.toString())
                    Toast.makeText(applicationContext, "koneksi gagal", Toast.LENGTH_SHORT)
                        .show()
                }
            })
    }


    //update data
    private fun onUpdate() {
        val loading = ProgressDialog(this)
        loading.setMessage("mengubah data...")
        loading.show()

        AndroidNetworking.post(apiendpoint.UPDATE)
            .addBodyParameter("id", txt_idmember.text.toString())
            .addBodyParameter("nama", txt_nama.text.toString())
            .addBodyParameter("alamat", txt_alamat.text.toString())
            .addBodyParameter("password", txt_password.text.toString())

            .setPriority(Priority.MEDIUM)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener {
                override fun onResponse(response: JSONObject?) {
                    loading.dismiss()

                    Toast.makeText(
                        applicationContext,
                        response?.getString("message"), Toast.LENGTH_SHORT
                    ).show()

                    if (response?.getString("message")?.contains("successfully")!!) {
                        this@managememberactivity.finish()
                    }

                }

                override fun onError(anError: ANError?) {
                    loading.dismiss()
                    Log.d("ONERROR", anError?.errorDetail?.toString())
                    Toast.makeText(applicationContext, "connection failure", Toast.LENGTH_SHORT)
                        .show()
                }
            })
    }
    private fun onDelete(){
        val loading = ProgressDialog(this)
        loading.setMessage("menghapus data...")
        loading.show()
        AndroidNetworking.post(apiendpoint.DELETE)
            .addBodyParameter("id", txt_idmember.text.toString())
            .addBodyParameter("nama", txt_nama.text.toString())
            .addBodyParameter("alamat", txt_alamat.text.toString())
            .addBodyParameter("password", txt_password.text.toString())
            .setPriority(Priority.MEDIUM)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener {
                override fun onResponse(response: JSONObject?) {
                    loading.dismiss()

                    Toast.makeText(
                        applicationContext,
                        response?.getString("message"),
                        Toast.LENGTH_SHORT
                    ).show()

                    if (response?.getString("message")?.contains("successfully")!!) {
                        this@managememberactivity.finish()
                    }
                }

                override fun onError(anError: ANError?) {
                    loading.dismiss()
                    Log.d("ONERROR", anError?.errorDetail?.toString())
                    Toast.makeText(applicationContext, "koneksi gagal", Toast.LENGTH_SHORT)
                        .show()
                }
            })
    }

}
