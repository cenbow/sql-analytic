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

import com.github.sql.analytic.expression.SQLExpression;
import com.github.sql.analytic.expression.ExpressionVisitor;





public class InExpression implements SQLExpression {
	private SQLExpression leftExpression;
	private ItemsList itemsList; 
	private boolean not = false;
	
	public InExpression() {
	}

	public InExpression(SQLExpression leftExpression, ItemsList itemsList) {
		setLeftExpression(leftExpression);
		setItemsList(itemsList);
	}
	
	public ItemsList getItemsList() {
		return itemsList;
	}

	public SQLExpression getLeftExpression() {
		return leftExpression;
	}

	public void setItemsList(ItemsList list) {
		itemsList = list;
	}

	public void setLeftExpression(SQLExpression expression) {
		leftExpression = expression;
	}

	public boolean isNot() {
		return not;
	}

	public void setNot(boolean b) {
		not = b;
	}

	public void accept(ExpressionVisitor expressionVisitor) {
		expressionVisitor.visit(this);
	}

	public String toString() {
		StringBuilder builder = new StringBuilder(leftExpression.toString());
		builder.append(" ");
		if(not){
			builder.append("NOT ");
		}		 
		builder.append("IN "); 
		builder.append(itemsList);		
		return builder.toString();
		
	}
}
