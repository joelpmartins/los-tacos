package com.lostacos.util.adapter;

import static com.lostacos.util.Strings.getStringByName;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.lostacos.Model.MyReserves;
import com.lostacos.R;

import java.util.ArrayList;

public class ReservesListAdapter extends ArrayAdapter<MyReserves> {
    private final Context mContext;
    private final int mResource;
    private int lastPosition = -1;

    private static class ViewHolder {
        ImageView img;
        TextView id;
        TextView date;
        TextView people;
    }

    public ReservesListAdapter(Context context, int resource, ArrayList<MyReserves> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String id = getItem(position).getId();
        String date = getItem(position).getDate();
        String hour = getItem(position).getHour();
        String people = getItem(position).getPeople();

        MyReserves myReserves = new MyReserves(id, date, hour, people);

        ViewHolder holder;

        if(convertView == null){
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(mResource, parent, false);
            holder= new ViewHolder();
            holder.img = (ImageView) convertView.findViewById(R.id.img_food);
            holder.id = (TextView) convertView.findViewById(R.id.textView1);
            holder.date = (TextView) convertView.findViewById(R.id.textView2);
            holder.people = (TextView) convertView.findViewById(R.id.textView3);

            convertView.setTag(holder);
        }
        else{
            holder = (ViewHolder) convertView.getTag();
        }

        lastPosition = position;
        holder.img.setImageResource(R.drawable.ic_qr_code);
        holder.id.setText(myReserves.getId());
        holder.date.setText(getStringByName(mContext, "data") + ": " + myReserves.getDate() + ".   " + getStringByName(mContext, "horario") + ": " + myReserves.getHour() + "h.");
        holder.people.setText(getStringByName(mContext, "pessoas") + ": " + myReserves.getPeople() + ".");
        return convertView;
    }
}