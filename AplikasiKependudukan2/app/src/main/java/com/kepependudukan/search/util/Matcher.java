package com.kepependudukan.search.util;

import com.kepependudukan.search.domain.ResultSet;
import com.kepependudukan.search.strategy.base.Strategy;

public class Matcher {
    public ResultSet find(String from, String target, Strategy strategy) {

        return strategy.find(from, target);
    }
}
