package converter.bl;

import converter.bl.Scale;
import fit.ColumnFixture;

public class CheckTemperature extends ColumnFixture {

  /**
   * Converts from a string into an instance of the typesafe Scale enumeration. 
   * You can follow a similar pattern for defining a mapping from a string 
   * to any Java class
   */
  @Override
  public Object parse(String s, Class type) throws Exception {
    if (type == Scale.class) {
      return Scale.valueOf(s);
    }
    return super.parse(s, type);
  }
  
  public int CelsiusToKelvin() {
	  return 1;
  }
  
  public to() {
	  
  }
  
  
}
