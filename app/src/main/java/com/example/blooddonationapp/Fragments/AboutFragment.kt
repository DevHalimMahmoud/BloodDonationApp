package com.example.blooddonationapp.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.blooddonationapp.Adapters.MemberAdapter
import com.example.blooddonationapp.R
import com.example.blooddonationapp.Models.MemberItem
import java.util.*

class AboutFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        val root = inflater.inflate(R.layout.fragment_about, container, false)
        val data_about = ArrayList<MemberItem>()
        data_about.add(MemberItem("AbdelHalim Mahmoud", "2018030258", R.drawable.abdehalim))
        data_about.add(MemberItem("Mahmoud Ashraf", "2018030143", R.drawable.mahmoud))
        data_about.add(MemberItem("Nowraa Hamdy Mahmoud", "2018030163", R.drawable.nowraa))
        data_about.add(MemberItem("Heidi Mohamed Samir", "2018030168", R.drawable.heidimohamed))
        data_about.add(MemberItem("Ezz Eldin Ayman", "2018030216", R.drawable.ezz_eldin))
        data_about.add(MemberItem("Ahmed hassan maghwry ", "2018030012", R.drawable.ahmedhassan))
        data_about.add(MemberItem("ahmed sedky mahmoud", "2018030021", R.drawable.ahmedsedky))
        data_about.add(MemberItem("Youssef khaled mohamed", "2018030176", R.drawable.youssef))
        data_about.add(MemberItem("Ahmed galal", "2018030009", R.drawable.default_account))
        data_about.add(MemberItem("Ahmed Abraham", "2018030003", R.drawable.default_account))

        val recyclerView1 = root.findViewById<View>(R.id.recyclerview1) as RecyclerView
        val recyclerview = MemberAdapter(
            context,
            data_about
        )
        recyclerView1.layoutManager = LinearLayoutManager(context)
        recyclerView1.adapter = recyclerview
        return root
    }
}