package com.francelabs.datafari.servlets.admin;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;

import com.francelabs.datafari.constants.CodesReturned;
import com.francelabs.realm.MongoDBRunning;
import com.francelabs.realm.User;

/**
 * Servlet implementation class getAllUsersAndRoles
 */
@WebServlet("/SearchAdministrator/getAllUsersAndRoles")
public class getAllUsersAndRoles extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(getAllUsersAndRoles.class);
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public getAllUsersAndRoles() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		MongoDBRunning mongoDBRunning = new MongoDBRunning(User.IDENTIFIERSDB);
		JSONObject jsonResponse = new JSONObject();
		request.setCharacterEncoding("utf8");
		response.setContentType("application/json");
		try {
			if (mongoDBRunning.isConnected()){
				User.getAllUsers(mongoDBRunning.getDb());
				jsonResponse.put("code",CodesReturned.ALLOK).put("statut",User.getAllUsers(mongoDBRunning.getDb()));
			}else{
				jsonResponse.put("code",CodesReturned.PROBLEMCONNECTIONMONGODB).put("statut","Datafari isn't connected to MongoDB");
			}
		}catch (JSONException e) {
				logger.error(e);
		}
		PrintWriter out = response.getWriter();
		out.print(jsonResponse);
	}

}