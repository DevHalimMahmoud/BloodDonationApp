package com.example.blooddonationapp.ui.About;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.blooddonationapp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class Recyclerview extends RecyclerView.Adapter<Recyclerview.ViewHolder> {


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView t1, t2;
        ImageView img;
        FloatingActionButton face;
        String[] LinkFace =
                {///link Email facebook
                        "https://www.linkedin.com/in/abdelhalim-mahmoud", // abdelhalim mahmoud
                        "https://web.facebook.com/profile.php?id=100011320336271", //mahmoud
                        "https://www.facebook.com/noww.raa",// nowraa
                        "https://www.facebook.com/profile.php?id=100008175133216",// heidi
                        "https://www.facebook.com/3zzEldin.Ayman",//ezz eldin
                        "https://www.facebook.com/0114966214a",//ahmed hassan
                        "https://www.facebook.com/ahmed.sedky.9/",// ahmed sedky
                        "https://www.facebook.com/yousef.khaled.37819",// youssef khaled
                        "https://www.facebook.com/Ahmedgalal001",//ahmed galal
                        "https://www.facebook.com/profile.php?id=100022082570103"//Ahmed Abraham

                };

        public ViewHolder(@NonNull View itemView) {

            super(itemView);

            t1 = (TextView) itemView.findViewById(R.id.textView);
            t2 = (TextView) itemView.findViewById(R.id.textView2);
            img = (ImageView) itemView.findViewById(R.id.imageView3);
            face = (FloatingActionButton) itemView.findViewById(R.id.imageButton);
            face.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    Intent w = new Intent(Intent.ACTION_VIEW);
                    w.setData(Uri.parse(LinkFace[position]));
                    Context.startActivity(w);
                }
            });
        }


    }

    private Context Context;
    private List<MemberItem> Post1;

    public Recyclerview(Context c, List<MemberItem> postList) {
        this.Context = c;
        this.Post1 = postList;
    }

    @NonNull
    @Override
    public Recyclerview.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View y = LayoutInflater.from(Context).inflate(R.layout.add_item, parent, false);
        return new ViewHolder(y);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MemberItem p = Post1.get(position);
        holder.t1.setText(p.getName());
        holder.t2.setText(p.getId());
        holder.img.setImageResource(p.getImg());
    }

    @Override
    public int getItemCount() {

        return Post1.size();
    }

}