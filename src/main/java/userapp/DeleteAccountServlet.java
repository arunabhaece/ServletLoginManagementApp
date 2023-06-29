package userapp;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import db_util.DataBaseManager;

public class DeleteAccountServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public DeleteAccountServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		PrintWriter pw = response.getWriter();
		if (session == null) {
			RequestDispatcher rd = request.getRequestDispatcher("LoginPage.html");
			rd.forward(request, response);
		} else {
			String email = (String) session.getAttribute(SessionStorage.EMAIL);
			Connection con = null;
			try {
				con = DataBaseManager.getDBConnection();
				PreparedStatement ps = con.prepareStatement("DELETE FROM USERDETAILS WHERE email = ?");
				ps.setString(1, email);
				ps.execute();
				session.invalidate();
				pw.write("<!DOCTYPE html>\r\n" + "<html lang=\"en\">\r\n" + "\r\n" + "<head>\r\n"
						+ "    <meta charset=\"UTF-8\">\r\n"
						+ "    <meta name=\"viewport\" content=\"width=device-width,height=device-height,initial-scale=1.0\">\r\n"
						+ "    <title>DeleteAccount</title>\r\n" + "</head>\r\n" + "\r\n" + "<body>\r\n"
						+ "    <div style=\"margin: 10px;padding: 20px;text-align: center;border: 2px solid black;width: fit-content;\">\r\n"
						+ "        <h1>Account Deleted Successfully!</h1>\r\n"
						+ "        <a href=\"HomePage.html\">Click to go back to Home page</a>\r\n" + "    </div>\r\n"
						+ "</body>\r\n" + "\r\n" + "</html>");
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

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
