package com.example.aplicationopa;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class OjekAdapter extends FirebaseRecyclerAdapter<OjekModel, OjekAdapter.myviewholder> {
    private Context mContext;
    private ArrayList<String> name = new ArrayList<>();
    private ArrayList<String> phone = new ArrayList<>();
    private ArrayList<String> region = new ArrayList<>();

    public OjekAdapter(@NonNull FirebaseRecyclerOptions<OjekModel> options)
    {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull final myviewholder holder, final int position, @NonNull final OjekModel model)
    {
        holder.name.setText(model.getName());
        holder.phone.setText(model.getPhone());
        holder.region.setText(model.getRegion());
//        layoutInflater = LayoutInflater.from(context);


        holder.dn.setOnClickListener(new View.OnClickListener() {
//            TextView name,phone,region;
            private Context context = holder.dn.getContext();


            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder=new AlertDialog.Builder(context);
                builder.setTitle("Order");
                builder.setMessage("the order will be confirmed ?? ");


                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        FirebaseDatabase.getInstance().getReference().child("driver")
                                .child(getRef(position).getKey()).removeValue();

                        Intent intent = new Intent(context, OnthewayActivity.class);
                        intent.putExtra("name", model.getName());
                        intent.putExtra("phone", model.getPhone());
                        intent.putExtra("region", model.getRegion());
                        context.startActivity(intent);
                    }
                });

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

                builder.show();
            }
        });
    } // End of OnBindViewMethod

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.card_recview,parent,false);
        return new myviewholder(view);
    }


    class myviewholder extends RecyclerView.ViewHolder
    {
        //        CircleImageView img;
        private Context context = itemView.getContext();

        Button dn;
        TextView name,phone,region;
        public myviewholder(@NonNull View itemView)
        {
            super(itemView);
//            img=(CircleImageView) itemView.findViewById(R.id.img1);
            name = (TextView)itemView.findViewById(R.id.nametext);
            phone = (TextView)itemView.findViewById(R.id.phonetext);
            region = (TextView)itemView.findViewById(R.id.regiontext);

            dn = (Button)itemView.findViewById(R.id.done);
//            delete=(ImageView)itemView.findViewById(R.id.deleteicon);

//
//            name = (TextView)itemView.findViewById(R.id.nametext);
//            phone = (TextView)itemView.findViewById(R.id.phonetext);
//            region = (TextView)itemView.findViewById(R.id.regiontext);
        }
    }
}
