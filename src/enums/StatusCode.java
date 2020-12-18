package enums;


public enum StatusCode {

  STATUS_100("Countinue"),
  STATUS_200("OK"),
  STATUS_403("Forbidden"),
  STATUS_404("Not Found"),
  STATUS_500("Internal Server Error");
  
  private final String explain;

  StatusCode(String explain) {

    this.explain = explain;
  }

  public String getExplain() {
  
    return explain;
  }
  
}
