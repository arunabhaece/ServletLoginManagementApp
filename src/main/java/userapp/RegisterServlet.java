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

import db_util.DataBaseManager;

/**
 * Servlet implementation class Register
 */
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public RegisterServlet() {

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String fName = request.getParameter("fname");
		String lName = request.getParameter("lname");
		String email = request.getParameter("email");
		String password = request.getParameter("pwd");
		String rpassword = request.getParameter("rpwd");
		PrintWriter pw = response.getWriter();
		if (!password.equals(rpassword)) {
			pw.write(
					"<!DOCTYPE html>\r\n" + "<html lang=\"en\">\r\n" + "<head>\r\n" + "    <meta charset=\"UTF-8\">\r\n"
							+ "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\r\n"
							+ "    <title>PasswordError</title>\r\n" + "</head>\r\n" + "<body>\r\n"
							+ "    <h1>Password Mismatch!</h1>\r\n"
							+ "    <a href=\"Register.html\">Click to go back to Registration page</a>\r\n"
							+ "</body>\r\n" + "</html>");
		} else {
			Connection con = null;
			try {
				Connection pcon = DataBaseManager.getDBConnection();
				PreparedStatement pps = pcon.prepareStatement("SELECT COUNT(*) FROM USERDETAILS WHERE email = ?");
				pps.setString(1, email);
				pps.execute();
				ResultSet rs = pps.getResultSet();
				rs.next();
				int userCount = rs.getInt(1);
				if (userCount != 0) {
					pw.write("<!DOCTYPE html>\r\n" + "<html lang=\"en\">\r\n" + "<head>\r\n"
							+ "    <meta charset=\"UTF-8\">\r\n"
							+ "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\r\n"
							+ "    <title>Error</title>\r\n" + "</head>\r\n" + "<body>\r\n"
							+ "    <h1>Email already exists! Try with unique email id...</h1>\r\n"
							+ "    <a href=\"Register.html\">Click to go back to Registration page</a>\r\n"
							+ "</body>\r\n" + "</html>");
				} else {
					con = DataBaseManager.getDBConnection();
					PreparedStatement ps = con.prepareStatement(
							"insert into userdetails(firstname, lastname, email, password) values(?,?,?,?)");
					ps.setString(1, fName);
					ps.setString(2, lName);
					ps.setString(3, email);
					ps.setString(4, password);
					ps.execute();
					pw.write("<!DOCTYPE html>\r\n" + "<html lang=\"en\">\r\n" + "<head>\r\n"
							+ "    <meta charset=\"UTF-8\">\r\n"
							+ "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\r\n"
							+ "    <title>Success</title>\r\n" + "</head>\r\n" + "<body>\r\n"
							+ "    <h1>Registration Successfull!</h1>\r\n"
							+ "    <a href=\"manageLogin\">Click to go back to Login page</a><br/>\r\n"
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
			}
		}
	}

}
