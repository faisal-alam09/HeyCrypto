package io.github.faisal09alam.heycrypto;


import android.app.DownloadManager;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import androidx.annotation.LongDef;

//Only USDT pairs are available as of now.
public class getCoinList {
    private RequestQueue rq;


    //APIs
    //Binance APIs
    private String listCoinsAPI = "https://www.binance.com/bapi/asset/v2/public/asset-service/product/get-products?includeEtf=true";


    getCoinList(RequestQueue rq) {
        this.rq = rq;
    }


    public void fetchJsonData() {

        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, listCoinsAPI, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            //MainActivity.JsonLiveArr.setValue(response.getJSONArray("data"));
                            Log.d("test", "Response---- : " + ( (JSONObject) response.getJSONArray("data").get(0) ));
                        } catch (JSONException e) {
                            //Log.d("test" , "error : in Response ----");
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Log.d("test" , "Error : idk");
            }
        });

        rq.add(req);
    }
}
