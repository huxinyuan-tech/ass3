package com.unsw.infs3634.ass3;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class Weight implements Serializable {

    private String imperial;
    private String metric;

    public Weight(@JsonProperty("imperial") String imperial,
                  @JsonProperty("metric") String metric) {
        this.imperial = imperial;
        this.metric = metric;
    }

    public String getImperial() {
        return imperial;
    }

    public String getMetric() {
        return metric;
    }
}
