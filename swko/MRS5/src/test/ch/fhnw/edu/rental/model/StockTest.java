package ch.fhnw.edu.rental.model;

import static org.easymock.EasyMock.*;
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
        
        movie.start();
        expect(movie.getTitle()).andReturn("The Big Lebowski.");
        movie.replay();
        
        lowStockListener.start();
        expect(lowStockListener.getThreshold()).andReturn(1);
        expectLastCall(lowStockListener.stockLow(movie,2));
        lowStockListener.replay();
        
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
