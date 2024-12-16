package db;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/viewBooks")
public class fetch extends HttpServlet {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/book_management";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "08092002";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        try {
            // Load the MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
                String sql = "SELECT b.BookID, b.Title, a.Name AS Author, g.Name AS Genre, b.Pages, b.PublishedDate " +
                             "FROM books b " +
                             "JOIN authors a ON b.AuthorID = a.AuthorID " +
                             "JOIN genres g ON b.GenreID = g.GenreID";
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery();

                // Generate table
                out.println("<html>");
                out.println("<head><title>View Books</title></head>");
                out.println("<body>");
                out.println("<h1 style='text-align:center;'>Book List</h1>");
                out.println("<table border='1' style='width:80%; margin:auto; text-align:center;'>");
                out.println("<tr>");
                out.println("<th>Book ID</th>");
                out.println("<th>Title</th>");
                out.println("<th>Author</th>");
                out.println("<th>Genre</th>");
                out.println("<th>Pages</th>");
                out.println("<th>Published Date</th>");
                out.println("</tr>");

                // Populate table rows
                while (rs.next()) {
                    out.println("<tr>");
                    out.println("<td>" + rs.getInt("BookID") + "</td>");
                    out.println("<td>" + rs.getString("Title") + "</td>");
                    out.println("<td>" + rs.getString("Author") + "</td>");
                    out.println("<td>" + rs.getString("Genre") + "</td>");
                    out.println("<td>" + rs.getInt("Pages") + "</td>");
                    out.println("<td>" + rs.getDate("PublishedDate") + "</td>");
                    out.println("</tr>");
                }

                out.println("</table>");
                out.println("</body>");
                out.println("</html>");
            }
        } catch (Exception e) {
            e.printStackTrace();
            out.println("<h3>Error: " + e.getMessage() + "</h3>");
        }
    }
}
