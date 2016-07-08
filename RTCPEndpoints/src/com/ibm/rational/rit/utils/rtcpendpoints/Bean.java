package com.ibm.rational.rit.utils.rtcpendpoints;

import java.util.ArrayList;
import java.util.List;

public class Bean {

    private String name;
    private String clazz;
    private List<String> requestMappings;

    public Bean(String clazz) {
        this.clazz = clazz;
        requestMappings = new ArrayList<String>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClazz() {
        return clazz;
    }

    public void setClazz(String clazz) {
        this.clazz = clazz;
    }

    public List<String> getRequestMappings() {
        return requestMappings;
    }

    public void setRequestMappings(List<String> requestMappings) {
        this.requestMappings = requestMappings;
    }

    public String toString() {

        // Non-collection variables
        String msg = String.format("name=%s, class=%s", name, clazz);

        // List of requestMappings
        msg += ", requestMappings=[";
        msg += requestMappings.isEmpty() ? "]" : "";
        for (int i = 0; i < requestMappings.size(); i++) {
            msg += String.format("%s", requestMappings.get(i));
            msg += i == requestMappings.size() - 1 ? "]" : ",";
        }

        return msg;
    }

}
