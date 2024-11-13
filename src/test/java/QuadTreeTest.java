import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class QuadTreeTest {

    private QuadTree quadTree;

    @BeforeEach
    void setUp() {
        // Initialize the QuadTree before each test
        quadTree = new QuadTree();
    }

    @Test
    void testInsertAndFind() {
        // Insert multiple rectangles
        quadTree.processCommand("insert 10 10 20 20");
        quadTree.processCommand("insert 15 15 25 25");

        // Find each rectangle by its position
        Rectangle found1 = quadTree.root.find(10, 10);
        Rectangle found2 = quadTree.root.find(15, 15);

        // Verify that each rectangle is found and has correct dimensions
        assertNotNull(found1, "Rectangle should be found at (10,10)");
        assertEquals(10, found1.x);
        assertEquals(10, found1.y);
        assertEquals(20, found1.width);
        assertEquals(20, found1.height);

        assertNotNull(found2, "Rectangle should be found at (15,15)");
        assertEquals(15, found2.x);
        assertEquals(15, found2.y);
        assertEquals(25, found2.width);
        assertEquals(25, found2.height);
    }

    @Test
    void testFindNonExistentRectangle() {
        // Try finding a rectangle that doesn't exist
        Rectangle found = quadTree.root.find(50, 50);

        // Assert that no rectangle is found
        assertNull(found, "No rectangle should be found at (50, 50)");
    }

    @Test
    void testDelete() {
        // Insert and then delete a rectangle
        quadTree.processCommand("insert 10 10 20 20");
        boolean deleted = quadTree.root.delete(10, 10);

        // Verify that the rectangle was deleted
        assertTrue(deleted, "Rectangle at (10,10) should be deleted");

        // Confirm the rectangle no longer exists
        Rectangle found = quadTree.root.find(10, 10);
        assertNull(found, "Rectangle should no longer exist at (10,10)");
    }

    @Test
    void testDeleteNonExistentRectangle() {
        // Try deleting a rectangle that doesn't exist
        boolean deleted = quadTree.root.delete(30, 30);

        // Assert that deletion fails
        assertFalse(deleted, "Deletion should fail for non-existent rectangle at (30,30)");
    }

    @Test
    void testUpdateRectangle() {
        // Insert and update a rectangle's dimensions
        quadTree.processCommand("insert 10 10 20 20");
        quadTree.processCommand("update 10 10 30 30");

        // Find the updated rectangle
        Rectangle updated = quadTree.root.find(10, 10);

        // Verify the update
        assertNotNull(updated, "Updated rectangle should still exist");
        assertEquals(30, updated.width, "Width should be updated to 30");
        assertEquals(30, updated.height, "Height should be updated to 30");
    }

    @Test
    void testInsertExceedingThreshold() {
        // Insert multiple rectangles to exceed the threshold and trigger a split
        quadTree.processCommand("insert 10 10 20 20");
        quadTree.processCommand("insert 15 15 20 20");
        quadTree.processCommand("insert 20 20 20 20");
        quadTree.processCommand("insert 25 25 20 20");
        quadTree.processCommand("insert 30 30 20 20");

        // Check that root has split into an internal node
        assertTrue(quadTree.root instanceof InternalNode, "Root should be an InternalNode after threshold is exceeded");

        // Verify each rectangle still exists
        assertNotNull(quadTree.root.find(10, 10), "Rectangle at (10,10) should still be in the QuadTree");
        assertNotNull(quadTree.root.find(15, 15), "Rectangle at (15,15) should still be in the QuadTree");
        assertNotNull(quadTree.root.find(20, 20), "Rectangle at (20,20) should still be in the QuadTree");
        assertNotNull(quadTree.root.find(25, 25), "Rectangle at (25,25) should still be in the QuadTree");
        assertNotNull(quadTree.root.find(30, 30), "Rectangle at (30,30) should still be in the QuadTree");
    }

    @Test
    void testQuadTreeRevertsToLeafAfterFullDeletion() {
        // Insert rectangles to create an internal node
        quadTree.processCommand("insert 10 10 20 20");
        quadTree.processCommand("insert 15 15 20 20");
        quadTree.processCommand("insert 20 20 20 20");
        quadTree.processCommand("insert 25 25 20 20");
        quadTree.processCommand("insert 30 30 20 20");

        // Delete each rectangle one by one
        quadTree.root.delete(10, 10);
        quadTree.root.delete(15, 15);
        quadTree.root.delete(20, 20);
        quadTree.root.delete(25, 25);
        quadTree.root.delete(30, 30);

        // Confirm that the QuadTree has reverted to a single empty leaf node
        assertTrue(quadTree.root instanceof LeafNode, "QuadTree should revert to a single LeafNode after all rectangles are deleted");
    }

    @Test
    void testDump() {
        // Insert multiple rectangles
        quadTree.processCommand("insert 10 10 20 20");
        quadTree.processCommand("insert 15 15 25 25");
        quadTree.processCommand("insert 30 30 15 15");

        // Capture and verify the QuadTree structure output
        System.out.println("Dumping QuadTree structure:");
        quadTree.printQuadTree();
    }
}
