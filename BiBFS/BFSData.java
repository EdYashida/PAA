
package bfsnetwork;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;


public class BFSData {
    public Queue<PathNode> toVisit = new LinkedList<PathNode>(); //lista de nos a serem visitados
    public HashMap<Integer, PathNode> visited = new HashMap<Integer, PathNode>(); 
                                                        //mapeia ID dos nos visitados ao objeto correspondente de PathNode
  
    public BFSData(Person root) //recebe uma pessoa como no inicial
    { 
        PathNode sourcePath = new PathNode(root, null); //cria objeto PathNode com a raiz passada
        toVisit.add(sourcePath); //insere o objeto criado na fila e mapa, com o ID da pessoa
        visited.put(root.getID(), sourcePath); 
    } 
    public boolean isFinished() 
    { 
        return toVisit.isEmpty(); //verifica se visitou todos os nos
    } 
}
