

public class Graph {
    private DoubleLL[] vertex;  // Array of adjacency lists for the graph
    private DoubleLL freeSlots; // Doubly linked list to track freed slots
    private int numberOfNodes;  // Number of nodes in the graph
    private int[] parent;       // Parent array for union-find
    private int[] rank;         // Rank array for union-find

    public Graph(int initialSize) {
        this.vertex = new DoubleLL[initialSize];
        this.numberOfNodes = 0;
        this.freeSlots = new DoubleLL(); // Initializes a new linked list for free slots
        this.parent = new int[initialSize];
        this.rank = new int[initialSize];
        for (int i = 0; i < initialSize; i++) {
            parent[i] = i;  // Each node is its own parent (self loop)
            rank[i] = 0;    // Initially, all trees have rank 0
        }
    }

    // Method to create a new node
    public void newNode() {
        if (!freeSlots.isEmpty()) {
            int reusedIndex = freeSlots.removeFirst();
            vertex[reusedIndex] = new DoubleLL();  // Create a new adjacency list for the node
        } else {
            if (numberOfNodes == vertex.length) {
                this.expand();  // Expand graph if full
            }
            vertex[numberOfNodes] = new DoubleLL();  // Add new adjacency list for the node
            parent[numberOfNodes] = numberOfNodes;  // Initialize the union-find parent
            numberOfNodes++;
        }
    }

    // Union-Find: Find method with path compression
    public int find(int node) {
        if (parent[node] != node) {
            parent[node] = find(parent[node]);  // Path compression
        }
        return parent[node];
    }

    // Union-Find: Union method with union by rank
    public void union(int node1, int node2) {
        int root1 = find(node1);
        int root2 = find(node2);

        if (root1 != root2) {
            if (rank[root1] > rank[root2]) {
                parent[root2] = root1;  // Attach smaller tree under the larger tree
            } else if (rank[root1] < rank[root2]) {
                parent[root1] = root2;
            } else {
                parent[root2] = root1;
                rank[root1]++;
            }
        }
    }

    // Method to add an edge between two nodes
    public void addEdge(int node1, int node2) {
        if (node1 >= vertex.length || node2 >= vertex.length || vertex[node1] == null || vertex[node2] == null) {
            throw new IllegalArgumentException("Invalid node indices");
        }
        vertex[node1].add(node2);
        vertex[node2].add(node1);

        // Perform union to indicate that node1 and node2 are now connected
        union(node1, node2);
    }

    // Method to check if two nodes are connected
    public boolean areConnected(int node1, int node2) {
        return find(node1) == find(node2);  // If they have the same root, they are connected
    }

    // Method to remove a node and all its edges
    public void removeNode(int node) {
        if (vertex[node] != null) {
            while (!vertex[node].isEmpty()) {
                int adjacentNode = vertex[node].removeFirst();
                vertex[adjacentNode].remove(node);  // Remove reciprocal edge
            }
            vertex[node] = null;
            freeSlots.add(node);  // Add the removed node index to freeSlots for reuse
        }
    }

    // Helper method to expand the vertex array
    private void expand() {
        int newSize = vertex.length * 2;
        DoubleLL[] newVertex = new DoubleLL[newSize];
        int[] newParent = new int[newSize];
        int[] newRank = new int[newSize];

        System.arraycopy(vertex, 0, newVertex, 0, vertex.length);
        System.arraycopy(parent, 0, newParent, 0, parent.length);
        System.arraycopy(rank, 0, newRank, 0, rank.length);

        vertex = newVertex;
        parent = newParent;
        rank = newRank;
    }
}
