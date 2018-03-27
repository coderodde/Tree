package net.coderodde.util;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

/**
 * This class implements a general tree node.
 * 
 * @author Rodion "rodde" Efremov
 * @version 1.6 (Mar 27, 2018)
 * @param <E> the stored element type.
 */
public final class GeneralTreeNode<E> {
    
    /**
     * The element stored in this tree node. May be {@code null}.
     */
    private final E element;
    
    /**
     * The parent node of this tree node. We need this in order to make sure
     * that there is no cycles, i.e., a node cannot be both its own predecessor
     * and successor.
     * 
     * This field is kept package-private so that the TODO
     */
    GeneralTreeNode<E> parent;
    
    /**
     * The set of child nodes. We will use {@link java.util.LinkedHashSet} for 
     * the child set implementation. While this allows adding/removing in 
     * average constant time, the child nodes will be ordered by their 
     * insertion order, i.e., if 'A' is first added to a specific tree node 'N', 
     * after which 'B' is added to 'N', when iterating over children of 'N', 'A'
     * will be always returned before 'B'.
     * 
     * This field is kept package-private so that the 
     * {@link GeneralTreeNodeChildrenView} can access the actual set of child tree
     * nodes.
     */
    Set<GeneralTreeNode<E>> children;
    
    /**
     * The view object over this tree node's children. It is kept 
     * package-private so that {@link GeneralTreeNodeChildrenView} can access it.
     */
    GeneralTreeNodeChildrenView<E> childrenView;
    
    /**
     * The constructor of this tree node.
     * 
     * @param element the element to set to this tree node. May be {@code null}.
     */
    GeneralTreeNode(E element) {
        this.element = element;
        this.parent = null;
    }
    
    /**
     * Adds a child tree node to this tree node. The new child will have a given
     * element.
     * 
     * @param element the element to set. May be {@code null}.
     * @return the newly created child node so that the client programmer can
     *         operate on it.
     */
    public GeneralTreeNode<E> addChild(E element) {
        if (children == null) {
            children = new LinkedHashSet<>();
            childrenView = new GeneralTreeNodeChildrenView<>(this);
        }
        
        GeneralTreeNode<E> child = new GeneralTreeNode<>(element);
        children.add(new GeneralTreeNode<>(element));
        return child;
    }
    
    /**
     * Returns the children of this tree node. The client programmer can
     * directly operate on this set, adding/removing tree nodes.
     * 
     * @return the children of this tree node. 
     */
    public GeneralTreeNodeChildrenView<E> getChildren() {
        if (children == null) {
            children = new LinkedHashSet<>();
            childrenView = new GeneralTreeNodeChildrenView<>(this);
        }
        
        return childrenView;
    }
    
    @Override
    public String toString() {
        return Objects.toString(element);
    }
}
