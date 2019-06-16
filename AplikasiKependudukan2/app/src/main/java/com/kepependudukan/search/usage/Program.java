package com.kepependudukan.search.usage;

import android.os.Build;
import android.support.annotation.RequiresApi;

import com.kepependudukan.search.domain.ResultSet;
import com.kepependudukan.search.strategy.impl.RK;
import com.kepependudukan.search.util.Matcher;

public class Program {
    @RequiresApi(api = Build.VERSION_CODES.N)
    public static void main(String[] args) {

        Matcher matcher = new Matcher();

        ResultSet result = matcher.find("Hello, world !! It is sample for testing string matching algorithm.", "in", new RK());

        if (!result.found()) {

            System.out.println("Cannot find :(");
            return;
        }
        //result.results().forEach(System.out::println);
    }
}
