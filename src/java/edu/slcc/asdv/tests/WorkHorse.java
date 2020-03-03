package edu.slcc.asdv.tests;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import edu.slcc.asdv.pojos.results;
import java.util.Date;
import org.openqa.selenium.Keys;

public class WorkHorse {
    
   private static final WebDriver DRIVER = new ChromeDriver();

   
   
    public static boolean launchTest() throws Exception {
        try{
        DRIVER.get("https://www.speedtest.net/"); //Launches SpeedTest.net
        Thread.sleep(2000); //wait 2secs
        DRIVER.findElement(By.xpath("//a[contains(@class, 'js-start-test test-mode-multi')]"))
                .click(); //Begin speed test
        Thread.sleep(60000); //wait 60secs
        
        String popUp = "//a[contains(@class, 'notification-dismiss close-btn')]";
        if(DRIVER.findElement(By.xpath(popUp)).isDisplayed())
                DRIVER.findElement(By.xpath(popUp)).click(); //Closes pop-up if exists
        Thread.sleep(3000); //wait 3secs
        
        results.setPing(DRIVER.findElement(By.xpath("//span[contains(@class, 'result-data-large number result-data-value ping-speed')]")).getText());
        results.setDownload(DRIVER.findElement(By.xpath("//span[contains(@class, 'result-data-large number result-data-value download-speed')]")).getText());
        results.setUpload(DRIVER.findElement(By.xpath("//span[contains(@class, 'result-data-large number result-data-value upload-speed')]")).getText());
        results.setServer(DRIVER.findElement(By.xpath("//a[contains(@class, 'js-data-sponsor')]")).getText());
        results.setProvider(DRIVER.findElement(By.xpath("//div[contains(@class, 'result-label js-data-isp')]")).getText());
        results.setDateRan(new Date().toString());
        gatherInfo(); //write to txt file for backup/debugging
        }
        catch(Exception e)
        {
            return false;
        }
        return true;
    }
    
    public static void gatherInfo() throws IOException
    {
       FileWriter fileWriter = new FileWriter("D:\\School\\NetBeans\\Side-Projects\\SpeedTestBot2\\outputData\\results.txt", true);
       try (PrintWriter printWriter = new PrintWriter(fileWriter)) {
           printWriter.println("----------- SPEEDTEST RESULTS -----------\n");
           printWriter.println("Date: ------------------\t" + results.getDateRan());
           printWriter.println("Ping: ------------------\t" + results.getPing());
           printWriter.println("Download: ----------\t" + results.getDownload());
           printWriter.println("Upload:  --------------\t" + results.getUpload());
           printWriter.println("Server: ---------------\t" + results.getServer());
           printWriter.println("Provider: ------------\t" + results.getProvider() + "\n");
       }
    }
    
    private static String Conclusion()
    {
        if(Double.valueOf(results.getDownload()) >=90 && Double.valueOf(results.getUpload()) >= 9)
        {
            return "Outsanding! :)";  //@Suddenlink?
        }
        else if(Double.valueOf(results.getDownload()) >= 60 && Double.valueOf(results.getUpload()) >= 6)
        {
            return "Awesome Job! You've met >= 60% expectations"; 
        }
        else if(Double.valueOf(results.getDownload()) >= 40 && Double.valueOf(results.getUpload()) >= 4)
        {
            return "Nice work! These are good numbers.";
        }
        else if(Double.valueOf(results.getDownload()) >= 20 && Double.valueOf(results.getUpload()) >= 2)
        {
            return "Not up to expectations but I can still work with this.";
        }
        else if(Double.valueOf(results.getDownload()) >= 5 && Double.valueOf(results.getUpload()) >= 1)
        {
            return "Poor results... Upload Speed needs serious work";
        }
        else if(Double.valueOf(results.getDownload()) >= 5 && Double.valueOf(results.getUpload()) < 1)
        {
            return "Ouch... Those results are straight up terrible"; //@Suddenlink?
        }
        else if(Double.valueOf(results.getDownload()) < 5 && Double.valueOf(results.getUpload()) < 1)
        {
            return "SERIOUSLY? How did this even post with that upload speed??"; //@Suddenlink?
        }
        
        return "N/A";
    }
    
    public static void launchTweet(String un, String pw) throws Exception {
        DRIVER.get("https://twitter.com/"); //Launch Twitter
        Thread.sleep(2000); //wait 2secs
        DRIVER.findElement(By.xpath("//input[contains(@name, 'session[username_or_email]')]"))
                .sendKeys(un); //Insert username saved from index
        DRIVER.findElement(By.xpath("//input[contains(@name, 'session[password]')]"))
                .sendKeys(pw); //Insert password saved from index
        DRIVER.findElement(By.xpath("//span[contains(text(), 'Log in')]"))
                .click(); //Login
        Thread.sleep(3000); //wait 3secs 
        
        
        String tweet = "Date:    " + results.getDateRan() + Keys.chord(Keys.SHIFT ,Keys.ENTER)
                    + "Download: " + results.getDownload() + Keys.chord(Keys.SHIFT ,Keys.ENTER)
                    + "Upload:   " + results.getUpload() + Keys.chord(Keys.SHIFT ,Keys.ENTER)
                    + "Ping:     " + results.getPing() + Keys.chord(Keys.SHIFT ,Keys.ENTER)
                    + "Server:   " + results.getServer() + Keys.chord(Keys.SHIFT ,Keys.ENTER)
                    + "Conclusion: " + Conclusion();
        
        DRIVER.findElement(By.xpath("/html/body/div/div/div/div/main/div/div/div/div[1]/div/div[2]/div[2]/div[1]/div/div/div/div[2]/div[1]/div/div/div/div/div/div/div/div/div[1]/div[1]/div/div/div/div[2]/div/div/div/div"))
                .sendKeys(tweet); //Type tweet
//        DRIVER.findElement(By.xpath("/html/body/div/div/div/div/main/div/div/div/div[1]/div/div[2]/div[2]/div[1]/div/div/div/div[2]/div[2]/div/div/div[2]/div[3]"))
//                .click(); //Publish tweet
    } 
    
    public static void stop()
    {
        DRIVER.quit();
    }
    
}
