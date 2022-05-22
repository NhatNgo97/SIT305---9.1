package com.example.a91p;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.a91p.model.Advert;

import java.util.ArrayList;

public class AdvertAdapter extends BaseAdapter {
    Context context;
    ArrayList<Advert> data;
    private static LayoutInflater inflater = null;

    public AdvertAdapter(Context context, ArrayList<Advert> data) {
        this.context = context;
        this.data = data;
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Advert getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return data.get(position).getAdvertId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null)
        {
            view = inflater.inflate(R.layout.layout_advert, null);
        }
        TextView text = view.findViewById(R.id.advert_name);
        Advert advert = data.get(position);
        text.setText(advert.getAdvertPostType() + " " + advert.getAdvertName() + "...");
        return view;
    }
}
