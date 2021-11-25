package io.github.faisal09alam.heycrypto;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class NewsListAdapter extends ArrayAdapter<eachNewsItems> {

    private int layoutResource;

    public NewsListAdapter(@NonNull Context context, int resource, @NonNull ArrayList objects) {
        super(context, resource, objects);

        this.layoutResource = resource;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view;
        view = convertView;

        if( view == null ) {
            LayoutInflater layoutInflater = LayoutInflater.from(MainActivity.mContext);
            view = layoutInflater.inflate(layoutResource , null);
        }

        eachNewsItems p = getItem(position);

        TextView nTitle = view.findViewById(R.id.newsTitle);
        TextView nComments = view.findViewById(R.id.newsComments);
        TextView nLikes = view.findViewById(R.id.newsLikes);
        TextView nDislikes = view.findViewById(R.id.newsDislikes);


        nTitle.setText(p.getTitle());
        nComments.setText(p.getComments());
        nLikes.setText(p.getLikes());
        nDislikes.setText(p.getDisLikes());


        return view;
    }

}
