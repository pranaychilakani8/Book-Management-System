package db;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/deleteBook")
public class deleteBookCode extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private static final String DB_URL = "jdbc:mysql://localhost:3306/book_management";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "08092002";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String bookIDStr = request.getParameter("bookID");

        if (bookIDStr == null || bookIDStr.isEmpty()) {
            response.getWriter().println("<h1>Error: Book ID is required.</h1>");
            return;
        }

        try {
        	
        	Class.forName("com.mysql.cj.jdbc.Driver");
        	
            int bookID = Integer.parseInt(bookIDStr); // Convert BookID to integer

            try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
                String sql = "DELETE FROM books WHERE BookID = ?";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setInt(1, bookID);

                int rowsAffected = stmt.executeUpdate();
                if (rowsAffected > 0) {
                    response.getWriter().println("<h1>Book deleted successfully.</h1>");
                } else {
                    response.getWriter().println("<h1>Error: No book found with the given ID.</h1>");
                }
            } catch (Exception e) {
                e.printStackTrace();
                response.getWriter().println("<h1>Error: " + e.getMessage() + "</h1>");
            }
        } catch (NumberFormatException | ClassNotFoundException e) {
            response.getWriter().println("<h1>Error: Invalid Book ID format.</h1>");
        }
    }
}
