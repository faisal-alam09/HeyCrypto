package io.github.faisal09alam.heycrypto;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ListView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link cryptoTopFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class cryptoTopFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public cryptoTopFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment cryptoTopFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static cryptoTopFragment newInstance(String param1, String param2) {
        cryptoTopFragment fragment = new cryptoTopFragment();
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

    public ListView ls;
    public boolean FragmentStatus = false;
    TopCoinsListAdapter coinAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_crypto_top , container , false);

        ls = v.findViewById(R.id.coinsListView);

        coinAdapter = new TopCoinsListAdapter(MainActivity.mContext , R.layout.coin_list_view_layout , MainActivity.mJsonArray );
        ls.setAdapter(coinAdapter);

        handler.post(runnable);

        return v;
    }

    private void updateStatus() {
        if ( (MainActivity.mJsonNewsArray.size() > 2) && (!FragmentStatus) ) {
            FragmentStatus = true;
            coinAdapter.notifyDataSetChanged();
        } else {
            coinAdapter.clear();
            coinAdapter.addAll(MainActivity.mJsonArray);
            coinAdapter.notifyDataSetChanged();
        }
    }

    private int handler_timer = 1000;
    private Handler handler = new Handler();
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            updateStatus();
            handler.postDelayed(this , handler_timer);

        }
    };

    @Override
    public void onResume() {
        handler.removeCallbacks(runnable);
        handler.post(runnable);
        FragmentStatus = true;
        super.onResume();
    }

    @Override
    public void onStop() {
        FragmentStatus = false;
        handler.removeCallbacks(runnable);
        super.onStop();
    }

    @Override
    public void onPause() {
        FragmentStatus = false;
        handler.removeCallbacks(runnable);
        super.onPause();
    }


}