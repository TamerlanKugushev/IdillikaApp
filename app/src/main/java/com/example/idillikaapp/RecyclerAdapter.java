package com.example.idillikaapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ProductViewHolder> {

    public List<Product> products;
    private SharedPreferences pref;
    private Context context;


    // Создание конструктора
    public RecyclerAdapter(List<Product> products,Context context) {
        this.products = products;
        this.context=context;
    }

    // Раздувание layout`a
    @NonNull
    @Override
    public RecyclerAdapter.ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        ProductViewHolder productViewHolder = new ProductViewHolder(v);
        if (pref == null) {
            pref = parent.getContext().getSharedPreferences("NICE", Context.MODE_PRIVATE);
        }
        return productViewHolder;
    }

    // Получаем значения по позиции
    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapter.ProductViewHolder holder, int position) {
        holder.bind(products.get(position));
    }


    @Override
    public int getItemCount() {
        return products.size();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder {
        // Объявление полей
        public TextView textTitle;
        private TextView textPrice;
        public ImageView imageLink;
        private ImageView imageFavorite;
        boolean s;
        private Product mProduct;


        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            textTitle = itemView.findViewById(R.id.title);
            textPrice = itemView.findViewById(R.id.price);
            imageLink = itemView.findViewById(R.id.imageLink);
            imageFavorite = itemView.findViewById(R.id.favorite);

            imageFavorite.setOnClickListener(v -> {
                if (pref.getBoolean(String.valueOf(mProduct.getId()), false)) {
                    imageFavorite.setImageResource(R.drawable.ic_favorite_button);
                    saveData(String.valueOf(mProduct.getId()), false);
                } else {
                    imageFavorite.setImageResource(R.drawable.ic_favorite_button_selected);
                    saveData(String.valueOf(mProduct.getId()), true);
                }
            });


        }


        public void bind(Product product) {
            mProduct= product;
            s = pref.getBoolean(String.valueOf(product.getId()), true);

            // Получение данных и их установка на модели
            textTitle.setText(product.getTitle());
            String price = String.valueOf(product.getPrice());
            textPrice.setText(price + " ₽");
            String url = product.getImageLink();
            Glide.with(itemView.getContext())
                    .load(url)
                    .into(imageLink);


            if (pref.getBoolean(String.valueOf(mProduct.getId()), false)) {
                imageFavorite.setImageResource(R.drawable.ic_favorite_button_selected);
            } else {
                imageFavorite.setImageResource(R.drawable.ic_favorite_button);
            }

        }


        public void saveData(String id, boolean dataToSave) {
            SharedPreferences.Editor editor = pref.edit();
            editor.putBoolean(id, dataToSave);
            editor.apply();
        }

    }
}
