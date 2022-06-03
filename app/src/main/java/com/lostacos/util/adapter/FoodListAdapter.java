package com.lostacos.util.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import com.lostacos.Model.Food;
import com.lostacos.R;
import java.util.ArrayList;

public class FoodListAdapter extends ArrayAdapter<Food> {
    private final Context mContext;
    private final int mResource;
    private int lastPosition = -1;

    private static class ViewHolder {
        ImageView img;
        TextView name;
        TextView desc;
        TextView price;
    }

    public FoodListAdapter(Context context, int resource, ArrayList<Food> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String name = getItem(position).getName();
        String desc = getItem(position).getDesc();
        String price = getItem(position).getPrice();
        
        Food food = new Food(name,desc,price);

        ViewHolder holder;

        if(convertView == null){
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(mResource, parent, false);
            holder= new ViewHolder();
            holder.img = (ImageView) convertView.findViewById(R.id.img_food);
            holder.name = (TextView) convertView.findViewById(R.id.textView1);
            holder.desc = (TextView) convertView.findViewById(R.id.textView2);
            holder.price = (TextView) convertView.findViewById(R.id.textView3);

            convertView.setTag(holder);
        }
        else{
            holder = (ViewHolder) convertView.getTag();
        }

        lastPosition = position;
        String variableValue = food.getName().toLowerCase();
        holder.img.setImageResource(mContext.getResources().getIdentifier(variableValue, "drawable", mContext.getPackageName()));
        holder.name.setText(food.getName());
        holder.desc.setText(food.getDesc());
        holder.price.setText("R$ " + food.getPrice().toUpperCase());
        return convertView;
    }
}