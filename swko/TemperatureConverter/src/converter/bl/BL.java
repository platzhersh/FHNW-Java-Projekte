package converter.bl;

public class BL {
  public Scale[] getScales() {
    return Scale.values();
  }
  
  public double convert(Scale from, Scale to, double value) {
    if (from == to) {
      return value;
    }
    switch (from) {
    case Kelvin:
      if (value < 0.0) { return Double.NaN; }
      else if (to == Scale.Celcius) { return value - 273.15; }
      else if (to == Scale.Fahrenheit) { return (value * (9.0/5.0)) - 459.67; }
      else { return Double.NaN; }
    case Celcius:
      if (value < -273.15) { return Double.NaN; }
      else if (to == Scale.Kelvin) { return value + 273.15; }
      else if (to == Scale.Fahrenheit) { return (value * (9.0/5.0)) + 32.0; }
      else { return Double.NaN; }
    case Fahrenheit:
      if (value < -459.67) { return Double.NaN; }
      else if (to == Scale.Kelvin) { return ((value + 459.67) * (5.0f/9.0)); }
      else if (to == Scale.Celcius) { return ((value - 32.0) * (5.0f/9.0)); }
      else { return Double.NaN; }
    default: return Double.NaN;
    }
  }
}
