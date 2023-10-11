package revenko.ru.classes;

import java.util.ArrayList;
import java.util.List;

public class QuadTree {
    private final int MAX_CAPACITY = 4;
    private final int level;
    private final List<Node> nodes;
    private QuadTree northWest = null;
    private QuadTree northEast = null;
    private QuadTree southWest = null;
    private QuadTree southEast = null;
    private final Boundry boundry;

    public QuadTree(int level, Boundry boundry) {
        this.level = level;
        nodes = new ArrayList<>();
        this.boundry = boundry;
    }

    private void split() {
        int xOffset = this.boundry.getXMin()
                + (this.boundry.getXMax() - this.boundry.getXMin()) / 2;
        int yOffset = this.boundry.getYin()
                + (this.boundry.getYMax() - this.boundry.getYin()) / 2;

        northWest = new QuadTree(this.level + 1, new Boundry(
                this.boundry.getXMin(), this.boundry.getYin(), xOffset,
                yOffset));
        northEast = new QuadTree(this.level + 1, new Boundry(xOffset, this.boundry.getYin(), this.boundry.getXMax(), yOffset));
        southWest = new QuadTree(this.level + 1, new Boundry(this.boundry.getXMin(), yOffset, xOffset, this.boundry.getYMax()));
        southEast = new QuadTree(this.level + 1, new Boundry(xOffset, yOffset,
                this.boundry.getXMax(), this.boundry.getYMax()));

    }

    public void insert(int x, int y, int value) {
        if (!this.boundry.inRange(x, y)) {
            return;
        }

        Node node = new Node(x, y, value);
        if (nodes.size() < MAX_CAPACITY) {
            nodes.add(node);
            return;
        }

        if (northWest == null) {
            split();
        }

        if (this.northWest.boundry.inRange(x, y))
            this.northWest.insert(x, y, value);
        else if (this.northEast.boundry.inRange(x, y))
            this.northEast.insert(x, y, value);
        else if (this.southWest.boundry.inRange(x, y))
            this.southWest.insert(x, y, value);
        else if (this.southEast.boundry.inRange(x, y))
            this.southEast.insert(x, y, value);
        else
            System.out.printf("ERROR : Unhandled partition %d %d", x, y);
    }

    public boolean delete(int x, int y) {
        if (!this.boundry.inRange(x, y)) {
            return false;
        }

        for (Node node : nodes) {
            if (node.getX() == x && node.getY() == y) {
                nodes.remove(node);
                return true;
            }
        }

        if (northWest != null && northWest.delete(x, y)) {
            return true;
        }
        if (northEast != null && northEast.delete(x, y)) {
            return true;
        }
        if (southWest != null && southWest.delete(x, y)) {
            return true;
        }
        if (southEast != null && southEast.delete(x, y)) {
            return true;
        }

        return false;
    }

    public boolean contains(int x, int y) {
        if (!this.boundry.inRange(x, y)) {
            return false;
        }

        for (Node node : nodes) {
            if (node.getX() == x && node.getY() == y) {
                return true;
            }
        }

        if (northWest != null && northWest.contains(x, y)) {
            return true;
        }

        if (northEast != null && northEast.contains(x, y)) {
            return true;
        }

        if (southWest != null && southWest.contains(x, y)) {
            return true;
        }

        if (southEast != null && southEast.contains(x, y)) {
            return true;
        }

        return false;
    }

    public void dfs(NodeAction action) {
        dfsHelper(this, action);
    }

    private void dfsHelper(QuadTree current, NodeAction action) {
        for (Node node : current.nodes) {
            action.perform(node);
        }

        if (current.northWest != null) {
            dfsHelper(current.northWest, action);
        }
        if (current.northEast != null) {
            dfsHelper(current.northEast, action);
        }
        if (current.southWest != null) {
            dfsHelper(current.southWest, action);
        }
        if (current.southEast != null) {
            dfsHelper(current.southEast, action);
        }
    }
}