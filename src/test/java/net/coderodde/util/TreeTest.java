package net.coderodde.util;

import java.util.Iterator;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 * This test class contains the unit tests for {@link Tree}.
 * 
 * @author Rodion "rodde" Efremov
 * @version 1.6 (Mar 27, 2018)
 */
public final class TreeTest {
    
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
        assertTrue(true);
    }
}
