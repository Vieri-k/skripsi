package com.example.aplicationopa;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class OList extends RecyclerView.Adapter<OList.OrderViewHolder> {

    private Context mCtx;
    private List<pfojek> orderL;


    public OList(Context mCtx, List<pfojek> orderL) {
        this.mCtx = mCtx;
        this.orderL = orderL;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.card_recview, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        pfojek orderm = orderL.get(position);
        holder.textViewName.setText(orderm.name);
        holder.textViewPhone.setText(orderm.phone);
        holder.textViewRegion.setText(orderm.region);
    }

    @Override
    public int getItemCount() {
        return orderL.size();
    }

    class OrderViewHolder extends RecyclerView.ViewHolder {

        TextView textViewName, textViewPhone, textViewRegion;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewName = itemView.findViewById(R.id.text_view_name);
            textViewPhone = itemView.findViewById(R.id.text_view_phone);
            textViewRegion = itemView.findViewById(R.id.text_view_region);
        }
    }
}
