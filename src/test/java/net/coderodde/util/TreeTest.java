package net.coderodde.util;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 * This test class contains the unit tests for {@link Tree}.
 * 
 * @author Rodion "rodde" Efremov
 * @version 1.6 (Mar 27, 2018)
 */
public final class TreeTest {
    
    // Tests mainly adding a tree node, containment query on views and 
    // iterating over views.
    @Test
    public void testAddNode() {
        Tree<Integer> tree = new Tree<>();
        TreeNode<Integer> root1 = tree.getPseudoRoot().addChild(1);
        TreeNode<Integer> root2 = tree.getPseudoRoot().addChild(2);
        TreeNode<Integer> root1Child1 = root1.addChild(11);
        TreeNode<Integer> root1Child2 = root1.addChild(12);
        TreeNode<Integer> root2Child1 = root2.addChild(21);
        TreeNode<Integer> root2Child2 = root2.addChild(22);
        TreeNode<Integer> root2Child3 = root2.addChild(23);
        TreeNode<Integer> root1Child1Child1 = root1Child1.addChild(111);
        
        TreeNode<Integer> pseudoroot = tree.getPseudoRoot();
        TreeNodeChildrenView<Integer> pseudorootChildView = 
                pseudoroot.getChildren();
        
        // Does the pseudoroot contain both the roots?
        assertTrue(pseudorootChildView.contains(root1));
        assertTrue(pseudorootChildView.contains(root2));
        
        // Does the root1 contain root1Child1 and root1Child2?
        assertTrue(root1.getChildren().contains(root1Child1));
        assertTrue(root1.getChildren().contains(root1Child2));
        
        // Does the root2 contain root2Child1, root2Child2 and root2Child3?
        assertTrue(root2.getChildren().contains(root2Child1));
        assertTrue(root2.getChildren().contains(root2Child2));
        assertTrue(root2.getChildren().contains(root2Child3));
        
        // Does the root1Child1 contain root1Child1Child1?
        assertTrue(root1Child1.getChildren().contains(root1Child1Child1));
        
        // Check the order in which the roots are returned:
        Iterator<TreeNode<Integer>> iterator = pseudorootChildView.iterator();
        assertEquals(root1, iterator.next());
        assertEquals(root2, iterator.next());
        assertFalse(iterator.hasNext()); // Iteration done.
        
        iterator = root1.getChildren().iterator();
        
        assertEquals(root1Child1, iterator.next());
        assertEquals(root1Child2, iterator.next());
        assertFalse(iterator.hasNext());
        
        iterator = root2.getChildren().iterator();
        
        assertEquals(root2Child1, iterator.next());
        assertEquals(root2Child2, iterator.next());
        assertEquals(root2Child3, iterator.next());
        assertFalse(iterator.hasNext());
        
        iterator = root1Child1.getChildren().iterator();
        
        assertEquals(root1Child1Child1, iterator.next());
        assertFalse(iterator.hasNext());
    }
    
    @Test
    public void testChildrenViewSize() {
        Tree<Integer> tree = new Tree<>();
        TreeNode<Integer> root1 = tree.getPseudoRoot().addChild(1);
        TreeNode<Integer> root2 = tree.getPseudoRoot().addChild(2);
        
        root1.addChild(11);
        root1.addChild(12);
        
        assertEquals(2, root1.getChildren().size());
        assertEquals(0, root2.getChildren().size());
    }
    
    @Test
    public void testChildrenViewIsEmpty() {
        Tree<Integer> tree = new Tree<>();
        TreeNode<Integer> root1 = tree.getPseudoRoot().addChild(1);
        TreeNode<Integer> root2 = tree.getPseudoRoot().addChild(2);
        
        root1.addChild(11);
        root1.addChild(12);
        
        assertFalse(root1.getChildren().isEmpty());
        assertTrue(root2.getChildren().isEmpty());
    }
    
    @Test
    public void testChildrenViewRemove() {
        Tree<Integer> tree = new Tree<>();
        TreeNode<Integer> root = tree.getPseudoRoot().addChild(1);
        TreeNode<Integer> child = root.addChild(11);
        
        assertNotNull(root.parent);
        assertNotNull(child.parent);
        
        root.getChildren().remove(child);
        
        assertNull(child.parent);
        assertNotNull(root.parent);
        
        tree.getPseudoRoot().getChildren().remove(root);
        
        assertNull(root.parent);
    }
    
    @Test
    public void testChildrenViewRemove2() {
        Tree<Integer> tree = new Tree<>();
        TreeNode<Integer> root1 = tree.getPseudoRoot().addChild(1);
        TreeNode<Integer> root2 = tree.getPseudoRoot().addChild(2);
        
        assertTrue(tree.getPseudoRoot().getChildren().contains(root1));
        assertTrue(tree.getPseudoRoot().getChildren().contains(root2));
        
        tree.getPseudoRoot().getChildren().remove(root1);
        
        assertFalse(tree.getPseudoRoot().getChildren().contains(root1));
        assertTrue(tree.getPseudoRoot().getChildren().contains(root2));
        
        tree.getPseudoRoot().getChildren().add(root1);
        tree.getPseudoRoot().getChildren().remove(root2);
        
        assertTrue(tree.getPseudoRoot().getChildren().contains(root1));
        assertFalse(tree.getPseudoRoot().getChildren().contains(root2));
        
        tree.getPseudoRoot().getChildren().remove(root1);
        
        assertFalse(tree.getPseudoRoot().getChildren().contains(root1));
        assertFalse(tree.getPseudoRoot().getChildren().contains(root2));
    }
    
    @Test(expected = IllegalStateException.class)
    public void testDetectsSelfLoops() {
        Tree<Integer> tree = new Tree<>();
        TreeNode<Integer> root = tree.getPseudoRoot().addChild(1);
        TreeNode<Integer> child1 = root.addChild(11);
        TreeNode<Integer> child2 = child1.addChild(111);
        
        child2.getChildren().add(child2);
    }
    
    @Test(expected = IllegalStateException.class)
    public void testDetectsLoops() {
        Tree<Integer> tree = new Tree<>();
        TreeNode<Integer> root = tree.getPseudoRoot().addChild(1);
        TreeNode<Integer> child1 = root.addChild(11);
        TreeNode<Integer> child2 = child1.addChild(111);
        
        child2.getChildren().add(child1);
    }
    
    @Test
    public void testChildrenViewContainsAll() {
        Tree<Integer> tree = new Tree<>();
        TreeNode<Integer> root1 = tree.getPseudoRoot().addChild(1);
        TreeNode<Integer> root2 = tree.getPseudoRoot().addChild(2);
        TreeNode<Integer> root3 = tree.getPseudoRoot().addChild(3);
        
        Collection<TreeNode<Integer>> collection = 
                Arrays.asList(root3, root2, root1);
        
        assertTrue(tree.getPseudoRoot().getChildren().containsAll(collection));
        
        tree.getPseudoRoot().getChildren().remove(root3);
        
        assertFalse(tree.getPseudoRoot().getChildren().containsAll(collection));
    }
    
    @Test
    public void testAddAll() {
        Tree<Integer> tree = new Tree<>();
        Collection<TreeNode<Integer>> collection =
                Arrays.asList(new TreeNode<>(1),
                              new TreeNode<>(2),
                              new TreeNode<>(3));
        assertTrue(tree.getPseudoRoot().getChildren().addAll(collection));
        assertFalse(tree.getPseudoRoot().getChildren().addAll(collection));
        
        collection = Collections.<TreeNode<Integer>>emptyList();
        
        assertFalse(tree.getPseudoRoot().getChildren().addAll(collection));
        
        collection = Arrays.asList(new TreeNode<>(1));
        
        // We are reinserting the element 1, but they are considered different
        // nodes:
        assertTrue(tree.getPseudoRoot().getChildren().addAll(collection));
        assertFalse(tree.getPseudoRoot().getChildren().addAll(collection));
    }
    
    @Test
    public void testRetainAll() {
        Tree<Integer> tree = new Tree<>();
        TreeNode<Integer> root = tree.getPseudoRoot().addChild(1);
        TreeNode<Integer> child1 = root.addChild(11);
        TreeNode<Integer> child2 = root.addChild(12);
        TreeNode<Integer> child3 = root.addChild(13);
        TreeNode<Integer> child4 = root.addChild(14);
        
        Collection<TreeNode<Integer>> collection = Arrays.asList(child1,
                                                                 child4);
        
        root.getChildren().retainAll(collection);
        
        assertTrue(root.getChildren().contains(child1));
        assertTrue(root.getChildren().contains(child4));
        
        assertFalse(root.getChildren().contains(child2));
        assertFalse(root.getChildren().contains(child3));
        
        root.getChildren().retainAll(Arrays.asList(child2));
        
        assertFalse(root.getChildren().contains(child2));
    }
    
    @Test
    public void testRemoveAll() {
        Tree<Integer> tree = new Tree<>();
        TreeNode<Integer> root = tree.getPseudoRoot().addChild(1);
        TreeNode<Integer> child1 = root.addChild(11);
        TreeNode<Integer> child2 = root.addChild(12);
        TreeNode<Integer> child3 = root.addChild(13);
        TreeNode<Integer> child4 = root.addChild(14);
        
        Collection<TreeNode<Integer>> collection = Arrays.asList(child1, 
                                                                 child4);
        
        root.getChildren().removeAll(collection);
        
        assertFalse(root.getChildren().contains(child1));
        assertFalse(root.getChildren().contains(child4));
        
        assertTrue(root.getChildren().contains(child2));
        assertTrue(root.getChildren().contains(child3));
    }
    
    @Test
    public void testClear() {
        Tree<Integer> tree = new Tree<>();
        TreeNode<Integer> root = tree.getPseudoRoot().addChild(1);
        TreeNode<Integer> child1 = root.addChild(11);
        TreeNode<Integer> child2 = root.addChild(12);
        TreeNode<Integer> child3 = root.addChild(13);
        
        assertTrue(root.getChildren().containsAll(Arrays.asList(child1,
                                                                child2,
                                                                child3)));
        
        root.getChildren().clear();
        
        assertFalse(root.getChildren().contains(child1));
        assertFalse(root.getChildren().contains(child2));
        assertFalse(root.getChildren().contains(child3));
        
        assertEquals(0, root.getChildren().size());
        assertTrue(root.getChildren().isEmpty());
    }
}
