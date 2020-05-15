package controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import compiler.lelar.compiler.CompilerForm;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import runner.BaseCodeRunner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SendVarsRestService extends DispatchAction
{
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		request.setCharacterEncoding("UTF-8");
		CompilerForm compilerForm = (CompilerForm) form;
		fixRequestedData(compilerForm);

		String vars = compilerForm.getVars().replace("\\plus", "+");
		String sessionId = request.getSession().getId();

		//AtomicReference<Process> sessionProcess = BaseCodeRunner.processes.get(sessionId);
		Process sessionProcess = BaseCodeRunner.processes.get(sessionId);
		if (sessionProcess != null)
		{
			//long time = System.currentTimeMillis();
			//OutputStream stdin = sessionProcess.getOutputStream();
			//stdin.write(vars.getBytes());

//			while (sessionProcess.isAlive() && System.currentTimeMillis() - time < 2000)
//			{
//			}

			BaseCodeRunner.writeInProcess(sessionId, Collections.singletonList(vars));

			if (!sessionProcess.isAlive())
			{
				sessionProcess.getOutputStream().close();
				BaseCodeRunner.processes.remove(sessionId);
			}

			//List<String> stdout = BaseCodeRunner.readFrom(sessionId);
			//List<String> stderr = BaseCodeRunner.readFrom(sessionId);
			//StringBuilder result = new StringBuilder();

			//stdout.forEach(line -> result.append(line.replace("+", "\\plus")).append("\r\n"));
			//stderr.forEach(line -> result.append(line.replace("+", "\\plus")).append("\r\n"));

			//compilerForm.setResponse(result.toString());

			//GsonBuilder builder = new GsonBuilder();
			//Gson gson = builder.create();
			//String jsonString = gson.toJson(compilerForm);

			//PrintWriter writer = response.getWriter();
			//writer.print(jsonString);
			//writer.flush();
		}

		response.setContentType("application/json");
		return null;
	}

	private void fixRequestedData(CompilerForm compilerForm)
	{
		Pattern p = Pattern.compile("\u200b|\n$");
		Matcher m = p.matcher(
				compilerForm.getRequest()
						.replace("\u00a0", " ")
						.replace("\\plus", "+"));
		compilerForm.setRequest(m.replaceAll(""));
	}
}
