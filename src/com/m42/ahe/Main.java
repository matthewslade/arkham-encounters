package com.m42.ahe;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

public class Main extends Activity implements DialogInterface.OnClickListener
{
    /** Called when the activity is first created. */

    CheckBox dh,kh,ih,mh,bg,lt,cp,ky;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        dh = (CheckBox)findViewById(R.id.DH);
        ih = (CheckBox)findViewById(R.id.IH);
        mh = (CheckBox)findViewById(R.id.MH);
        kh = (CheckBox)findViewById(R.id.KH);
        bg = (CheckBox)findViewById(R.id.BG);
        lt = (CheckBox)findViewById(R.id.LT);
        cp = (CheckBox)findViewById(R.id.CP);
        ky = (CheckBox)findViewById(R.id.KY);
    }

    public void displayArkhamLocations(View view)
    {
        Intent intent = new Intent(getApplicationContext(),LocationList.class);
        intent.putStringArrayListExtra("expansions",(ArrayList<String>)this.getExpansionCodes());
        intent.putExtra("locationType","AE");
        startActivity(intent);
    }

    public void displayOtherWorldlyLocations(View view)
    {
        Intent intent = new Intent(getApplicationContext(),LocationList.class);
        intent.putStringArrayListExtra("expansions",(ArrayList<String>)this.getExpansionCodes());
        intent.putExtra("locationType","OE");
        startActivity(intent);
    }


    public List<String> getExpansionCodes()
    {
        List<String> expansionCodes= new ArrayList<String>();
        if (dh.isChecked())
            expansionCodes.add("DH");
        if (kh.isChecked())
            expansionCodes.add("KH");
        if (ih.isChecked())
            expansionCodes.add("IH");
        if (mh.isChecked())
            expansionCodes.add("MH");
        if (bg.isChecked())
            expansionCodes.add("BG");
        if (lt.isChecked())
            expansionCodes.add("LT");
        if (cp.isChecked())
            expansionCodes.add("CP");
        if (ky.isChecked())
            expansionCodes.add("KY");
        expansionCodes.add("AH");
        return expansionCodes;

    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to exit Arkham Encounters?").setPositiveButton("Yes", this)
                .setNegativeButton("No", this).show();
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        switch (which){
            case DialogInterface.BUTTON_POSITIVE:
                this.finish();
                break;

            case DialogInterface.BUTTON_NEGATIVE:
                //No button clicked
                break;
        }
    }
}
