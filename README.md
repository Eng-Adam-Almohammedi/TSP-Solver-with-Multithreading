# TSP Solver with Multithreading

This repository contains a solution to the Traveling Salesman Problem (TSP) using a multithreading approach in Java. The TSP is a well-known problem in computer science that involves finding the shortest possible route that visits a set of given points exactly once and returns to the starting point. The goal is to determine the optimal sequence of visits that minimizes the total distance traveled.

## Problem Description and Sequential Solution

The TSP is a challenging problem that remains unsolved for all instances efficiently. The sequential solution to the TSP is based on brute force, where all possible permutations of city visitation orders are explored, and the total distance is calculated for each permutation. The algorithm keeps track of the minimum distance encountered along with the corresponding path.

The sequential solution can be summarized as follows:

1.  Permutation Generation:  Generate all possible permutations of city visitation orders.
2.  Distance Calculation:  For each permutation, compute the total distance traveled.
3.  Optimal Path Identification: Keep track of the permutation with the minimum distance, representing the optimal path.
4.  Return Result:  Output the optimal path and its total distance.

Although the sequential solution guarantees finding the shortest tour through the cities, it becomes computationally complex for larger problem instances.

## Parallel Algorithm - Multithreading

To expedite the solution and address the computational complexity, a parallel algorithm using multithreading is introduced. The multithreading approach leverages multiple threads to concurrently explore various routes, accelerating the search for the optimal route.

### Algorithm: Brute-Force TSP with Multithreading

#### 1. Inputs:
   - `tsppoints`: List of points with coordinates read from a file.
   - `startPoint`: Starting point index.
   - `numThreads`: Number of threads for parallel processing.
#### 2. Call `findMinPath(tsppoints, startPoint, numThreads)` to start the TSP-solving process.
#### 3. Method `findMinPath(tsppoints, startPoint, numThreads):`
  - a. Initialize `initialPermutation` by generating indices for all points.
  - b. `startFromPoint = startPoint`
  - c. If `startFromPoint == -1` (not found in the list), print an error message and exit.
  - d. Initialize threads by calling `initThreads(tsppoints, numThreads, initialPermutation, startPoint)`.
  - e. Start all threads to compute permutations.
  - f. Wait for all threads to finish.
  - g. Determine the minimum path and distance from all threads.
  - h. Print the minimum path and its total distance.
   
#### 4.  Method `initThreads(tsppoints, numThreads, initialPermutation, startFromPoint)`:
  - a. Calculate the total number of permutations for the points.
  - b. Create an array of `TSPThread` objects based on the number of threads.<br>
  - c. For each thread:<br>
     - i. Clone the initial permutation.<br>
     - ii. Determine the subset of points to process for this thread.<br>
     - iii. Initialize a `TSPThread` object with the subset and start the thread.<br>
        
#### 5.  Class `TSPThread`:
  - a.  Constructor `TSPThread(tsppoints, permutation, start, end, startAtPoint)`:<br> 
     - i. Initialize `TSPThread` attributes `tsppoints`, `permutation`, `start`, `end`, and `startpoint`.
     - ii. Set distance to a maximum value and `startFrompoint` to `startAtPoint`.<br>
  - b.  Method `run()`:<br> 
     - i. For each assigned subset of points:<br>
         - Calls `permute` method passing required parameters.<br>
  - c.  Method `permute(tsppoints, permutation, index, swappedPoint)`:<br>
    -  i. Checks if the index is equal to the length of permutation - 1.<br>
    -  ii. If true:<br>
          - Calls `calculateTotalDistance` to calculate the total distance and updates the minimum distance and path if needed.<br>
     - iii. Otherwise:<br>
          - From `i = index` to the length of permutation:<br>
            a. Call `swap(permutation, swappedPoint, i)`.<br>
            b. Recursively call itself.<br>

#### 6.  Method `calculateTotalDistance(tsppoints, permutation):` 
  - a. Calculates the total distance of a given permutation.
  - b. Returns the total distance.
   
#### 7.  Method `swap(permutation, current, newPosition):` 
  - a. Swaps two elements in the permutation array.

## Sample Outputs

Here are some sample outputs for different configurations:

- When the number of threads is 1 and the number of points is 10 and 20:
  ![Sample Output](/test/out1.png)

- When the number of threads is 2 and the number of points is 10 and 20:
  ![Sample Output](/test/out2.png)

- When the number of threads is 4 and the number of points is 10 and 20:
  ![Sample Output](/test/out3.png)

## 4.	Performance evaluation in a comparison form:

| Input Size | Sequential (1 Thread) Elapsed Time (ms) | Multithreaded (2 Threads) Elapsed Time (ms) | Multithreaded (4 Threads) Elapsed Time (ms) |
|------------|---------------------------------------|-------------------------------------------|-------------------------------------------|
| 10 points  | 360                                   | 219                                       | 218                                       |
| 12 points  | 51293                                 | 27573                                     | 16564                                     |

## Contact

For any inquiries or questions regarding this project, feel free to contact me at: [email@example.com](mailto:mohammediadam24@gmail.com).