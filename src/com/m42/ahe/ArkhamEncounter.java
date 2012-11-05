package com.m42.ahe;

/**
 * Created by IntelliJ IDEA.
 * User: matthew
 * Date: 2012/04/18
 * Time: 5:31 PM
 * To change this template use File | Settings | File Templates.
 */
public class ArkhamEncounter {
    String encounterText;
    String expansionCode;
    String expansionCode2;

    public ArkhamEncounter(String encounterText, String expansionCode, String expansionCode2) {
        this.encounterText = encounterText;
        this.expansionCode = expansionCode;
        this.expansionCode2 = expansionCode2;
    }

    public String getEncounterText() {
        return encounterText;
    }

    public void setEncounterText(String encounterText) {
        this.encounterText = encounterText;
    }

    public String getExpansionCode() {
        return expansionCode;
    }

    public void setExpansionCode(String expansionCode) {
        this.expansionCode = expansionCode;
    }

    public String getExpansionCode2() {
        return expansionCode2;
    }

    public void setExpansionCode2(String expansionCode2) {
        this.expansionCode2 = expansionCode2;
    }

    @Override
    public String toString() {
        return "encounterText='" + encounterText + '\'' +
                ", expansionCode='" + expansionCode + '\'' +
                ", expansionCode2='" + expansionCode2 + '\'';
    }
}
