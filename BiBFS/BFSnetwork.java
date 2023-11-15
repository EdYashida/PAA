
package bfsnetwork;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;


public class BFSnetwork {

   
    public static void main(String[] args) {
        
        //cria nos
        
        Person person1 = new Person(1);
        Person person2 = new Person(2);
        Person person3 = new Person(3);
        Person person4 = new Person(4);
        Person person5 = new Person(5);
        
        //estabelece arestas
        person1.addFriend(2);
        person1.addFriend(3);
        person2.addFriend(1);
        person2.addFriend(4);
        person3.addFriend(1);
        person4.addFriend(2);
        person4.addFriend(5);
        person5.addFriend(4);

        // mapeamento dos nos
        HashMap<Integer, Person> people = new HashMap<>();
        people.put(1, person1);
        people.put(2, person2);
        people.put(3, person3);
        people.put(4, person4);
        people.put(5, person5);

        // origem e destino
        int source = 1;
        int destination = 4;

        // Chama findPathBiBFS para achar o caminho pelo BFS bidirecional
        BFSnetwork bfsNetwork = new BFSnetwork();
        LinkedList<Person> path = bfsNetwork.findPathBiBFS(people, source, destination);

        // resultados
        if (path != null) {
            System.out.println("Path from " + source + " to " + destination + ": " + path);
        } else {
            System.out.println("No path found from " + source + " to " + destination);
        }
  
        
    }
    
    LinkedList<Person> findPathBiBFS(HashMap<Integer, Person> people, int source, int destination) //retorna lista das pessoas(caminho)
{ 
    //cria objeto BFSData para destino e origem
    BFSData sourceData = new BFSData(people.get(source)); 
    BFSData destData = new BFSData(people.get(destination)); 
  
    while (!sourceData.isFinished() && !destData.isFinished()) //continua ate BFS em origem e destino ser realizado
    { 
  
        /* Search out from source. */
        Person collision = searchLevel(people, sourceData, destData); //chama search level pra buscar colisao a 1 nivel da origem
        if (collision != null) //se achar
            return mergePaths(sourceData, destData, collision.getID()); //retorna o caminho "mixado" pelo metodo mergePaths
  
        /* Search out from destination. */
        collision = searchLevel(people, destData, sourceData); //o mesmo que antes, mas faz para o destino
        if (collision != null) 
            return mergePaths(sourceData, destData, collision.getID()); 
    } 
  
    return null; //retorna null se encerrar loop sem achar caminho
}  
  
  
/* Search one level and return collision, if any.*/
Person searchLevel(HashMap<Integer, Person> people, BFSData primary, BFSData secondary) 
{ //retorna a pessoa presente na colisao das buscas , recebendo o mapeamento dos nos do grafo
  
    /* We only want to search one level at a time. Count 
       how many nodes are currently 
       in the primary's level and only do that many nodes. 
       We continue to add nodes to the end. */
  
    int count = primary.toVisit.size(); 
    
    for (int i= 0; i < count; i++) //itera sobre os nos do nivel corrente
    { 
        /* Pull out first node. */
        PathNode pathNode = primary.toVisit.poll(); //tira o primeiro no da fila de "a ser visitado"
        int personid = pathNode.getPerson().getID(); //recura ID da pessoa relacionada ao no atual
  
        /* Check if it's already been visited. */
        if (secondary.visited.containsKey(personid)) //verifica se o no atual ja foi visitado no BFSData secundario                                     
            return pathNode.getPerson(); //se sim, colisao encontrada e retorna a pessoa na colisao
  
        /* Add friends to queue. */
        Person person = pathNode. getPerson(); //recupera pessoa associada ao no atual
        ArrayList<Integer> friends = person.getFriends(); //lista de amigos dessa pessoa
        for (int friendid : friends) //itera para cada amigo
        { 
            if (!primary.visited.containsKey(friendid)) //amigo visitado pelo BFSData primario?
            { 
                Person friend= people.get(friendid); //recupera amigo do hashmap
                PathNode next = new PathNode(friend, pathNode); //criar novo PathNode para o amigo, com nó atual como o pai
                primary.visited.put(friendid, next); //marca amigo como visitado no primario
                primary.toVisit.add(next); //poe amigo no fim de "a visitar"
            } 
        } 
    } 
    return null; //retorna null se completar loop sem achar colisao
} 
  
  
/* Merge paths where searches met at the connection. */
LinkedList<Person> mergePaths(BFSData bfsl, BFSData bfs2, int connection) //a conexao representa o perfil que fica no encontro entre as 2 buscas
{ 
    // endl -> source, end2 -> dest 
    PathNode endl = bfsl.visited.get(connection); //recupera o caminho relacionado ao ponto de conexao de cada BFSData
    PathNode end2 = bfs2.visited.get(connection); 
  
    LinkedList<Person> pathOne = endl.collapse(false); //vai do ponto de conexao ate a raiz
    LinkedList<Person> pathTwo = end2.collapse(true); //vai do ponto de conexao ate o destino
  
    pathTwo.removeFirst(); // remove a pessoa que serviu de conexao na segunda lista, para evitar duplica-lo
    pathOne.addAll(pathTwo); // adiciona as pessoas da segunda parte ao final da lista da primeira
  
    return pathOne; //retorna a lista de pessoa no caminho
} 
}
