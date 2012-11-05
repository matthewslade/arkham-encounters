package com.m42.ahe;

import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

/**
 * Created by IntelliJ IDEA.
 * User: matthew
 * Date: 2012/04/18
 * Time: 8:02 PM
 * To change this template use File | Settings | File Templates.
 */
public class EncounterTextActivity extends Activity{
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.encounter_text);
        TextView textView = (TextView) findViewById(R.id.cardtext);
        
        textView.setText(Html.fromHtml(this.getIntent().getAction()));
        textView.setMovementMethod(new ScrollingMovementMethod());
    }
}
