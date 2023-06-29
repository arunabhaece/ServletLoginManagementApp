package userapp;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import db_util.DataBaseManager;

public class ResetServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ResetServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		if (session == null) {
			RequestDispatcher rd = request.getRequestDispatcher("LoginPage.html");
			rd.forward(request, response);
		} else {
			String newPassword = request.getParameter("npass");
			String rNewPassword = request.getParameter("rnpass");
			String email = (String) session.getAttribute(SessionStorage.EMAIL);
			String firstName = (String) session.getAttribute(SessionStorage.FIRSTNAME);
			String lastName = (String) session.getAttribute(SessionStorage.LASTNAME);
			PrintWriter pw = response.getWriter();
			if (!newPassword.equals(rNewPassword)) {
				pw.write("<!DOCTYPE html>\r\n" + "<html lang=\"en\">\r\n" + "\r\n" + "<head>\r\n"
						+ "    <meta charset=\"UTF-8\">\r\n"
						+ "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\r\n"
						+ "    <title>Login</title>\r\n" + "</head>\r\n" + "\r\n" + "<body>\r\n"
						+ "    <div style=\"width: min-content; margin: 10px; padding: 20px; border: 2px black solid;\">\r\n"
						+ "<h1>Password Mismatch! Please try again...</h1><br/>" + "        <h1>Hi " + firstName + " "
						+ lastName + "!</h1>\r\n" + "        <h2>Reset Password!</h2>\r\n"
						+ "        <form action=\"reset\" method=\"post\">\r\n"
						+ "            <label><b>Enter New Password:</b></label>\r\n"
						+ "            <input type=\"password\" name=\"npass\" />\r\n" + "            <br /><br />\r\n"
						+ "            <label><b>Repeat New Password:</b></label>\r\n"
						+ "            <input type=\"password\" name=\"rnpass\" />\r\n" + "            <br /><br />\r\n"
						+ "            <input type=\"submit\" />\r\n" + "            <br/>\r\n" + "        </form>\r\n"
						+ "    </div>\r\n" + "</body>\r\n" + "</html>");
			} else {
				Connection con = null, con1 = null;
				try {
					con = DataBaseManager.getDBConnection();
					PreparedStatement ps = con.prepareStatement("SELECT * FROM USERDETAILS WHERE EMAIL=?");
					ps.setString(1, email);
					ps.execute();
					ResultSet rs = ps.getResultSet();
					rs.next();
					String epassword = rs.getString("password");
					if (epassword.equals(newPassword)) {
						pw.write("<!DOCTYPE html>\r\n" + "<html lang=\"en\">\r\n" + "\r\n" + "<head>\r\n"
								+ "    <meta charset=\"UTF-8\">\r\n"
								+ "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\r\n"
								+ "    <title>Login</title>\r\n" + "</head>\r\n" + "\r\n" + "<body>\r\n"
								+ "    <div style=\"width: min-content; margin: 10px; padding: 20px; border: 2px black solid;\">\r\n"
								+ "<h1>Using the existing password! Please try again with new password...</h1><br/>"
								+ "        <h1>Hi " + firstName + " " + lastName + "!</h1>\r\n"
								+ "        <h2>Reset Password!</h2>\r\n"
								+ "        <form action=\"reset\" method=\"post\">\r\n"
								+ "            <label><b>Enter New Password:</b></label>\r\n"
								+ "            <input type=\"password\" name=\"npass\" />\r\n"
								+ "            <br /><br />\r\n"
								+ "            <label><b>Repeat New Password:</b></label>\r\n"
								+ "            <input type=\"password\" name=\"rnpass\" />\r\n"
								+ "            <br /><br />\r\n" + "            <input type=\"submit\" />\r\n"
								+ "            <br/>\r\n" + "        </form>\r\n" + "    </div>\r\n" + "</body>\r\n"
								+ "</html>");
					} else {
						con1 = DataBaseManager.getDBConnection();
						PreparedStatement ps1 = con1
								.prepareStatement("UPDATE userdetails SET password = ? WHERE email =?");
						ps1.setString(1, newPassword);
						ps1.setString(2, email);
						ps1.execute();
						session.invalidate();
						pw.write("<!DOCTYPE html>\r\n" + "<html lang=\"en\">\r\n" + "<head>\r\n"
								+ "    <meta charset=\"UTF-8\">\r\n"
								+ "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\r\n"
								+ "    <title>Success</title>\r\n" + "</head>\r\n" + "<body>\r\n"
								+ "    <h1>Password Reset Successfull!</h1>\r\n"
								+ "    <a href=\"LoginPage.html\">Click to go back to Login page</a><br/>\r\n"
								+ "    <a href=\"HomePage.html\">Click to go back to Homepage</a>\r\n" + "</body>\r\n"
								+ "</html>");
					}
				} catch (Exception e) {
					request.setAttribute(RequestStorage.ERROR_TEXT, e.getMessage());
					RequestDispatcher rd = request.getRequestDispatcher("/error");
					rd.forward(request, response);
					e.printStackTrace();
				} finally {
					try {
						DataBaseManager.closeDBConnection(con);
					} catch (Exception e) {
						e.printStackTrace();
					}
					try {
						DataBaseManager.closeDBConnection(con1);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
