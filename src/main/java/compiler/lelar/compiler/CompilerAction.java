package compiler.lelar.compiler;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CompilerAction extends DispatchAction {
    private final static String START_FORWARD = "Start";

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws Exception {
        CompilerForm compilerForm = (CompilerForm) form;
//        return super.execute(mapping, form, request, response);
        return mapping.findForward(START_FORWARD);/*super.execute(mapping form, request, response);*/
    }

}
