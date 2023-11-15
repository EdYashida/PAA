
package bfsnetwork;

import java.util.LinkedList;


public class PathNode {
    private Person person = null; 
    private PathNode previousNode = null; 
    
    public PathNode(Person p, PathNode previous) //contrutor que seta o nó pessoa associado e seu pai no pai
    { 
        person = p; 
        previousNode = previous; 
    } 
  
    public Person getPerson() 
    { 
        return person; 
    } 
  
    public LinkedList<Person> collapse(boolean startsWithRoot) //caminho da raiz ate o no atual
    { 
        LinkedList<Person> path= new LinkedList<Person>(); //armazena pessoas pelo caminho
        PathNode node = this; //instancia node com a atual instancia de PathNode
                              //comeca o caminho a partir do no atual
        while (node != null) 
        { 
            if (startsWithRoot) //se comecar caminho com a raiz
                path.addLast(node.person); //insere a pessoa associada ao no atual, ao fim da lista
                                            //caminho da raiz ate o no atual
            else
                path.addFirst(node.person);  //se nao, insere pessoa associada ao no atual no inicio da lista
                                             //caminho do no atual ate a raiz
            node = node.previousNode;   //atualiza o no para seu pai
        } 
  
        return path; 
    } 
}
