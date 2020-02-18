package kap19_Minimaler_Spannbaum;

/**
 * Codebeispiel für das Array, in dem die aktuellen kleinsten Werte gehalten werden
 */

class ArrayItem
{
  ArrayItem(double value, boolean fix)
  {
    this.value = value;
    this.fix = fix;
  }
  
  boolean fix;
  double  value;
}
