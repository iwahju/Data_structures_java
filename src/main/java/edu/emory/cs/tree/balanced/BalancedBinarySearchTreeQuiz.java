package edu.emory.cs.tree.balanced;

import edu.emory.cs.tree.BinaryNode;

public class BalancedBinarySearchTreeQuiz<T extends Comparable<T>> extends AbstractBalancedBinarySearchTree<T, BinaryNode<T>> {
    @Override
    public BinaryNode<T> createNode(T key) {
        return new BinaryNode<>(key);
    }

    @Override
    protected void balance(BinaryNode<T> node) {
        if (node == null) return;
        if (node.hasLeftChild() || node.hasRightChild()) return;

        BinaryNode<T> parent = node.getParent();
        BinaryNode<T> grandParent = node.getGrandParent();
        BinaryNode<T> uncle = node.getUncle();

        if (parent == null || grandParent == null || uncle == null) return;

        if (!(grandParent.isRightChild(parent) && !uncle.hasBothChildren() &&
                (uncle.hasLeftChild() || uncle.hasRightChild()))) {
            return;
        }

        BinaryNode<T> uncleLeft = uncle.getLeftChild();
        BinaryNode<T> uncleRight = uncle.getRightChild();


        if (parent.isLeftChild(node)) {
            if (uncleRight == null) {
                rotateRight(parent);
                rotateLeft(grandParent);
                rotateRight(grandParent);
            } else if (uncleLeft == null) {
                rotateLeft(uncle);
                balance(node);
            }
        } else {
            if (uncleRight == null) {
                rotateLeft(parent);
                balance(parent);
            } else if (uncleLeft == null) {
                rotateLeft(parent);
                balance(parent);
            }
        }
    }

}