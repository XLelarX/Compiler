package compiler.lelar.compiler;

import org.apache.struts.action.ActionForm;

public class CompilerForm extends ActionForm {
    private String request;
//            "public class Main {\r\n" +
//            "    public static void main(String[] args) {\r\n" +
//            "        System.out.println(\"Hello world\");\r\n" +
//            "    }\r\n" +
//            "}\r\n";
    private String response;
    private String vars;
    private boolean complete;

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getVars() {
        return vars;
    }

    public void setVars(String vars) {
        this.vars = vars;
    }

    public boolean isComplete()
    {
        return complete;
    }

    public void setComplete(boolean complete)
    {
        this.complete = complete;
    }
}
