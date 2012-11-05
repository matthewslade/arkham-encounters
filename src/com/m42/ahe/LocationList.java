package com.m42.ahe;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: matthew
 * Date: 2012/04/18
 * Time: 7:27 PM
 * To change this template use File | Settings | File Templates.
 */
public class LocationList extends Activity implements AdapterView.OnItemClickListener {

    List<String> expansionCodes;
    RadioGroup group2;
    RadioButton rad1;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.location_list);
        expansionCodes = getIntent().getStringArrayListExtra("expansions");

        String[] locationCodes=null;
        String[][] locationConstants=null;

        if (getIntent().getStringExtra("locationType").equals("AE"))
        {
            locationCodes = ArkhamConstants.arkhamLocationCodes;
            locationConstants = ArkhamConstants.arkhamLocations;
        }
        else
        {
            locationCodes = ArkhamConstants.otherWorldlyLocationCodes;
            locationConstants = ArkhamConstants.otherWorldlyLocations;
        }

        List<String> locations = new ArrayList<String>(Arrays.asList(locationConstants[0]));
        for(int i =1;i<locationCodes.length;i++)
        {
            if (expansionCodes.contains(locationCodes[i]))
            {
                locations.addAll(Arrays.asList(locationConstants[i]));
            }
        }
        //add header to list
        ListView lv = (ListView)findViewById(R.id.list_view);
        LayoutInflater inflater = getLayoutInflater();
        //View header = inflater.inflate(R.layout.footer, (ViewGroup) findViewById(R.id.footer_root));
        //lv.addFooterView(header, null, false);
        lv.setAdapter(new ArrayAdapter(this.getApplicationContext(),R.layout.list_item, locations));
        lv.setTextFilterEnabled(true);
        lv.setOnItemClickListener(this);
        //setListAdapter(new ArrayAdapter<String>(this,R.layout.list_item , locations));
        group2 = (RadioGroup)findViewById(R.id.Group2);
        rad1 = (RadioButton)findViewById(R.id.rad1);
        group2.check(0);
        rad1.setChecked(true);
    }

    public void onItemClick(AdapterView<?> parent, View view,int position, long id)
    {
        int cardsDrawn=1;
        switch (group2.getCheckedRadioButtonId())
        {
            case R.id.rad1:
                cardsDrawn=1;
                break;
            case R.id.rad2:
                cardsDrawn=2;
                break;
            case R.id.rad3:
                cardsDrawn=3;
                break;
        }

        Intent intent = new Intent(this.getApplicationContext(),EncounterTextActivity.class);
        String location = ((TextView) view).getText().toString();
        String text;
        if (getIntent().getStringExtra("locationType").equals("AE"))
        {
            text = ((ArkhamApplication)this.getApplication()).getArkhamLocationCard(expansionCodes,location,cardsDrawn);
        }
        else
        {
            text = ((ArkhamApplication)this.getApplication()).getOtherWorldlyLocationCard(expansionCodes,location,cardsDrawn);
        }
        intent.setAction(text);
        group2.check(0);
        rad1.setChecked(true);
        startActivity(intent);

    }
}