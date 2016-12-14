package com.logistic.dao.mysql;

import com.logistic.dao.exceptions.InternalDAOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by Vojts on 13.11.2016.
 */
public class MySQLDAOConnection {

    private static volatile MySQLDAOConnection instance;

    private ConnectionPool connectionPool;

    private final String MYSQL_CONNECTOR_CLASS = "com.mysql.jdbc.Driver";

    private static final String URL = "jdbc:mysql://localhost:3306/logistic";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";
    /*private static final String URL = "jdbc:mysql://localhost:3306/myTestDB";
    private static final String USERNAME = "debian-sys-maint";
    private static final String PASSWORD = "2H3Oigcnv3wczD7y";*/



    private Logger logger;

    /**
     * @throws InternalDAOException
     */
    private MySQLDAOConnection() throws InternalDAOException {
        logger = LoggerFactory.getLogger("com.logistic.dao.mysql.MySQLDAOConnection");
        try {
            Class.forName(MYSQL_CONNECTOR_CLASS);
            connectionPool = new ConnectionPool(URL, USERNAME, PASSWORD);
            connectionPool.setCleaningInterval(20*1000);
            logger.trace("ConnectionPool create");
        } catch (ClassNotFoundException e) {
            logger.error("Driver for database failed");
            throw new InternalDAOException("Driver for database failed", e);
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
            return connectionPool.getConnection();
        } catch (SQLException e) {
            logger.error("Get connection failed", e);
            throw new InternalDAOException("Get connection failed", e);
        }
    }

}