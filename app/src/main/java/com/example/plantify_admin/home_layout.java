package com.example.plantify_admin;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;

import com.google.android.material.internal.NavigationMenuItemView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class home_layout extends Fragment {



    public home_layout() {
        // Required empty public constructor
    }

    private GridView productListed;
    private ArrayList<ProductModel>  productList;

    private ProductAdapter adapter;

    private FirebaseDatabase firebaseDatabase;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_layout, container, false);


        firebaseDatabase = FirebaseDatabase.getInstance();

        productListed = view.findViewById(R.id.productListed);
        productList = new ArrayList<>();
        adapter = new ProductAdapter(getContext(),productList);
        productListed.setAdapter(adapter);

        firebaseDatabase.getReference("Products").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds: snapshot.getChildren()){
                    ProductModel productModel = ds.getValue(ProductModel.class);
                    productList.add(productModel);

                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });







        return view;
    }
}