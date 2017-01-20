package com.example.stacjonarny.testapp.model;

import org.androidannotations.annotations.EBean;

/**
 * Created by Lenovo on 2017-01-20.
 */
@EBean(scope = EBean.Scope.Singleton)
public class TestSingleton {
    int asd;

    public int getAsd() {
        return asd;
    }

    public void setAsd(int asd) {
        this.asd = asd;
    }
}
