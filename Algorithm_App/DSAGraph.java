import java.io.Serializable;


 // * Self-citation: The graph class is obtained from practical 6 and some of the
 // * methods
 // * from it are unmodified, but most of methods are newly written to this.
 

public class DSAGraph implements Serializable {
    private DSALinkedList vertices;
    private DSALinkedList edges;
    private int vertexCount;
    private int edgeCount;
    private DSALinkedList allPaths;
    private DSALinkedList currentPath;

    public DSAGraph() {
        vertices = new DSALinkedList();
        vertexCount = 0;
        edgeCount = 0;
        edges = new DSALinkedList();
    }

    public int getVertexCount() {
        return vertexCount;
    }

    public int getEdgeCount() {
        return edgeCount;
    }

    /**
     * Method to add a new vertex to graph
     */
    public void addVertex(String inLabel, Object inObject) {
        if (!hasVertex(inLabel)) {
            DSAGraphVertex newVertex = new DSAGraphVertex(inLabel, inObject);
            vertices.insertLast(newVertex);
            vertexCount++;
        }

    }

    /**
     * Method to add a new edge to the graph
     */
    public void addEdge(String inLabel1, String inLabel2, Object inEdgeValue, boolean inDirected) {
        DSAGraphVertex newVertex;

        if (!hasVertex(inLabel1)) {
            newVertex = new DSAGraphVertex(inLabel1, null);
            vertices.insertLast(newVertex);
            vertexCount++;
        }

        if (!hasVertex(inLabel2)) {
            newVertex = new DSAGraphVertex(inLabel2, null);
            vertices.insertLast(newVertex);
            vertexCount++;
        }

        DSAGraphVertex vertex = getVertex(inLabel1);
        DSAGraphEdge edge = new DSAGraphEdge(inLabel1, inLabel2, inEdgeValue, inDirected);
        edges.insertLast(edge);

        if (!vertex.hasEdge(getVertex(inLabel2))) {
            vertex.addEdge(getVertex(inLabel2));
        }
        edgeCount++;

    }

    /** Checks if two vertices are adjacent to each other */
    public boolean isAdjacent(String inLabel1, String inLabel2) {
        boolean returnValue = false;
        DSALinkedList adjacentList = (getVertex(inLabel1)).getLinks();

        for (Object o : adjacentList) {
            if (o.equals(inLabel2))
                returnValue = true;
        }
        return returnValue;
    }

    /* Gets the adjacent vertex of the given label's vertex */
    public DSALinkedList getAdjacent(String inLabel) {
        DSALinkedList returnAdjacentList = getVertex(inLabel).getLinks();

        return returnAdjacentList;
    }

    /* Returns the vertex of the given label */
    public DSAGraphVertex getVertex(String inLabel) {
        DSAGraphVertex returnVertex = null;

        for (Object o : vertices) {
            if (((DSAGraphVertex) o).getLabel().equals(inLabel))
                returnVertex = (DSAGraphVertex) o;
        }
        if (returnVertex == null)
            System.out.println("Node not found");
        return returnVertex;

    }

    public boolean hasVertex(String inLabel) {
        boolean containsVertex = false;

        for (Object o : vertices) {
            if (((DSAGraphVertex) o).getLabel().equals(inLabel))
                containsVertex = true;
        }

        return containsVertex;
    }

    /* Displays the graph as an adjacency list */
    public void displayAsList() {
        for (Object o : vertices) {
            DSAGraphVertex tempVertex = (DSAGraphVertex) o;

            System.out.print(tempVertex.getLabel() + ": ");

            for (Object obj : tempVertex.getLinks()) {
                System.out.print(((DSAGraphVertex) obj).getLabel() + " ");
            }
            System.out.print("\n");
        }
    }

    /* Returns the adjacency matrix of the graph as a String */
    public String getAdjacencyMatrix() {
        String returnString = "     ";
        int maxCharCount = 5;

        String[] vertexArray = new String[vertexCount];
        int index = 0;

        for (Object o : vertices) {
            String vertexLabel = ((DSAGraphVertex) o).getLabel();
            vertexArray[index] = vertexLabel;

            int whitespacestoAdd = maxCharCount - String.valueOf(index + 1).length();
            returnString += addWhitespaces(String.valueOf(index + 1), whitespacestoAdd);

            index++;
        }

        returnString += "\n";
        for (int ii = 0; ii < vertexCount; ii++) {
            returnString += "     ";
        }
        returnString += "\n";

        index = 0;
        for (Object obj : vertices) {
            DSAGraphVertex currentVertex = (DSAGraphVertex) obj;
            DSALinkedList currentLinks = currentVertex.getLinks();
            String distance;

            returnString += addWhitespaces(String.valueOf(index + 1),
                    maxCharCount - 1 - String.valueOf(index + 1).length()) + "|";

            for (int i = 0; i < vertexCount; i++) {
                if (currentLinks.contains(getVertex(vertexArray[i]))) {
                    distance = String
                            .valueOf(getDistance(currentVertex.getLabel(), getVertex(vertexArray[i]).getLabel()));
                    returnString += addWhitespaces(String.format("%.1f", Double.parseDouble(distance)),
                            maxCharCount - distance.length());
                } else {
                    returnString += addWhitespaces("0", 4);

                }

            }
            index++;
            returnString += "\n";
        }

        returnString += "\n";

        for (int i = 0; i < vertexCount; i++) {
            returnString += (i + 1) + "  " + getVertex(vertexArray[i]).getLabel() + "\n";
        }

        return returnString;

    }

    /*
     * This method adds a number of whitespaces to a String. This method is used in
     * the getAdjacencyMatrix method
     * to ensure fixed spacing between labels and strings in the displayed matrix to
     * improve user's readability.
     */
    private String addWhitespaces(String string, int n) {
        String returnString = string;

        for (int i = 0; i < n; i++) {
            returnString += " ";
        }
        return returnString;
    }

    /* Returns all vertices of the graph as a LinkedList */
    public DSALinkedList getVertices() {
        return vertices;
    }

    /* Displays all the edges of the graph */
    public void getEdges() {
        int index = 0;

        for (Object o : edges) {
            System.out.println(index + " " + ((DSAGraphEdge) o).toString());
            index++;
        }
    }

    /*
     * Displays all possible paths from start vertex to end vertex. This is a
     * wrapper method
     */
    public void getAllPaths(String inStart, String inEnd, String inBarrier1, String inBarrier2, int inSecurity) {
        allPaths = new DSALinkedList();
        currentPath = new DSALinkedList();

        for (Object obj : vertices) {
            for (Object subObj : ((DSAGraphVertex) obj).getLinks()) {
                ((DSAGraphVertex) subObj).setVisited(false);
            }
        }

        findAllPaths(inStart, inEnd, inBarrier1, inBarrier2, inSecurity);
    }

    /* Calculate all possible paths from start vertex to end vertex */
    private void findAllPaths(String inStart, String inEnd, String inBarrier1, String inBarrier2, int inSecurity) {
        DSAGraphVertex startVertex = getVertex(inStart);
        DSAGraphVertex endVertex = getVertex(inEnd);

        if (startVertex == (null)) {
            System.out.println("No path found");
        } else if (startVertex.getVisited())
            return;
        else {
            startVertex.setVisited(true);
            currentPath.insertLast(startVertex);

            if (startVertex == endVertex) {
                DSALinkedList tempList = new DSALinkedList();

                for (Object c : currentPath) {
                    tempList.insertLast(c);
                }
                allPaths.insertLast(tempList);
                startVertex.setVisited(false);
                currentPath.removeLast();
                return;
            }

            for (Object o : startVertex.getLinks()) {
                DSAGraphVertex currentVertex = (DSAGraphVertex) o;
                boolean doesNotHaveBarriers = false;

                DSALinkedList edgesofCurrentVertex = getedgesofVertex(currentVertex);
                for (Object obj : edgesofCurrentVertex) {
                    Connection connection = (Connection) ((DSAGraphEdge) obj).getValue();
                    if (!(connection.getBarriers()).equals(inBarrier1) &&
                            !(connection.getBarriers()).equals(inBarrier2) &&
                            connection.getSecurity() <= inSecurity) {
                        doesNotHaveBarriers = true;
                    }
                }
                if (doesNotHaveBarriers) {
                    findAllPaths(currentVertex.getLabel(), inEnd, inBarrier1, inBarrier2, inSecurity);
                }

            }
            currentPath.removeLast();
            startVertex.setVisited(false);
        }

    }

    /**
     * This method is used to get all existing edges of a given vertex. This is a
     * private method of a wrapper method
     */
    private DSALinkedList getedgesofVertex(DSAGraphVertex inStart) {
        String startVertexName = inStart.getLabel();
        DSALinkedList returnList = new DSALinkedList();

        for (Object o : edges) {
            DSAGraphEdge edge = (DSAGraphEdge) o;
            Connection currentConnection = (Connection) edge.getValue();

            if ((currentConnection.getFromLocation()).equals(startVertexName))
                returnList.insertLast(edge);
        }
        return returnList;
    }

    /** Wrapper method used to print edges of a vertex */
    public void printEdgesofVertex(String inStart) {
        DSALinkedList edgeList = getedgesofVertex(getVertex(inStart));

        for (Object o : edgeList) {
            Connection connection = (Connection) ((DSAGraphEdge) o).getValue();
            System.out.print(connection.getFromLocation() + "  ");
            System.out.print(connection.getToLocation() + "  ");
            System.out.print(connection.getDistance() + "  ");
            System.out.print(connection.getBarriers() + "  ");
            System.out.print(connection.getSecurity() + "  \n");
        }
    }

    /**
     * Method used to obtain routes from one location to another, this method
     * filters the routes
     * according to security clearance and obstacles.
     */
    private DSALinkedList getRoutes(String inStart, String inEnd, String inBarrier1, String inBarrier2,
            int inSecurity) {
        allPaths = new DSALinkedList();
        currentPath = new DSALinkedList();
        DSALinkedList routesToRank = new DSALinkedList();

        for (Object obj : vertices) {
            for (Object subObj : ((DSAGraphVertex) obj).getLinks()) {
                ((DSAGraphVertex) subObj).setVisited(false);
            }
        }

        findAllPaths(inStart, inEnd, inBarrier1, inBarrier2, inSecurity);

        for (Object o : allPaths) {
            DSALinkedList currentList = (DSALinkedList) o;
            float distance = getDistanceofRoute(currentList);

            Route route = new Route(currentList, distance);

            routesToRank.insertLast(route);
        }

        return routesToRank;

    }

    public String getRankedRoutes(String inStart, String inEnd, String inBarrier1, String inBarrier2, int inSecurity) {
        DSALinkedList routes = getRoutes(inStart, inEnd, inBarrier1, inBarrier2, inSecurity);
        Route[] routesArray = new Route[routes.getCount()];
        int i = 0;
        String returnString = "";

        for (Object o : routes) {
            routesArray[i] = (Route) o;
            i++;
        }

        insertionSort(routesArray);

        for (int ii = 0; ii < routesArray.length; ii++) {
            DSALinkedList currentRoute = routesArray[ii].getRoute();
            float distance = getDistanceofRoute(currentRoute);

            for (Object o : currentRoute) {
                returnString += ((DSAGraphVertex) o).getLabel() + " ";
            }
            returnString += "Distance: " + distance + "\n\n";
        }

        if (returnString.isEmpty())
            returnString += "No routes available";

        returnString += "\n\nStart location: " + inStart + "  End location: " + inEnd
                + "\nSecurity clearance: " + inSecurity + "   Barriers to avoid: " + inBarrier1 + ", " + inBarrier2;
        return returnString;

    }

    public static void insertionSort(Route[] A) {
        for (int n = 1; n < (A.length); n++) {
            int index = n;

            while ((index > 0) && (A[index - 1].getDistance() > A[index].getDistance())) {
                Route temp;

                temp = A[index];
                A[index] = A[index - 1];
                A[index - 1] = temp;

                index--;
            }
        }
    }

    public float getDistanceofRoute(DSALinkedList inRoute) {
        float routeDistance = 0;
        int i = 0;

        DSAGraphVertex[] tempArray = new DSAGraphVertex[inRoute.getCount()];

        for (Object obj : inRoute) {
            tempArray[i] = (DSAGraphVertex) obj;
            i++;
        }

        for (int ii = 0; ii < tempArray.length - 1; ii++) {
            routeDistance += getDistance(tempArray[ii].getLabel(), tempArray[ii + 1].getLabel());
        }

        return routeDistance;
    }

    private double getDistance(String inFrom, String inTo) {
        double distance = 0;

        for (Object o : edges) {
            DSAGraphEdge edge = (DSAGraphEdge) o;
            Connection currentConnection = (Connection) edge.getValue();

            if (edge.getFrom().equals(inFrom) && edge.getTo().equals(inTo)) {
                distance = currentConnection.getDistance();
            }

        }

        return distance;
    }

    public void printEdges() {
        int i = 1;

        for (Object o : edges) {
            DSAGraphEdge currentEdge = (DSAGraphEdge) o;
            Connection connection = (Connection) currentEdge.getValue();

            System.out.println(i + "  From: " + currentEdge.getFrom() +
                    "  To: " + currentEdge.getTo() +
                    "  Distance " + connection.getDistance() +
                    "  Secutriy level " + connection.getSecurity() +
                    "  Barriers " + connection.getBarriers());
            i++;
        }
    }

    public void parameterTweak(double inScaleFactor, int inOption) {
        if (inOption == 1) {
            for (Object o : edges) {
                DSAGraphEdge edge = (DSAGraphEdge) o;
                Connection connection = (Connection) edge.getValue();

                double distance = connection.getDistance();

                connection.setDistance(distance * inScaleFactor);
            }

        } else if (inOption == 2) {
            for (Object o : edges) {
                Connection connection = (Connection) (((DSAGraphEdge) o).getValue());

                double distance = connection.getDistance();

                connection.setDistance(distance / inScaleFactor);
            }
        }

    }

    /**
     * Purpose: This method finds a specific node by label and returns it
     * as a GraphVertex.
     * 
     * @param inName
     * @return
     */
    public DSAGraphVertex findNode(String inName) {
        DSAGraphVertex node = null;

        for (Object o : vertices) {
            DSAGraphVertex tempNode = (DSAGraphVertex) o;

            if (tempNode.getLabel().equals(inName))
                node = tempNode;
            else
                System.out.println("Node not found");

        }

        return node;
    }

    public void deleteNode(String inLabel) {
        DSAGraphVertex nodeToDelete = getVertex(inLabel);

        /* Deleting the node from the vertex list */
        vertices.delete(nodeToDelete);

        /* Deleting the node from the edge list */
        for (Object o : edges) {
            DSAGraphEdge edge = (DSAGraphEdge) o;

            if (edge.getFrom().equals(inLabel)
                    || edge.getTo().equals(inLabel)) {
                edges.delete(edge);

            }
        }

        /* Deleting the node from the list of links in other nodes */
        for (Object o : vertices) {
            DSAGraphVertex vertex = (DSAGraphVertex) o;
            DSALinkedList links = vertex.getLinks();

            for (Object obj : links) {
                if (inLabel.equals(((DSAGraphVertex) obj).getLabel()))
                    links.delete((DSAGraphVertex) obj);
            }
        }
    }

    public void updateVertex(String oldLabel, String newLabel) {
        for (Object o : vertices) {
            DSAGraphVertex vertex = (DSAGraphVertex) o;
            DSALinkedList links = vertex.getLinks();

            if (vertex.getLabel().equals(oldLabel))
                vertex.setLabel(newLabel);

            for (Object obj : links) {
                DSAGraphVertex tempVertex = (DSAGraphVertex) obj;

                if (tempVertex.getLabel().equals(oldLabel))
                    tempVertex.setLabel(newLabel);
            }
        }

        for (Object obj : edges) {
            DSAGraphEdge edge = (DSAGraphEdge) obj;
            Connection connection = (Connection) edge.getValue();

            if (edge.getFrom().equals(oldLabel))
                edge.setFrom(newLabel);

            if (edge.getTo().equals(oldLabel))
                edge.setTo(newLabel);

            if (connection.getFromLocation().equals(oldLabel))
                connection.setFromLocation(newLabel);

            if (connection.getToLocation().equals(oldLabel))
                connection.setToLocation(newLabel);
        }

    }

    public void removeEdge(Connection inConnection) {
        for (Object o : edges) {
            DSAGraphEdge edge = (DSAGraphEdge) o;
            Connection connection = (Connection) edge.getValue();

            if (connection.equals(inConnection)) {
                edges.delete(edge);
            }
        }
    }

    public void updateEdge(Connection oldConnection, Connection newConnection) {
        for (Object o : edges) {
            DSAGraphEdge edge = (DSAGraphEdge) o;
            Connection connection = (Connection) edge.getValue();

            if (connection.equals(oldConnection)) {
                edge.setValue(newConnection);

                edge.setFrom(newConnection.getFromLocation());
                edge.setTo(newConnection.getToLocation());
            }
        }
    }

    public DSAGraphEdge findEdge(Connection inConnection) {
        DSAGraphEdge edgetoReturn = null;

        for (Object o : edges) {
            DSAGraphEdge edge = (DSAGraphEdge) o;
            Connection connection = (Connection) edge.getValue();

            if (connection.equals(inConnection))
                edgetoReturn = edge;
        }

        return edgetoReturn;
    }

    public void deleteEdge(Connection inConnection) {
        for (Object o : edges) {
            DSAGraphEdge edge = (DSAGraphEdge) o;
            Connection connection = (Connection) edge.getValue();

            if (connection.equals(inConnection))
                edges.delete(edge);
        }
    }

    public String getWorld() {
        String returnString = "";
        int maxCharCount = 25;

        for (Object o : edges) {
            Connection connection = (Connection) ((DSAGraphEdge) o).getValue();

            String from = "From: " + connection.getFromLocation();
            String to = "To: " + connection.getToLocation();
            String distance = "Distance: " + String.valueOf(connection.getDistance());
            String security = "Security Level: " + String.valueOf(connection.getSecurity());
            String barriers = "Barrier: " + connection.getBarriers();

            returnString += addWhitespaces(from, maxCharCount - from.length())
                    + addWhitespaces(to, maxCharCount - to.length())
                    + addWhitespaces(distance, maxCharCount - distance.length())
                    + addWhitespaces(security, maxCharCount - security.length())
                    + addWhitespaces(barriers, maxCharCount - barriers.length())
                    + "\n";

        }
        return returnString;
    }
}
