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
public final class TreeNode<E> {
    
    /**
     * The element stored in this tree node. May be {@code null}.
     */
    private E element;
    
    /**
     * The parent node of this tree node. We need this in order to make sure
     * that there is no cycles, i.e., a node cannot be both its own predecessor
     * and successor.
     * 
     * This field is kept package-private so that the TODO
     */
    TreeNode<E> parent;
    
    /**
     * The set of child nodes. We will use {@link java.util.LinkedHashSet} for 
     * the child set implementation. While this allows adding/removing in 
     * average constant time, the child nodes will be ordered by their 
     * insertion order, i.e., if 'A' is first added to a specific tree node 'N', 
     * after which 'B' is added to 'N', when iterating over children of 'N', 'A'
     * will be always returned before 'B'.
     * 
     * This field is kept package-private so that the 
     * {@link TreeNodeChildrenView} can access the actual set of child tree
     * nodes.
     */
    Set<TreeNode<E>> children;
    
    /**
     * The view object over this tree node's children. It is kept 
     * package-private so that {@link TreeNodeChildrenView} can access it.
     */
    TreeNodeChildrenView<E> childrenView;
    
    /**
     * The constructor of this tree node.
     * 
     * @param element the element to set to this tree node. May be {@code null}.
     */
    TreeNode(E element) {
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
    public TreeNode<E> addChild(E element) {
        if (children == null) {
            children = new LinkedHashSet<>();
            childrenView = new TreeNodeChildrenView<>(this);
        }
        
        TreeNode<E> child = new TreeNode<>(element);
        child.parent = this;
        children.add(child);
        return child;
    }
    
    /**
     * Returns the children of this tree node. The client programmer can
     * directly operate on this set, adding/removing tree nodes.
     * 
     * @return the children of this tree node. 
     */
    public TreeNodeChildrenView<E> getChildren() {
        if (children == null) {
            children = new LinkedHashSet<>();
            childrenView = new TreeNodeChildrenView<>(this);
        }
        
        return childrenView;
    }
    
    public E getElement() {
        return element;
    }
    
    public void setElement(E element) {
        this.element = element;
    }
    
    @Override
    public String toString() {
        return Objects.toString(element);
    }
}
