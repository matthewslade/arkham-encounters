package com.m42.ahe;

/**
 * Created by IntelliJ IDEA.
 * User: matthew
 * Date: 2012/04/18
 * Time: 5:31 PM
 * To change this template use File | Settings | File Templates.
 */
public class OtherWorldlyEncounter  extends ArkhamEncounter{
    String colourCode;

    public OtherWorldlyEncounter(String encounterText, String expansionCode, String expansionCode2,String colourCode) {
        super(encounterText,expansionCode,expansionCode2);
        this.colourCode = colourCode;
    }

    public String getColourCode() {
        return colourCode;
    }

    public void setColourCode(String colourCode) {
        this.colourCode = colourCode;
    }

    @Override
    public String toString() {
        return "colourCode='" + colourCode +"\' "+ super.toString();
    }
}
