import java.io.*;

public class MapFileIO 
{
    private static DSAGraph graph;

    public static DSAGraph getGraphFromMapFile(String inFileName)
    {
        graph=new DSAGraph();
        readMapFile(inFileName);

        return graph;
    }
    public static void readMapFile(String inFileName)
    {
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

            if(line!=null && line.charAt(0)!='#')
                splitMapFileLine(line);

            while(line!=null)
            {
                line=bufferedReader.readLine();
                if(line!=null && line.charAt(0)!='#')
                    splitMapFileLine(line);
            }

        }
        catch (Exception e) 
        {
             System.out.println("Error: "+e.getMessage()+e.getStackTrace());
        }
    }

    public static void splitMapFileLine(String inLine)
    {
        String[] lineArr;
        Connection connection1, connection2;
        String fromLocation, toLocation, barriers;
        int securityLevel, distance;
        boolean isDirected;

        if(inLine.contains("<>"))
        {
            lineArr=inLine.split("<>|\\|");

             fromLocation=lineArr[0];
             toLocation=lineArr[1];
             distance=getDistanceFromString(lineArr[2]);
             securityLevel=getSecurityFromString(lineArr[3]);
             barriers=getBarriersFromString(lineArr[4]);
             isDirected=false;
            
        }
        else if(inLine.contains("<"))
        {
            lineArr=inLine.split("<|\\|");

             fromLocation=lineArr[1];
             toLocation=lineArr[0];
             distance=getDistanceFromString(lineArr[2]);
             securityLevel=getSecurityFromString(lineArr[3]);
             barriers=getBarriersFromString(lineArr[4]);
             isDirected=true;
              
        }
        else 
        {
            lineArr=inLine.split(">|\\|");

            fromLocation=lineArr[0];
            toLocation=lineArr[1];
            distance=getDistanceFromString(lineArr[2]);
            securityLevel=getSecurityFromString(lineArr[3]);
            barriers=getBarriersFromString(lineArr[4]);
            isDirected=true;
        }

        connection1=new Connection(fromLocation, toLocation, distance, securityLevel, barriers);
        connection2=new Connection(toLocation, fromLocation, distance, securityLevel, barriers);

        if(!isDirected)
        {
            graph.addEdge(fromLocation, toLocation, connection1, isDirected);
            graph.addEdge(toLocation, fromLocation, connection2, isDirected);
        }
        else
            graph.addEdge(fromLocation, toLocation, connection1, isDirected);
    }

    public static int getDistanceFromString(String inString)
    {
        int returnValue=Integer.parseInt(inString.replaceAll("[^\\d]", ""));

        return returnValue;
    }

    public static int getSecurityFromString(String inString)
    {
        int greatest=0;

        if(inString.contains("1"))
        {

        //Extracts only the integer part from the String.
        String[] arr=(inString.replaceAll("[^\\d]", " ")).trim().split(" ");

        for(int i=0;i<arr.length;i++)
        {
            if(Integer.parseInt(arr[i])>greatest)
                greatest=Integer.parseInt(arr[i]);
        }
        }
        
        return greatest;
    }

    public static String getBarriersFromString(String inString)
    {
        String returnVal="";

        String[] stringArray=inString.split(":");

        // Checks if there are barriers or not(if there are no barriers, after splitting the string from ':', there 
        //  only one item, so if there's a barrier the array length would be 2).
        if(stringArray.length<2)
            returnVal="";
        else    
            returnVal=stringArray[1];

        return returnVal;
    }

    public static void serializeGraph(Object inGraph, String pFileName) throws IllegalArgumentException
    {
        FileOutputStream fileOutputStream;
        ObjectOutputStream objOutputStream;

        try
        {
            fileOutputStream=new FileOutputStream(pFileName);
            objOutputStream=new ObjectOutputStream(fileOutputStream);

            objOutputStream.writeObject(inGraph);
            objOutputStream.close();
            System.out.println("Object successfully serialised to "+pFileName);

        }
        catch(Exception e)
        {
            throw new IllegalArgumentException("Unable to save object to file: "+e.getMessage());
        }
    }
    public static Object deserializeGraph(String pFileName) throws IllegalArgumentException
    {
        FileInputStream fileInputStream;
        ObjectInputStream objectInputStream;
        Object deserializedObject;

        try
        {
            fileInputStream=new FileInputStream(pFileName);
            objectInputStream=new ObjectInputStream(fileInputStream);

            deserializedObject=objectInputStream.readObject();

            objectInputStream.close();
            System.out.println("Object successfully deserialised from "+pFileName);

            return deserializedObject;
        }
        catch(Exception e)
        {
            throw new IllegalArgumentException("Unable to load object from file");
        }
    }
    
}
