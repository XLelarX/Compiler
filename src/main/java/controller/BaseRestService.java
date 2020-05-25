package controller;

import compiler.lelar.compiler.CompilerForm;
import org.apache.struts.actions.DispatchAction;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

abstract class BaseRestService extends DispatchAction
{
	static void fixRequestedData(CompilerForm compilerForm)
	{
		Pattern p = Pattern.compile("\u200b|\n$");
		Matcher m = p.matcher(
				compilerForm.getRequest()
						.replace("\u00a0", " ")
						.replace("/plus", "+"));
		compilerForm.setRequest(m.replaceAll(""));
	}
}
