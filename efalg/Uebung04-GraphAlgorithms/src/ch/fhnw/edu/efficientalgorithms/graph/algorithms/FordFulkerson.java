package ch.fhnw.edu.efficientalgorithms.graph.algorithms;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.stream.Collectors;

import ch.fhnw.edu.efficientalgorithms.graph.Edge;
import ch.fhnw.edu.efficientalgorithms.graph.Graph;
import ch.fhnw.edu.efficientalgorithms.graph.GraphAlgorithmData;
import ch.fhnw.edu.efficientalgorithms.graph.Vertex;
import ch.fhnw.edu.efficientalgorithms.graph.edges.CapacityFlowEdge;
import ch.fhnw.edu.efficientalgorithms.graph.edges.CostCapacityFlowEdge;

public class FordFulkerson<V extends Vertex, E extends Edge> extends AbstractMaxFlowAlgorithm<V, E>  {

	public FordFulkerson() {
		super("Ford Fulkerson");
	}

	@Override
	public String execute(GraphAlgorithmData<V, E> data) {
		// TODO Auto-generated method stub
		
		V source = null;
		V sink = null;
		
		// get Source s (indegree= 0, outdegree >= 1) and Sink t (indegree >= 1, outdegree = 0)
		while (null == sink || null == source) {
			for (V v : data.getGraph().getVertices()) {
				if (data.getGraph().getOutgoingEdges(v).size() > 0 && data.getGraph().getIncomingEdges(v).size() == 0) {
					source = v;
				} else if (data.getGraph().getOutgoingEdges(v).size() == 0 && data.getGraph().getIncomingEdges(v).size() > 0) {
					sink = v;
				}
			}
		}
		
		data.getColorMapper().setVertexColor(source, Color.RED);
		data.getColorMapper().setVertexColor(sink, Color.ORANGE);	
		
		
		calculateMaxFlow(data,source,sink);
		
		return "" + calculateFlow(data, source);
	}

	@Override
	protected void calculateMaxFlow(GraphAlgorithmData<V, E> data, V source, V sink) {
		
		int flowMax = Integer.MAX_VALUE/2;
		// TODO: residual graph & edges
		Graph residualGraph = data.getGraph();
		
		// get augmented path with DFS
		Stack<V> toVisit = new Stack<V>();
		List<V> visited = new ArrayList<V>();
		Map<V, E> eToVisit = new HashMap<V, E>();
		Map<V, E> eVisited = new HashMap<V, E>();
		
		toVisit.add(source);

		// depth-first search until reaching sink
		while (!toVisit.empty()) {
			V cur = toVisit.pop();
			if (null != eToVisit.get(cur)) {
				CapacityFlowEdge e = (CapacityFlowEdge) eToVisit.get(cur);
				if (e.getCapacity()-e.getFlow() < flowMax) flowMax = e.getCapacity()-e.getFlow();
				eVisited.put(cur, eToVisit.get(cur));
			}
			if (!visited.contains(cur)) {
				visited.add(cur);
				
				// calculate flow and reset loop variables when reaching the sink
				if (cur.equals(sink)) {
					// calculate flow
					Iterator<V> it = visited.iterator();
					V to = it.next();
					V from = null;
					System.out.println("--- add maxflow " + flowMax + "-----");
					while (it.hasNext()){
						from = to;
						to = it.next();
						CapacityFlowEdge e = (CapacityFlowEdge) eVisited.get(to);
						e.setFlow(e.getFlow()+flowMax);
						System.out.println(e.toString());
						
						// TODO: residual edges
						/*if (null == data.getGraph().getEdge(to, from)) {
							//data.getGraph().addEdge(cur, prev);
						} else {
							((CapacityFlowEdge) data.getGraph().getEdge(to, from)).setFlow(flowMax);
						}*/
					}
					System.out.println("--- end add maxflow " + flowMax + "-----");
					
					// reset the loop
					visited.clear();
					toVisit.clear();
					toVisit.push(source);
					eToVisit.clear(); eVisited.clear();
					flowMax = Integer.MAX_VALUE/2;
				} else {
					List<E> out = new ArrayList<E>(data.getGraph().getOutgoingEdges(cur));
					out = out.stream().filter(e -> ((CapacityFlowEdge) e).getCapacity() - ((CapacityFlowEdge) e).getFlow() > 0).collect(Collectors.toList());
					if (out.size() == 0) {
						eVisited.remove(cur); visited.remove(cur);
					} else {
						for (E e : out) {
								V dst = otherEndpoint(data, e, cur);
								toVisit.push(dst);
								eToVisit.put(dst, e);
						}
					}
				}
			}
		}		
		
	}

}
