package controller;

import data.UserData;
import form.CompilerForm;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import runner.BaseCodeRunner;
import util.Constants;
import util.TerminalHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class GetResultRestService extends BaseRestService
{
	@Override
	public ActionForward execute(
			ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response
	) throws Exception
	{
		request.setCharacterEncoding(Constants.CHAR_ENCODING);
		CompilerForm compilerForm = (CompilerForm) form;
		String sessionId = request.getSession().getId();
		Process sessionProcess = BaseCodeRunner.getUserDataMap().get(sessionId).getProcess();

		if (sessionProcess != null)
		{
			StringBuilder result = new StringBuilder();
			UserData userData = BaseCodeRunner.getUserDataMap().get(sessionId);
			userData.getOut().forEach(line -> result.append(line).append("\r\n"));
			result.append("Execution time: ").append((System.currentTimeMillis() - userData.getExecutionTime()) / 1000).append("sec");

			if (!sessionProcess.isAlive())
			{
				compilerForm.setComplete(true);
				cleanUpData(sessionId, sessionProcess);
			}

			fillResponse(compilerForm, result.toString(), response);
		}

		return null;
	}

	private void cleanUpData(String sessionId, Process sessionProcess) throws IOException
	{
		sessionProcess.getOutputStream().close();
		sessionProcess.getInputStream().close();
		sessionProcess.getErrorStream().close();

		String folderName = BaseCodeRunner.getUserDataMap().remove(sessionId).getFolderName();
		if (folderName != null)
		{
			TerminalHelper.deleteTemporaryData(folderName);
		}
	}
}
