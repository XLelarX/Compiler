package controller;

import compiler.lelar.compiler.CompilerForm;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import runner.BaseCodeRunner;
import utils.ConsoleHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;

public class SendVarsRestService extends BaseRestService
{
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		request.setCharacterEncoding("UTF-8");
		CompilerForm compilerForm = (CompilerForm) form;
		fixRequestedData(compilerForm);

		String vars = compilerForm.getVars().replace("\\plus", "+");
		String sessionId = request.getSession().getId();

		Process sessionProcess = BaseCodeRunner.getProcesses().get(sessionId);
		if (sessionProcess != null)
		{
			BaseCodeRunner.writeInProcess(sessionId, Collections.singletonList(vars));

			if (!sessionProcess.isAlive())
			{
				sessionProcess.getOutputStream().close();
				String folderName = BaseCodeRunner.getFolderNames().remove(sessionId);
				if (folderName != null)
				{
					ConsoleHelper.deleteTemporaryData(folderName);
				}
			}
		}

		response.setContentType("application/json");
		return null;
	}
}
