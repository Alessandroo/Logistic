package com.logistic.dao.mysql;

import com.logistic.dao.exceptions.InternalDAOException;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by Vojts on 13.11.2016.
 */
public class MySQLDAOConnection {

    private static volatile MySQLDAOConnection instance;

    private static DataSource dataSource;

    /**
     * @throws InternalDAOException
     */
    private MySQLDAOConnection() throws InternalDAOException {

        try {
            InitialContext ctx = new InitialContext();
            dataSource = (DataSource) ctx.lookup("jdbc/City");
        } catch (NamingException e) {
            throw new InternalDAOException(e);
        }
    }

    /**
     * @throws InternalDAOException
     */
    public static MySQLDAOConnection getInstance() throws InternalDAOException {
        MySQLDAOConnection localInstance = instance;
        if (localInstance == null) {
            synchronized (MySQLDAOConnection.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new MySQLDAOConnection();
                }
            }
        }
        return localInstance;
    }

    public Connection getConnection() throws InternalDAOException {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            throw new InternalDAOException("Get connection failed", e);
        }
    }
}