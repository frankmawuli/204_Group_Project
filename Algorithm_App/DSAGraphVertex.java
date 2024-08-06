import java.io.Serializable;

/**
 * Author: Web Weavers
 * Date: 6th 
 * Self Citation: The graph class is obtained from practical 6 worksheet.
 */
public class DSAGraphVertex implements Serializable
{
  private String label;
  private Object value;
  private DSALinkedList links;
  private boolean visited;

  public DSAGraphVertex(String inLabel, Object inValue) 
  {
      label=inLabel;
      value=inValue;
      links=new DSALinkedList();
      visited=false;
  }

  public String getLabel() { return label; }
  public Object getValue() { return value; }
  public DSALinkedList getLinks() { return links; }

  public void addEdge(DSAGraphVertex inVertex)
  {
      links.insertLast(inVertex);
  }

  public boolean hasEdge(DSAGraphVertex inVertex)
  {
    boolean containsEdge=false;

    for(Object o : links)
    {
      if(((DSAGraphVertex)o).getLabel().equals(inVertex.getLabel()))
        containsEdge=true;
    }

    return containsEdge;
  }

  public void setLabel(String inLabel) { label=inLabel; }
  public void setVisited(boolean pState) { visited=pState; }
  public void clearVisited() { visited=false; }
  public boolean getVisited() { return visited; }

  @Override
  public String toString()
  {
    String returnString=this.label+"  Links: ";

    for(Object o : links)
    {
      DSAGraphVertex vertex=(DSAGraphVertex)o;
      returnString+=vertex.getLabel()+" ";
    }
    return returnString;
  }

  /*
   * This method overrides the equal method, since it checks for the reference of the object
    passed as the parameter. This overriden method checks if the fields of the object are equal 
    to an object of same instance, even if it contains a different reference.
   */
  @Override
  public boolean equals(Object inObject)
  {
    boolean returnVal=false;

    if(inObject instanceof DSAGraphVertex)
    {
      DSAGraphVertex objectToTest=(DSAGraphVertex)inObject;

      if(objectToTest.getLabel().equals(this.getLabel())
       // && objectToTest.getValue().equals(this.value)
        //&& objectToTest.getLinks().equals(this.links)
        )
        returnVal=true;
    }

    return returnVal;
  }
}