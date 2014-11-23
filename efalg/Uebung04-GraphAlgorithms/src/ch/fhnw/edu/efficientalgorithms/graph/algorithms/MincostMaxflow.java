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
import ch.fhnw.edu.efficientalgorithms.graph.edges.CostCapacityFlowEdge;


// TODO: find negative circles (Bellmann Ford)

/***
 * 
 * @author chregi
 *
 * @param <V>
 * @param <E>
 */
public class MincostMaxflow<V extends Vertex, E extends Edge> extends AbstractMaxFlowAlgorithm<V, E> {

	public MincostMaxflow() {
		super("Mincost Maxflow");
	}
	
	/**
	 * This algorithm works only with edges with flow, cost and capacity.
	 */
	@Override
	public boolean worksWith(final Graph<V, E> graph) {
		return graph.isDirected() && CostCapacityFlowEdge.class.isAssignableFrom(graph.edgeClass());
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
	 * creates back edges and adds them to the graph (BLUE)
	 */
	@Override
	protected synchronized void calculateMaxFlow(GraphAlgorithmData<V, E> data, final V source, final V sink) {
		
		int flowMax = Integer.MAX_VALUE/2;
		
		// get augmented path with DFS
		Stack<V> toVisit = new Stack<V>();
		List<V> visited = new LinkedList<V>();
		Map<V, E> eToVisit = new HashMap<V, E>();
		Map<V, E> eVisited = new HashMap<V, E>();
		List<E> backEdges = new LinkedList<E>();
		
		toVisit.add(source);

		// depth-first search until reaching sink
		while (!toVisit.empty()) {
			V cur = toVisit.pop();
			if (null != eToVisit.get(cur)) {
				CostCapacityFlowEdge e = (CostCapacityFlowEdge) eToVisit.remove(cur);
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
							((CostCapacityFlowEdge) e).setFlow(((CostCapacityFlowEdge)e).getFlow()+flowMax);
						} catch (IllegalArgumentException ie) {
							System.err.println(((CostCapacityFlowEdge)e).getCapacity() + " "+ ((CostCapacityFlowEdge)e).getFlow() +" " + flowMax);
						}
						System.out.println(from.toString() + " -> " +to.toString() + " " + e.toString());
						

						// create / update residual edges
						CostCapacityFlowEdge resEdge = (CostCapacityFlowEdge) data.getGraph().getEdge(to, from);
						if (null == resEdge){
							resEdge = new CostCapacityFlowEdge(((CostCapacityFlowEdge)e).getCapacity(), -((CostCapacityFlowEdge)e).getCost());
							backEdges.add((E) resEdge);
							if (!data.getGraph().addEdge(to, from, (E) resEdge)) {
								System.out.println("Could not add residual Edge " + resEdge.toString());
							}
						}
						
						int resFlow = resEdge.getCapacity() - ((CostCapacityFlowEdge) e).getFlow();
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
					out = out.stream().filter(e -> ((CostCapacityFlowEdge) e).getCapacity() - ((CostCapacityFlowEdge) e).getFlow() > 0).collect(Collectors.toList());
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
		highlightEdges(data, backEdges, Color.BLUE);
	}


}
