package gitlet;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

//图是存储shal的,但是他是操作commit的，不属于commit，
public class Vertex<String> implements Serializable {
    private String label;
    private List<Edge> edgelist;
    private Boolean visited;
    public  Vertex(){
        label=null;
        edgelist=new LinkedList<>();
        visited=false;
    }
    public void add(String label,String alabel){
        this.label=label;
        if(alabel.equals("")){
           return;
        }
        else{
            Edge a=new Edge(alabel);
            edgelist.add(a);
            visited=false;
        }

    }
    public class Edge implements Serializable {
        private String arrow;

        public Edge(String s) {
            arrow = s;
        }

        public Edge() {
            arrow = null;
        }
    }}