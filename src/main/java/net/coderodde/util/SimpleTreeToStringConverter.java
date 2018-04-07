package net.coderodde.util;

import java.util.Objects;

/**
 * This class implements a simple converter from a 
 * {@link net.coderodde.util.Tree} to a string.
 * 
 * @author Rodion "rodde" Efremov
 * @version 1.6 (Apr 7, 2018)
 */
public final class SimpleTreeToStringConverter<E>
implements TreeToStringConverter<E> {
    
    /**
     * {@inheritDoc }
     */
    @Override
    public String toString(Tree<E> tree) {
        Objects.requireNonNull(tree, "The input tree is null.");
        StringBuilder stringBuilder = new StringBuilder();        
        
        for (TreeNode<E> root : tree.getPseudoRoot().getChildren()) {
            toString(stringBuilder, root, 0);
        }
        
        return stringBuilder.toString();
    }
    
    // Implements the actual conversion procedure.
    private void toString(StringBuilder stringBuilder, 
                          TreeNode<E> node,
                          int nodeDepth) {
        for (int i = 0; i < nodeDepth; i++) {
            stringBuilder.append(' ');
        }
        
        stringBuilder.append(Objects.toString(node.getElement()))
                     .append('\n');
        
        for (TreeNode<E> child : node.getChildren()) {
            toString(stringBuilder, child, nodeDepth + 1);
        }
    }
}
