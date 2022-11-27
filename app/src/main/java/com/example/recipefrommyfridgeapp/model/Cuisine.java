package com.example.recipefrommyfridgeapp.model;

import java.util.Locale;

public class Cuisine {

    public String enName, zhName;

    public Cuisine(String enName, String zhName) {
        this.enName = enName;
        this.zhName = zhName;
    }

    public Cuisine() {
    }

    public String getName() {
        if (Locale.getDefault().getLanguage().equals("en")) {
            return enName;
        } else {
            return zhName;
        }
    }

    public String getEnName() {
        return enName;
    }

    public void setEnName(String enName) {
        this.enName = enName;
    }

    public String getZhName() {
        return zhName;
    }

    public void setZhName(String zhName) {
        this.zhName = zhName;
    }
}
