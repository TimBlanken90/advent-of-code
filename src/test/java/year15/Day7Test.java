package year15;

import java.io.IOException;
import java.util.HashMap;
import java.util.function.IntBinaryOperator;
import java.util.regex.Pattern;

import org.junit.jupiter.api.Test;

import common.Day;
import util.InputResolver;


public class Day7Test implements Day {

    static HashMap<String, IntBinaryOperator> operations = new HashMap<>();
    static HashMap<String, Node> nodes = new HashMap<>();

    static {
        operations.put("AND", (a, b) -> a & b);
        operations.put("OR", (a, b) -> a | b);
        operations.put("LSHIFT", (a, b) -> a << b);
        operations.put("RSHIFT", (a, b) -> a >> b);
    }

    @Override
    @Test
    public void partOne() throws IOException {
        var input = InputResolver.toList(15, 7);

        input.forEach(str -> {
            var node = new Node();
            var split = str.split(" ");

            if (split.length == 3) {
                node.setValue(getValue(split[0]));
                nodes.put(split[2], node);
            } else if (split[0].equals("NOT")) {
                node.setValue(new Negation(getValue(split[1])));
                nodes.put(split[3], node);
            } else {
                node.setValue(new Operator(split[1], getValue(split[0]), getValue(split[2])));
                nodes.put(split[4], node);
            }
        });

        System.out.println(nodes.get("a").getValue());
    }

    @Override
    @Test
    public void partTwo() throws IOException {

    }

    static Element getValue(String s) {
        if (s.matches("\\d+")) {
            return new Literal(Integer.parseInt(s));
        } else {
            return new LazyNode(s);
        }
    }

    interface Element {
        int getValue();
    }

    static class Node implements Element {
        Element value;
        Integer cached = null;

        public void setValue(Element value) {
            this.value = value;
        }

        @Override
        public int getValue() {
            if (cached == null) {
                cached = value.getValue() & 0xffff;
            }
            return cached;
        }
    }

    static class LazyNode implements Element {
        String name;

        public LazyNode(String name) {
            this.name = name;
        }

        @Override
        public int getValue() {
            return nodes.get(name).getValue();
        }
    }

    static class Literal implements Element {
        int value;

        public Literal(int value) {
            this.value = value;
        }

        @Override
        public int getValue() {
            return value;
        }
    }

    static class Negation implements Element {
        Element orig;

        public Negation(Element orig) {
            this.orig = orig;
        }

        @Override
        public int getValue() {
            return ~orig.getValue();
        }
    }

    static class Operator implements Element {
        IntBinaryOperator op;
        String title;
        Element left, right;

        public Operator(String title, Element left, Element right) {
            this.title = title;
            op = operations.get(title);
            this.left = left;
            this.right = right;
        }

        @Override
        public int getValue() {
            return op.applyAsInt(left.getValue(), right.getValue());
        }
    }
}