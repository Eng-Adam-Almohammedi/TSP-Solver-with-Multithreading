
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.*;
import java.util.Scanner;

public class TSPMain {

    private static int[] generateInitialPermutation(int size) {
        int[] permutation = new int[size];
        for (int i = 0; i < size; i++) {
            permutation[i] = i;
        }
        return permutation;
    }

    public static void findMinPath(List<Point> tsppoints, int start, int numTheads) {
        int[] initialPermutation = generateInitialPermutation(tsppoints.size());
        int startFromPoint = start;
        if (startFromPoint == -1) {
            System.out.println("Start point not found in the list.");
            return;
        }
        List<TSPThread> threads = initThreads(tsppoints, numTheads, initialPermutation, startFromPoint);
        startThreads(threads);
        printMinPathDistance(tsppoints, threads);
    }

    private static List<TSPThread> initThreads(List<Point> tsppoints, int numThreads, int[] initialPermutation, int startFromPoint) {
        int numPoints = tsppoints.size();
        int numPermute = fact(numPoints);
        System.out.print("Input Size: " + numPoints + "  Number Permutations: (" + numPoints + "!) = " + numPermute + " | Threads: " + numThreads);
        List<TSPThread> workers = new ArrayList<>();
        for (int i = 0; i < numThreads; i++) {
            int[] permutation = initialPermutation.clone();
            int startTrPoint = (numPoints / numThreads) * i;
            int endTrPoint = (numPoints * (i + 1)) / numThreads;
            TSPThread worker = new TSPThread(tsppoints, permutation, startTrPoint, endTrPoint, startFromPoint);
            workers.add(worker);
        }
        return workers;
    }

    private static void startThreads(List<TSPThread> threads) {
        long startTime, endTime, totalTime;
        startTime = System.currentTimeMillis();
        for (TSPThread thread : threads) {
            thread.start();
        }
        for (TSPThread worker : threads) {
            try {
                worker.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        endTime = System.currentTimeMillis();
        totalTime = endTime - startTime;
        System.out.println(" | Elapsed Time: " + totalTime + " milliseconds");
    }

    public static void printMinPathDistance(List<Point> points, List<TSPThread> workers) {
        double minDistance = Double.MAX_VALUE;
        int n = points.size();
        int[] minPatht = new int[n];
        for (TSPThread worker : workers) {
            double distance = worker.getDistance();
            if (distance < minDistance) {
                minDistance = distance;
                minPatht = worker.getMinPathPermutation();
            }
        }
        printPath(points, minPatht, minDistance);

    }

    public static void printPath(List<Point> tsppoin, int[] minPath, double dist) {
        if (minPath != null) {
            System.out.print("Minimum path: ");
            for (int i = 0; i < minPath.length; i++) {
                System.out.print(tsppoin.get(minPath[i]).name + " -> ");
            }
            System.out.print(tsppoin.get(minPath[0]).name); // Return to the starting point
            System.out.println("\tdist: " + dist);

        }
    }

    static List<Point> readPointsFromFile(String inputFile) {
        List<Point> pointsn = new ArrayList<>();
        File file = new File(inputFile);
        try {
            Scanner in = new Scanner(file);
            while (in.hasNext()) {
                String name = in.next();
                int x = in.nextInt();
                int y = in.nextInt();
                pointsn.add(new Point(name, x, y));
            }
            for (Point p : pointsn) {
                System.out.println(p.name + ",(" + p.x_c + "," + p.y_c + ")");
            }
            return pointsn;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return pointsn;
    }

    public static int fact(int n) {
        if (n == 1) {
            return n;
        }
        return (n * fact(n - 1));
    }

    public static void main(String[] args) {
        String[] inputFiles = {"input_10.txt", "input_12.txt"};
        int startPoint = 1;
        int numThreads = 1;
        for (String inputFile : inputFiles) {
            List<Point> points3 = readPointsFromFile(inputFile);
            findMinPath(points3, startPoint, numThreads);
            System.out.println("==");
        }
    }
}
