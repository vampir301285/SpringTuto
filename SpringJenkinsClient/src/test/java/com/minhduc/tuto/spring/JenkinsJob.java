package com.minhduc.tuto.spring;

import java.util.List;

public class JenkinsJob {

    private String description;
    
    private String name;
    
    private String url; 
    
    private List<JenkinsBuild> builds;
    
    
   
    /**
     * @return the builds
     */
    public List<JenkinsBuild> getBuilds() {
        return builds;
    }

    /**
     * @param builds the builds to set
     */
    public void setBuilds(List<JenkinsBuild> builds) {
        this.builds = builds;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url the url to set
     */
    public void setUrl(String url) {
        this.url = url;
    }
    
    
}
