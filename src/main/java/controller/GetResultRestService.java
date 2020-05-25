package controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import compiler.lelar.compiler.CompilerForm;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import runner.BaseCodeRunner;
import utils.TerminalHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

public class GetResultRestService extends BaseRestService
{
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		CompilerForm compilerForm = (CompilerForm) form;
		fixRequestedData(compilerForm);

		String sessionId = request.getSession().getId();
		Process sessionProcess = BaseCodeRunner.getProcesses().get(sessionId);

		if (sessionProcess != null)
		{
			StringBuilder result = new StringBuilder();
			BaseCodeRunner.getOut().get(sessionId)
					.forEach(line -> result.append(line.replace("+", "/plus")).append("\r\n"));
			result.append("Execution time: ").append((System.currentTimeMillis() - BaseCodeRunner.getExecutionTimes().get(sessionId)) / 1000).append("sec");

			if (!sessionProcess.isAlive())
			{
				compilerForm.setComplete(true);
			}

			if (!sessionProcess.isAlive())
			{
				sessionProcess.getInputStream().close();
				sessionProcess.getErrorStream().close();
				BaseCodeRunner.getProcesses().remove(sessionId);
				BaseCodeRunner.getOut().remove(sessionId);

				String folderName = BaseCodeRunner.getFolderNames().remove(sessionId);
				if (folderName != null)
				{
					TerminalHelper.deleteTemporaryData(folderName);
				}

				BaseCodeRunner.getExecutionTimes().remove(sessionId);
			}

			compilerForm.setResponse(result.toString());

			GsonBuilder builder = new GsonBuilder();
			Gson gson = builder.create();
			String jsonString = gson.toJson(compilerForm);

			PrintWriter writer = response.getWriter();
			writer.print(jsonString);
			writer.flush();
		}

		response.setContentType("application/json");
		return null;
	}
}
