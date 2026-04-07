Intra-AS Routing with Mandatory Security Inspection:
This project implements a custom Link State Intra-AS routing protocol designed for high-security environments. Unlike standard protocols that optimize purely for cost, Secure-Path enforces a strict security policy: every datagram must undergo inspection at a designated Security Agent (SA) before exiting the system through a gateway.

Core Features:

Mandatory Policy Enforcement: Guaranteed 100% inspection rate by forcing all outbound traffic through the SA.

Non-Bypass Routing: Logic prevents "shortcut" routes through gateways before the inspection point is reached.

Dual-Phase Path Optimization: Uses a constrained Dijkstra approach to find the shortest secure path.

Automated Forwarding Tables: Generates verified hop-by-hop instructions for all non-gateway routers in the Autonomous System.

Technical Stack:

Language: Java (JDK 11+)

Algorithm: Modified Dijkstra’s (Shortest Path First)

Data Structures: PriorityQueues, Adjacency Matrices, TreeSets (for deterministic hop sorting)

The Routing Logic:

The system calculates the total cost for a secure path using the following logic:

TotalCost = dist(Source→SA) + dist(SA→Gateway)
Phase 1 (Inbound to SA): The algorithm runs from the source to the SA, treating all gateways as unreachable "sink nodes" to prevent policy violations.

Phase 2 (Outbound to Gateway): A second pass calculates the most efficient exit route from the SA to the target gateway.
