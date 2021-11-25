package io.github.faisal09alam.heycrypto;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class getNews {
    private RequestQueue rq;
    private String NEWS_API = "https://cryptopanic.com/api/v1/posts/?kind=news&auth_token=";  //Paste Token Here
    private String NEWS_API_TRENDING = "https://cryptopanic.com/api/v1/posts/?filter=rising&auth_token="; //Paste Token Here


    public void fetchJsonData() {
        JsonObjectRequest jreq = new JsonObjectRequest(Request.Method.GET, NEWS_API_TRENDING, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            MainActivity.mJsonNewsArray = filteredData(  response.getJSONArray("results")  );
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                });

        MainActivity.mReqQ.add(jreq);
    }

    public void fetchJsonData(String next_url) {
        JsonObjectRequest jreq = new JsonObjectRequest(Request.Method.GET, next_url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            MainActivity.mJsonNewsArray.addAll(filteredData( response.getJSONArray("results") ));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                });

        MainActivity.mReqQ.add(jreq);
    }

    private ArrayList filteredData(JSONArray ja) throws JSONException {
        ArrayList<eachNewsItems> arr = new ArrayList<>();

        for(int i=0; i<ja.length(); i++) {
            JSONObject temp = (  (JSONObject)ja.get(i)  );

            JSONObject votes = temp.getJSONObject("votes");
            arr.add(new eachNewsItems(
                    temp.getString("title") ,
                    temp.getString("url") ,
                    temp.getString("created_at") ,
                    votes.getString("comments") ,
                    votes.getString("liked") ,
                    votes.getString("disliked")

            ));

        }

        return arr;
    }


}
