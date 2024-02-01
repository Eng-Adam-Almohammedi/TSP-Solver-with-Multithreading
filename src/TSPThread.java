
import java.util.List;

public class TSPThread extends Thread {
    private final List<Point> points;
    private final int[] permutation;
    private int startFrompoint  = 0;
    private final int startPointThreadIndex;
    private final int endPointIndex; 
    private int[] minCostPath;
    private double distance;

    public TSPThread(List<Point> points, int[] permutation,int startPoint, int endPoint, int startAtPoint) {
        this.points = points;
        this.permutation = permutation.clone();
        startPointThreadIndex = startPoint;
        endPointIndex = endPoint;
        this.distance = Double.MAX_VALUE;
        this.startFrompoint  = startAtPoint;
    }

    public double getDistance() {
        return distance;
    }

    public int[] getPermutation() {
        return permutation;
    }

    public int[] getMinPathPermutation() {
        return minCostPath;
    }

    @Override
    public void run() {
        for (int currentPoint = startPointThreadIndex; currentPoint < endPointIndex; currentPoint++) {
            int[] permutationnew = this.permutation.clone();
            permute(points, permutationnew, startFrompoint , currentPoint);
        }
    }

    private void permute(List<Point> points, int[] perm, int index, int swapedPoint) {
        if (index == perm.length - 1) {
            double dist = calculateTotalDistance(points, perm);
            if (dist < this.distance) {
                this.distance = dist;
                this.minCostPath = perm.clone();
            }
        } else {
            for (int i = index; i < perm.length; i++) {
                swap(perm, swapedPoint, i);
                permute(points, perm, index + 1, swapedPoint);
            }
        }
    }

    private static double calculateDistance(Point first, Point second) {
        double sumsq = Math.pow(first.x_c - second.x_c, 2) + Math.pow(first.y_c - second.y_c, 2);
        double dist = Math.sqrt(sumsq);
        return dist;
    }
    private static double calculateTotalDistance(List<Point> points, int[] permutation) {
        double totalDistance = 0;
        for (int i = 0; i < permutation.length - 1; i++) {
            int currentIndex = permutation[i];
            int nextIndex = permutation[i + 1];
            Point current = points.get(currentIndex);
            Point next = points.get(nextIndex);
            totalDistance += calculateDistance(current, next);
        }
        Point current = points.get(permutation[permutation.length - 1]);
        Point next = points.get(permutation[0]);
        // Add distance from the last point to the starting point
        totalDistance += calculateDistance(current, next);
        return totalDistance;
    }
    private static void swap(int[] permutation, int current, int newPostion) {
        int temp = permutation[current];
        permutation[current] = permutation[newPostion];
        permutation[newPostion] = temp;
    }
}
