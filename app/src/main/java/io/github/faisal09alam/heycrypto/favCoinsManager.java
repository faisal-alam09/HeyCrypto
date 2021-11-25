package io.github.faisal09alam.heycrypto;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class favCoinsManager {
    public void saveData(String fileName , String data) {
        try {
            FileOutputStream fos  = MainActivity.mContext.openFileOutput( fileName , Context.MODE_PRIVATE );
            fos.write(data.getBytes());
            fos.close();

        } catch (FileNotFoundException e) {
            //Log.d("test" , "Error while saving data - fileNotFound ");
            e.printStackTrace();
        } catch (IOException e) {
            //Log.d("test" , "Error while saving data - IOException ");
            e.printStackTrace();
        }
    }

    public String readData( String fileName ) {
        try {
            FileInputStream fis = MainActivity.mContext.openFileInput(fileName);
            int c;
            String temp = "";

            while( (c = fis.read()) != -1 ) {
                temp = temp +Character.toString((char)c);
            }
            return temp;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "null_too0";
    }

}
