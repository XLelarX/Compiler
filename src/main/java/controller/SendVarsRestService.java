package controller;

import form.CompilerForm;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import runner.BaseCodeRunner;
import util.Constants;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

public class SendVarsRestService extends BaseRestService
{
	@Override
	public ActionForward execute(
			ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response
	) throws IOException
	{
		request.setCharacterEncoding(Constants.CHAR_ENCODING);
		String vars = fixRequest(((CompilerForm) form).getVars());
		String sessionId = request.getSession().getId();
		Process sessionProcess = BaseCodeRunner.getUserDataMap().get(sessionId).getProcess();

		if (sessionProcess != null)
		{
			BaseCodeRunner.writeInProcess(sessionId, Collections.singletonList(vars));
		}

		return null;
	}
}
