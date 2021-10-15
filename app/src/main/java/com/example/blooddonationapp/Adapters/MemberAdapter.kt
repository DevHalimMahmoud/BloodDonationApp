package com.example.blooddonationapp.Adapters

import android.content.Context

import com.example.blooddonationapp.models.MemberItem
import androidx.recyclerview.widget.RecyclerView
import android.widget.TextView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.example.blooddonationapp.R
import android.content.Intent
import android.net.Uri
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView

class MemberAdapter(private val Context: Context?, private val Post1: List<MemberItem>) :
    RecyclerView.Adapter<MemberAdapter.ViewHolder>() {
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var t1: TextView
        var t2: TextView
        var img: ImageView
        var face: FloatingActionButton
        var LinkFace = arrayOf( ///link Email facebook
            "https://www.linkedin.com/in/abdelhalim-mahmoud",  // abdelhalim mahmoud
            "https://web.facebook.com/profile.php?id=100011320336271",  //mahmoud
            "https://www.facebook.com/noww.raa",  // nowraa
            "https://www.facebook.com/profile.php?id=100008175133216",  // heidi
            "https://www.facebook.com/3zzEldin.Ayman",  //ezz eldin
            "https://www.facebook.com/0114966214a",  //ahmed hassan
            "https://www.facebook.com/ahmed.sedky.9/",  // ahmed sedky
            "https://www.facebook.com/yousef.khaled.37819",  // youssef khaled
            "https://www.facebook.com/Ahmedgalal001",  //ahmed galal
            "https://www.facebook.com/profile.php?id=100022082570103" //Ahmed Abraham
        )

        init {
            t1 = itemView.findViewById<View>(R.id.textView) as TextView
            t2 = itemView.findViewById<View>(R.id.textView2) as TextView
            img = itemView.findViewById<View>(R.id.imageView3) as ImageView
            face = itemView.findViewById<View>(R.id.imageButton) as FloatingActionButton
            face.setOnClickListener {
                val position = adapterPosition
                val w = Intent(Intent.ACTION_VIEW)
                w.data = Uri.parse(LinkFace[position])
                Context?.startActivity(w)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val y = LayoutInflater.from(Context).inflate(R.layout.add_item, parent, false)
        return ViewHolder(y)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val (name, id, img) = Post1[position]
        holder.t1.text = name
        holder.t2.text = id
        holder.img.setImageResource(img!!)
    }

    override fun getItemCount(): Int {
        return Post1.size
    }
}