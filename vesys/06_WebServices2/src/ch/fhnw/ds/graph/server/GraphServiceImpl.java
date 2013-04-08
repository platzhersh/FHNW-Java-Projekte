package ch.fhnw.ds.graph.server;

import java.util.HashSet;
import java.util.Set;

import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;
import javax.jws.soap.SOAPBinding.Use;

@WebService
@SOAPBinding(style=Style.DOCUMENT, use=Use.LITERAL)
public class GraphServiceImpl implements GraphService {
	
	@Override
	public boolean isTree(Node root) {
		Set<Node> visited = new HashSet<Node>();
		return visitTree(root, visited);
	}
	
	boolean visitTree(Node root, Set<Node> visited){
		if(visited.contains(root)) return false;
		visited.add(root);
		for(Node child : root.getChildren()){
			if(!visitTree(child, visited)) return false;
		}
		return true;
	}

	@Override
	public boolean isDAG(Node root) {
		Set<Node> visited = new HashSet<Node>();
		return visitDAG(root, visited);
	}

	boolean visitDAG(Node root, Set<Node> visited) {
		if (visited.contains(root))
			return false;
		visited.add(root);
		try {
			for (Node child : root.getChildren()) {
				if (!visitDAG(child, visited))
					return false;
			}
			return true;
		} finally {
			visited.remove(root);
		}
	}

}
