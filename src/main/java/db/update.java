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

@WebServlet("/updateBookCode")
public class update extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String bookID = request.getParameter("bookID");
        String title = request.getParameter("title");
        String authorID = request.getParameter("authorID");
        String genreID = request.getParameter("genreID");
        String pages = request.getParameter("pages");
        String publishedDate = request.getParameter("publishedDate");

        try {
            // Database connection
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/book_management", "root", "08092002");

            // Update query
            String sql = "UPDATE books SET Title = ?, AuthorID = ?, GenreID = ?, Pages = ?, PublishedDate = ? WHERE BookID = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, title);
            stmt.setString(2,authorID);
            stmt.setString(3,genreID);
            stmt.setString(4, pages);
            stmt.setString(5, publishedDate);
            stmt.setString(6,bookID);

            // Execute update
            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                response.getWriter().println("Book updated successfully!");
            } else {
                response.getWriter().println("Error: Book update failed.");
            }

            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Error: " + e.getMessage());
        }
    }
}
