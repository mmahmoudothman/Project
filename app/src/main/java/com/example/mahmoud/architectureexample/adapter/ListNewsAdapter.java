package com.example.mahmoud.architectureexample.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mahmoud.architectureexample.DetailArticle;
import com.example.mahmoud.architectureexample.R;
import com.example.mahmoud.architectureexample.common.ISO8601Parse;
import com.example.mahmoud.architectureexample.model.Item;
import com.github.curioustechizen.ago.RelativeTimeTextView;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.mahmoud.architectureexample.common.Const.WEB_URL;


public class ListNewsAdapter extends RecyclerView.Adapter<ListNewsAdapter.ListNewsViewHolder> {

    private List<Item> articleList;
    private Context context;


    public ListNewsAdapter(List<Item> articleList, Context context) {
        this.articleList = articleList;
        this.context = context;
    }


    @NonNull
    @Override
    public ListNewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.news_layout, parent, false);
        return new ListNewsViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull ListNewsViewHolder holder, int position) {

        if (!articleList.get(position).getThumbnail().equals("")) {
            Picasso.get()
                    .load(articleList.get(position).getThumbnail())
                    .into(holder.article_image);
        }

        if (articleList.get(position).getTitle().length() > 65)
            holder.article_title.setText(articleList.get(position).getTitle().substring(0, 65) + "...");
        else
            holder.article_title.setText(articleList.get(position).getTitle());

        if (articleList.get(position).getPubDate() != null) {
            Date date = null;
            try {
                date = ISO8601Parse.parse(articleList.get(position).getPubDate());
                holder.article_time.setReferenceTime(date.getTime());

            } catch (ParseException ex) {
                ex.printStackTrace();

            }
        }


    }

    @Override
    public int getItemCount() {

        return articleList.size();
    }

    class ListNewsViewHolder extends RecyclerView.ViewHolder {
        TextView article_title;
        RelativeTimeTextView article_time;
        CircleImageView article_image;

        public ListNewsViewHolder(View itemView) {
            super(itemView);
            article_image = (CircleImageView) itemView.findViewById(R.id.article_image);
            article_title = (TextView) itemView.findViewById(R.id.article_title);
            article_time = (RelativeTimeTextView) itemView.findViewById(R.id.article_time);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // add action here
                    Intent detail = new Intent(context, DetailArticle.class);
                    detail.putExtra(WEB_URL, articleList.get(getAdapterPosition()).getLink());
                    detail.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(detail);

                }
            });
        }


    }

}
