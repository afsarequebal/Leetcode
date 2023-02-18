class UnionFind {

    // It tracks ths number of components
    int count;

    // It defines the root/parent of a group of elements
    int[] parent;

    // It is used to keep the parent array grow in width, instead of height.
    // Reducing the search time of parent of an element
    int[] rank;
    
    // Initializing the elements
    UnionFind(int m, int n) {
        count = 0;
        parent = new int[m * n];
        rank = new int[m * n];
    }

    public int getCount() {
        return count;
    }
    public int incCount(int i, int j, int n) {
         parent[i * n + j] = i * n + j;
        return count++;
    }

    // Submerging two groups into one
    //union with rank
    void union(int x, int y) { 
        int rootx = find(x);
        int rooty = find(y);
        if(rootx!=rooty) {
            if(rank[rootx] > rank[rooty]) {
                parent[rooty] = rootx;
            } else if(rank[rootx] < rank[rooty]) {
                parent[rootx] = rooty;
            } else {
                parent[rooty] = rootx; rank[rootx]+=1;
            }
            count--;
        }
    }
    
    // finding the parent of an element
    int find(int i) {//path compression
        if(parent[i]!=i) parent[i] = find(parent[i]);
        return parent[i];
    }

    public List<Integer> numIslands2(int m, int n, int[][] positions) {
        if(positions.length==0) return new ArrayList<>();

        //Iniitalize a union find data structure
        UnionFind uf  = new UnionFind(m, n);

        int[][] p = new int[m][n];

        // ArrayList to be returned
        List<Integer> l = new ArrayList<>();

        // Iterating over all elements and merging similar groups 
        for (int k = 0 ; k < positions.length; k++) {
            int i = positions[k][0]; int j = positions[k][1];
                if (p[i][j] != 1) {
                    p[i][j]=1;

                    uf.incCount(i,j,n);

                    //check right
                    if (j+1<n&& p[i][j+1]==1) {
                        uf.union(i * n + j, (i) * n + j+1);
                    }
                    
                    //check down
                    if (i+1<m && p[i+1][j]==1) {
                        uf.union(i * n + j, (i+1) * n + j);
                    }

                    //check left
                    if (j-1>-1  && p[i][j-1]==1) {
                        uf.union(i * n + j, (i) * n + j-1);
                    }

                    // check up
                    if (i-1>-1 && p[i-1][j]==1) {
                        uf.union(i * n + j, (i-1) * n + j);
                    }
                }
            l.add(uf.getCount());
        }
        return l;
    }