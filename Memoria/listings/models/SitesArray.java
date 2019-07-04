package com.alehp.ull_navigation.Models;

import java.io.Serializable;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class SitesArray implements Serializable {


    private ArrayList<ULLSiteSerializable> ullSiteSerializables;

    public SitesArray(ArrayList<ULLSite> ullSites) {
        if(ullSites != null) {
            ullSiteSerializables = new ArrayList<ULLSiteSerializable>();
            for (int i = 0; i < ullSites.size(); i++) {
                ullSiteSerializables.add(new ULLSiteSerializable(ullSites.get(i)));
            }
        }
    }

    public ArrayList<ULLSiteSerializable> getUllSiteSerializables() {
        return ullSiteSerializables;
    }

    public void setUllSiteSerializables(ArrayList<ULLSiteSerializable> ullSiteSerializables) {
        this.ullSiteSerializables = ullSiteSerializables;
    }

    public ULLSiteSerializable get(int i) {
        return this.ullSiteSerializables.get(i);
    }


}
