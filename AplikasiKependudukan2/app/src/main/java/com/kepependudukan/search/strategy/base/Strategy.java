package com.kepependudukan.search.strategy.base;

import com.kepependudukan.search.domain.ResultSet;

public interface Strategy {
    ResultSet find(String from, String target);

}
