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
        // Insert a rectangle into the QuadTree
        quadTree.processCommand("insert 10 10 20 20");
        
        // Find the rectangle at (10, 10)
        Rectangle found = quadTree.root.find(10, 10);
        
        // Verify that the rectangle is found and has correct dimensions
        assertNotNull(found, "Rectangle should be found at (10,10)");
        assertEquals(10, found.x);
        assertEquals(10, found.y);
        assertEquals(20, found.width);
        assertEquals(20, found.height);
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
        // Insert a rectangle and then delete it
        quadTree.processCommand("insert 10 10 20 20");
        boolean deleted = quadTree.root.delete(10, 10);
        
        // Verify that the rectangle was deleted
        assertTrue(deleted, "Rectangle at (10,10) should be deleted");

        // Verify that the rectangle no longer exists
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
    void testUpdate() {
        // Insert and update a rectangle
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
    void testQuadTreeRevertsToLeafAfterDeletion() {
        // Insert rectangles to create an internal node
        quadTree.processCommand("insert 10 10 20 20");
        quadTree.processCommand("insert 15 15 20 20");
        quadTree.processCommand("insert 20 20 20 20");
        quadTree.processCommand("insert 25 25 20 20");
        quadTree.processCommand("insert 30 30 20 20");
        
        // Delete all rectangles
        quadTree.root.delete(10, 10);
        quadTree.root.delete(15, 15);
        quadTree.root.delete(20, 20);
        quadTree.root.delete(25, 25);
        quadTree.root.delete(30, 30);
        
        // Verify that the QuadTree has reverted to a leaf node
        assertTrue(quadTree.root instanceof LeafNode, "QuadTree should revert to a LeafNode after all rectangles are deleted");
    }
}
