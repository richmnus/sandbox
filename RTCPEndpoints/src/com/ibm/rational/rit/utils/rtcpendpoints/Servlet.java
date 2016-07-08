package com.ibm.rational.rit.utils.rtcpendpoints;

import java.util.ArrayList;
import java.util.List;

public class Servlet {

    private String name;
    private String servletClass;
    private List<String> urlMappings;
    private List<Bean> beans;

    public Servlet(String name, String servletClass) {
        this.name = name;
        this.servletClass = servletClass;
        urlMappings = new ArrayList<String>();
        beans = new ArrayList<Bean>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getUrlMappings() {
        return urlMappings;
    }

    public void setUrlMappings(List<String> urlMappings) {
        this.urlMappings = urlMappings;
    }

    public String getServletClass() {
        return servletClass;
    }

    public void setServletClass(String servletClass) {
        this.servletClass = servletClass;
    }

    public List<Bean> getBeans() {
        return beans;
    }

    public void setBeans(List<Bean> beans) {
        this.beans = beans;
    }

    public String toString() {

        // Non-collection variables
        String msg = String.format("name=%s, class=%s", name, servletClass);

        // List of urlMappings
        msg += ", urlMappings=[";
        msg += urlMappings.isEmpty() ? "]" : "";
        for (int i = 0; i < urlMappings.size(); i++) {
            msg += String.format("%s", urlMappings.get(i));
            msg += i == urlMappings.size() - 1 ? "]" : ",";
        }

        // List of beans
        msg += ", beans=[";
        msg += beans.isEmpty() ? "]" : "";
        for (int i = 0; i < beans.size(); i++) {
            msg += String.format("%s", beans.get(i));
            msg += i == beans.size() - 1 ? "]" : ",";
        }

        return msg;
    }

}
