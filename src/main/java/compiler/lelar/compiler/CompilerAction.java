package compiler.lelar.compiler;

import exception.CompilerException;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import runner.BaseCodeRunner;
import runner.JavaCodeRunner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CompilerAction extends DispatchAction
{
	private final static String START_FORWARD = "Start";

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		request.setCharacterEncoding("UTF-8");
		CompilerForm compilerForm = (CompilerForm) form;
		request.getAuthType();

		StringBuilder code = new StringBuilder(compilerForm.getRequest());
		String vars = compilerForm.getVars();

		//request.getSession()
		CompilerEntity compilerEntity;
		BaseCodeRunner codeRunner = new JavaCodeRunner();

		try
		{
			compilerEntity = codeRunner.start(code.toString(), vars, request);
		} catch (CompilerException e)
		{
			compilerForm.setResponse(e.getMessage());
			return mapping.findForward(START_FORWARD);
		}

		StringBuilder result = new StringBuilder();

		compilerEntity.getOut().forEach(line -> result.append(line).append("\r\n"));
		compilerEntity.getErrors().forEach(line -> result.append(line).append("\r\n"));
		compilerForm.setResponse(result.toString());

		return mapping.findForward(START_FORWARD);
	}
}
