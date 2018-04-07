package net.coderodde.util;

/**
 * This interface defines API for converting generic trees to a textual format.
 * 
 * @author Rodion "rodde" Efremov
 * @version 1.6 (Apr 7, 2018)
 */
public interface TreeToStringConverter<E> {
   
    /**
     * Converts the input tree into a textual format.
     * 
     * @param tree the tree to convert.
     * @return a textual representation of the input tree according to  the
     *         implementing format.
     */
    public String toString(Tree<E> tree);
}
