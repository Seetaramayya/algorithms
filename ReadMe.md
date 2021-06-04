# Algorithms
  
 Refreshing algorithms and datastructures using coursera course [algorithms](https://www.coursera.org/learn/algorithms-part1) by unlce Bob (Bob Sedgewick)
 
## Week1 
- Dynamic Connectivity
    - Quick Find
    - Quick Union
    - Union Find
- Assignment is Percolation, it is available in `tutorial.java.part1.assignment.percolation.Percolation`
- Analysis of Algorithms
- Theory of Algorithms
- Memory

## Week 2

- Stacks and implement with our own linked list
- Stacks with resizable arrays
- Queues 
- Generics
- Iterators
- Bags
- Assignment 
  - tutorial.java.part1.assignment.permutation.Deque (double linked list) implementation
  - tutorial.java.part1.assignment.permutation.RandomizedQueue (with array) implementation
  - tutorial.java.part1.assignment.permutation.Permutation
- Sorting
  - Insertion sort
  
  
  
## TODO 


- Following sortings needs to be written in Scala (of course there should not be any difference in the logic, readability might change)
  - Selection sort (unstable + O(N^2) + inplace)
  - Insertion sort (stable + O(N^2) + inplace) average case is better than selection sort
  - Shell sort (unstable + O(N^3/2) + inplace) is a kind of `Insertion` sort, but it moves `h` instead of 1 movement. Performance is way better than elementary sorts.
  - Merge sort (stable + O(NlogN) + extra space O(N) ) 
  - Quick sort (unstable + O(NlogN) + inplace) but needs to be careful otherwise worst case will be O(N^2) 
  - Heap sort (unstable + O(NlogN) + inplace) guaranteed average O(NlogN) but less used in reality due to unstable algorithm
  
- Following sortings needs to be written in Java
  - Quick sort
  - Heap sort

- Binary heap (both in java and scala)

- Benchmark above programs

