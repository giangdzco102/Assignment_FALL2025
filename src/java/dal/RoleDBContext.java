/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

/**
 *
 * @author ASUS
 */

import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Role;
import java.util.List;

public class RoleDBContext extends DBContext<Role> {

    @Override
    public ArrayList<Role> list() {
        ArrayList<Role> list = new ArrayList<>();
        String sql = "SELECT ID, name FROM Role";
        try {
            PreparedStatement stm = connection.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Role r = new Role();
                r.setId(rs.getLong("ID"));
                r.setName(rs.getString("name"));
                list.add(r);
            }
        } catch (SQLException ex) {
            Logger.getLogger(RoleDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    @Override
    public Role get(int id) {
        Role r = null;
        String sql = "SELECT ID, name FROM Role WHERE ID = ?";
        try {
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, id);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                r = new Role();
                r.setId(rs.getLong("ID"));
                r.setName(rs.getString("name"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(RoleDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return r;
    }

    @Override
    public void insert(Role r) {
        String sql = "INSERT INTO Role (name) VALUES (?)";
        try {
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, r.getName());
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(RoleDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void update(Role r) {
        String sql = "UPDATE Role SET name = ? WHERE ID = ?";
        try {
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, r.getName());
            stm.setLong(2, r.getId());
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(RoleDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void delete(Role r) {
        String sql = "DELETE FROM Role WHERE ID = ?";
        try {
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setLong(1, r.getId());
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(RoleDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public List<Role> GetListRole() {
        List<Role> roles = new ArrayList<>();
        String sql = "SELECT ID, name FROM Role";
        try (PreparedStatement stm = connection.prepareStatement(sql);
             ResultSet rs = stm.executeQuery()) {

            while (rs.next()) {
                Role r = new Role();
                r.setId(rs.getLong("ID"));
                r.setName(rs.getString("name"));
                roles.add(r);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return roles;
    }
}

