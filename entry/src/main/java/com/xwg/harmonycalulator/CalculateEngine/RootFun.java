package com.xwg.harmonycalulator.CalculateEngine;import com.xwg.harmonycalulator.ResourceTable;import ohos.global.resource.NotExistException;import ohos.global.resource.WrongTypeException;import java.io.IOException;import java.util.LinkedList;public class RootFun extends CalculateFunction {	String getName(){		return "root";	}		String getPrototype(){		return "root(X1,X2)";	}		boolean execute(LinkedList<Complex> paraList, EvaluateContext context) throws NotExistException, WrongTypeException, IOException {		if(paraList.size() != 2)	    {	        context.setErrorMessage(getPrototype(), ResourceTable.String_error_invalid_parameter_count);	        return false;	    }	    Complex x = paraList.removeFirst();	    if(x.i != 0)	    {	    	context.setErrorMessage(getPrototype(), ResourceTable.String_error_invalid_date_type);	        return false;	    }	    Complex y = paraList.removeFirst();	    if(y.r == 0)	    {	    	context.setErrorMessage(getPrototype(), ResourceTable.String_error_invalid_input);	        return false;	    }	    if(y.i != 0)	    {	    	context.setErrorMessage(getPrototype(), ResourceTable.String_error_invalid_date_type);	        return false;	    }	    context.setCurrentResult(new Complex(Math.pow(x.r, 1/y.r)));	    return checkResult(context);	}}