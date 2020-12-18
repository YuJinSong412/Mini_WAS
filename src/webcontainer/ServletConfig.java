package webcontainer;

import java.util.Map;

public class ServletConfig {

  private String getServletName = "index2";

  private String initParameter = "ujsong4";

  private Map<String, String> initParameterNames;

  public ServletConfig(Map<String, String> initParameterNames) {

    this.initParameterNames = initParameterNames;
  }


  public String getGetServletName() {

    return getServletName;
  }


  public String getInitParameter(String name) {

    return initParameter;
  }


  public Map<String, String> getInitParameterNames() {

    return initParameterNames;
  }


}
