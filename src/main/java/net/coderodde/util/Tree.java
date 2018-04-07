package net.coderodde.util;

/**
 * This class implements a general tree data structure.
 * 
 * @author Rodion "rodde" Efremov
 * @version 1.6 (Mar 27, 2018)
 * @param <E> the tree node element type.
 */
public final class Tree<E> {
   
    /**
     * The root node. This node does not logically belong to this tree as it
     * merely provides a way of having multiple "roots". 
     */
    private final TreeNode<E> pseudoroot = new TreeNode<>(null);
    
    /**
     * Returns the root node. It is <b>not</b> considered to belong to the
     * actual tree. We merely want to have a way of attaching nodes to it.
     * 
     * @return the pseudoroot of this tree.
     */
    public TreeNode<E> getPseudoRoot() {
        return pseudoroot;
    }
    
    @Override
    public String toString() {
        return "";
    }
}
