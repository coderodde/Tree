package net.coderodde.util;

/**
 * This class implements a general tree data structure.
 * 
 * @author Rodion "rodde" Efremov
 * @version 1.6 (Mar 27, 2018)
 * @param <E> the tree node element type.
 */
public final class GeneralTree<E> {
   
    /**
     * The root node. This node does not logically belong to this tree as it
     * merely provides a way of having multiple "roots". 
     */
    private final GeneralTreeNode<E> root = new GeneralTreeNode<>(null);
    
    /**
     * Returns the root node. It is <b>not</b> considered to belong to the
     * actual tree. We merely want to have a way of attaching nodes to it.
     * 
     * @return the pseudoroot of this tree.
     */
    public GeneralTreeNode<E> getPseudoRoot() {
        return root;
    }
    
    @Override
    public String toString() {
        return "";
    }
    
    public static void main(String[] args) {
        GeneralTree<Integer> tree = new GeneralTree<>();
        
        GeneralTreeNode<Integer> root1 = tree.getPseudoRoot().addChild(1);
        GeneralTreeNode<Integer> root2 = tree.getPseudoRoot().addChild(2);
        
        root1.addChild(11);
        root1.addChild(12);
        root1.addChild(13);
        GeneralTreeNode<Integer> child21 = root2.addChild(21);
        child21.addChild(211);
        
        System.out.println("");
    }
}
