package com.example.blooddonationapp.ui.About;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.blooddonationapp.R;

import java.util.ArrayList;

public class AboutFragment extends Fragment {


    private RecyclerView RecyclerView1;
    private Recyclerview rec_viwe;
    private ArrayList<MemberItem> post;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View about = inflater.inflate(R.layout.fragment_about, container, false);
ArrayList<ArrDataAbout> data_about= new ArrayList<ArrDataAbout>();
        data_about.add( new ArrDataAbout("AbdelHalim Mahmoud","2018030258",R.drawable.abdehalim));
        data_about.add( new ArrDataAbout("Mahmoud Ashraf","2018030143",R.drawable.mahmoud));
        data_about.add( new ArrDataAbout("Nowraa Hamdy Mahmoud","2018030163",R.drawable.nowraa));
        data_about.add( new ArrDataAbout("Heidi Mohamed Samir","2018030168",R.drawable.heidimohamed));
        data_about.add( new ArrDataAbout("Ezz Eldin Ayman","2018030216",R.drawable.ezz_eldin));
        data_about.add( new ArrDataAbout("Ahmed hassan maghwry ","2018030012",R.drawable.ahmedhassan));
        data_about.add( new ArrDataAbout("ahmed sedky mahmoud","2018030021",R.drawable.ahmedsedky));
        data_about.add( new ArrDataAbout("Youssef khaled mohamed","2018030176",R.drawable.youssef));
        data_about.add( new ArrDataAbout("Ahmed galal","2018030009",R.drawable.default_account));
        data_about.add( new ArrDataAbout("Ahmed Abraham","2018030003",R.drawable.default_account));

        RecyclerView1 =(RecyclerView) about.findViewById(R.id.recyclerview1);


        post=new ArrayList<MemberItem>();

        for (int i=0 ;i<data_about.size() ;i++){
            post.add(new MemberItem( data_about.get(i).getName(),  data_about.get(i).getID(), data_about.get(i).getImg()));
        }
        rec_viwe=new Recyclerview(getContext(),post);
        RecyclerView1.setLayoutManager(new LinearLayoutManager(getContext()));
        RecyclerView1.setAdapter(rec_viwe);
      //  Button t= (Button) about.findViewById(R.id.button);
       /* t.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             /*   Intent w=new Intent(Intent.ACTION_VIEW);
                w.setData(Uri.parse("https://www.youtube.com/watch?v=DYMhrOOVui0"));
              startActivity(w);
            }
        });*/
        return about;
    }
}