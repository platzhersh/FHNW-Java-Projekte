package webfr.flashcards.server;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.InputStream;
import java.sql.Connection;
import java.util.Date;
import java.util.List;

import org.dbunit.IDatabaseTester;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.dataset.xml.XmlDataSet;
import org.dbunit.operation.DatabaseOperation;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import webfr.flashcards.server.ServiceLayer;
import webfr.flashcards.server.data.Database;
import webfr.flashcards.shared.Card;
import webfr.flashcards.shared.Cardbox;

public class ServiceLayerTest {

  @BeforeClass
  public static void setUpBeforeClass() throws Exception {
    new Database(null).initDatabase();    
  }

  @AfterClass
  public static void tearDownAfterClass() throws Exception {
  }

  IDatabaseTester tester;
  Connection connection;
  ServiceLayer dal;
  
  @Before
  public void setUp() throws Exception {
    tester = new JdbcDatabaseTester("org.hsqldb.jdbcDriver", "jdbc:hsqldb:mem:flashcards", "sa", "");
    connection = tester.getConnection().getConnection();
    InputStream stream = this.getClass().getResourceAsStream("EmptyDB.xml");

    IDataSet dataSet = new XmlDataSet(stream);
    DatabaseOperation.CLEAN_INSERT.execute(tester.getConnection(), dataSet);
    tester.setDataSet(dataSet);
    tester.onSetup();
    dal = new ServiceLayer(null);
  }

  @After
  public void tearDown() throws Exception {
    tester.onTearDown();
  }

  @Test
  public void testDataAbstractionLayer() {
    assertNotNull(dal.em);
  }

  @Test
  public void testNewCardbox() {
    Cardbox box = new Cardbox("Title", "Desc", new Date());
    dal.newCardbox(box);
    List<Cardbox>boxes = dal.getCardboxes();
    assertEquals(1, boxes.size());
    Cardbox act = boxes.get(0);
    assertEquals(box, act);
  }

  @Test
  public void testGetCardboxes() throws Exception {
    InputStream stream = this.getClass().getResourceAsStream("CardboxTestData.xml");

    IDataSet dataSet = new FlatXmlDataSet(stream);
    DatabaseOperation.CLEAN_INSERT.execute(tester.getConnection(), dataSet);
    tester.setDataSet(dataSet);
    List<Cardbox>boxes = dal.getCardboxes();
    assertEquals(3, boxes.size());
  }

  @Test
  public void testGetCardboxById() throws Exception {
    InputStream stream = this.getClass().getResourceAsStream("CardboxTestData.xml");

    IDataSet dataSet = new FlatXmlDataSet(stream);
    DatabaseOperation.CLEAN_INSERT.execute(tester.getConnection(), dataSet);
    tester.setDataSet(dataSet);
    Cardbox box = dal.getCardboxById(5);
    assertNotNull(box);
    assertEquals("Geographie", box.getTitle());
  }

//  @Test @Ignore
//  public void testUpdateCardboxOnly() throws Exception {
//    InputStream stream = this.getClass().getResourceAsStream("CardboxTestData.xml");
//
//    IDataSet dataSet = new FlatXmlDataSet(stream);
//    DatabaseOperation.CLEAN_INSERT.execute(tester.getConnection(), dataSet);
//    tester.setDataSet(dataSet);
//    Cardbox box = dal.getCardboxById(5);
//    String title = box.getTitle();
//    String desc = box.getDescription();
//    Date version = box.getVersion();
//    int nofcards = box.getCards().size();
//    box.setTitle("G"); box.setDescription("D");
//    Card card = box.getCards().get(0);
//    box.removeCard(card);
//    dal.updateCardboxOnly(box);
//    
//    Cardbox box2 = dal.getCardboxById(5);
//    assertFalse(title.equals(box2.getTitle()));
//    assertEquals("G", box2.getTitle());
//    assertFalse(desc.equals(box2.getDescription()));
//    assertEquals("D", box2.getDescription());
//    assertTrue(version.before(box2.getVersion()));
//    assertEquals(nofcards, box2.getCards().size());
//  }

  @Test
  public void testUpdateCardboxWithCards() throws Exception {
    InputStream stream = this.getClass().getResourceAsStream("CardboxTestData.xml");

    IDataSet dataSet = new FlatXmlDataSet(stream);
    DatabaseOperation.CLEAN_INSERT.execute(tester.getConnection(), dataSet);
    tester.setDataSet(dataSet);
    Cardbox box = dal.getCardboxById(5);
    String title = box.getTitle();
    String desc = box.getDescription();
    Date version = box.getVersion();
    int nofcards = box.getCards().size();
    box.setTitle("G"); box.setDescription("D");
    Card card = box.getCards().get(0);
    box.getCards().remove(card);
    dal.updateCardboxWithCards(box);
    
    Cardbox box2 = dal.getCardboxById(5);
    assertFalse(title.equals(box2.getTitle()));
    assertEquals("G", box2.getTitle());
    assertFalse(desc.equals(box2.getDescription()));
    assertEquals("D", box2.getDescription());
    assertTrue(version.before(box2.getVersion()));
    assertEquals(nofcards-1, box2.getCards().size());
  }

  @Test
  public void testDeleteCardbox() throws Exception {
    InputStream stream = this.getClass().getResourceAsStream("CardboxTestData.xml");

    IDataSet dataSet = new FlatXmlDataSet(stream);
    DatabaseOperation.CLEAN_INSERT.execute(tester.getConnection(), dataSet);
    tester.setDataSet(dataSet);
    Cardbox box = dal.getCardboxById(5);
    dal.deleteCardbox(box);
    box = dal.getCardboxById(5);
    assertNull(box);
    
    // Fetch database data after deletion
    IDataSet databaseDataSet = tester.getConnection().createDataSet();
    ITable actualTable = databaseDataSet.getTable("CARDS");
    assertEquals(0, actualTable.getRowCount());
  }

  @Test
  public void testNewCard() throws Exception {
    Cardbox box = new Cardbox("Title", "Desc", new Date());
    Card card = new Card("q", "a", box);
    box.getCards().add(card);
    dal.newCardbox(box);
    List<Cardbox>boxes = dal.getCardboxes();
    assertEquals(1, boxes.size());
    Cardbox act = boxes.get(0);
    List<Card>cards = act.getCards();
    assertEquals(1, cards.size());
    Card newCard = cards.get(0); 
    assertEquals(card, newCard);
    
    Card card2 = new Card("q1", "a1", box);
    act.getCards().add(card2);
    dal.newCard(card2);
    
    // Fetch database data after deletion
    IDataSet databaseDataSet = tester.getConnection().createDataSet();
    ITable actualTable = databaseDataSet.getTable("CARDS");
    assertEquals(2, actualTable.getRowCount());
    
    List<Cardbox> boxes2 = dal.getCardboxes();
    assertEquals(1, boxes2.size());
    Cardbox act2 = boxes.get(0);
    List<Card>cs = act2.getCards();
    assertEquals(2, cs.size());
  }

  @Test
  public void testGetCardsFor() throws Exception {
    InputStream stream = this.getClass().getResourceAsStream("CardboxTestData.xml");

    IDataSet dataSet = new FlatXmlDataSet(stream);
    DatabaseOperation.CLEAN_INSERT.execute(tester.getConnection(), dataSet);
    tester.setDataSet(dataSet);
    Cardbox box = dal.getCardboxById(5);

    List<Card> cards = dal.getCardsFor(box);
    assertEquals(box.getCards().size(), cards.size());
    for (Card c : box.getCards()) {
      assertTrue(cards.contains(c));
    }
  }

//  @Test
//  public void testUpdateCard() throws Exception {
//    InputStream stream = this.getClass().getResourceAsStream("CardboxTestData.xml");
//
//    IDataSet dataSet = new FlatXmlDataSet(stream);
//    DatabaseOperation.CLEAN_INSERT.execute(tester.getConnection(), dataSet);
//    tester.setDataSet(dataSet);
//    Cardbox box = dal.getCardboxById(5);
//
//    Card c = box.getCards().get(1);
//    c.setAnswer("question"); c.setQuestion("answer");
//    
//    Cardbox act = dal.getCardboxes().get(1);
//    Card c2 = act.getCards().get(1);
//    assertEquals("question", c2.getAnswer());
//    assertEquals("answer", c2.getQuestion());
//  }

//  @Test
//  public void testDeleteCard() throws Exception {
//    InputStream stream = this.getClass().getResourceAsStream("CardboxTestData.xml");
//
//    IDataSet dataSet = new FlatXmlDataSet(stream);
//    DatabaseOperation.CLEAN_INSERT.execute(tester.getConnection(), dataSet);
//    tester.setDataSet(dataSet);
//    Cardbox box = dal.getCardboxById(5);
//
//    Card c = box.getCards().get(1);
//    box.removeCard(c);
//    dal.deleteCard(c);
//    
//    // Fetch database data after deletion
//    IDataSet databaseDataSet = tester.getConnection().createDataSet();
//    ITable actualTable = databaseDataSet.getTable("CARDS");
//    assertEquals(1, actualTable.getRowCount());
//    
//    box = dal.getCardboxById(5);
//    assertFalse(box.getCards().contains(c));
//  }

}
