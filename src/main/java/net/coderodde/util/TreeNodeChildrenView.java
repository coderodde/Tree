package net.coderodde.util;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;

/**
 * This class provides a view over a tree nodes children. The client programmer
 * can manipulate it to his/her own liking.
 * 
 * @author Rodion "rodde" Efremov
 * @version 1.6 (Mar 27, 2018)
 * @param <E> the tree node element type.
 */
public final class TreeNodeChildrenView<E> implements Set<TreeNode<E>> {

    /**
     * The tree node that owns this view.
     */
    private final TreeNode<E> ownerTreeNode;
    
    /**
     * Constructs this view for the input tree node.
     * 
     * @param ownerTreeNode the tree node this children view belongs to.
     */
    TreeNodeChildrenView(TreeNode<E> ownerTreeNode) {
        this.ownerTreeNode = ownerTreeNode;
    }
    
    /**
     * Returns the number of children in this view.
     * 
     * @return the number of children.
     */
    @Override
    public int size() {
        return ownerTreeNode.children.size();
    }

    /**
     * Returns {@code true} only if this view has no child tree nodes.
     * 
     * @return {@code true} if this view has no child tree nodes. 
     */
    @Override
    public boolean isEmpty() {
        return ownerTreeNode.children.isEmpty();
    }

    /**
     * Returns {@code true} only if this tree node children view contains a 
     * given tree node.
     * 
     * @param o the query tree node.
     * @return {@code true} only if {@code o} is in this view.
     */
    @Override
    public boolean contains(Object o) {
        if (o == null) {
            return false;
        } else if (!(o instanceof TreeNode)) {
            return false;
        } else {
            return ownerTreeNode.children.contains(o);
        }
    }

    /**
     * Returns an iterator over this view's children.
     * 
     * @return an iterator over this view's children.
     */
    @Override
    public Iterator<TreeNode<E>> iterator() {
        return ownerTreeNode.children.iterator();
    }

    @Override
    public boolean add(TreeNode<E> treeNode) {
        Objects.requireNonNull(treeNode, "The input tree node is null.");
        checkInputTreeNodeIsNotPredecessorOfThisTreeNode(treeNode);
        
        // Return {@code false} whenever the input tree node is already in this
        // tree.
        if (ownerTreeNode.children.contains(treeNode)) {
            return false;
        }
        
        // If the input tree node belongs to a parent, disconnect it from it:
        if (treeNode.parent != null) {
            treeNode.parent.children.remove(treeNode);
        }
        
        // Connect the input tree node as the child of this view.
        ownerTreeNode.children.add(treeNode);
        treeNode.parent = ownerTreeNode;
        return true;
    }

    @Override
    public boolean remove(Object o) {
        if (o == null) {
            return false;
        } else if (!(o instanceof TreeNode)) {
            return false;
        }
        
        TreeNode<E> treeNode = (TreeNode<E>) o;
        treeNode.parent = null;
        return ownerTreeNode.children.remove(treeNode);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object o : c) {
            if (!contains(o)) {
                return false;
            }
        }
        
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends TreeNode<E>> c) {
        boolean modified = false;
        
        for (TreeNode<E> treeNode : c) {
            if (add(treeNode)) {
                modified = true;
            }
        }
        
        return modified;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        int numberOfChildrenBefore = size();
        
        Set<?> collectionAsSet = 
                (c instanceof HashSet) ? (Set<?>) c : new HashSet(c);
        
        Iterator<TreeNode<E>> iterator =
                ownerTreeNode.children.iterator();
        
        while (iterator.hasNext()) {
            TreeNode<E> currentTreeNode = iterator.next();
            
            if (!collectionAsSet.contains(currentTreeNode)) {
                iterator.remove();
            }
        }
        
        return size() < numberOfChildrenBefore;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return ownerTreeNode.children.removeAll(c);
    }

    @Override
    public void clear() {
        ownerTreeNode.children.clear();
    }

    @Override
    public Object[] toArray() {
        throw new UnsupportedOperationException();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        throw new UnsupportedOperationException();
    }
    
    /**
     * Checks that the input tree node is not a predecessor of itself.
     * 
     * @param treeNode the tree node to check.
     */
    private void checkInputTreeNodeIsNotPredecessorOfThisTreeNode(
            TreeNode<E> treeNode) {
        TreeNode<E> currentTreeNode = ownerTreeNode;
        
        while (currentTreeNode != null) {
            if (currentTreeNode == treeNode) {
                throw new IllegalStateException(
                        "Trying to create a cycle in this tree.");
            }
            
            currentTreeNode = currentTreeNode.parent;
        }
    }
}
