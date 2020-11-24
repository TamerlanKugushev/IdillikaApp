package com.example.idillikaapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private List<Product> products;
    private RecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);

        toolbar = findViewById(R.id.catalog_activity_Toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProductActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        recyclerView = findViewById(R.id.products_recyclerview);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));


        ApiClient.getApiService().getCatalogList(21, "f3e82db3d0b2bcce07eae17dd9cb46d3").enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (response.isSuccessful()) {
                    products = response.body();
                    adapter = new RecyclerAdapter(products,getApplicationContext());
                    recyclerView.setAdapter(adapter);
                }

            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Toast toast = Toast.makeText(getApplicationContext(), "Проверьте подключение к сети", Toast.LENGTH_SHORT);
                toast.show();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actionbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.basket_button) {
            Intent intent = new Intent(ProductActivity.this, BasketActivity.class);
            startActivity(intent);
        }
        return true;
    }
}