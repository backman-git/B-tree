import java.util.ArrayList;
import java.util.Map;
import java.util.AbstractMap;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.FileWriter;
public class BTree{
    static final int cLimit=6;
    BNode root;
    int nodeLimitSize;
    int nSize;

    private class BNode<K,V> implements Map.Entry<K,V>{
        private final K key;
        private  V value;
        ArrayList<BNode<K,V>> children;
        boolean isLeaf;
        String name;
        BNode smallerNode;

        @Override
        public K getKey(){
            return this.key;
        }
        @Override
        public V getValue(){
            return this.value;
        }
        @Override
        public V setValue(V value){
            V old = this.value;
            this.value = value;
            return old;
        }

        public BNode(boolean isLeaf,String name,K key, V value){
            this.key=key;
            this.value=value;
            this.name=name;
            this.isLeaf=isLeaf;
            this.children = new ArrayList<BNode<K,V>>();
            this.smallerNode=null;
        }


    }

    public void genGraph(){


        try{
            FileWriter fileWriter = new FileWriter("btree.dot");
            PrintWriter printWriter = new PrintWriter(fileWriter);
            printWriter.println("digraph g{");
            printWriter.println("node [shape = record,height=.1];");

            printNode(this.root,printWriter);




            printWriter.println("}");
            printWriter.close();
        }catch(IOException e){
            
        }
    }


    public void printNode(BNode node,PrintWriter writer){
        //name
        writer.printf("%s[label =\"",node.name);
        for(int idx=node.children.size()-1;0<=idx;idx--){
            BNode<Integer,String>cNode=(BNode<Integer,String>) node.children.get(idx);
            writer.printf("<f%d> |%d| ",idx,cNode.getKey());
        }
        writer.println("\"]");


    }



    public BTree(){
        this.root=new BNode<Integer,String>(true,"root",0,null);
        this.nSize=0;
    }

    public boolean isEmpty(){
        return size()==0 ?true:false;
    }

    public int size(){
        return this.nSize;

    }

    public void set(int key,String value){
        insert(this.root,key,value);
    }

    public BNode insert(BNode node,int key,String value){
        int insertPoint=0;
        if(node.isLeaf==true){
            for(int idx=0;idx<node.children.size();idx++){
                BNode<Integer,String> cNode =(BNode<Integer,String>) node.children.get(idx);
                if( key <cNode.getKey()){
                    insertPoint=idx;
                    break;
                }
            }
        }else{

        }


        node.children.add(insertPoint,new BNode<Integer,String>(true,"node"+nSize,key,value));
        this.nSize++;
    
        return null;
    }

    /*
    public String get(int key){
        return search();
    }

    public String search(BNode node,int key){

        //葉子
        if(node.isLeaf==true){
        }
        return null;
    }

*/

    public static void main(String[] args){
        System.out.println("hello B-tree");
        BTree tree = new BTree();
        tree.set(1,"hello");
        tree.set(2,"oh");
        tree.genGraph();
    }


}