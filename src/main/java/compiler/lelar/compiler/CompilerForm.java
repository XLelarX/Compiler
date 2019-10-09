package compiler.lelar.compiler;

import org.apache.struts.action.ActionForm;

public class CompilerForm extends ActionForm {
    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
