package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

public class Util {

    private final static String DB_HOST = "localhost";
    private final static String DB_PORT = "3306";
    private final static String DB_USER = "root";
    private final static String DB_PASS = "root";
    private final static String DB_NAME = "my_db_test";
    private static SessionFactory sessionFactory;
    static String connections = "jdbc:mysql://" + DB_HOST + ":" + DB_PORT + "/" + DB_NAME;
    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(connections, DB_USER, DB_PASS);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
// jdbc:mysql://localhost:3306

    //+ "?useSSL=false"
    public static SessionFactory getSessionFactory() {

        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration();
                Properties settings = new Properties();
                settings.put(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");
                settings.put(Environment.URL, connections + "?useSSL=false");
                settings.put(Environment.USER, DB_USER);
                settings.put(Environment.PASS, DB_PASS);
                settings.put(Environment.DIALECT, "org.hibernate.dialect.MySQLDialect");
                settings.put(Environment.SHOW_SQL, "true");
                settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
                settings.put(Environment.HBM2DDL_AUTO, "");
                configuration.setProperties(settings).addAnnotatedClass(User.class);
                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties()).build();
                sessionFactory = configuration.buildSessionFactory(serviceRegistry);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return sessionFactory;
    }



}

