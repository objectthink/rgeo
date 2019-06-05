
/**
 * entry point into rgeo request
 *
 * @author (stephen eshelman)
 * @version (0.1)
 */

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import javax.net.ssl.HttpsURLConnection;
import java.net.URL;

import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.*;

import java.util.*;

public class Main
{
    /**
     * Constructor for objects of class Main
     */
    public Main()
    {
    }

    public static void main(String[] args)
    {
        Main entry = new Main();
        
        final Value<Integer> _numberOfResponses = new Value<>(0);
        try
        {
            System.out.println("----------------------------START");
            
            /* TEST POST
            for(int i = 0; i < 10; i++)
            {
                command.post(new Request(39.874680,-75.253433), new IAction<Response>()
                {
                    @Override
                    public void action(Response response)
                    {
                        _numberOfResponses.setValue(_numberOfResponses.value() + 1);
                        System.out.println("got response:" + response.xml());
                    }
                });
            }
            
            command.post(new Request(28.373070,-81.556938), new IAction<Response>()
            {
                @Override
                public void action(Response response)
                {
                    System.out.println("got response:" + response.xml());
                }
            });
            */
           
            /* TEST GET INTERFACE
            for(int i = 0; i < 10; i++)
            {
                Thread.sleep(1000);
                command.get(39.874680,-75.253433, new IAction<Response>()
                {
                    @Override
                    public void action(Response response)
                    {
                        _numberOfResponses.setValue(_numberOfResponses.value() + 1);
                        System.out.println("got response:" + response.xml());
                    }
                });
            }
            
            //test cache interface
            command.get(39.778177,-75.843060, new IAction<Response>()
            {
                @Override
                public void action(Response response)
                {
                    System.out.println("got response:" + response.xml());
                }
            });
            
            command.get(39.777890,-75.842720, new IAction<Response>()
            {
                @Override
                public void action(Response response)
                {
                    System.out.println("got response:" + response.xml());
                }
            });
            
            System.out.println("DONE:" + _numberOfResponses.value());
            */
        
           Command command = new Command(new ResponseCache(200));

           //ADD SKYHOOK TO CACHE
           //1400 Liberty Ridge Drive
           //Suite 102
           //Wayne, PA 19087
           command.get(40.070170,-75.465230, new IAction<Response>()
           {
               @Override
               public void action(Response response)
               {
                   //System.out.println("got response:" + response);
                   response.toConsole();
               }
           });
        
           //SCAN INPUT FOR DOUBLES FOREVER, LAT THEN LON
           Scanner scan= new Scanner(System.in);
        
           while(true)
           {
               double lat = scan.nextDouble();
               double lon = scan.nextDouble();
           
               command.get(lat,lon, new IAction<Response>()
               {
                   @Override
                   public void action(Response response)
                   {
                       response.toConsole();
                   }
                });
           }

        }
        catch(Exception e)
        {
        }                
    }  
}
