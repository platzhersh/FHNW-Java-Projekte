package ch.fhnw.edu.rental.model;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.*;

import org.junit.*;


public class StockTest {
    private Stock stock;
    private IMovie movie;
    private LowStockListener lowStockListener;
	

    @Before
    public void setUp() {
        movie = createMock(IMovie.class);
        stock = new Stock();
        lowStockListener = createMock(LowStockListener.class);
        stock.addLowStockListener(lowStockListener);
    }


    @After
    public void tearDown() {

    }
    
    @Test
    public void testaddToStock() {
        expect(movie.getTitle()).andReturn("The Big Lebowski").anyTimes();
        replay(movie);
        
        stock.addToStock(movie);
        
        assertEquals(1, stock.getInStock("The Big Lebowski"));
        
    	verify(movie);   	
    }
    
    @Test
    public void testremoveFromStock1() {
    	
    	boolean thrown = false;
    	
    	expect(movie.getTitle()).andReturn("The Big Lebowski").atLeastOnce();
    	expect(lowStockListener.getThreshold()).andReturn(2).atLeastOnce();
    	lowStockListener.stockLow(movie, 2);
    	expectLastCall().once();
    	lowStockListener.stockLow(movie, 1);
    	expectLastCall().once();
    	lowStockListener.stockLow(movie, 0);
    	expectLastCall().once();
    	
    	replay(movie);
    	replay(lowStockListener);
    	
    	assertEquals(1, stock.addToStock(movie));
    	assertEquals(1, stock.getInStock("The Big Lebowski"));
    	assertEquals(2, stock.addToStock(movie));
    	assertEquals(2, stock.getInStock("The Big Lebowski"));
    	assertEquals(3, stock.addToStock(movie));
    	assertEquals(3, stock.getInStock("The Big Lebowski"));
    	
    	assertEquals(2, stock.removeFromStock(movie));
    	assertEquals(2, stock.getInStock("The Big Lebowski"));
    	assertEquals(1, stock.removeFromStock(movie));
    	assertEquals(1, stock.getInStock("The Big Lebowski"));
    	assertEquals(0, stock.removeFromStock(movie));
    	assertEquals(0, stock.getInStock("The Big Lebowski"));
    	
    	
    	try {
    		stock.removeFromStock(movie);
    		fail();
    	} catch (MovieRentalException e) {
    		thrown = true;
    	}
    	
    	assertEquals(true, thrown);
    	
    	verify(movie);
    	verify(lowStockListener);
    }
}
