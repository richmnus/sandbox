package com.ibm.rational.rit.utils.rtcpendpoints;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.Namespace;
import org.jdom2.input.SAXBuilder;

public class RTCPEndpoints {

    private List<Servlet> servlets = new ArrayList<Servlet>();

    public RTCPEndpoints() {
        servlets = new ArrayList<Servlet>();
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        RTCPEndpoints endpointFinder = new RTCPEndpoints();
        endpointFinder.findServlets();
        endpointFinder.findBeans();
        endpointFinder.findRequestMappings();
        // System.out.println(endpointFinder);
        endpointFinder.printResults();
    }

    private String projectLoc = "c:\\Work\\workspaces\\eclipse-modeling-kepler-SR2-server-mainline\\";
    private String webInf = "com.greenhat.server.container.web\\war\\WEB-INF\\";
    private String webXml = "web.xml";
    private String restControllerName = "RestServiceController";

    /**
     * Reads the web.xml file into a SAX parser and extracts every 'servlet' and
     * 'servlet-mapping'. Then attempts to match every servlet-mapping found
     * with a servlet and then writes the associated url-pattern into the
     * servlet object.
     */
    private void findServlets() {
        SAXBuilder builder = new SAXBuilder();
        FileInputStream in = null;
        Document doc = null;

        // Read the web.xml file that contains the servlet descriptions
        try {
            in = new FileInputStream(projectLoc + webInf + webXml);
            doc = builder.build(in);
        } catch (JDOMException | IOException e) {
            e.printStackTrace();
        }

        // Get the servlet and servlet-mapping elements
        List<Element> elServlets = doc.getRootElement().getChildren("servlet");
        List<Element> elServletMappings = doc.getRootElement().getChildren(
                "servlet-mapping");

        // Create a new Servlet object for each servlet found
        for (Element elServlet : elServlets) {
            String name = "";
            String servletClass = "";
            name += elServlet.getChildText("servlet-name");
            servletClass += elServlet.getChildText("servlet-class");
            Servlet servlet = new Servlet(name, servletClass);
            servlets.add(servlet);
        }

        // Scan each servlet-mapping and attempt to find a corresponding servlet
        for (Element elServletMapping : elServletMappings) {
            Servlet servlet;
            // If a match is found then record the url-pattern in the servlet
            // object
            if ((servlet = findServlet(elServletMapping
                    .getChildText("servlet-name"))) != null) {
                String servletMapping = elServletMapping
                        .getChildText("url-pattern");
                if (servletMapping != null) {
                    servlet.getUrlMappings().add(servletMapping);
                }
            }
            // Otherwise report it and ignore it
            else {
                System.out.printf(
                        "No matching servlet found for servlet mapping %s\n",
                        elServletMapping.getChildText("url-pattern"));
            }
        }
    }

    /**
     * Iterates through the list of servlets and tries to find its xml
     * definition, and if found creates a Bean object for each Spring-managed
     * bean and adds it to the list of beans associated with that servlet
     * object.
     */
    private void findBeans() {
        FileInputStream in = null;
        Document doc = null;

        for (Servlet servlet : servlets) {
            boolean found = true;
            String filename = projectLoc + webInf + servlet.getName()
                    + "-servlet.xml";

            // Try reading in the servlet's xml file
            try {
                in = new FileInputStream(filename);
            } catch (FileNotFoundException e) {
                found = false;
                System.out.printf("File %s not found\n", filename);
            }
            if (!found) {
                continue;
            }

            // Parse the xml file
            SAXBuilder builder = new SAXBuilder();
            Namespace xmlns = Namespace
                    .getNamespace("http://www.springframework.org/schema/beans");
            try {
                doc = builder.build(in);
            } catch (JDOMException | IOException e) {
                e.printStackTrace();
            }

            // Get all the Spring-managed bean elements in this servlet's
            // definition
            List<Element> elBeans = doc.getRootElement().getChildren("bean",
                    xmlns);
            for (Element elBean : elBeans) {
                List<Attribute> attributes = elBean.getAttributes();
                String className = "";
                for (Attribute attribute : attributes) {
                    // Assume each bean only has a single class attribute
                    className = attribute.getName() == "class" ? attribute
                            .getValue() : null;
                }
                if (className != null) {
                    Bean bean = new Bean(className);
                    servlet.getBeans().add(bean);
                }
            }
        }

    }

    /**
     * Iterates through the list of servlets and returns the servlet object
     * who's name matches the specified string.
     * 
     * @param name
     *            Specified servlet name to match
     * @return The matching servlet object, else null
     */
    private Servlet findServlet(String name) {
        for (Servlet servlet : servlets) {
            if (name.equals(servlet.getName())) {
                return servlet;
            }
        }
        return null;
    }

    private void findRequestMappings() {
        for (Servlet servlet : servlets) {
            for (Bean bean : servlet.getBeans()) {
                if (bean.getClazz() != null) {
                    URL[] urls = new URL[10];
                    try {
                        URL url = new URL(
                                "file:///c:/Work/workspaces/eclipse-modeling-kepler-SR2-server-mainline");
                        urls[0] = url;
                    } catch (MalformedURLException e1) {
                        e1.printStackTrace();
                    }
                    URLClassLoader classLoader = new URLClassLoader(urls);
                    classLoader.findResource(bean.getClazz());
                    FileReader reader = null;
                    String line;
                    String classpath = projectLoc + bean.getClazz();
                    try {
                        reader = new FileReader(classpath);
                        BufferedReader bufRdr = new BufferedReader(reader);
                        while ((line = bufRdr.readLine()) != null) {
                            if (line.startsWith("@RequestMapping")) {
                                bean.getRequestMappings().add(line);
                            }
                        }
                        bufRdr.close();
                    } catch (IOException e) {
                        System.out.printf("Can't find class source file %s\n",
                                bean.getClazz());
                        continue;
                    }
                }
            }
        }
    }

    private void printResults() {
        String msg = "";
        for (Servlet servlet : servlets) {
            msg += String.format(
                    "\nServlet name=%s, class=%s, URL patterns:\n",
                    servlet.getName(), servlet.getServletClass());
            for (String urlPattern : servlet.getUrlMappings()) {
                msg += String.format("%s\n", urlPattern);
            }
            for (Bean bean : servlet.getBeans()) {
                msg += String.format("Bean class=%s\n", bean.getClazz());
            }
        }
        System.out.println(msg);
    }

    public String toString() {
        String msg = "servlets=[\n";
        for (int i = 0; i < servlets.size(); i++) {
            msg += String.format("%s", servlets.get(i).toString());
            msg += i == servlets.size() - 1 ? "\n]" : ",\n";
        }
        return msg;
    }

}
