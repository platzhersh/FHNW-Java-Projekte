package ch.fhnw.edu.rental.model;

import static org.easymock.EasyMock.*;
import org.junit.*;


public class StockTest {
    private Stock stock;
    private IMovie movie;
    private LowStockListener lowStockListener;
	

    @Before
    public void setUp() {
        // movie = createMock(IMovie.class); // 1
        stock = createMock(Stock.class);
        stock.addLowStockListener(lowStockListener);
    }


    @After
    public void tearDown() {

    }
    
    @Test
    public void testaddToStock() {
    	expect(stock.addToStock((IMovie) new Movie())).andReturn(1);
    	
    	replay(stock);
    	
    	stock.addToStock((IMovie) new Movie());
    	
    	verify(stock);
    	
    }
    
    @Test
    public void testremoveFromStock() {

    }
}
