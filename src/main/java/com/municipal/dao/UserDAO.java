package com.municipal.dao;

import com.municipal.model.User;
import com.municipal.controller.util.DB;
import java.sql.*;

public class UserDAO {
    public static User login(String username, String password) {
        try (Connection conn = DB.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT * FROM users WHERE username = ? AND password = ?")) {
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                User u = new User();
                u.setUsername(rs.getString("username"));
                u.setPassword(rs.getString("password"));
                u.setRole(rs.getString("role"));
                return u;
                
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return null;
    }
}
