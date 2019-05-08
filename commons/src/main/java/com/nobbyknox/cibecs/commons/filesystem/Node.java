package com.nobbyknox.cibecs.commons.filesystem;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * A representation of a node in a tree.
 *
 * <p>
 * A node can represent either a directory or a file. Directories can
 * be empty, so the number of children is no reliable indication of
 * whether a node is a directory. An explicit flag is used for this
 * purpose.
 */
public class Node implements FilesystemEntry, Serializable {

    private String name;
    private String path;
    private boolean directory;
    private List<Node> children;

    /**
     * Instantiates a new tree node
     *
     * @param name of the file or directory
     * @param path to the file or directory
     * @param directory whether the node represents a directory or not
     */
    public Node(String name, String path, boolean directory) {
        this.name = name;
        this.path = path;
        this.directory = directory;
        this.children = new ArrayList<>();
    }

    /**
     * Get the name of the file or directory
     *
     * @return name of the file or directory
     */
    @Override
    public String getName() {
        return this.name;
    }

    /**
     * Get the path of the file or directory
     *
     * @return the path to the file or directory
     */
    @Override
    public String getPath() {
        return this.path;
    }

    /**
     * Indicates whether the node represents a directory
     *
     * @return directory flag indicator
     */
    @Override
    public boolean isDirectory() {
        return this.directory;
    }

    /**
     * Get list of children which could be subdirectories or files
     *
     * @return list of {@link Node} items
     */
    @Override
    public List<Node> getChildren() {
        return this.children;
    }

    @Override
    public String toString() {
        return this.name;
    }

    /**
     * Prints the tree graph to standard out
     */
    public void printGraph() {
        printNode(this, "");
    }

    private void printNode(Node node, String accumulator) {
        System.out.println(accumulator + node.getName());

        // Descend further into subdirectories only when current node is a directory
        node.getChildren().stream().filter(Node::isDirectory).forEach((child) -> printNode(child, accumulator + "  "));
    }

}
