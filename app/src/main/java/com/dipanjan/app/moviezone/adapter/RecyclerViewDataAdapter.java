package com.dipanjan.app.moviezone.adapter;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import info.dipanjan.app.R;
import com.dipanjan.app.moviezone.activity.ListMovieItem;
import com.dipanjan.app.moviezone.activity.ListMovieSeriesTitles;
import com.dipanjan.app.moviezone.activity.MovieGerneListLayout;
import com.dipanjan.app.moviezone.model.DataModel;
import com.dipanjan.app.moviezone.model.Movie;
import com.dipanjan.app.moviezone.model.MovieSeries;
import com.dipanjan.app.moviezone.util.Constant;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class RecyclerViewDataAdapter extends RecyclerView.Adapter<RecyclerViewDataAdapter.ItemRowHolder> {

    private ArrayList<DataModel> dataList;
    private Context mContext;
    ArrayList<Movie> movieList;
    private Integer URLIndexPosition;

    public RecyclerViewDataAdapter(Context context, ArrayList<DataModel> dataList,Integer URLIndexPosition) {
        this.dataList = dataList;
        this.mContext = context;
        this.URLIndexPosition=URLIndexPosition;
    }

    @Override
    public ItemRowHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item, null);
        ItemRowHolder mh = new ItemRowHolder(v);
        return mh;
    }

    @Override
    public void onBindViewHolder(ItemRowHolder itemRowHolder, final int i) {

        final String sectionName = dataList.get(i).getSectionDataModel().getHeaderTitle();
        final String movieIdentifier = dataList.get(i).getSectionDataModel().getMovieIdentifier();
        final ArrayList<MovieSeries> movieSeriesArrayList= dataList.get(i).getMovieSeriesArrayList();
        movieList = new ArrayList<Movie>();

        ArrayList<Movie> singleSectionItems = dataList.get(i).getSectionDataModel().getAllItemsInSection();

        itemRowHolder.itemTitle.setText(sectionName);
        itemRowHolder.btnMore.setTag("" + sectionName);

        SectionListDataAdapter itemListDataAdapter = new SectionListDataAdapter(mContext, singleSectionItems,URLIndexPosition);

        itemRowHolder.recycler_view_list.setHasFixedSize(false);
        itemRowHolder.recycler_view_list.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        itemRowHolder.recycler_view_list.setNestedScrollingEnabled(false);
        itemRowHolder.recycler_view_list.setAdapter(itemListDataAdapter);
        populateMovie(singleSectionItems);

        itemListDataAdapter.notifyDataSetChanged();





         /*itemRowHolder.recycler_view_list.setOnTouchListener(new View.OnTouchListener() {
             @Override
             public boolean onTouch(View view, MotionEvent motionEvent) {
                 return false;
             }
             @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        // Disallow ScrollView to intercept touch events.
                        v.getParent().requestDisallowInterceptTouchEvent(true);
                        break;
                    case MotionEvent.ACTION_UP:
                        //Allow ScrollView to intercept touch events once again.
                        v.getParent().requestDisallowInterceptTouchEvent(false);
                        break;
                }
                // Handle RecyclerView touch events.
                v.onTouchEvent(event);
                return true;
            }
        });*/

        itemRowHolder.btnMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (movieIdentifier != null && movieIdentifier.equals(Constant.MovieCategory.MOVIE_GERNE)) {

                    Intent intent = new Intent(v.getContext(), MovieGerneListLayout.class);
                    v.getContext().startActivity(intent);
                }
                else  if (movieIdentifier != null && movieIdentifier.equals(Constant.MovieCategory.MOVIE_SERIES)) {

                    Intent intent = new Intent(v.getContext(), ListMovieSeriesTitles.class);
                    Gson gson = new Gson();
                    String json = gson.toJson(movieSeriesArrayList);
                    intent.putExtra("MOVIE_SERIES_JSON",json);
                    Log.d("@@@@@@@",json);
                    v.getContext().startActivity(intent);
                }
                else{

                    Intent intent = new Intent(v.getContext(), ListMovieItem.class);
                    intent.putExtra("CATEGORY", movieIdentifier);
                    intent.putExtra(Constant.ACTIVITY_NAME, sectionName);
                    v.getContext().startActivity(intent);
                }


            }
        });







       /* Glide.with(mContext)
                .load(feedItem.getImageURL())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .error(R.drawable.bg)
                .into(feedListRowHolder.thumbView);*/
    }

    public void populateMovie(ArrayList<Movie> singleSectionItems) {
        for (Movie movie : singleSectionItems) {
            movieList.add(movie);
        }
    }

    @Override
    public int getItemCount() {
        return (null != dataList ? dataList.size() : 0);
    }

    public class ItemRowHolder extends RecyclerView.ViewHolder {

        protected TextView itemTitle;

        protected RecyclerView recycler_view_list;

        protected Button btnMore;


        public ItemRowHolder(View view) {
            super(view);

            this.itemTitle = (TextView) view.findViewById(R.id.itemTitle);
            this.recycler_view_list = (RecyclerView) view.findViewById(R.id.recycler_view_list);
            this.btnMore = (Button) view.findViewById(R.id.btnMore);


        }

    }

}