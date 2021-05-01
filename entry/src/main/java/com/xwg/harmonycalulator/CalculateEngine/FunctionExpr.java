package com.xwg.harmonycalulator.CalculateEngine;

import ohos.global.resource.NotExistException;
import ohos.global.resource.WrongTypeException;

import java.io.IOException;
import java.util.LinkedList;

public class FunctionExpr extends NonterminalExpr {
	FunctionExpr(String name){
		functionName = name;
	}
	public static Expr buildExpr(BuildContext context){
		Token token = null;
	    String functionName = new String();

	    token = context.tokenList.getFirst();
	    if(token.getType() != Token.EType.FunctionName){
	    	context.errorMessage = "Invalid function name:" + token.getContent();
	    	return null;
	    }
	    functionName = token.getContent();
	    context.tokenList.removeFirst();

	    if(context.tokenList.size() == 0)
	    {
	        context.errorMessage = "Expression is't complete";
	        return null;
	    }

	    token = context.tokenList.getFirst();
	    if(token.getType() != Token.EType.Parenthese || token.getContent().compareTo("(") != 0) // != "(")
	    {
	        context.errorMessage = "\'(\'is necessary";
	        return null;
	    }
	    context.tokenList.removeFirst();

	    if(context.tokenList.size() == 0)
	    {
	        context.errorMessage = "Expression is't complete";
	        return null;
	    }

	    FunctionExpr funExpr = new FunctionExpr(functionName);

	    token = context.tokenList.getFirst();
	    if(token.getType() == Token.EType.Parenthese && token.getContent().compareTo(")") == 0) //== ")")
	    {
	        context.tokenList.removeFirst();
	        return funExpr;
	    }

	    while(true)
	    {
	        Expr expr = AdditiveExpr.buildExpr(context);
	        if(expr == null) break;
	        funExpr.appendExpr(expr);

	        if(context.tokenList.size() == 0)
	        {
	            context.errorMessage = "Expression is't complete";
	            break;
	        }

	        token = context.tokenList.getFirst();
	        if(token.getType() == Token.EType.Comma)
	        {
	            context.tokenList.removeFirst();
	        }
	        else if(token.getType() == Token.EType.Parenthese && token.getContent().compareTo(")") == 0)
	        {
	            context.tokenList.removeFirst();
	            return funExpr;
	        }
	        else
	        {
	            context.errorMessage = functionName + " parameter error";
	            break;
	        }
	    }
	    return null;
	}
	public boolean evaluate(EvaluateContext context) throws NotExistException, WrongTypeException, IOException {
		context.pushResult(new Complex(0));
	    LinkedList<Complex> paraList = new LinkedList<Complex>();
	    for(Expr expr:exprList)
	    {
	        if(expr.evaluate(context))
	        {
	            paraList.add(context.getCurrentResult());
	        }
	        else
	        {
	            return false;
	        }
	    }
	    context.popResult();

	    CalculateFunction fun = context.getFunctionManager().getFunction(functionName);
	    if(fun == null)
	    {
	        context.setErrorMessage("Can't found the function:" + functionName);
	        return false;
	    }
	    boolean result = false;
	    try{
			result = fun.execute(paraList, context);
		} catch (NotExistException | WrongTypeException | IOException e) {
			e.printStackTrace();
		}
	    return result;
	}
	private String functionName;
};
