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
 
 package com.github.sql.analytic.expression.operators.relational;

import java.util.List;

import com.github.sql.analytic.expression.SQLExpression;
import com.github.sql.analytic.expression.ExpressionVisitor;
import com.github.sql.analytic.expression.GroupingExpression;
import com.github.sql.analytic.statement.select.PlainSelect;


/**
 * A list of expressions, as in SELECT A FROM TAB WHERE B IN (expr1,expr2,expr3)
 */

public class ExpressionList implements ItemsList,GroupingExpression {
	
	private List<SQLExpression> expressions;

	public ExpressionList() {
	}

	
	public ExpressionList(List<SQLExpression> expressions) {
		this.expressions = expressions;
	}

	
	public List<SQLExpression> getExpressions() {
		return expressions;
	}

	
	public void setExpressions(List<SQLExpression> list) {
		expressions = list;
	}

	public void accept(ItemsListVisitor itemsListVisitor) {
		itemsListVisitor.visit(this);
	}

	public String toString() {
		return PlainSelect.getStringList(expressions, true, true);
	}


	
	public void accept(ExpressionVisitor expressionVisitor) {
		
		expressionVisitor.visit(this);
	}
}
