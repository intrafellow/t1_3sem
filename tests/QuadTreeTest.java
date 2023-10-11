import org.junit.jupiter.api.Test;
import revenko.ru.classes.Boundry;
import revenko.ru.classes.QuadTree;

import static org.junit.jupiter.api.Assertions.*;

public class QuadTreeTest {
    @Test
    void testInsertAndQuery() {
        QuadTree quadTree = new QuadTree(0, new Boundry(0, 0, 1000, 1000));
        quadTree.insert(3, 5, 1);
        quadTree.insert(5, 5, 2);
        quadTree.insert(7, 6, 3);
        quadTree.insert(9, 9, 4);

        assertTrue(quadTree.contains(3, 5));
        assertTrue(quadTree.contains(5, 5));
        assertTrue(quadTree.contains(7, 6));
        assertTrue(quadTree.contains(9, 9));
        assertFalse(quadTree.contains(6, 6));
    }

    @Test
    void testDFSTraversal() {
        QuadTree quadTree = new QuadTree(0, new Boundry(0, 0, 1000, 1000));
        quadTree.insert(10, 20, 1);
        quadTree.insert(30, 50, 2);
        quadTree.insert(80, 10, 3);
        quadTree.insert(70, 70, 4);

        StringBuilder sb = new StringBuilder();
        quadTree.dfs(node -> sb.append(node.getValue()).append(" "));
        assertEquals("1 2 3 4 ", sb.toString());
    }

    @Test
    void deleteNodeSuccess() {
        // Создаем экземпляр QuadTree и вставляем узел
        Boundry boundry = new Boundry(0, 0, 100, 100);
        QuadTree quadTree = new QuadTree(0, boundry);
        quadTree.insert(10, 20, 42);

        // Удаляем узел
        assertTrue(quadTree.delete(10, 20));
        assertFalse(quadTree.contains(10, 20));
    }

    @Test
    void deleteNodeNotFound() {
        // Создаем экземпляр QuadTree и вставляем узел
        Boundry boundry = new Boundry(0, 0, 100, 100);
        QuadTree quadTree = new QuadTree(0, boundry);
        quadTree.insert(10, 20, 42);

        // Попытка удаления несуществующего узла
        assertFalse(quadTree.delete(15, 25));
        assertTrue(quadTree.contains(10, 20));
    }

    @Test
    void deleteNodeOutOfRange() {
        // Создаем экземпляр QuadTree и вставляем узел
        Boundry boundry = new Boundry(0, 0, 100, 100);
        QuadTree quadTree = new QuadTree(0, boundry);
        quadTree.insert(10, 20, 42);

        // Попытка удаления узла вне диапазона QuadTree
        assertFalse(quadTree.delete(200, 200));
        assertTrue(quadTree.contains(10, 20));
    }
}