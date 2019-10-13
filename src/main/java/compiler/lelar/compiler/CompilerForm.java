package compiler.lelar.compiler;

import org.apache.struts.action.ActionForm;

public class CompilerForm extends ActionForm {
    private String request;
    private String response;
    private String vars;

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
}
