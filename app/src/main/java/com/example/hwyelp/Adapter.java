package com.example.hwyelp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    LayoutInflater inflater;
    List<Business> businesses;
    Context ctx;


    public Adapter(Context ctx, List<Business> businesses){
        this.inflater = LayoutInflater.from(ctx);
        this.businesses = businesses;
        this.ctx = ctx;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.custom_list_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {


        holder.bid.setText(businesses.get(position).getBid());
        holder.bname.setText(businesses.get(position).getBname());
        holder.bdistance.setText(businesses.get(position).getBdistance());
        holder.brating.setText(businesses.get(position).getBrating());
        Picasso.get().load(businesses.get(position).getCoverImageURL()).into(holder.coverImage);

    }

    @Override
    public int getItemCount() {
        return businesses.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView bid,bname,brating,bdistance;
        ImageView coverImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            bid = itemView.findViewById(R.id.bid);
            coverImage = itemView.findViewById(R.id.coverImage);
            bname = itemView.findViewById(R.id.bname);
            brating = itemView.findViewById(R.id.brating);
            bdistance = itemView.findViewById(R.id.bdistance);
            itemView.setOnClickListener(this);


        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            Intent myintent = new Intent(ctx,bDetails.class);
            myintent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
            myintent.putExtra("bid",businesses.get(position).getBd());
            myintent.putExtra("bname",businesses.get(position).getBname());
            myintent.putExtra("burl",businesses.get(position).getBurl());
            ctx.startActivity(myintent);

        }
    }
}
