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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class TopCoinsListAdapter extends ArrayAdapter<eachItems> {

    private int layoutResource;
    private Context mFContext;

    public TopCoinsListAdapter(@NonNull Context context, int resource, @NonNull ArrayList<eachItems> objects) {
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
                //Log.d("test" , "ohkk working");
                String[] items = {"Mark as Important - " +p.getCoinName() };

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.mContext);
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        favCoinsManager fav = new favCoinsManager();
                        String data =  fav.readData(MainActivity.fileName);
                        fav.saveData(MainActivity.fileName , data+","+p.getCoinName());
                        MainActivity.mFavCoin += ","+p.getCoinName();

                        Toast.makeText(MainActivity.mContext, "Added to favourates !!", Toast.LENGTH_SHORT).show();
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });


        return view;
    }
}
