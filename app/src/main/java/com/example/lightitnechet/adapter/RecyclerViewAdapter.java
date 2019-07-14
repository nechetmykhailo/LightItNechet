package com.example.lightitnechet.adapter;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.lightitnechet.R;
import com.example.lightitnechet.model.Product;
import com.example.lightitnechet.ui.ProductActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolderAdapter>{

    private List<Product> productList;
    private Context context;
    private Intent intent;

    public RecyclerViewAdapter(Context context, List<Product> productList) {
        this.context = context;
        this.productList = productList;
    }

    @Override
    public ViewHolderAdapter onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_row_view, parent, false);
        return new ViewHolderAdapter(v);
    }

    @Override
    public void onBindViewHolder(ViewHolderAdapter holder, final int position) {
        holder.tvTitle.setText(productList.get(position).getText());
        holder.tvName.setText(productList.get(position).getTitle());

        Picasso.get()
                .load(productList.get(position).getImg())
                .into(holder.imageView);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                intent = new Intent(context, ProductActivity.class);
                intent.putExtra("tvName", productList.get(position).getText());
                intent.putExtra("tvTitle", productList.get(position).getTitle());
                intent.putExtra("tvID", productList.get(position).getId());
                intent.putExtra("image", productList.get(position).getImg());
                view.getContext().startActivity(intent);
            }
        });
    }
    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class ViewHolderAdapter extends RecyclerView.ViewHolder {

        private TextView tvName;
        private TextView tvTitle;
        private ImageView imageView;

        private ViewHolderAdapter(final View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            imageView = itemView.findViewById(R.id.imageView);
        }
    }
}


