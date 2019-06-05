------------------------------------------------------------------------
This is the project README file. Here, you should describe your project.
Tell the reader (someone who does not know anything about this project)
all he/she needs to know. The comments should usually include at least:
------------------------------------------------------------------------

PROJECT TITLE: rgeo
PURPOSE OF PROJECT: demonstrate rgeo requests
VERSION or DATE: 
HOW TO START THIS PROJECT:
AUTHORS:
USER INSTRUCTIONS:

address to lat long:
https://www.latlong.net/convert-address-to-lat-long.html

API:
create Command instance on a ReqponseCache passing in the max distance threshold and call get():

Command command = new Command(new ResponseCache(200));
command.get(40.070170,-75.465230, new IAction<Response>()
{
    @Override
    public void action(Response response)
    {
        System.out.println("got response:" + response.xml());
    }
});


NOTES:
    bluej ide was used for development = www.bluej.org
    swiped distance calculation in meters from:
    https://stackoverflow.com/questions/3694380/calculating-distance-between-two-points-using-latitude-longitude
    
    compile:
    javac Main.java
    
    run:
    java Main
    
    or:
    open the bluej project and run from bluej
    
    Enter lat, return
    Enter lon, return
    
    Cache is checked first, distance of less than 200 meters results in a hit.

    Cache count is reported each time an item is added to the cache ( on a miss ).
    
    Control-C to quit
    
TODO:
    check request result code
    check for response nulls
    Command and ReqponseCache should be Singleton's
    change the class names to better represent what they do
    move to Futures based http request library
    profile for memory and cpu usage
    add simple ui
    add a ResponseCache interface
    find a third part xml parser
    test test test
    
    
    

