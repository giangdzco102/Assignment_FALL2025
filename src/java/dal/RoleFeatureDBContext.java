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
import model.RoleFeature;
import model.Role;
import model.Feature;

public class RoleFeatureDBContext extends DBContext<RoleFeature> {

    private RoleDBContext roleDB = new RoleDBContext();
    private FeatureDBContext featureDB = new FeatureDBContext();

    @Override
    public ArrayList<RoleFeature> list() {
        ArrayList<RoleFeature> list = new ArrayList<>();
        String sql = "SELECT ID, RoleID, FeatureID FROM RoleFeature";
        try {
            PreparedStatement stm = connection.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                RoleFeature rf = new RoleFeature();
                rf.setId(rs.getLong("ID"));

                Role role = new Role();
                role.setId(rs.getLong("RoleID"));
                rf.setRole(role);

                Feature feature = new Feature();
                feature.setId(rs.getLong("FeatureID"));
                rf.setFeature(feature);

                list.add(rf);
            }
        } catch (SQLException ex) {
            Logger.getLogger(RoleFeatureDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    @Override
    public RoleFeature get(int id) {
        RoleFeature rf = null;
        String sql = "SELECT ID, RoleID, FeatureID FROM RoleFeature WHERE ID = ?";
        try {
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, id);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                rf = new RoleFeature();
                rf.setId(rs.getLong("ID"));

                Role role = new Role();
                role.setId(rs.getLong("RoleID"));
                rf.setRole(role);

                Feature feature = new Feature();
                feature.setId(rs.getLong("FeatureID"));
                rf.setFeature(feature);
            }
        } catch (SQLException ex) {
            Logger.getLogger(RoleFeatureDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rf;
    }

    @Override
    public void insert(RoleFeature rf) {
        String sql = "INSERT INTO RoleFeature (RoleID, FeatureID) VALUES (?, ?)";
        try {
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setLong(1, rf.getRole().getId());
            stm.setLong(2, rf.getFeature().getId());
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(RoleFeatureDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void update(RoleFeature rf) {
        String sql = "UPDATE RoleFeature SET RoleID = ?, FeatureID = ? WHERE ID = ?";
        try {
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setLong(1, rf.getRole().getId());
            stm.setLong(2, rf.getFeature().getId());
            stm.setLong(3, rf.getId());
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(RoleFeatureDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void delete(RoleFeature rf) {
        String sql = "DELETE FROM RoleFeature WHERE ID = ?";
        try {
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setLong(1, rf.getId());
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(RoleFeatureDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

