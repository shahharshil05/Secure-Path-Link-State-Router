import java.util.*;
public class RouteToGateway {
    private static final int INF = Integer.MAX_VALUE;
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        if (!sc.hasNextInt()) return;

        int n = sc.nextInt();
        int[][] adj = new int[n + 1][n + 1];
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                adj[i][j] = sc.nextInt();
                if (adj[i][j] == -1) adj[i][j] = INF;
            }
        }

        Set<Integer> gateways = new TreeSet<>();
        sc.nextLine();
        String line = sc.nextLine().trim();
        if (!line.isEmpty()) {
            String[] gwLine = line.split("\\s+");
            for (String s : gwLine) {
                if (!s.isEmpty()) gateways.add(Integer.parseInt(s));
            }
        }

        int sa = sc.nextInt();

        // Standard Dijkstra: SA to all Gateways
        Result fromSA = dijkstra(sa, n, adj, gateways, false);

        for (int i = 1; i <= n; i++) {
            if (gateways.contains(i)) continue;

            // Dijkstra: Source i to SA
            Result toSA = dijkstra(i, n, adj, gateways, true);

            System.out.println("Forwarding Table for " + i);
            System.out.println("To\tCost\tNext Hop");

            for (int gw : gateways) {
                long totalCost = INF;
                Set<Integer> nextHopsSet = new TreeSet<>();

                if (toSA.dist[sa] != INF && fromSA.dist[gw] != INF) {
                    totalCost = (long) toSA.dist[sa] + fromSA.dist[gw];
                    
                    if (i == sa) {
                        nextHopsSet.addAll(fromSA.nextHops.get(gw));
                    } else {
                        nextHopsSet.addAll(toSA.nextHops.get(sa));
                    }
                }

                String costStr = (totalCost >= INF) ? "-1" : String.valueOf(totalCost);
                String hopStr = nextHopsSet.isEmpty() ? "-1" : formatHops(nextHopsSet);
                System.out.println(gw + "\t" + costStr + "\t" + hopStr);
            }
            System.out.println();
        }
    }

    private static String formatHops(Set<Integer> hops) {
        List<Integer> list = new ArrayList<>(hops);
        Collections.sort(list);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            sb.append(list.get(i));
            if (i < list.size() - 1) sb.append(",");
        }
        return sb.toString();
    }

    private static Result dijkstra(int startNode, int n, int[][] adj, Set<Integer> gateways, boolean isSourceToSA) {
        int[] dist = new int[n + 1];
        Arrays.fill(dist, INF);
        dist[startNode] = 0;

        Map<Integer, Set<Integer>> nextHopMap = new HashMap<>();
        for (int i = 1; i <= n; i++) nextHopMap.put(i, new HashSet<>());

        PriorityQueue<Node> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a.weight));
        pq.add(new Node(startNode, 0));

        while (!pq.isEmpty()) {
            Node current = pq.poll();
            int u = current.id;

            if (current.weight > dist[u]) continue;

            if (isSourceToSA && gateways.contains(u) && u != startNode) continue;

            for (int v = 1; v <= n; v++) {
                if (adj[u][v] != INF && u != v) {
                    int newDist = dist[u] + adj[u][v];
                    if (newDist < dist[v]) {
                        dist[v] = newDist;
                        nextHopMap.get(v).clear();
                        if (u == startNode) nextHopMap.get(v).add(v);
                        else nextHopMap.get(v).addAll(nextHopMap.get(u));
                        pq.add(new Node(v, dist[v]));
                    } else if (newDist == dist[v] && newDist != INF) {
                        if (u == startNode) nextHopMap.get(v).add(v);
                        else nextHopMap.get(v).addAll(nextHopMap.get(u));
                    }
                }
            }
        }
        return new Result(dist, nextHopMap);
    }

    static class Node {
        int id, weight;
        Node(int id, int weight) { this.id = id; this.weight = weight; }
    }

    static class Result {
        int[] dist;
        Map<Integer, Set<Integer>> nextHops;
        Result(int[] dist, Map<Integer, Set<Integer>> nextHops) {
            this.dist = dist;
            this.nextHops = nextHops;
        }
    }
}