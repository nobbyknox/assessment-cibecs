package com.nobbyknox.cibecs.commons.communications;

import com.nobbyknox.cibecs.commons.filesystem.Node;

import java.io.Serializable;

public class TreeGraphMessage implements Message, Serializable {
    private String filename;
    private String path;
    private Node treeGraph;

    public TreeGraphMessage(String name, String path, Node node) {
        this.filename = name;
        this.path = path;
        this.treeGraph = node;
    }

    public String getFilename() {
        return this.filename;
    }

    public String getPath() {
        return this.path;
    }

    public Node getTreeGraph() {
        return this.treeGraph;
    }

    @Override
    public MessageType getType() {
        return MessageType.TREE_GRAPH;
    }

    @Override
    public String toString() {
        return String.format("[%s] %s", getType(), getFilename());
    }
}
