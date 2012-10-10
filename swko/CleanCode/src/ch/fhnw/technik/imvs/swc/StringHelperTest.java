package ch.fhnw.technik.imvs.swc;

import org.junit.Assert;
import org.junit.Test;

/**
 * StringHelper test class.
 * 
 */
public class StringHelperTest {

  /**
   * Tests with valid input data.
   */
  @Test
  public void testConcatenateValidString() {

    String[] input1 = {"www", "fhnw", "ch"};
    char separator = '.';
    Assert.assertEquals("www.fhnw.ch", StringHelper.concatenate(input1, separator));

    String[] input2 = {"w", "f", "h"};
    char separator2 = ' ';
    Assert.assertEquals("w f h", StringHelper.concatenate(input2, separator2));

  }

  /**
   * Tests for null strings as input. Must throw exceptions.
   */
  @Test
  public void testConcatenateNullString() {

    // test first input string null
    try {
      String[] input1 = {null, "fhnw", "ch"};
      char separator = '.';
      StringHelper.concatenate(input1, separator);
      Assert.fail("should throw exception");
    } catch (IllegalArgumentException e) {
      Assert.assertTrue(true);
    }

    // test middle input string null
    try {
      String[] input1 = {"www", null, "ch"};
      char separator = '.';
      StringHelper.concatenate(input1, separator);
      Assert.fail("should throw exception");
    } catch (IllegalArgumentException e) {
      Assert.assertTrue(true);
    }

    // test last input string null
    try {
      String[] input1 = {"www", "fhnw", null};
      char separator = '.';
      StringHelper.concatenate(input1, separator);
      Assert.fail("should throw exception");
    } catch (IllegalArgumentException e) {
      Assert.assertTrue(true);
    }
  }

  /**
   * Tests for null strings as input. Must throw exceptions.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testConcatenateEmptyStringArray() {

    // test first input string null
    String[] input1 = {};
    char separator = '.';
    StringHelper.concatenate(input1, separator);
    Assert.fail("should throw exception");
  }

  /**
   * Tests for empty strings as input. Must throw exceptions.
   */
  @Test
  public void testConcatenateEmptyString() {

    // test first input string empty
    try {
      String[] input1 = {"", "fhnw", "ch"};
      char separator = '.';
      StringHelper.concatenate(input1, separator);
      Assert.fail("should throw exception");
    } catch (IllegalArgumentException e) {
      Assert.assertTrue(true);
    }

    // test middle input string empty
    try {
      String[] input1 = {"www", "", "ch"};
      char separator = '.';
      StringHelper.concatenate(input1, separator);
      Assert.fail("should throw exception");
    } catch (IllegalArgumentException e) {
      Assert.assertTrue(true);
    }

    // test last input string empty
    try {
      String[] input1 = {"www", "fhnw", ""};
      char separator = '.';
      StringHelper.concatenate(input1, separator);
      Assert.fail("should throw exception");
    } catch (IllegalArgumentException e) {
      Assert.assertTrue(true);
    }
  }

}
