package com.uty.tgs_clientserver

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import  androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.member_list.view.*
import com.uty.tgs_clientserver.MainActivity as MainActivity1

class RVAdaptermember(private val context: Context,private val arrayList: ArrayList<member>) :
RecyclerView.Adapter<RVAdaptermember.Holder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        return Holder(LayoutInflater.from(parent.context).inflate(R.layout.member_list,parent,false))
    }

    override fun getItemCount(): Int =arrayList!!.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        holder.view.label_idmember.text=this.arrayList?.get(position)?.id
        holder.view.label_namamember.text="nama"+arrayList?.get(position)?.nama
        holder.view.label_alamat.text="Alamat"+arrayList?.get(position)?.alamat
        holder.view.label_password.text=this.arrayList?.get(position)?.password

        holder.view.cardview_list.setOnClickListener {
            val i=Intent(context,managememberactivity::class.java)
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            i.putExtra("editmode","1")
            i.putExtra("txt_idmember",arrayList?.get(position)?.id)
            i.putExtra("txt_nama",arrayList?.get(position)?.nama)
            i.putExtra("txt_alamat",arrayList?.get(position)?.alamat)
            i.putExtra("txt_password",arrayList?.get(position)?.password)
        }
    }
    class Holder(val view: View) : RecyclerView.ViewHolder(view)
}