package edu.slcc.asdv.beans;

import edu.slcc.asdv.tests.WorkHorse;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;

@Named(value = "accessBean")
@RequestScoped
public class AccessBean {

    public AccessBean() {
    }
    private String username;
    private String password;
    Timer t = new Timer();
    TimerTask tt;

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
    public void launch() throws Exception {
        if (WorkHorse.launchTest()) {
            launchTweet();
        }
    }

    public void launchTweet() throws Exception {
        if (WorkHorse.launchTweet(username, password)) {           
            tt = new TimerTask() {
                @Override
                public void run() {
                    try {
                        launch();
                    } catch (Exception ex) {
                        Logger.getLogger(AccessBean.class.getName()).log(Level.SEVERE, null, ex);
                    }
                };
            };
            t.schedule(tt,14400000); //Run again in 4 hours
        }
    }
    public void stop() throws Exception {
        try{
        t.cancel();
        tt.cancel();
        }catch(Exception e){System.out.println(e.toString());}
        WorkHorse.stop();
    }

}
