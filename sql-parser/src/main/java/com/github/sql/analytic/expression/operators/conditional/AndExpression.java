/* ================================================================
 * JSQLParser : java based sql parser 
 * ================================================================
 *
 * Project Info:  http://jsqlparser.sourceforge.net
 * Project Lead:  Leonardo Francalanci (leoonardoo@yahoo.it);
 *
 * (C) Copyright 2004, by Leonardo Francalanci
 *
 * This library is free software; you can redistribute it and/or modify it under the terms
 * of the GNU Lesser General Public License as published by the Free Software Foundation;
 * either version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License along with this
 * library; if not, write to the Free Software Foundation, Inc., 59 Temple Place, Suite 330,
 * Boston, MA 02111-1307, USA.
 */
 
package com.github.sql.analytic.expression.operators.conditional;

import com.github.sql.analytic.expression.BinaryExpression;
import com.github.sql.analytic.expression.SQLExpression;
import com.github.sql.analytic.expression.ExpressionVisitor;



public class AndExpression extends BinaryExpression {
	public AndExpression(SQLExpression leftExpression, SQLExpression rightExpression) {
		setLeftExpression(leftExpression);
		setRightExpression(rightExpression);
	}
	public AndExpression() {		
	}
	public void accept(ExpressionVisitor expressionVisitor) {
		expressionVisitor.visit(this);
	}
	
	public String getStringExpression() {
		return "AND";
	}
}
