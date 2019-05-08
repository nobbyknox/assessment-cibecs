package com.nobbyknox.cibecs.commons.filesystem;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Node implements FilesystemEntry, Serializable {

    private String name;
    private String path;
    private boolean directory;
    private List<Node> children;

    public Node(String name, String path, boolean directory) {
        this.name = name;
        this.path = path;
        this.directory = directory;
        this.children = new ArrayList<>();
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getPath() {
        return this.path;
    }

    @Override
    public boolean isDirectory() {
        return this.directory;
    }

    @Override
    public List<Node> getChildren() {
        return this.children;
    }

    @Override
    public String toString() {
        return this.name;
    }

    public void printGraph() {
        printNode(this, "");
    }

    private void printNode(Node node, String accumulator) {
        System.out.println(accumulator + node.getName());

        // Descend further into subdirectories only when current node is a directory
        node.getChildren().stream().filter(Node::isDirectory).forEach((child) -> printNode(child, accumulator + "  "));
    }

}
