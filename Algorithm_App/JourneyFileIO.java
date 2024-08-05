import java.io.*;

public class JourneyFileIO 
{

    public static Journey readJourneyFile(String inFileName)
    {
        String start="", destination="", barrier1="", barrier2="";
        String[] barriers;
        int securityLevel=0;

        FileInputStream fileInputStream;
        InputStreamReader inputStreamReader;
        BufferedReader bufferedReader;
        String line;

        try 
        {
            fileInputStream=new FileInputStream(inFileName);
            inputStreamReader=new InputStreamReader(fileInputStream);
            bufferedReader=new BufferedReader(inputStreamReader);

            line=bufferedReader.readLine();

            while(line==null || line.charAt(0)=='#')
            {
                if(line==null || line.charAt(0)=='#')
                    line=bufferedReader.readLine();
            }

            start=getSourceFromJourneyFile(line);
            destination=getToFromJourneyFile(bufferedReader.readLine());
            bufferedReader.readLine();
            barriers=getBarriersFromJourneyFile(bufferedReader.readLine()).split(",");
            barrier1=barriers[0];
            barrier2=barriers[1];
            securityLevel=getSecurityFromJourneyFile(bufferedReader.readLine());


        }
        catch(Exception e)
        {
            System.out.println(e.getLocalizedMessage());
        }

        Journey journey=new Journey(start, destination, barrier1, barrier2,securityLevel);
        
        return journey;
    }

    public static void writeToFile(String inFilename, String inRoutes)
    {
        FileOutputStream fileOutputStream;
        PrintWriter printWriter;

        try 
        {
            fileOutputStream=new FileOutputStream(inFilename);
            printWriter=new PrintWriter(fileOutputStream);

            printWriter.println(inRoutes);

            printWriter.close();
        } 
        catch (Exception e) 
        {
            System.out.print(e.getLocalizedMessage());
        }

    }

    public static String getSourceFromJourneyFile(String inSource)
    {
        String string=inSource.substring(inSource.indexOf(' '),inSource.indexOf('#')).trim();

        return string;
    }

    public static String getToFromJourneyFile(String inTo)
    {
        String string=inTo.substring(inTo.indexOf(' '), inTo.indexOf("#")).trim();
        
        return string;
    }

    public static String getBarriersFromJourneyFile(String inBarriers)
    {
        String string=inBarriers.substring(inBarriers.indexOf(' '), inBarriers.indexOf('#')).trim();
        
        return string;
    }

    public static int getSecurityFromJourneyFile(String inSecurity)
    {
        int security=0;
        String string=inSecurity.substring(inSecurity.indexOf(' '), inSecurity.indexOf('#')).trim();
        
        if(!string.equals(null))
            security=Integer.parseInt(string);

        return security;
    }
    
   

   
}
