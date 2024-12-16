package db;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;

@WebServlet("/addBook")
public class addBookCode extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get form data from the request
        String title = request.getParameter("title");
        String authorID = request.getParameter("authorID");
        String genreID = request.getParameter("genreID");
        String pages = request.getParameter("pages");
        String publishedDate = request.getParameter("publishedDate");

        // Insert data into the database
        try {
            // Database connection setup
        	Class.forName("com.mysql.cj.jdbc.Driver");
        	
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/book_management", "root", "08092002");

            String sql = "INSERT INTO Books (Title, AuthorID, GenreID, Pages, PublishedDate) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);

            // Set parameters
            stmt.setString(1, title);
            stmt.setString(2, authorID);
            stmt.setString(3, genreID);
            stmt.setString(4, pages);
            stmt.setDate(5, Date.valueOf(publishedDate));

            // Execute the query
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                response.getWriter().println("Book added successfully!");
            } else {
                response.getWriter().println("Failed to add the book.");
            }

            // Close the connection
            conn.close();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            response.getWriter().println("Error: " + e.getMessage());
        }
    }
}
