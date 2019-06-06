
/**
 * Write a description of class Response here.
 *
 * @author (your name)
 * @version (a version number or a date)
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

public class Response
{
    String _xml;
    int _responseCode;
    
    /**
     * Constructor for objects of class Response
     */
    public Response(String xml)
    {
        _xml = xml;
    }

    public void setResponseCode(int code)
    {
        _responseCode = code;
    }
    
    public int responseCode()
    {
        return _responseCode;
    }
    
    public String xml()
    {
        return _xml;
    }
    
    @Override 
    public String toString()
    {
        return _xml;
    }
    
    public void toConsole()
    {
        try
        {
            //create document builder
            DocumentBuilderFactory factory =
            DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
        
            //create document from stream
            StringBuilder xmlStringBuilder = new StringBuilder();
            xmlStringBuilder.append(_xml);
            ByteArrayInputStream input = new ByteArrayInputStream(
            xmlStringBuilder.toString().getBytes("UTF-8"));
            Document doc = builder.parse(input);
        
            //get root
            doc.getDocumentElement().normalize();
        
            //get attributes
            NodeList nList = doc.getElementsByTagName("street-address");
            
            for (int temp = 0; temp < nList.getLength(); temp++) 
            {
                Node nNode = nList.item(temp);
                //System.out.println("\nCurrent Element :" + nNode.getNodeName());
            
                if (nNode.getNodeType() == Node.ELEMENT_NODE) 
                {
                    Element eElement = (Element) nNode;
                    
                    try //some address don't have a stree number
                    {
                        System.out.println("street-number: " + eElement
                            .getElementsByTagName("street-number")
                            .item(0)
                            .getTextContent());
                    }
                    catch(Exception e)
                    {
                    }
                        
                    System.out.println("address-line: " + eElement
                        .getElementsByTagName("address-line")
                        .item(0)
                        .getTextContent());
                                           
                    System.out.println("metro1: " + eElement
                        .getElementsByTagName("metro1")
                        .item(0)
                        .getTextContent());

                    System.out.println("metro2: " + eElement
                        .getElementsByTagName("metro2")
                        .item(0)
                        .getTextContent());
                        
                    System.out.println("postal-code: " + eElement
                        .getElementsByTagName("postal-code")
                        .item(0)
                        .getTextContent());

                    System.out.println("county: " + eElement
                        .getElementsByTagName("county")
                        .item(0)
                        .getTextContent());
                        
                    System.out.println("state: " + eElement
                        .getElementsByTagName("state")
                        .item(0)
                        .getTextContent());
                    
                    System.out.println();
                }
            }
        }
        catch(Exception e)
        {
            //punt and show the xml
            System.out.println(_xml);
        }
    }
}
