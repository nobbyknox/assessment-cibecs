package com.nobbyknox.secret001.commons.communications;

import com.nobbyknox.secret001.commons.filesystem.Node;

import java.io.Serializable;

/**
 * The TreeGraphMessage carries a directory tree for a specific account (customer)
 * from the client to the server.
 */
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
    public String toString() {
        return getAccountCode();
    }
}
