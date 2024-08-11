public class Route
{
    private DSALinkedList route;
    private float distance;

    public Route(DSALinkedList route, float distance)
    {
        this.route=route;
        this.distance=distance;
    }

    public DSALinkedList getRoute() { return route; }
    public float getDistance() { return distance; }

    public void setRoute(DSALinkedList inRoute) { route=inRoute; }
    public void setDistance(float inDistance) { distance=inDistance; }
}
