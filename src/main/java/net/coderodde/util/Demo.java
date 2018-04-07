package net.coderodde.util;

public final class Demo {

    public static void main(String[] args) {
        Tree<Integer> tree = new Tree<>();
        
        // Roots:
        TreeNode<Integer> root1 = tree.getPseudoRoot().addChild(1);
        TreeNode<Integer> root2 = tree.getPseudoRoot().addChild(2);
        
        // Children of 1st root:
        TreeNode<Integer> root1Child1 = root1.addChild(11);
        TreeNode<Integer> root1Child2 = root1.addChild(12);
        
        // Children of 2nd root:
        TreeNode<Integer> root2Child1 = root2.addChild(21);
        TreeNode<Integer> root2Child2 = root2.addChild(22);
        TreeNode<Integer> root2Child3 = root2.addChild(23);
        
        // Children of 2nd root, second child:
        TreeNode<Integer> root2Child2Child1 = root2Child2.addChild(221);
        TreeNode<Integer> root2Child2Child2 = root2Child2.addChild(222);
        
        // Print the entire tree in a simple format:
        System.out.println("(Indentation communicates node depth.)");
        System.out.println(new SimpleTreeToStringConverter().toString(tree));
    }
}
