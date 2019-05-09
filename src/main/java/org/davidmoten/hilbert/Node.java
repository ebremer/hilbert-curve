package org.davidmoten.hilbert;

import com.github.davidmoten.guavamini.Preconditions;

// NotThreadSafe
final class Node implements Comparable<Node> {

    private static long counter = 0;

    private final long id;
    final Range value;
    private Node next;
    private Node previous;
    private long distanceToPrevious;

    Node(Range value) {
        this.value = value;
        this.id = counter++;
    }

    Node next() {
        return next;
    }

    Node previous() {
        return previous;
    }

    Node setNext(Node next) {
        Preconditions.checkNotNull(next);
        Preconditions.checkArgument(next != this);
        this.next = next;
        next.distanceToPrevious = value.low() - next.value.high();
        next.previous = this;
        return this;
    }

    Node insert(Range value) {
        Node n = new Node(value);
        n.next = this;
        previous = n;
        return n;
    }

    @Override
    public int compareTo(Node o) {
        if (this == o) {
            return 0;
        } else {
            if (next == null) {
                return -1;
            }
            long x = distanceToPrevious;
            long y = o.distanceToPrevious;
            if (x < y) {
                return -1;
            } else if (x == y) {
                return Long.compare(id, o.id);
            } else {
                return 1;
            }
        }
    }

    @Override
    public String toString() {
        return "Node [value=" + value + ", next=" + next + ", previous=" + previous + "]";
    }

    public void setDistanceToPrevious(long distance) {
        this.distanceToPrevious = distance;
    }

    public void clearNext() {
        next = null;
    }
    
    public void clearPrevious() {
        previous = null;
    }

}