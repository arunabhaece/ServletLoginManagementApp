package userapp;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import db_util.DataBaseManager;

public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public LoginServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher rd = request.getRequestDispatcher("/manageLogin");
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		if (session == null) {
			session = request.getSession(true);
		}
		String userEmail = (String) request.getParameter("email");
		String password = request.getParameter("password");
		Connection con = null;
		PrintWriter pw = response.getWriter();
		String firstName = null, lastName = null;
		try {
			con = DataBaseManager.getDBConnection();
			PreparedStatement ps = con.prepareStatement("SELECT * FROM USERDETAILS WHERE EMAIL = ?");
			ps.setString(1, userEmail);
			ps.execute();
			ResultSet rs = ps.getResultSet();
			int resSize = 0;
			String pwd = null;
			if (rs.next()) {
				resSize++;
				pwd = rs.getString("password");
				firstName = rs.getString("firstname");
				lastName = rs.getString("lastname");
			}
			if (resSize == 0) {
				pw.write("<!DOCTYPE html>\r\n" + "<html lang=\"en\">\r\n" + "<head>\r\n"
						+ "    <meta charset=\"UTF-8\">\r\n"
						+ "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\r\n"
						+ "    <title>Error</title>\r\n" + "</head>\r\n" + "<body>\r\n"
						+ "    <h1>Email ID does not exist!</h1>\r\n"
						+ "    <a href=\"register.html\">Click to go back to Registration page</a><br/>\r\n"
						+ "    <a href=\"homepage.html\">Click to go back to Homepage</a><br/>\r\n"
						+ "    <a href=\"login.html\">Click to go back to Login page</a>\r\n" + "</body>\r\n"
						+ "</html>");
			} else {
				if (password.equals(pwd)) {
					String cookieText = session.getId() + "Login" + userEmail;
					Cookie cookie = new Cookie("loginValidation", cookieText);
					response.addCookie(cookie);
					session.setAttribute(SessionStorage.COOKIE_TEXT, cookieText);
					session.setAttribute(SessionStorage.EMAIL, userEmail);
					session.setAttribute(SessionStorage.FIRSTNAME, firstName);
					session.setAttribute(SessionStorage.LASTNAME, lastName);
					RequestDispatcher rd = request.getRequestDispatcher("/home");
					rd.forward(request, response);
				} else {
					pw.write("<!DOCTYPE html>\r\n" + "<html lang=\"en\">\r\n" + "<head>\r\n"
							+ "    <meta charset=\"UTF-8\">\r\n"
							+ "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\r\n"
							+ "    <title>Error</title>\r\n" + "</head>\r\n" + "<body>\r\n"
							+ "    <h1>Wrong password!</h1>\r\n"
							+ "    <a href=\"LoginPage.html\">Click to go back to Login page</a>\r\n" + "</body>\r\n"
							+ "</html>");
				}
			}
		} catch (Exception e) {
			request.setAttribute(RequestStorage.ERROR_TEXT, e.getMessage());
			RequestDispatcher rd = request.getRequestDispatcher("/error");
			rd.forward(request, response);
		} finally {
			try {
				DataBaseManager.closeDBConnection(con);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
