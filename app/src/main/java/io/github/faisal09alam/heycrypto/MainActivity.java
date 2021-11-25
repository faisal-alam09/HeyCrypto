package io.github.faisal09alam.heycrypto;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bnv;
    public static RequestQueue mReqQ;
    public static Context mContext;
    public static ArrayList mJsonArray = new ArrayList();
    public static int mJsonArray_id = 0;

    public static ArrayList mJsonNewsArray = new ArrayList();

    public static String fileName = "mFavCoinsList.txt";
    public static String  mFavCoin;

    private String listCoinsAPI = "https://www.binance.com/bapi/asset/v2/public/asset-service/product/get-products?includeEtf=true";
    private String market_api =  "https://api.binance.com/api/v3/ticker/24hr";
    private String newsAPI = "https://cryptopanic.com/api/v1/posts/?kind=news&auth_token="; //Add NewApi Token Here


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;

        mReqQ = Volley.newRequestQueue(this);
        favCoinsManager fav = new favCoinsManager();
        mFavCoin =  fav.readData(MainActivity.fileName);

        getSupportFragmentManager().beginTransaction().replace(R.id.framelayout_fragment , new cryptoHomeFragment()).commit();

        bnv = (BottomNavigationView) findViewById(R.id.bottomNavigation);
        bnv.setSelectedItemId(R.id.cryptoHome);
        bnv.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment temp=null;

                switch (item.getItemId()) {
                    case R.id.cryptoTop:temp = new cryptoTopFragment();
                    break;
                    case R.id.cryptoHome: temp = new cryptoHomeFragment();
                    break;
                    case R.id.cryptoNews: temp = new cryptoNewsFragment();
                }

                getSupportFragmentManager().beginTransaction().replace(R.id.framelayout_fragment , temp).commit();

                return true;
            }
        });

        handler.post(runnable);

    }

    @Override
    protected void onResume() {
        handler.removeCallbacks(runnable);
        handler.post(runnable);
        super.onResume();
    }

    @Override
    protected void onPause() {
        handler.removeCallbacks(runnable);
        super.onPause();
    }

    private int handler_timer = 5000;
    private Handler handler = new Handler();
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            getCoinData();
            handler.postDelayed(this , handler_timer);
        }
    };


    private void getCoinData() {
        favCoinsManager fav = new favCoinsManager();
        String dataCoins = fav.readData(MainActivity.fileName);

        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, listCoinsAPI, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            mJsonArray = filterData( response.getJSONArray("data") );
                            mJsonArray_id = (int)(Math.random() * 100000 );
                            if (mJsonArray_id == 0) {mJsonArray_id = 1;}
                        } catch (JSONException e) {
                            mJsonArray = null;
                            mJsonArray_id = 1;
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mJsonArray = null;
                mJsonArray_id = 1;
            }
        });

        mReqQ.add(req);
    }

    private ArrayList filterData(JSONArray ja) throws JSONException {
        ArrayList<eachItems> arr = new ArrayList<>();
        DecimalFormat df = new DecimalFormat("#0.00");
        int len = ja.length();
        for(int i=0; i<len; i++) {
            JSONObject temp = (  (JSONObject)ja.get(i)  );
            if (temp.getString("q").equals("USDT")) {
                String tvol = df.format( temp.getDouble("qv") );
                double percentageChange =  ( (temp.getDouble("o") - temp.getDouble("c"))/ temp.getDouble("o") )*100;
                if( temp.getDouble("o") <= temp.getDouble("c") ) {
                    percentageChange = percentageChange * -1;
                }
                arr.add( new eachItems( temp.getString("b") , temp.getDouble("c")  , temp.getString("q") ,  tvol , percentageChange ) );
            }
        }
        return arr;
    }
}