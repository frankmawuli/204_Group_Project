import java.util.*;

public class whereNow
{
    public static void main(String[] args)
    {
        String mapFile;
        String journeyFile;
        DSAGraph graph=null;
        Journey journeyDetails=null;
        String routes="";
        String serializedFilename="SerializedGraph.txt";
        String rankedRoutesFile="RankedRoutes.txt";

        int userChoice=0;

        if(args.length==0)
            System.out.println("\nIn command line, enter '-i' or'-s' for interactive mode and silent mode respectively.\n");
        else
        {
            Scanner scan=new Scanner(System.in);

            String modeChoice=args[0];

            if(modeChoice.trim().equals("-i"))
            {
                System.out.println("Interactive mode");

                while(userChoice!=-1)
                {
                    try
                    {
                        System.out.println("1. Load input file");
                        System.out.println("2. Node Operations (find, insert, delete, update)");
                        System.out.println("3. Edge Operations (find, insert, remove, update)");
                        System.out.println("4. Parameter tweaks (Enter a scalar factor with a choice of * or /)");
                        System.out.println("5. Display graph");
                        System.out.println("6. Display world");
                        System.out.println("7. Enter journey details");
                        System.out.println("8. Generate routes");
                        System.out.println("9. Display routes");
                        System.out.println("10. Save network");

                        userChoice=Integer.parseInt(scan.nextLine());

                        if(userChoice==1)
                        {
                            System.out.print("Enter input map file name: ");
                            System.out.print("Enter 1 to read a Map file and 2 to read a pre-saved file: ");
                            int choice=Integer.parseInt(scan.nextLine());

                            if(choice==1)
                            {
                                mapFile=scan.nextLine().trim();
                                graph=MapFileIO.getGraphFromMapFile(mapFile);
                            }
                            else if(choice==2)
                            {
                                mapFile=scan.nextLine().trim();
                                graph=(DSAGraph)MapFileIO.deserializeGraph(mapFile);
                            }

                        }
                        else if(userChoice==2)
                        {
                            int userOperationChoice;
                            System.out.println("1. Find Node  2. Insert new node  3. Delete node  4. Update node");
                            userOperationChoice=Integer.parseInt(scan.nextLine());

                            if(userOperationChoice==1)
                            {
                                System.out.print("Enter the label of the node to find: ");
                                DSAGraphVertex vertex=graph.getVertex(scan.nextLine());
                                System.out.println(vertex.toString());
                            }
                            else if(userOperationChoice==2)
                            {
                                System.out.print("Enter the label of new node: ");
                                String sLabel=scan.nextLine();
                                graph.addVertex(sLabel.trim(), null);
                            }
                            else if(userOperationChoice==3)
                            {
                                System.out.print("Enter the label of the node to delete");
                                graph.deleteNode(scan.nextLine().trim());
                            }
                            else if(userOperationChoice==4)
                            {
                                System.out.print("Enter label to update: ");
                                String oldLabel=scan.nextLine().trim();
                                System.out.print("Enter new label name: ");
                                String newLabel=scan.nextLine().trim();

                                graph.updateVertex(oldLabel, newLabel);
                            }
                        }
                        else if(userChoice==3)
                        {
                            int userOperationChoice;
                            System.out.println("1. Find Edge  2. Insert new Edge  3. Delete Edge  4. Update Edge");
                            userOperationChoice=Integer.parseInt(scan.nextLine());

                            if(userOperationChoice==1)
                            {
                                System.out.println("Enter from, to, distance, security lvl and barriers seperated by a ',");
                                String[] connection=scan.nextLine().split(",");

                                DSAGraphEdge edgeFound=graph.findEdge(new Connection(connection[0].trim(), connection[1].trim(),
                                        Integer.parseInt(connection[2]),
                                        Integer.parseInt(connection[3]),
                                        connection[4].trim()));
                                edgeFound.toString();
                            }
                            else if(userOperationChoice==2)
                            {
                                System.out.println("Enter from, to, distance, security lvl and barriers seperated by a ',");
                                String[] line=scan.nextLine().split(",");

                                Connection connection=new Connection(line[0].trim(), line[1].trim(),
                                        Integer.parseInt(line[2]),
                                        Integer.parseInt(line[3]),
                                        line[4].trim());

                                graph.addEdge(line[0], line[1], connection, true);
                            }
                            else if(userOperationChoice==3)
                            {
                                System.out.println("Enter from, to, distance, security lvl and barriers seperated by a ',");
                                String[] line=scan.nextLine().split(",");

                                graph.deleteEdge(new Connection(line[0].trim(), line[1].trim(),
                                        Integer.parseInt(line[2]),
                                        Integer.parseInt(line[3]),
                                        line[4].trim()));
                            }

                        }
                        else if(userChoice==4)
                        {
                            System.out.print("Enter a parameter tweak: ");
                            int parameterScalarFactor=Integer.parseInt(scan.nextLine());

                            System.out.print("\nEnter a parameter operation  1.Multiply  2.Divide: ");
                            int parameterOperation=Integer.parseInt(scan.nextLine());

                            graph.parameterTweak(parameterScalarFactor, parameterOperation);
                        }
                        else if(userChoice==5)
                        {
                            System.out.print("Enter 1 for the adjacency list and 2 for adjacency matrix: ");
                            int choice=Integer.parseInt(scan.nextLine());

                            if(choice==1)
                            {
                                graph.displayAsList();
                            }
                            else if(choice==2)
                            {
                                System.out.println(graph.getAdjacencyMatrix());
                            }
                            else
                                System.out.println("Inavlid choice");

                            System.out.println("Would you like save this to 'AdjacencyMatrix.txt'? Enter 1 for YES or 0 for NO");
                            choice=Integer.parseInt(scan.nextLine());

                            if(choice==1)
                                JourneyFileIO.writeToFile("AdjacencyMatrix.txt", graph.getAdjacencyMatrix());


                        }
                        else if(userChoice==6)
                        {
                            String graphWorld=graph.getWorld();

                            System.out.println("Woudl you like save this to 'World.txt'? Enter 1 for YES or 0 for NO");
                            int userSaveOption=Integer.parseInt(scan.nextLine());

                            if(userSaveOption==1)
                                JourneyFileIO.writeToFile("World.txt", graphWorld);

                            System.out.println(graphWorld);

                        }
                        else if(userChoice==7)
                        {
                            System.out.print("Enter journey file name: ");
                            journeyFile=scan.nextLine().trim();

                            journeyDetails=JourneyFileIO.readJourneyFile(journeyFile);
                        }
                        else if(userChoice==8)
                        {
                            if(journeyDetails!=null)
                            {
                                routes=graph.getRankedRoutes(journeyDetails.getSource(),
                                        journeyDetails.getDestination(),
                                        journeyDetails.getBarrier1(),
                                        journeyDetails.getBarrier2(),
                                        journeyDetails.getSecurityClearance());
                            }
                            else
                                System.out.println("Enter journey details first.");
                        }
                        else if(userChoice==9)
                        {
                            int userSaveOption;
                            System.out.println("Would you like save this to 'RankedRoutes.txt'? Enter 1 for YES or 0 for NO");
                            userSaveOption=Integer.parseInt(scan.nextLine());

                            if(userSaveOption==1)
                                JourneyFileIO.writeToFile(rankedRoutesFile, routes);

                            System.out.println(routes);

                        }
                        else if(userChoice==10)
                        {
                            MapFileIO.serializeGraph(graph, serializedFilename);
                        }

                    }
                    catch(Exception e)
                    {
                        System.out.println("Error occurred: "+e.getMessage());
                    }
                }

            }
            else if(modeChoice.trim().equals("-s"))
            {
                System.out.println("Silent mode");

                System.out.print("Enter input map file name: ");
                mapFile=scan.nextLine().trim();

                graph=MapFileIO.getGraphFromMapFile(mapFile);

                System.out.print("Enter journey file name: ");
                journeyFile=scan.nextLine().trim();

                journeyDetails=JourneyFileIO.readJourneyFile(journeyFile);
            }


            scan.close();
        }


    }
}


