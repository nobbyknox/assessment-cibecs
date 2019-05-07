package com.nobbyknox.cibecs.commons.communications;

import com.nobbyknox.cibecs.commons.filesystem.Node;

import java.io.Serializable;

public class TreeGraphMessage implements Message, Serializable {
    private String accountCode;
    private Node treeGraph;

    public TreeGraphMessage(String accountCode, Node node) {
        this.accountCode = accountCode;
        this.treeGraph = node;
    }

    /**
     * The fictitious account code for one of our customers.
     *
     * @return unique customer account code
     */
    public String getAccountCode() {
        return this.accountCode;
    }

    /**
     * Returns the tree graph
     *
     * @return filesystem tree graph
     */
    public Node getTreeGraph() {
        return this.treeGraph;
    }

    @Override
    public MessageType getType() {
        return MessageType.TREE_GRAPH;
    }

    @Override
    public String toString() {
        return String.format("[%s] %s", getType(), getAccountCode());
    }
}
