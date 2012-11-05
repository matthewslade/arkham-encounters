package com.m42.ahe;

import android.app.Application;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: matthew
 * Date: 2012/04/18
 * Time: 7:08 PM
 * To change this template use File | Settings | File Templates.
 */
public class ArkhamApplication extends Application {

    Random random;
    Map<String,List<ArkhamEncounter>> arkhamLocationData=null;
    Map<String,List<OtherWorldlyEncounter>> otherWorldlyData=null;

    Map<String,List<OtherWorldlyEncounter>> undrawnOtherWorldlyData=null;

    Map<String,String> otherWorlds;

    public ArkhamApplication()
    {
        super();
        random = new Random();
    }

    //loads mapping of other worldly names to accepted colours for that world
    public void loadOtherWorlds()
    {
       otherWorlds = new HashMap<String,String>();
       otherWorlds.put("Abyss", "rb");
       otherWorlds.put("The Dreamlands", "rgby");
       otherWorlds.put("Another Time", "rg");
       otherWorlds.put("City of the Great Race", "gy");
       otherWorlds.put("Another Dimension", "rgby");
       otherWorlds.put("Great Hall Of Celeano", "gb");
       otherWorlds.put("Lost Carcosa", "by");
       otherWorlds.put("Plateau of Leng", "rg");
       otherWorlds.put("R'lyeh", "ry");
       otherWorlds.put("The Underworld","bg");
       otherWorlds.put("Unknown Kadath", "ry");
       otherWorlds.put("Yuggoth", "by");
    }

    //loads data in from raw data files
    public void loadData()
    {
        loadOtherWorlds();
        //read in code
        InputStream in_s = this.getResources().openRawResource(R.raw.ae);
        BufferedReader reader = new BufferedReader(new InputStreamReader(in_s));
        arkhamLocationData = new HashMap<String, List<ArkhamEncounter>>();
        String code2="",line=null;
        String[] parsedString;
        //do while we have lines to read
        do{
            try
            {
                line = reader.readLine();
                //split the line on % which is our separator for the values
                parsedString=line.split("%");
                //trim off the excess white space on the location name
                String locationName = parsedString[0].trim();
                //if we don't have this location name then put a new list in for it, into our map
                if (!arkhamLocationData.containsKey(locationName))
                {
                    arkhamLocationData.put(locationName,new ArrayList<ArkhamEncounter>());
                }
                //if it's from miskatonic horror then grab it's subcode as code2 else code2 is arkham horror base
                if(parsedString[2].trim().equals("MH")&&parsedString.length>3)
                {
                    code2=parsedString[3];
                }
                else
                {
                    code2="AH";
                }
                //finally add in our new encounter entity
                arkhamLocationData.get(locationName).add(new ArkhamEncounter(parsedString[1].trim(),parsedString[2].trim(),code2));
            }
            catch(Exception e)
            {
                //log any lines that provide us with problems
                if (line!=null&&line.length()>1)
                    Log.i("PARSEDLINEOE",line);
                //print out any stack traces
                e.printStackTrace();
            }

        }while (line != null);

        try
        {
            reader.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        //read in code
        in_s = this.getResources().openRawResource(R.raw.oe);
        reader = new BufferedReader(new InputStreamReader(in_s));
        otherWorldlyData = new HashMap<String, List<OtherWorldlyEncounter>>();
        do{
            try
            {
                line = reader.readLine();
                //split the line on % which is our separator for the values
                parsedString=line.split("%");
                //trim off the excess white space on the location name
                String locationName = parsedString[0].trim();
                if (!parsedString[2].trim().equals("MH")){
                    parsedString[3]="AH";
                }
                //if we don't have this location name then put a new list in for it, into our map
                if (!otherWorldlyData.containsKey(locationName))
                {
                    otherWorldlyData.put(locationName,new ArrayList<OtherWorldlyEncounter>());
                }

                if (parsedString.length==5)
                {
                    code2=parsedString[4].trim();
                }
                else
                {
                    code2="z";
                }
                otherWorldlyData.get(locationName).add(new OtherWorldlyEncounter(parsedString[1].trim(),parsedString[2].trim(),parsedString[3].trim(),code2));
            }
            catch(Exception e)
            {
                if (line!=null&&line.length()>1)
                Log.i("PARSEDLINEOE",line);
                e.printStackTrace();
            }

        }while (line != null);

        shuffleOtherWorldlyLocationCards();

        try
        {
            reader.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public Map<String,List<ArkhamEncounter>> getArkhamLocationData()
    {
        if (arkhamLocationData==null)
            this.loadData();

        return arkhamLocationData;
    }

    public Map<String,List<OtherWorldlyEncounter>> getOtherWorldlyData()
    {
        if (otherWorldlyData==null)
            this.loadData();

        return otherWorldlyData;
    }
    public String getArkhamLocationCard(List<String> expansionCodes,String location, int cardsDrawn)
    {
        //construct the string starting with the name of the location in caps
        String text = "<font size=3><b>"+location+"</b></font><br><br>";
        //create a soft copy of the encounter list for this location
        List<ArkhamEncounter> encounters = new ArrayList<ArkhamEncounter>(this.getArkhamLocationData().get(location));
        ArkhamEncounter encounter = null;
        //do this for each encounter we wish to draw
        for (int i=0;i<cardsDrawn;i++)
        {

        do {
            //draw a random encounter card from the remaining cards in the list
            encounter = encounters.remove(random.nextInt(encounters.size()));
        //if the expansion codes contains both code 1 and code 2 then break this loop.
        //Or if we run out of cards before then we have a problem and we should break this loop
        } while(!(expansionCodes.contains(encounter.getExpansionCode())&&expansionCodes.contains(encounter.getExpansionCode2()))&&!encounters.isEmpty());

        if (encounters.isEmpty())
        {
            return "Something has gone horribly wrong and we couldn't find enough encounter cards to match your expansion criteria :(";
        }
        //if we're drawing more than one encounter give us some spacing and a heading
        if (cardsDrawn>1)
                text+="<u><b>Encounter "+ String.valueOf(i+1)+"</b></u><br>";
        text+=encounter.getEncounterText()+"<br><br>";
        }

        return text;
    }

    public void shuffleOtherWorldlyLocationCards()
    {
        //this runs through each list in our other worldly encounter deck and shuffles it
        for (Map.Entry<String,List<OtherWorldlyEncounter>> entry:otherWorldlyData.entrySet())
        {
            Collections.shuffle(entry.getValue());
            /*Log.i("OTHERWORLD",entry.getKey());
            for (OtherWorldlyEncounter encounter:entry.getValue())
            {
                Log.i("OTHERWORLD",encounter.toString());
            } */
        }
    }

    public String getOtherWorldlyLocationCard(List<String> expansionCodes,String location, int cardsDrawn)
    {
        String text = "";
        //set our named location to null
        List<OtherWorldlyEncounter> namedLocation= null;
        //get the list for other location cards
        List<OtherWorldlyEncounter> otherLocation = this.getOtherWorldlyData().get("Other");

        int otherEncounterPoolSize=0;
        //count the "other" cards that match our 2 expansion codes so we know what odds to draw against
        for (OtherWorldlyEncounter encounter: otherLocation)
        {
            if (expansionCodes.contains(encounter.getExpansionCode())&&expansionCodes.contains(encounter.getExpansionCode2()))
            {
                otherEncounterPoolSize++;
            }
        }
        //Log.i("OTHERWORLD",String.format("otherEncounterPoolSize %d",otherEncounterPoolSize));
        //if we hit a 1 in other pool size chance then we should shuffle this deck
        //remember the "other" pool size is the same size as the named pool size technically for this particular expansion combo
        if(random.nextInt(otherEncounterPoolSize)==7)
        {
            shuffleOtherWorldlyLocationCards();
           //Log.i("OTHERWORLD","shuffled");
            text = "<b>The Stars Are Right</b><br><br>";
        }
        //set our ratio of others to named encounters to null
        float ratio=0.0f;
        //if the location is not another dimension then we need to count the named location pool size
        if (!location.equals("Another Dimension"))
        {

            namedLocation = this.getOtherWorldlyData().get(location);
            int namedEncounterPoolSize=0;
            for (OtherWorldlyEncounter encounter: namedLocation)
            {
                if (expansionCodes.contains(encounter.getExpansionCode())&&expansionCodes.contains(encounter.getExpansionCode2()))
                {
                    namedEncounterPoolSize++;
                }
            }
            //Log.i("OTHERWORLD",String.format("namedEncounterPoolSize %d",namedEncounterPoolSize));
            if (!location.equals("The Dreamlands"))
            {
                otherEncounterPoolSize/=2;
            }
            //set our ratio for other vs named
            ratio =((float)namedEncounterPoolSize)/otherEncounterPoolSize;
            //Log.i("OTHERWORLD",String.format("ratio %f",ratio));

        }


            String otherText="";
        //for the number of cards we're drawing do this operation
        for (int j=0;j<cardsDrawn;j++)
        {
            OtherWorldlyEncounter encounter=null;
            //if our random float beats the ratio then we need an "other" card.
            // this will always happen for another dimension that has 0 as it's ratio
            if(random.nextFloat()>ratio)
            {
                //let us know that it's an other encounter
                otherText=" (Other)";
                //for our stack of other cards
                //check both expansion codes as well as the colour code then remove it and place it at the end of list
                //then break this for loop we don't need to carry on searching
                for (int i =0;i<otherLocation.size();i++)
                {
                    encounter = otherLocation.get(i);
                    //Log.i("OTHERWORLD",encounter.toString());
                    if (expansionCodes.contains(encounter.getExpansionCode())
                            &&expansionCodes.contains(encounter.getExpansionCode2())
                            &&otherWorlds.get(location).contains(encounter.colourCode))
                    {
                        otherLocation.remove(i);
                        otherLocation.add(encounter);
                    break;
                    }
                }
            }
            //else search through our named location cards
            else
            {
                otherText="";
                //for our stack of named location cards
                //check both expansion codes then remove it and place it at the end of list
                //then break this for loop we don't need to carry on searching
                for (int i =0;i<namedLocation.size();i++)
                {
                    encounter = namedLocation.get(i);
                    Log.i("OTHERWORLD",encounter.toString());
                    if (expansionCodes.contains(encounter.getExpansionCode())
                            &&expansionCodes.contains(encounter.getExpansionCode2()))
                    {
                        namedLocation.remove(i);
                        namedLocation.add(encounter);
                        break;
                    }
                }
            }


            if (cardsDrawn==1)
            {
                text+="<b>"+location+otherText+"</b><br><br>";
            }
            else if (j==0){
                text+="<b>"+location+"</b><br><br>";
            }

            if (cardsDrawn>1)
            {
                text+="<u><b>Encounter "+ String.valueOf(j+1)+otherText+"</b></u><br>";
            }
            text+=encounter.getEncounterText()+"<br><br>";
        }

        return text;
    }
}
