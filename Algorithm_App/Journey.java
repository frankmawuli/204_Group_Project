public class Journey 
{
    private String source;
    private String destination;
    private String barrier1;
    private String barrier2;
    private int securityClearance;

    public Journey(String source, String destination, String barrier1, String barrier2, int securityClearance) 
    {
        this.source=source;
        this.destination=destination;
        this.barrier1=barrier1;
        this.barrier2=barrier2;
        this.securityClearance=securityClearance;
    }

    public String getSource() { return source; }
    public String getDestination() { return destination; }
    public String getBarrier1() { return barrier1; }
    public String getBarrier2() { return barrier2; }
    public int getSecurityClearance() { return securityClearance; }

        
}
