package internship.batch1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class ProductActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<ProductList> arrayList;
    ProductAdapter adapter;

    String[] nameProductArray = {"Chocolate Oreo","Apple","Black Forest","Hershey","Orange","Ferrero Rocher","Onion","Potato","Oreo","Little Heart","Vanilla","Strawberry","Mango","Green Chilli","Strawberry"};
    int[] imgProductArray = {R.drawable.chocolate_oreo_cake,R.drawable.apple,R.drawable.black_forest_cake,R.drawable.hersheys,R.drawable.orange,R.drawable.ferrero_rocher,R.drawable.onion,R.drawable.potato,R.drawable.oreo1,R.drawable.little_hearts,R.drawable.vanilla,R.drawable.strawberry_ice_cream,R.drawable.mangoes,R.drawable.green_chilli,R.drawable.strawberry};
    String[] priceProductArray = {"450","80","400","50","40","150","60","30","30","20","30","40","100","20","50"};
    String[] unitProductArray = {"500 GM","1 KG","500 GM","80 GM","1 KG","200 GM","1 KG","1 KG","270 GM","100 GM","1 Scoop","1 Scoop","1 KG","500 GM","6 Items"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        getSupportActionBar().setTitle("Products");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = findViewById(R.id.product_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(ProductActivity.this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        arrayList = new ArrayList<>();
        for (int i=0;i<nameProductArray.length;i++){
            ProductList list = new ProductList();
            list.setNameProduct(nameProductArray[i]);
            list.setImgProduct(imgProductArray[i]);
            list.setPriceProduct(priceProductArray[i]);
            list.setUnitProduct(unitProductArray[i]);
            arrayList.add(list);
        }

        adapter = new ProductAdapter(ProductActivity.this,arrayList);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home){
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    private class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.MyHolder> {

        Context context;
        ArrayList<ProductList> arrayList;

        public ProductAdapter(ProductActivity productActivity, ArrayList<ProductList> arrayList) {

            this.context = productActivity;
            this.arrayList = arrayList;

        }

        @NonNull
        @Override
        public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_product_page,parent,false);
            return new MyHolder(view);
        }

        public class MyHolder extends RecyclerView.ViewHolder {

            ImageView imageView;
            TextView name,price;

            public MyHolder(@NonNull View itemView) {
                super(itemView);
                imageView = itemView.findViewById(R.id.product_image);
                name = itemView.findViewById(R.id.product_name);
                price = itemView.findViewById(R.id.product_price);
            }
        }

        @Override
        public void onBindViewHolder(@NonNull ProductActivity.ProductAdapter.MyHolder holder, int position) {

            holder.imageView.setImageResource(arrayList.get(position).getImgProduct());
            holder.name.setText(arrayList.get(position).getNameProduct());
            holder.price.setText(getResources().getString(R.string.RS_price)+arrayList.get(position).getPriceProduct()+"/"+arrayList.get(position).getUnitProduct());

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context,ProductDetailActivity.class);

                    Bundle bundle = new Bundle();
                    bundle.putString("name",arrayList.get(position).getNameProduct());
                    bundle.putString("price",getResources().getString(R.string.RS_price)+arrayList.get(position).getPriceProduct()+"/"+arrayList.get(position).getUnitProduct());
                    bundle.putInt("image",arrayList.get(position).getImgProduct());
                    intent.putExtras(bundle);
                    context.startActivity(intent);
                }
            });

        }

        @Override
        public int getItemCount() {
            return arrayList.size();
        }

    }
}