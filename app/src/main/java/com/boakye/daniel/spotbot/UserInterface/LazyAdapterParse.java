package com.boakye.daniel.spotbot.UserInterface;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.boakye.daniel.spotbot.R;
import com.parse.ParseImageView;

import java.util.List;

public class LazyAdapterParse extends ArrayAdapter<RowItemParse> {

    Context context;

    public LazyAdapterParse(Context context, int resourceId, List<RowItemParse> items){
        super(context, resourceId, items);
        this.context = context;
    }

    public class ViewHolder{
        ParseImageView image;
        TextView title;
        TextView description;
        TextView timelastSeen;
        RelativeLayout card;
    }


    public View getView(int position, View convertView, ViewGroup parent){
        ViewHolder holder;
        RowItemParse rowItem = getItem(position);

        LayoutInflater mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null){
            convertView = mInflater.inflate(R.layout.list_rowparse, null);
            holder = new ViewHolder();
            holder.card = (RelativeLayout) convertView.findViewById(R.id.card);
          holder.image = (ParseImageView) convertView.findViewById(R.id.list_image);
           // holder.image = (ImageView)convertView.findViewById(R.id.list_image);
            holder.title = (TextView)convertView.findViewById(R.id.title);
            holder.description = (TextView)convertView.findViewById(R.id.description);
            holder.timelastSeen = (TextView) convertView.findViewById(R.id.last_seen);
            convertView.setTag(holder);
        } else
            holder = (ViewHolder)convertView.getTag();

        holder.image.setParseFile(rowItem.getImageId());
        holder.image.loadInBackground();
        holder.title.setText(rowItem.getTitle());
        holder.description.setText(rowItem.getDesc());
        holder.timelastSeen.setText(rowItem.getLastSeen());

        Animation animation = AnimationUtils.loadAnimation(context, R.anim.card_animation);
        holder.card.startAnimation(animation);


        return convertView;
    }
}
