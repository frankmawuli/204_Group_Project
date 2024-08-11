import java.io.Serializable;

public class DSAGraphEdge implements Serializable
{
    String from, to;
    Object value;
    boolean isDirected;

    public DSAGraphEdge(String inFrom, String inTo, Object inValue, boolean pDirected) 
    {
        from=inFrom;
        to=inTo;
        value=inValue;
        isDirected=pDirected;

    }

    public String getFrom() { return from; }
    public String getTo() { return to; }
    public Object getValue() { return value; }
    public boolean isDirected() { return isDirected; }
    public void setDirected(boolean pDirection) { isDirected=pDirection; }

    @Override
    public String toString()
    {
        Connection connection=(Connection)value;

        String returnString="From: "+from+" To: "+to+" Distance: "+String.valueOf(connection.getDistance())+" IsDirected: "+isDirected
                            +"  Barriers: "+connection.getBarriers()+"  Security level: "+connection.getSecurity();

        return returnString;
    }

    public void setValue(Object inValue) { value=inValue; }
    public void setFrom(String inFrom) { from=inFrom; }
    public void setTo (String inTo) { to=inTo; }


    @Override
    public boolean equals(Object inObject)
    {
        boolean returnVal=false;

        if(inObject instanceof DSAGraphEdge)
        {
            DSAGraphEdge edgeToTest=(DSAGraphEdge)inObject;

            if(edgeToTest.getFrom().equals(this.from)
            && edgeToTest.getTo().equals(this.to)
            && edgeToTest.getValue().equals(this.value)
            && edgeToTest.isDirected()==this.isDirected)
                returnVal=true;
        }

        return returnVal;
    }
    

}