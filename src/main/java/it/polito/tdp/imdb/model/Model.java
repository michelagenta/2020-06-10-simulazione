package it.polito.tdp.imdb.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.imdb.db.ImdbDAO;

public class Model {
	ImdbDAO dao = new ImdbDAO();
	Map<Integer, Actor> idMap; 
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
		
		
	}
	
	public int numeroVerici() {
		return this.grafo.vertexSet().size(); 
	}

}
