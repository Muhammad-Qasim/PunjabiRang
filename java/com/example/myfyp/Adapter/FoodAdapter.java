package com.example.myfyp.Adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.myfyp.DisplayModelActivity;
import com.example.myfyp.FoodActivity;
import com.example.myfyp.Model.Food;

import com.example.myfyp.R;

import java.util.List;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.MyViewHolder>
{

    ProgressDialog pd;

    private Context mcontext;
    private List<Food> mdata;
    RequestOptions option;

    public FoodAdapter(Context mcontext, List<Food> mdata) {
        this.mcontext = mcontext;
        this.mdata = mdata;

        //Request option for glide
        option= new RequestOptions().centerCrop().placeholder(R.drawable.loading).error(R.drawable.loading);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view;
        LayoutInflater inflater= LayoutInflater.from(mcontext);
        view = inflater.inflate(R.layout.item_view , viewGroup , false);

        final MyViewHolder viewHolder = new MyViewHolder(view);

        viewHolder.view_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mcontext , DisplayModelActivity.class);
                i.putExtra("name" , mdata.get(viewHolder.getAdapterPosition()).getName());
                i.putExtra("desc" , mdata.get(viewHolder.getAdapterPosition()).getDescription());
                i.putExtra("model" , mdata.get(viewHolder.getAdapterPosition()).getModels());
                i.putExtra("img" , mdata.get(viewHolder.getAdapterPosition()).getImage_url());

                mcontext.startActivity(i);
            }
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
      /*  pd= new ProgressDialog(mcontext);
        pd.setMessage("Please Wait...");
        pd.setTitle("Loadings...");
        pd.show();
*/
        myViewHolder.tv_name.setText(mdata.get(i).getName());
        Glide.with(mcontext).load(mdata.get(i).getImage_url()).apply(option).into(myViewHolder.img_data);
        myViewHolder.model_ratings.setText(mdata.get(i).getAvrgrating());
//        Toast.makeText(mcontext, "rating"+mdata.get(i).getAvrgrating(), Toast.LENGTH_SHORT).show();
  //      pd.dismiss();

    }

    @Override
    public int getItemCount() {
        return mdata.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder
    {

        TextView tv_name ;
        ImageView img_data;
        LinearLayout view_container;
        TextView model_ratings;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_name = itemView.findViewById(R.id.name);
            img_data = itemView.findViewById(R.id.data_img);
            view_container =itemView.findViewById(R.id.container);
            model_ratings= itemView.findViewById(R.id.model_ratings);

        }
    }
}
