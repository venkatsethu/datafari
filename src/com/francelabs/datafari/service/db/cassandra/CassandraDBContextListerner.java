package com.francelabs.datafari.service.db.cassandra;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.apache.log4j.Logger;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
import com.francelabs.datafari.service.db.MongoDBContextListerner;
import com.francelabs.datafari.utils.ScriptConfiguration;
import com.mongodb.MongoClient;

/**
 * Application Lifecycle Listener implementation class CassandraDBContextListerner
 *
 */
@WebListener
public class CassandraDBContextListerner implements ServletContextListener {


	private final static Logger LOGGER = Logger
			.getLogger(MongoDBContextListerner.class.getName());

	private static final String KEYSPACE = "datafari";

	private static Cluster cluster;
	private static Session session;
	
	public static Session getSession(){
		while  (session == null){
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				LOGGER.error("Unknow error");
			}
		}
		return session;
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		cluster.close();
		LOGGER.info("Cassandra closed successfully");
	}
	

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		try {
			// Gets the address of the host
			String host = ScriptConfiguration.getProperty("HOST");

			// Connect to the cluster and keyspace "demo"
			cluster = Cluster.builder().addContactPoint(host).build();
			session = cluster.connect(KEYSPACE);
			LOGGER.info("Cassandra client initialized successfully");
		} catch (Exception e) {
			LOGGER.error("Error initializing Cassandra client", e);
		}
	}
	
}