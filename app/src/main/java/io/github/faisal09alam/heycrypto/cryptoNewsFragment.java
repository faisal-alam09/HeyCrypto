package io.github.faisal09alam.heycrypto;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link cryptoNewsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class cryptoNewsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public cryptoNewsFragment() {
        // Required empty public constructor
        //Log.d("test", "newInstance: fragment News Constructor");
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment cryptoNewsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static cryptoNewsFragment newInstance(String param1, String param2) {
        cryptoNewsFragment fragment = new cryptoNewsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Log.d("test", "newInstance: fragment News");
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }


    NewsListAdapter newsAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view;
        view = inflater.inflate(R.layout.fragment_crypto_news, container, false);

        getNews nw = new getNews();
        nw.fetchJsonData();

        ListView lv = view.findViewById(R.id.newsListView);
        newsAdapter = new NewsListAdapter(MainActivity.mContext , R.layout.news_list_view_layout , MainActivity.mJsonNewsArray);
        lv.setAdapter(newsAdapter);

        //Log.d("test" , "Array : ----- HERE ");

        handler.post(runnable);

        return view;
    }

    @Override
    public void onStop() {
        handler.removeCallbacks(runnable);
        super.onStop();
    }

    @Override
    public void onResume() {
        handler.post(runnable);
        super.onResume();
    }

    @Override
    public void onPause() {
        handler.removeCallbacks(runnable);
        super.onPause();
    }

    private int handler_timer = 1000;
    private Handler handler = new Handler();
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            //Log.d("test", "Handler: Loaded ---- NewsFrag");
            newsAdapter.clear();
            newsAdapter.addAll(MainActivity.mJsonNewsArray);
            newsAdapter.notifyDataSetChanged();

            handler.postDelayed(this , handler_timer);
        }
    };

}