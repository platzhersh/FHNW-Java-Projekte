package ch.fhnw.edu.efficientalgorithms.graph.algorithms;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
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
/***
 * Algorithm to find the maximum flow through a network from source s to sink t.
 * 
 * @author chregi
 *
 * @param <V>
 * @param <E>
 */
public class FordFulkerson<V extends Vertex, E extends Edge> extends AbstractMaxFlowAlgorithm<V, E>  {

	public FordFulkerson() {
		super("Ford Fulkerson");
	}

	/***
	 * determines a valid source (indeg = 0) and sink (outdeg = 0)
	 * calls calculateMaxFlow(..) to find max flow through the network
	 * source is marked RED
	 * sink is marked ORANGE
	 */
	@Override
	public String execute(GraphAlgorithmData<V, E> data) {
		V source = null;
		V sink = null;
		
		// get Source s (indegree= 0, outdegree >= 1) and Sink t (indegree >= 1, outdegree = 0)
			for (V v : data.getGraph().getVertices()) {
				if (data.getGraph().getOutgoingEdges(v).size() > 0 && data.getGraph().getIncomingEdges(v).size() == 0) {
					source = v;
				} else if (data.getGraph().getOutgoingEdges(v).size() == 0 && data.getGraph().getIncomingEdges(v).size() > 0) {
					sink = v;
				}
			}
		
		if (sink == null || source == null) return "no valid source or sink";
		
		data.getColorMapper().setVertexColor(source, Color.RED);
		data.getColorMapper().setVertexColor(sink, Color.ORANGE);	
		
		
		calculateMaxFlow(data,source,sink);
		
		return "" + calculateFlow(data, source);
	}
	/***
	 * calculates maximum flows through the network using augmenting paths
	 * creates residual edges and adds them to the graph
	 */
	@Override
	protected synchronized void calculateMaxFlow(GraphAlgorithmData<V, E> data, final V source, final V sink) {
		
		int flowMax = Integer.MAX_VALUE/2;
		// TODO: residual graph & edges
		Graph residualGraph = data.getGraph();
		
		// get augmented path with DFS
		Stack<V> toVisit = new Stack<V>();
		List<V> visited = new LinkedList<V>();
		Map<V, E> eToVisit = new HashMap<V, E>();
		Map<V, E> eVisited = new HashMap<V, E>();
		
		toVisit.add(source);

		// depth-first search until reaching sink
		while (!toVisit.empty()) {
			V cur = toVisit.pop();
			if (null != eToVisit.get(cur)) {
				CapacityFlowEdge e = (CapacityFlowEdge) eToVisit.remove(cur);
				if (e.getCapacity()-e.getFlow() < flowMax) flowMax = e.getCapacity()-e.getFlow();
				eVisited.put(cur, (E) e);
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
						//E e =  eVisited.get(to);
						E e = data.getGraph().getEdge(from, to);
						//from = otherEndpoint(data, e, to);
						if (null == e) System.out.println("WTF!");
						try {
							((CapacityFlowEdge) e).setFlow(((CapacityFlowEdge)e).getFlow()+flowMax);
						} catch (IllegalArgumentException ie) {
							System.err.println(((CapacityFlowEdge)e).getCapacity() + " "+ ((CapacityFlowEdge)e).getFlow() +" " + flowMax);
						}
						System.out.println(from.toString() + " -> " +to.toString() + " " + e.toString());
						

						// create / update residual edges
						CapacityFlowEdge resEdge = (CapacityFlowEdge) data.getGraph().getEdge(to, from);
						if (null == resEdge){
							resEdge = new CapacityFlowEdge(((CapacityFlowEdge)e).getCapacity());
							if (!data.getGraph().addEdge(to, from, (E) resEdge)) {
								System.out.println("Could not add residual Edge " + resEdge.toString());
							}
						}
						
						int resFlow = resEdge.getCapacity() - ((CapacityFlowEdge) e).getFlow();
						resEdge.setFlow(resFlow);
						
					}
					System.out.println("--- end add maxflow " + flowMax + "-----");
					
					// reset the loop
					visited.clear();
					toVisit.clear();
					toVisit.push(source);
					eToVisit.clear(); eVisited.clear();
					flowMax = Integer.MAX_VALUE/2;
				} else {
					// get valid outgoing edges
					List<E> out = new ArrayList<E>(data.getGraph().getOutgoingEdges(cur));
					out = out.stream().filter(e -> ((CapacityFlowEdge) e).getCapacity() - ((CapacityFlowEdge) e).getFlow() > 0).collect(Collectors.toList());
					out = out.stream().filter(e -> !visited.contains(otherEndpoint(data, e, cur))).collect(Collectors.toList());
					if (out.size() == 0) {
						System.out.println("Remove " + cur);
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
