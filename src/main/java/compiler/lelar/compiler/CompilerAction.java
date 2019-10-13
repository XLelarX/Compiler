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
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        CompilerForm compilerForm = (CompilerForm) form;
        StringBuilder requestCode = new StringBuilder(compilerForm.getRequest());
        String vars = compilerForm.getVars();

        CompilerEntity compilerEntity = new Runner().run(requestCode.toString(), vars);

        StringBuilder responseCode = new StringBuilder();

        if (compilerEntity.getOut() != null)
            for (String out : compilerEntity.getOut())
                responseCode.append(out).append("\r\n");

        for (String err : compilerEntity.getErr())
            responseCode.append(err).append("\r\n");

        compilerForm.setResponse(responseCode.toString());

//        return super.execute(mapping, form, request, response);
        return mapping.findForward(START_FORWARD);/*super.execute(mapping form, request, response);*/
    }

}
