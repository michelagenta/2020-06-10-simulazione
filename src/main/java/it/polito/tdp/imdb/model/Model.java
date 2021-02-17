package it.polito.tdp.imdb.model;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.alg.connectivity.ConnectivityInspector;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.imdb.db.ImdbDAO;

public class Model {
	ImdbDAO dao = new ImdbDAO();
	public Map<Integer, Actor> idMap; 
	private Graph<Actor, DefaultEdge> grafo; 
	
	
	public List<String> getGenres(){
		return this.dao.getGenres();
	}
	
	public void creaGrafo(String genre) {
		this.grafo= new SimpleWeightedGraph<Actor, DefaultEdge>(DefaultEdge.class);
		idMap= new HashMap<Integer, Actor>();
		dao.getVertici(idMap,genre);
		Graphs.addAllVertices(grafo, idMap.values());
		
		//aggiungo archi
		for(Adiacenza a: dao.getAdiacenze(idMap, genre)) {
			if(this.grafo.containsVertex(a.getA1()) && this.grafo.containsVertex(a.getA2())) {
				Graphs.addEdgeWithVertices(grafo, a.getA1(), a.getA2(), a.getPeso()); 
			}
		}
		
	}
	
	public List<Actor> getAttoriVicini(Actor partenza){
		List<Actor> connessi = new LinkedList<Actor>();
		ConnectivityInspector <Actor, DefaultEdge> ci= new ConnectivityInspector<Actor, DefaultEdge>(grafo); 
		ci.connectedSetOf(partenza); 
		return connessi;
	}
	
	public int numeroVerici() {
		return this.grafo.vertexSet().size(); 
	}
	
	public int numeroArchi() {
		return this.grafo.edgeSet().size(); 
	}

}
