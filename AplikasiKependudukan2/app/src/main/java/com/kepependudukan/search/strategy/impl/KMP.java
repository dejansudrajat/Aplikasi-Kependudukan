package com.kepependudukan.search.strategy.impl;

import com.kepependudukan.search.domain.Result;
import com.kepependudukan.search.domain.ResultSet;
import com.kepependudukan.search.strategy.base.Strategy;

public class KMP implements Strategy {
    @Override
    public ResultSet find(String from, String target) {
        ResultSet set = new ResultSet();

        int[] inform = preprocessing(target);

        int i = 0;
        int j = 0;

        while(i < from.length()){
            if(j == -1 || from.charAt(i) == target.charAt(j)){
                i++; j++;
            }
            else {
                j = inform[j];
            }
            if( j == target.length() ) {
                set.add(new Result(i - target.length(),i-1));
                j = inform[j];
            }
        }

        return set;
    }

    private int[] preprocessing(String target) {
        int[] inform = new int[target.length()+1];

        int j = 1;
        int k = 0;

        inform[0] = -1;


        while(j < target.length()) {
            if(k == -1 || target.charAt(j) == target.charAt(k)){
                j++; k++;
                inform[j] = k;
            }
            else {
                k = inform[k];
            }
        }
        return inform;
    }
}
