package jobinterviewquestions;

import algs4.Stack;

import java.util.NoSuchElementException;

/**
 * Created by Leon on 7/5/15.
 */
public class StackandQueues {
    /*
    Question 1
    Queue with two stacks.
    Implement a queue with two stacks so that each queue operations takes a constant amortized number of stack operations.
     */

    class TwoStackQueue<Item> {
        private Stack<Item> s1;
        private Stack<Item> s2;

        public TwoStackQueue() {
            s1 = new Stack<Item>();
            s2 = new Stack<Item>();
        }

        public boolean isEmpty() {
            return s1.isEmpty() && s2.isEmpty();
        }

        public int size() {
            return s1.size() + s2.size();
        }

        private void transfer() {
            while (!s1.isEmpty()) {
                Item tmp = s1.pop();
                s2.push(tmp);
            }
        }

        public Item peek() {
            if (isEmpty()) throw new NoSuchElementException("algs4.Queue underflow");
            if (s2.isEmpty()) transfer();
            return s2.peek();
        }

        public void enqueue(Item item) {
            s1.push(item);
        }

        public Item dequeue() {
            if (isEmpty()) throw new NoSuchElementException("algs4.Queue underflow");
            if (s2.isEmpty()) transfer();
            return s2.pop();
        }
    }

    /*
    Question 2
    Stack with max.
    Create a data structure that efficiently supports the stack operations (push and pop) and also a return-the-maximum operation.
    Assume the elements are reals numbers so that you can compare them.
     */

    class MaxStack {
        private int N;
        private Node first;
        private Node max;

        private class Node {
            private double item;
            private Node next;
        }

        public MaxStack() {
            N = 0;
            first = null;
            max = null;
        }

        public double getMax() {
            return max.item;
        }

        public void push(double item) {
            Node oldfirst = first;
            first = new Node();
            first.item = item;
            first.next = oldfirst;
            N++;
            if (item >= getMax()) {
                Node oldmax = max;
                max = new Node();
                max.next = oldmax;
            }
        }

        public double pop() {
            double tmp = first.item;
            first = first.next;
            N--;
            if (tmp == getMax()) {
                max = max.next;
            }
            return tmp;
        }
    }

    /*
    Question 3
    Java generics. Explain why Java prohibits generic array creation.
     */

    /*
    Question 4
    Detect cycle in a linked list.
    A singly-linked data structure is a data structure made up of nodes where each node has a pointer to the next node (or a pointer to null).
    Suppose that you have a pointer to the first node of a singly-linked list data structure:
    1. Determine whether a singly-linked data structure contains a cycle.
    You may use only two pointers into the list (and no other variables).
    The running time of your algorithm should be linear in the number of nodes in the data structure.
    2. If a singly-linked data structure contains a cycle, determine the first node that participates in the cycle.
    you may use only a constant number of pointers into the list (and no other variables).
    The running time of your algorithm should be linear in the number of nodes in the data structure.
    You may not modify the structure of the linked list.
     */

    class CycleLinkedList<Item> {
        private Node first;
        private int size;

        public class Node {
            private Item item;
            private Node next;
        }

        public boolean isCycle() {
            Node p1 = first;
            Node p2 = first;

            while (p1 != null && p2 != null) {
                p1 = p1.next;
                p2 = p2.next.next;
                if (p1 == p2) break;
            }
            if (p1 == null) return false;
            else return true;
        }

        public Node getStart() {
            Node p1 = first;
            Node p2 = first;
            int count = 0;
            while (count < size) {
                p2 = p1.next;
                p1 = p1.next.next;
                count += 2;
            }
            if (size % 2 == 0) return p1;
            else return p2;
        }
    }

    /*
    Question 5
    Clone a linked structure with two pointers per node.
    Suppose that you are given a reference to the first node of a linked structure where each node has two pointers:
    one pointer to the next node in the sequence (as in a standard singly-linked list) and one pointer to an arbitrary node.

    Design a linear-time algorithm to create a copy of the doubly-linked structure.
    You may modify the original linked structure, but you must end up with two copies of the original.
     */

    private class Node {
        private String item;
        private Node next;
        private Node random;
    }

    public Node copyRandomLinkedList(Node head) {
        if (head == null) return null;
        //first pass, add copy nodes in between singly-linked list
        Node p = head;
        while (p != null) {
            Node copy = new Node();
            copy.item = p.item;
            copy.next = p.next;
            p.next = copy;
            p = p.next.next;
        }
        //second pass, add random pointers for copy nodes
        p = head;
        while (p != null) {
            if (p.random != null) p.next.random = p.random.next;
            p = p.next.next;
        }

        //final pass, break the links between copy and original list
        p = head;
        Node newhead = p.next;
        while (p != null) {
            Node copy = p.next;
            p.next = copy.next;
            if (p.next != null) copy.next = p.next.next;
            p = p.next;
        }
        return newhead;
    }

    public static void main(String[] args) {
        Node n = null;
        //Node m = n.next;
    }


}
