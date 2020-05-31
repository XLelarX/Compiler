package controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import form.CompilerForm;
import org.apache.struts.actions.DispatchAction;
import util.Constants;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class BaseRestService extends DispatchAction
{
	private final Gson gson = new GsonBuilder().create();

	static String fixRequest(String request)
	{
		Matcher m = Pattern.compile("\u200b|\n$")
				.matcher(
						request.replace("/plus", "+")
								.replace("\u00a0", " ")
								.replace("/enter", "\n")
								.replace("/tab", "\t")
								.replace("/cell", "#")
								.replace("/percent", "%")
								.replace("/and", "&")
								.replace("/lcb", "{")
								.replace("/rcb", "}")
								.replace("/lbb", "[")
								.replace("/rbb", "]")
								.replace("/slash", "\\")
				);

		return m.replaceAll("");
	}

	void fillResponse(CompilerForm compilerForm, String result, HttpServletResponse response) throws IOException
	{
		compilerForm.setResponse(result);
		PrintWriter writer = response.getWriter();
		writer.print(gson.toJson(compilerForm));
		writer.flush();

		response.setCharacterEncoding(Constants.CHAR_ENCODING);
		response.setContentType("application/json");
	}
}
