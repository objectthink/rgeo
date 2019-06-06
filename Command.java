
/**
 * Command class 
 *
 * @author stephen eshelman
 * @version 0.1
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

/**
 * Command - request api interface
 */
public class Command
{
    ResponseCache _cache = new ResponseCache();
   
    /**
     * constructor - use default cache
     */
    public Command()
    {
    }

    /**
     * constructor - create a command on a cache
     */
    public Command(ResponseCache cache)
    {
        _cache = cache;
    }
    
    /**
     * get - get a Response for lat and lon - callback is called with response
     * @return void
     */
    public void get(double lat, double lon, IAction<Response> callback)
    {
        //check cache
        //post if not found
        
        final IAction<Response> fCallback = callback;
        final ResponseCache fResponseCache = _cache;
        
        Response response = _cache.find(new Request(lat,lon));
        if(response != null)
        {
            callback.action(response);
        }
        else
        {
            post(new Request(lat,lon), new IAction<Response>()
            {
                @Override
                public void action(Response response)
                {
                    if(response.responseCode() == 200)
                    {
                        fResponseCache.add(new Request(lat,lon), response);
                        fCallback.action(response);
                    }
                    else
                    {
                        fCallback.error(response.responseCode());
                    }
                }
                
                public void error(int errorCode)
                {
                }
            });
        }
    }
    
    /**
     * sendSkyHookPost - post http request
     */
    private Response sendSkyhookPost(Request request) throws Exception 
    {       
        String url = "https://api.skyhookwireless.com:443/wps2/reverse-geo";
        URL obj = new URL(url);
        HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();

        String xmlBody = request.xmlBody();
        
        //add reuqest header
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "text/xml");
        
        // Send post request
        con.setDoOutput(true);
        con.setRequestProperty("Content-Length", Integer.toString(xmlBody.length()));
        con.getOutputStream().write(xmlBody.getBytes("UTF8"));

        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'POST' request to URL : " + url);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(
            new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer responseString = new StringBuffer();

        while ((inputLine = in.readLine()) != null)
        {
            responseString.append(inputLine);
        }
    
        in.close();
        
        //print result
        //System.out.println(response.toString());

        //xmlToObject(response.toString());
        
        //CREATE REPONSE - SET RESPONSE CODE - INSTANCE AND RETURN IT
        Response response = new Response(responseString.toString());
        response.setResponseCode(responseCode);
        
        return response;
    }    

    
    public void post(Request request, IAction<Response> callback)
    {
        final Request fRequest = request;
        run(
                new IFunc<Response>()
                {
                    @Override
                    public Response func()
                    {
                        try
                        {
                            return sendSkyhookPost(fRequest);
                        }
                        catch(Exception e)
                        {
                            return null;
                        }
                    }
                },
                callback
                );
    }

    
    public Response run(
      IFunc<Response> func,
      IAction<Response> action)
   {
      final IFunc<Response> fFunc = func;
      final IAction<Response> fAction = action;

      if (action != null)
      {
         Thread t = new Thread(
            new Runnable()
            {
               @Override
               public void run()
               {
                  fAction.action(fFunc.func());
               }
            });

         //SETUP AN UNCAUGHT EXCEPTION HANDLER FOR THIS THREAD
         //LOG IF AN EXCEPTION IS THROWN
         t.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler()
         {
            @Override
            public void uncaughtException(Thread t, Throwable e)
            {
                System.out.println(String.format("exception thrown in Command.run(): %s", e.toString()));
            }
         });

         t.start();
      }
      else
      {
         return func.func();
      }

      return null;
   }
}
