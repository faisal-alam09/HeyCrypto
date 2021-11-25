package io.github.faisal09alam.heycrypto;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class HomeFavCoinAdapter extends ArrayAdapter<eachItems> {
    private int layoutResource;
    private Context mFContext;

    public HomeFavCoinAdapter(@NonNull Context context, int resource, @NonNull ArrayList<eachItems> objects) {
        super(context, resource, objects);
        this.mFContext = context;
        this.layoutResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view;
        view = convertView;

        if( view == null ) {
            LayoutInflater layoutInflater = LayoutInflater.from(mFContext);
            view = layoutInflater.inflate(layoutResource , null);
        }

        eachItems p = getItem(position);

        //Get Views
        TextView cName = view.findViewById(R.id.coinNameView);
        TextView cPrice = view.findViewById(R.id.coinPriceView);
        TextView cBase = view.findViewById(R.id.baseCoin);
        TextView cVol = view.findViewById(R.id.volume);
        TextView cPercentage24Chng = view.findViewById(R.id.percentageChange24hour);
        LinearLayout lv = view.findViewById(R.id.linearLayoutEachItem);

        cName.setText( p.getCoinName() );
        cPrice.setText( Double.toString(p.getPrice()) );
        cBase.setText(p.getBaseCoinName());
        cVol.setText( "Vol "+p.getCoinVolume() );

        //Percentage Change 24H
        if ( p.getPercentageChange24H() >= 0 ) {
            cPercentage24Chng.setBackgroundColor(Color.parseColor("#4CAF50"));
        } else {
            cPercentage24Chng.setBackgroundColor(Color.parseColor("#FB1236"));
        }
        DecimalFormat df = new DecimalFormat("#0.00");
        String tChange24H = df.format( p.getPercentageChange24H() );
        cPercentage24Chng.setText( tChange24H+"%" );
        //End ----

        //SetOnClickListner + Dialogue Box (Saving choices)
        lv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] items = {"Remove as Important - " +p.getCoinName() };

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.mContext);
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        favCoinsManager fav = new favCoinsManager();
                        String data =  fav.readData(MainActivity.fileName);

                        data = removefromList(data , p.getCoinName());
                        fav.saveData(MainActivity.fileName , data);
                        MainActivity.mFavCoin = data;

                        Toast.makeText(MainActivity.mContext, "Removed from favourates !!", Toast.LENGTH_SHORT).show();

                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });


        return view;
    }

    private String removefromList(String mList , String removeItem) {
        List list = Arrays.asList(mList.split(","));
        String out = "";
        for (int i=0; i<list.size(); i++) {
            if( !list.get(i).equals(removeItem) ) {
                out += list.get(i)+",";
            }
        }
        out = out.substring( 0, out.length()-1 );
        return out;
    }

}
