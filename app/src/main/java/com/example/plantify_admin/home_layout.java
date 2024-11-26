package com.example.plantify_admin;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.plantify_admin.adapter.ProductAdapter;
import com.example.plantify_admin.model.ProductModel;
import com.google.android.material.internal.NavigationMenuItemView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class home_layout extends Fragment {



    public home_layout() {
        // Required empty public constructor
    }

    private GridView productListed;
    private ArrayList<ProductModel>  productList;

    private ProductAdapter adapter;

    private FirebaseDatabase firebaseDatabase;
    private SearchView  searchView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_layout, container, false);

        firebaseDatabase = FirebaseDatabase.getInstance();
        searchView = view.findViewById(R.id.searchItem);
        productListed = view.findViewById(R.id.productListed);
        productList = new ArrayList<>();
        adapter = new ProductAdapter(getContext(), productList);
        productListed.setAdapter(adapter);

        // Handle item click
        adapter.setOnItemClickListener(new ProductAdapter.onItemClickListener() {
            @Override
            public void OnClick(ProductModel productModel) {
                SelectOption(productModel.getKey());
            }
        });

        // Fetch products from Firebase
        firebaseDatabase.getReference("Products").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                productList.clear();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    ProductModel productModel = ds.getValue(ProductModel.class);
                    productModel.setKey(ds.getKey());
                    productList.add(productModel);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                filterProductList(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterProductList(newText);
                return true;
            }
        });

        return view;
    }

    private void filterProductList(String query) {
        if (query.isEmpty()) {

            adapter.updateList(productList);
        } else {
            ArrayList<ProductModel> filteredList = new ArrayList<>();
            for (ProductModel product : productList) {
                if (product.getProductName().toLowerCase().contains(query.toLowerCase())) {
                    filteredList.add(product);
                }
            }

            adapter.updateList(filteredList);
        }
    }
    private void SelectOption(String id){

        AlertDialog.Builder OptionSelect = new AlertDialog.Builder(getContext());

        CharSequence options [] = {"Edit","Delete"};

        OptionSelect.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch(which){
                    case 0:
                        EditProduct(id);
                        break;
                    case 1:
                        firebaseDatabase.getReference("Products").child(id).removeValue();
                        break;
                    default:
                }

            }
        });
        OptionSelect.show();

    }

    private void EditProduct(String id ){

        AlertDialog.Builder EditProducts = new AlertDialog.Builder(getContext());

        View view = LayoutInflater.from(getContext()).inflate(R.layout.edit_product_layout,null,false);

        TextInputEditText productName, productPrice,productQuantity;

        productName = view.findViewById(R.id.productName);
        productPrice = view.findViewById(R.id.productPrice);
        productQuantity = view.findViewById(R.id.productQuantity);

        EditProducts.setView(view);


        firebaseDatabase.getReference("Products").child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ProductModel productModel = snapshot.getValue(ProductModel.class);
                productName.setText(productModel.getProductName());
                productPrice.setText(productModel.getPrice());
                productQuantity.setText(productModel.getQuantity());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        EditProducts.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Map<String,Object> UpdateProducts = new HashMap<>();
                UpdateProducts.put("Price",productPrice.getText().toString());
                UpdateProducts.put("ProductName",productName.getText().toString());
                UpdateProducts.put("Quantity",productQuantity.getText().toString());

             firebaseDatabase.getReference("Products").child(id).updateChildren(UpdateProducts);
                Toast.makeText(getContext(), "Product Updated", Toast.LENGTH_SHORT).show();



            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

             dialog.dismiss();
            }
        });
        EditProducts.show();







    }

}