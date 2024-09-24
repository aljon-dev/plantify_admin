package com.example.plantify_admin;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class ProductAdapter extends BaseAdapter {

    public ProductAdapter(){

    }

    interface  onItemClickListener{

        void onItemClick(ProductModel productModel);

    }


    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}
