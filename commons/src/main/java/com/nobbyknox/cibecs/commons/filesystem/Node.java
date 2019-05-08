package com.nobbyknox.cibecs.commons.filesystem;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Node implements FilesystemEntry, Serializable {

    private String name;
    private String path;
    private List<Node> children;

    public Node(String name, String path) {
        this.name = name;
        this.path = path;
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
        node.getChildren().stream().forEach((child) -> printNode(child, accumulator + "  "));
    }

}
