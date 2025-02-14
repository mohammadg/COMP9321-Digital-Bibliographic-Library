package dbl;

/**
 * GeneralUtils class with general helper functions.
 *
 * @author Mohammad Ghasembeigi
 */
public class GeneralUtils {
  /**
   * Determines if item should be skipped (because it failed to match query)
   * Limitations: Does not handle casefold comparisons for various special characters.
   *
   * @param query the query string
   * @param data the data being queried against
   * @return true if item should be skipped (match failed), false otherwise
   */
  public static boolean querySkip(String query, String data, boolean matchCase, boolean exactMatch) {
    if (query != null) {
      if (data == null)
        return true;
        
      String caseConvertedData = data;
      String caseConvertedQuery = query;
      
      if (!matchCase) {
        caseConvertedData = data.toLowerCase();
        caseConvertedQuery = query.toLowerCase();
      }
      
      if (exactMatch) {
        if (!caseConvertedData.equals(caseConvertedQuery))
          return true;
      }
      else {
        if (!caseConvertedData.contains(caseConvertedQuery))
          return true;
      }
    }
    
    return false;
  }
  
  /**
   * Determines if item should be skipped (because it failed to match query).
   * In this case, there may be multiple queries (which all need to match).
   * In other words, for each query, there needs to be a match for some element
   * of data.
   *
   * @param query the query string
   * @param data the data being queried against
   * @return true if item should be skipped (match failed), false otherwise
   */
  public static boolean querySkipArray(String[] query, String[] data, boolean matchCase, boolean exactMatch) {
    if (query.length  != 0) {
      if (data.length == 0) {
        return true;
      }
  
      //Iterate through queries
      for (String sQuery : query) {
        boolean found = false;
        
        //Iterate through data
        for (String sData : data) {
          if (!querySkip(sQuery, sData, matchCase, exactMatch)) {  //delegate to querySkip for string,string comparisons
            found = true;
            break;
          }
        }
        
        //If we haven't found a match for this query then the entire match fails and we should skip
        if (!found)
          return true;
      }
    }
    
    return false;
  }
  
  /**
   * Given an publications item type returns the font awesome icon for publication.
   *
   * @param type  Item type of publication
   * @return  font awesome icon name (without the fa- prefix) or empty string if no match found
   */
  public static String getPublicationIcon(Item.ItemTypes type) {
    
    if (type == Item.ItemTypes.ARTICLE) {
      return "newspaper-o";
    } else if (type == Item.ItemTypes.PROCEEDINGS || type == Item.ItemTypes.INPROCEEDINGS) {
      return "file-text-o";
    } else if (type == Item.ItemTypes.BOOK) {
      return "book";
    } else if (type == Item.ItemTypes.INCOLLECTION) {
      return "users";
    } else if (type == Item.ItemTypes.PHDTHESIS || type == Item.ItemTypes.MASTERSTHESIS) {
      return "graduation-cap";
    } else if (type == Item.ItemTypes.WWW) {
      return "globe";
    }
  
  
    return "";
  }
}
