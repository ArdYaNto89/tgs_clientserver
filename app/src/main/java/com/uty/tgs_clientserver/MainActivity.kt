@file:Suppress("DEPRECATION")
package com.uty.tgs_clientserver

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import  android.app.ProgressDialog
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject
import java.util.ArrayList

class MainActivity : AppCompatActivity() {
    var arrayList= ArrayList<member>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.title="Data member"

        recycle_view.setHasFixedSize(true)
        recycle_view.layoutManager=LinearLayoutManager(this)

        mFloatingActionButton.setOnClickListener{
            startActivity(Intent(this,managememberactivity::class.java))
        }
        loadAllmember()
    }

    override fun onResume() {
        super.onResume()
        loadAllmember()
    }
    private fun loadAllmember(){
        val loading=ProgressDialog(this)
        loading.setMessage("memuat data...")
        loading.show()
        AndroidNetworking.get(apiendpoint.READ)
            .setPriority(Priority.MEDIUM)
            .build()
            .getAsJSONObject(object :JSONObjectRequestListener{
                override fun onResponse(response:JSONObject?){
                    arrayList.clear()
                    val jsonArray=response?.optJSONArray("result")
                    if(jsonArray?.length()==0)
                    {
                        loading.dismiss()
                        Toast.makeText(applicationContext,"data member kosong,datambahkan data ",Toast.LENGTH_SHORT
                        ).show()
                    }
                    for(i in 0 until  jsonArray?.length()!!)
                    {
                        val jsonObject= jsonArray?.optJSONObject(i)
                        arrayList.add(
                            member(
                                jsonObject.getString("id"),
                            jsonObject.getString("nama"),
                        jsonObject.getString("alamat"),
                            jsonObject.getString("password")
                         )

                        )
                        if (jsonArray?.length()-1==i){
                            loading.dismiss()
                            val adapter = RVAdaptermember(applicationContext,arrayList)
                                        adapter.notifyDataSetChanged()
                            recycle_view.adapter=adapter

                        }
                    }
                }



                override fun onError(anError: ANError?) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    loading.dismiss()
                    Log.d("ONERROR",anError?.errorDetail?.toString())
                    Toast.makeText(applicationContext,"Connection Failure",Toast.LENGTH_SHORT).show()
                }
            })
    }
}
