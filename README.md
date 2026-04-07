Policy-Enforced AS Routing
This project implements a core functionality of a Link State Intra-AS routing algorithm with a specific focus on Security Policy Enforcement. While standard routing protocols prioritize the shortest path based solely on cost, this implementation ensures that all outbound traffic undergoes a mandatory security inspection before exiting the Autonomous System (AS).

The Problem
In modern network security, organizations often require all datagrams leaving an internal network to pass through a specific inspection point—a Security Agent (SA)—to prevent data exfiltration or unauthorized access.

This implementation enforces three critical policies:

Mandatory Inspection: Every datagram leaving the AS must pass through the SA before it reaches an exit gateway.

Intermediate Node Constraint: An exit gateway cannot serve as an intermediate hop on the way to the SA.

Optimized Exit: Once a packet has been inspected by the SA, it must take the shortest path to its designated exit gateway.

Implementation Details
The system processes a weighted directed graph representing the AS topology and generates optimized forwarding tables for all non-gateway routers.

Language: Java

Algorithm: Modified Dijkstra's Algorithm

Logic: * The algorithm runs Dijkstra from the source to the SA while treating all gateways as "dead ends" to ensure no traffic bypasses inspection.

A second Dijkstra pass calculates the shortest path from the SA to all available gateways.

These results are combined to form a final forwarding table that minimizes total cost while adhering 100% to security constraints.

How It Works
Input Format

The program reads from standard input in the following order:

n: The number of routers in the AS.

Adjacency Matrix: An n×n matrix representing edge weights (use -1 for no connection).

Gateways: A list of router indices acting as exit points.

SA: The index of the Security Agent router.

Output

The program generates a forwarding table for every non-gateway router, detailing:

To: The destination gateway.

Cost: The total optimized cost from source → SA → Gateway.

Next Hop: The immediate neighbor(s) to reach the SA or the gateway.

Why This Matters
Standard shortest-path algorithms are often insufficient for secure enterprise environments. This project demonstrates the ability to engineer custom routing logic that balances network efficiency with security compliance, a core requirement for Junior Network and Security Operations roles.