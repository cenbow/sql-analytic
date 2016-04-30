package com.github.sql.analytic.odata.web.test;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.Properties;
import java.util.logging.Logger;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.sql.DataSource;

import com.github.sql.analytic.odata.testdata.Loader;
import com.github.sql.analytic.odata.web.SQLODataServlet;

public class TestSQLODataServlet extends SQLODataServlet {

	
	private static final long serialVersionUID = 1L;

	static final String url = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1";

	

	@Override
	public void init(ServletConfig config) throws ServletException {		
		super.init(config);

		setDatasource(new H2Datasource());
		try{
			try(Connection connection = getDatasource().getConnection()){
				Loader.execute(connection);
			} catch (IOException e) {
				throw new ServletException(e);
			} 
		}catch (SQLException e) {
			throw new ServletException(e);
		}

	}


}

class H2Datasource implements DataSource {
	@Override
	public <T> T unwrap(Class<T> iface) throws SQLException {
		
		return null;
	}

	@Override
	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		
		return false;
	}

	@Override
	public void setLoginTimeout(int seconds) throws SQLException {
		

	}

	@Override
	public void setLogWriter(PrintWriter out) throws SQLException {
		

	}

	@Override
	public Logger getParentLogger() throws SQLFeatureNotSupportedException {
		
		return null;
	}

	@Override
	public int getLoginTimeout() throws SQLException {
		
		return 0;
	}

	@Override
	public PrintWriter getLogWriter() throws SQLException {
		
		return null;
	}

	@Override
	public Connection getConnection(String username, String password) throws SQLException {
		
		return null;
	}

	@Override
	public Connection getConnection() throws SQLException {

		return  new org.h2.Driver().connect(TestSQLODataServlet.url, new Properties());
	}
}


