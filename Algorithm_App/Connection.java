import java.io.Serializable;

/*****************************************************************************

 * Purpose: This class is created to model the connections into objects and
 * store them as a DSAGraphEdge
 * in a DSAGraph object.
 *****************************************************************************/
public class Connection implements Serializable {
    private String fromLocation, toLocation, barriers;
    private double distance;
    private int security;

    public Connection(String inFrom, String inTo, int inDistance, int inSecurity, String inBarriers) {
        fromLocation = inFrom;
        toLocation = inTo;
        distance = inDistance;
        security = inSecurity;
        barriers = inBarriers;
    }

    public String getFromLocation() {
        return fromLocation;
    }

    public String getToLocation() {
        return toLocation;
    }

    public double getDistance() {
        return distance;
    }

    public int getSecurity() {
        return security;
    }

    public String getBarriers() {
        return barriers;
    }

    public void setFromLocation(String pFrom) {
        fromLocation = pFrom;
    }

    public void setToLocation(String pTo) {
        toLocation = pTo;
    }

    public void setDistance(double pDistance) {
        distance = pDistance;
    }

    public void setSecurity(int pSecurity) {
        security = pSecurity;
    }

    public void setBarriers(String pBarriers) {
        barriers = pBarriers;
    }

    public boolean hasBarriers() {
        boolean returnVal = true;

        if (barriers.equals(""))
            returnVal = false;

        return returnVal;
    }

    /*
     * This method overrides the equal method, since it checks for the reference of
     * the object
     * passed as the parameter. This overriden method checks if the fields of the
     * object are equal
     * to an object of same instance, even if it contains a different reference.
     */
    @Override
    public boolean equals(Object inObject) {
        boolean returnVal = false;

        if (inObject instanceof Connection) {
            Connection ObjectToTest = (Connection) inObject;

            if (ObjectToTest.getBarriers().equals(this.getBarriers())
                    && ObjectToTest.getFromLocation().equals(this.getFromLocation())
                    && ObjectToTest.getToLocation().equals(this.getToLocation())
                    && ObjectToTest.getDistance() == this.getDistance()
                    && ObjectToTest.getSecurity() == this.getSecurity())
                returnVal = true;

        }

        return returnVal;
    }

}
