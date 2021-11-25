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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link cryptoHomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class cryptoHomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public cryptoHomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment cryptoHomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static cryptoHomeFragment newInstance(String param1, String param2) {
        cryptoHomeFragment fragment = new cryptoHomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);

        return fragment;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

//    private RequestQueue mReqQ;
    public ListView ls;
    public HomeFavCoinAdapter coinAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_crypto_home , container , false);
        ls = v.findViewById(R.id.coinsListView);

        coinAdapter = new HomeFavCoinAdapter(MainActivity.mContext , R.layout.coin_list_view_layout , filterData(MainActivity.mFavCoin)  );
        ls.setAdapter(coinAdapter);

        handler.post(runnable);

        return v;
    }

    private int handler_timer = 1000;
    private Handler handler = new Handler();
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            coinAdapter.clear();
            coinAdapter.addAll(filterData( MainActivity.mFavCoin ));
            coinAdapter.notifyDataSetChanged();
            handler.postDelayed(this , handler_timer);

        }
    };

    @Override
    public void onResume() {
        handler.removeCallbacks(runnable);
        handler.post(runnable);
        super.onResume();
    }

    @Override
    public void onStop() {
        handler.removeCallbacks(runnable);
        super.onStop();
    }

    @Override
    public void onPause() {
        handler.removeCallbacks(runnable);
        super.onPause();
    }

    private ArrayList filterData(String str) {
        ArrayList<eachItems> arr = new ArrayList<>();
        List favCoinData = Arrays.asList(MainActivity.mFavCoin.split(","));
        ArrayList jsonArr = MainActivity.mJsonArray;
        for (int i=0;i<jsonArr.size(); i++ ) {
            for (int j=0;j<favCoinData.size();j++) {
                try {
                    eachItems ech = (eachItems) MainActivity.mJsonArray.get(i);
                    if( ech.getCoinName().equals( favCoinData.get(j) )  ) {
                        arr.add( ech );
                    }
                } catch (Exception e ) {
                }
            }
        }
        return arr;
    }

}