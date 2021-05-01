package com.xwg.harmonycalulator.CalculateEngine;

import com.xwg.harmonycalulator.ResourceTable;

public class DegreesFormatter  extends ComplexFormatter {
	public DegreesFormatter(CalculatorContext _context){
		super(_context);
	}
	
	public String toString(Complex complex){
		if(complex.i == 0){
			return  toString(StrictMath.toDegrees(complex.r), 12) + context.getText(ResourceTable.String_character_degree);
		}else{
			RadiusAngleFormatter formatter = new RadiusAngleFormatter(context, true);
			return formatter.toString(complex);
		}
	}
}