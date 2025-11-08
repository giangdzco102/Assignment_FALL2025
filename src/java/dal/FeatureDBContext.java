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
import model.Feature;

public class FeatureDBContext extends DBContext<Feature> {

    @Override
    public ArrayList<Feature> list() {
        ArrayList<Feature> list = new ArrayList<>();
        String sql = "SELECT ID, URL FROM Feature";
        try {
            PreparedStatement stm = connection.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Feature f = new Feature();
                f.setId(rs.getLong("ID"));
                f.setUrl(rs.getString("URL"));
                list.add(f);
            }
        } catch (SQLException ex) {
            Logger.getLogger(FeatureDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    @Override
    public Feature get(int id) {
        Feature f = null;
        String sql = "SELECT ID, URL FROM Feature WHERE ID = ?";
        try {
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, id);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                f = new Feature();
                f.setId(rs.getLong("ID"));
                f.setUrl(rs.getString("URL"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(FeatureDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return f;
    }

    @Override
    public void insert(Feature f) {
        String sql = "INSERT INTO Feature (URL) VALUES (?)";
        try {
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, f.getUrl());
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(FeatureDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void update(Feature f) {
        String sql = "UPDATE Feature SET URL = ? WHERE ID = ?";
        try {
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, f.getUrl());
            stm.setLong(2, f.getId());
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(FeatureDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void delete(Feature f) {
        String sql = "DELETE FROM Feature WHERE ID = ?";
        try {
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setLong(1, f.getId());
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(FeatureDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

