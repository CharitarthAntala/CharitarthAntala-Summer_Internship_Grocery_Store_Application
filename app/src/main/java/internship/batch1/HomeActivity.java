package internship.batch1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toolbar;

import java.util.ArrayList;
import java.util.Locale;

public class HomeActivity extends AppCompatActivity {

    RecyclerView recyclerView, recyclerProductView;
    //SearchView searchView;
    EditText searchEdit;
    ///AutoCompleteTextView autoCompleteTextView;

    SharedPreferences sharedPreferences;

    CategoryAdapter adapter;
    ProductAdapter productAdapter;

    ArrayList<CategoryList> arrayCategoryList;
    ArrayList<ProductList> arrayProductList;
    //ArrayList<String> categoryNameArrayList;

    String[] nameArray = {"Vegetable","Fruit","Berrie","Chili","Cake","Biscuit","Chocolate","Ice Cream"};
    int[] imgArray = {R.drawable.vegetables,R.drawable.fruitpng,R.drawable.barries,R.drawable.chili,R.drawable.cake,R.drawable.biscuit,R.drawable.chocolate,R.drawable.ice_cream};

    String[] nameProductArray = {"Chocolate Oreo","Apple","Black Forest","Hershey","Orange","Ferrero Rocher","Onion","Potato","Oreo","Little Heart","Vanilla","Strawberry","Mango","Green Chilli","Strawberry"};
    int[] imgProductArray = {R.drawable.chocolate_oreo_cake,R.drawable.apple,R.drawable.black_forest_cake,R.drawable.hersheys,R.drawable.orange,R.drawable.ferrero_rocher,R.drawable.onion,R.drawable.potato,R.drawable.oreo1,R.drawable.little_hearts,R.drawable.vanilla,R.drawable.strawberry_ice_cream,R.drawable.mangoes,R.drawable.green_chilli,R.drawable.strawberry};
    String[] priceProductArray = {"450","80","400","50","40","150","60","30","30","20","30","40","100","20","50"};
    String[] unitProductArray = {"500 GM","1 KG","500 GM","80 GM","1 KG","200 GM","1 KG","1 KG","270 GM","100 GM","1 Scoop","1 Scoop","1 KG","500 GM","6 Items"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("Dashboard");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_home);
        //getSupportActionBar().hide();
        //getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        //getSupportActionBar().setCustomView(R.layout.custom_toolbar);
        sharedPreferences = getSharedPreferences(ConstantURL.PREF,MODE_PRIVATE);

        /*searchView = findViewById(R.id.home_search);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.trim().equalsIgnoreCase("")){
                    adapter.filter("");
                }
                else {
                    adapter.filter(newText);
                }
                return false;
            }
        });*/

        searchEdit = findViewById(R.id.home_search);

        searchEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().trim().equalsIgnoreCase("")){
                    adapter.filter("");
                    productAdapter.filter("");
                }
                else {
                    adapter.filter(s.toString());
                    productAdapter.filter(s.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });



        recyclerView = findViewById(R.id.home_category);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        arrayCategoryList = new ArrayList<>();
        //categoryNameArrayList = new ArrayList<>();
        for(int i=0;i<nameArray.length;i++){
            CategoryList list = new CategoryList();
            list.setCategoryImage(imgArray[i]);
            list.setCategoryName(nameArray[i]);
            arrayCategoryList.add(list);
            //categoryNameArrayList.add(nameArray[i]);
        }

         adapter = new CategoryAdapter(HomeActivity.this,arrayCategoryList);
        //CategoryAdapter adapter = new CategoryAdapter(HomeActivity.this,nameArray,imgArray);
        recyclerView.setAdapter(adapter);


        recyclerProductView = findViewById(R.id.home_product);
        recyclerProductView.setLayoutManager(new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.HORIZONTAL));
        recyclerProductView.setItemAnimator(new DefaultItemAnimator());

        arrayProductList = new ArrayList<>();
        //categoryNameArrayList = new ArrayList<>();
        for(int i=0;i<nameProductArray.length;i++){
            ProductList list = new ProductList();
            list.setImgProduct(imgProductArray[i]);
            list.setNameProduct(nameProductArray[i]);
            list.setPriceProduct(priceProductArray[i]);
            list.setUnitProduct(unitProductArray[i]);
            arrayProductList.add(list);
            //categoryNameArrayList.add(nameArray[i]);
        }

        productAdapter = new ProductAdapter(HomeActivity.this,arrayProductList);
        //CategoryAdapter adapter = new CategoryAdapter(HomeActivity.this,nameArray,imgArray);
        recyclerProductView.setAdapter(productAdapter);

        //autoCompleteTextView = findViewById(R.id.home_search);
        //ArrayAdapter autoAdapter = new ArrayAdapter(HomeActivity.this, android.R.layout.simple_list_item_1,categoryNameArrayList);
        //autoCompleteTextView.setThreshold(1);
        //autoCompleteTextView.setAdapter(autoAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_item,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id =item.getItemId();
        if(id == android.R.id.home){
            finishAffinity();
        }
        if (id==R.id.shopping_Cart){
            new CommonMethod(HomeActivity.this,ShoppingCartActivity.class);
        }
        if(id==R.id.message){
            new CommonMethod(HomeActivity.this,MessageActivity.class);
        }
        if (id == R.id.log_out){
            //sharedPreferences.edit().remove(ConstantURL.ID).commit();
            sharedPreferences.edit().clear().commit();
            new CommonMethod(HomeActivity.this,LoginActivity.class);
            new CommonMethod(HomeActivity.this,"Log Out Successfully");
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        finishAffinity();
    }

    private class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.MyHolder> {

        Context context;
        ArrayList<CategoryList> arrayCategoryList;
        ArrayList<CategoryList> searchList;

        public CategoryAdapter(HomeActivity homeActivity, ArrayList<CategoryList> arrayCategoryLists) {
            this.context = homeActivity;
            this.arrayCategoryList = arrayCategoryLists;
            searchList = new ArrayList<>();
            searchList.addAll(arrayCategoryList);
        }

        @NonNull
        @Override
        public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_category,parent,false);
            return new MyHolder(view);
        }

        public void filter(String s) {
            s = s.toLowerCase(Locale.getDefault());
            arrayCategoryList.clear();
            if (s.length()==0){
                arrayCategoryList.addAll(searchList);
            }
            else {
                for (CategoryList cat: searchList){
                    if (cat.getCategoryName().toLowerCase(Locale.getDefault()).contains(s)){
                        arrayCategoryList.add(cat);
                    }
                }
            }
            notifyDataSetChanged();
        }

        public class MyHolder extends RecyclerView.ViewHolder {

            ImageView imageView;
            TextView textView;

            public MyHolder(@NonNull View itemView) {
                super(itemView);
                imageView = itemView.findViewById(R.id.custom_category_image);
                textView = itemView.findViewById(R.id.custom_category_name);
            }
        }

        @Override
        public void onBindViewHolder(@NonNull MyHolder holder, int position) {
            holder.textView.setText(arrayCategoryList.get(position).getCategoryName());
            holder.imageView.setImageResource(arrayCategoryList.get(position).getCategoryImage());

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new CommonMethod(context,ProductActivity.class);
                }
            });

        }

        @Override
        public int getItemCount() {
            return arrayCategoryList.size();
        }
    }

    private class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.MyHolder1> {

        Context context1;
        ArrayList<ProductList> arrayProductList;
        ArrayList<ProductList> searchList;

        public ProductAdapter(HomeActivity homeActivity, ArrayList<ProductList> arrayProductList) {
            this.context1 = homeActivity;
            this.arrayProductList = arrayProductList;
            this.searchList = new ArrayList<>();
            searchList.addAll(arrayProductList);
        }

         @NonNull
        @Override
        public ProductAdapter.MyHolder1 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_product,parent,false);
            return new ProductAdapter.MyHolder1(view);
        }

        public void filter(String s) {
            s = s.toLowerCase(Locale.getDefault());
            arrayProductList.clear();
            if (s.length()==0){
                arrayProductList.addAll(searchList);
            }
            else {
                for (ProductList cat: searchList){
                    if (cat.getNameProduct().toLowerCase(Locale.getDefault()).contains(s)){
                        arrayProductList.add(cat);
                    }
                }
            }
            notifyDataSetChanged();
        }

        public class MyHolder1 extends RecyclerView.ViewHolder {

            ImageView imageView;
            TextView nameView,priceView;

            public MyHolder1(@NonNull View itemView) {
                super(itemView);
                imageView = itemView.findViewById(R.id.product_image);
                nameView = itemView.findViewById(R.id.product_name);
                priceView = itemView.findViewById(R.id.product_price);

            }
        }

        @Override
        public void onBindViewHolder(@NonNull MyHolder1 holder1, int position) {
            holder1.nameView.setText(arrayProductList.get(position).getNameProduct());
            holder1.imageView.setImageResource(arrayProductList.get(position).getImgProduct());
            holder1.priceView.setText(getResources().getString(R.string.RS_price)+arrayProductList.get(position).getPriceProduct()+"/"+arrayProductList.get(position).getUnitProduct());

            holder1.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context1,ProductDetailActivity.class);

                    Bundle bundle = new Bundle();
                    bundle.putString("name",arrayProductList.get(position).getNameProduct());
                    bundle.putString("price",getResources().getString(R.string.RS_price)+arrayProductList.get(position).getPriceProduct()+"/"+arrayProductList.get(position).getUnitProduct());
                    bundle.putInt("image",arrayProductList.get(position).getImgProduct());
                    intent.putExtras(bundle);
                    context1.startActivity(intent);
                }
            });

        }

        @Override
        public int getItemCount() {
            return arrayProductList.size();
        }
    }


    /*private class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.MyHolder> {

        Context context;
        String[] nameArray;
        int[] imgArray;

        public CategoryAdapter(HomeActivity homeActivity, String[] nameArray, int[] imgArray) {

            this.context = homeActivity;
            this.nameArray = nameArray;
            this.imgArray = imgArray;

        }

        @NonNull
        @Override
        public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_category,parent,false);
            return new MyHolder(view);
        }

        public class MyHolder extends RecyclerView.ViewHolder {

            ImageView imageView;
            TextView textView;

            public MyHolder(@NonNull View itemView) {
                super(itemView);
                imageView = itemView.findViewById(R.id.custom_category_image);
                textView = itemView.findViewById(R.id.custom_category_name);
            }
        }

        @Override
        public void onBindViewHolder(@NonNull HomeActivity.CategoryAdapter.MyHolder    holder, int position) {
                holder.textView.setText(nameArray[position]);
                holder.imageView.setImageResource(imgArray[position]);
        }

        @Override
        public int getItemCount() {
            return nameArray.length;
        }

    }*/
}