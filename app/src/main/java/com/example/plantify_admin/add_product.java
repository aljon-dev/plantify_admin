package com.example.plantify_admin;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class add_product extends Fragment {


    public add_product() {
        // Required empty public constructor
    }


    MaterialButton uploadPhoto,sendProduct;
    TextInputEditText productName,productPrice,Quantity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View  view = inflater.inflate(R.layout.fragment_add_product, container, false);
        
        productName = view.findViewById(R.id.productName);
        productPrice = view.findViewById(R.id.productPrice);
        Quantity = view.findViewById(R.id.productQuantity);


        uploadPhoto = view.findViewById(R.id.UploadPhoto);
        sendProduct = view.findViewById(R.id.SubmitProduct);


        




        return view;
    }
}