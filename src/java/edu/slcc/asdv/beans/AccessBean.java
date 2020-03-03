package edu.slcc.asdv.beans;

import edu.slcc.asdv.tests.WorkHorse;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;

@Named(value = "accessBean")
@RequestScoped
public class AccessBean {

    public AccessBean() {
    }
    private String username;
    private String password;

    //<editor-fold desc="getters/setters">

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    //</editor-fold> 
    
    public void launch() throws Exception
    {
        if(WorkHorse.launchTest())
            launchTweet();
    }
    
    public void launchTweet() throws Exception
    {
        WorkHorse.launchTweet(username, password);
    }
    public void stop() throws Exception
    {
        WorkHorse.stop();
    }
    
}
