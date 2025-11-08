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
import model.EmployeeRole;
import model.Employee;
import model.Role;

public class EmployeeRoleDBContext extends DBContext<EmployeeRole> {

    @Override
    public ArrayList<EmployeeRole> list() {
        ArrayList<EmployeeRole> list = new ArrayList<>();
        String sql = "SELECT ID, EmployeeID, RoleID FROM EmployeeRole";
        try {
            PreparedStatement stm = connection.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                EmployeeRole er = new EmployeeRole();
                er.setId(rs.getLong("ID"));

                Employee emp = new Employee();
                emp.setId(rs.getLong("EmployeeID"));
                er.setEmployee(emp);

                Role role = new Role();
                role.setId(rs.getLong("RoleID"));
                er.setRole(role);

                list.add(er);
            }
        } catch (SQLException ex) {
            Logger.getLogger(EmployeeRoleDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    @Override
    public EmployeeRole get(int id) {
        EmployeeRole er = null;
        String sql = "SELECT ID, EmployeeID, RoleID FROM EmployeeRole WHERE ID = ?";
        try {
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, id);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                er = new EmployeeRole();
                er.setId(rs.getLong("ID"));

                Employee emp = new Employee();
                emp.setId(rs.getLong("EmployeeID"));
                er.setEmployee(emp);

                Role role = new Role();
                role.setId(rs.getLong("RoleID"));
                er.setRole(role);
            }
        } catch (SQLException ex) {
            Logger.getLogger(EmployeeRoleDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return er;
    }

    @Override
    public void insert(EmployeeRole er) {
        String sql = "INSERT INTO EmployeeRole (EmployeeID, RoleID) VALUES (?, ?)";
        try {
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setLong(1, er.getEmployee().getId());
            stm.setLong(2, er.getRole().getId());
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(EmployeeRoleDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void update(EmployeeRole er) {
        String sql = "UPDATE EmployeeRole SET EmployeeID = ?, RoleID = ? WHERE ID = ?";
        try {
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setLong(1, er.getEmployee().getId());
            stm.setLong(2, er.getRole().getId());
            stm.setLong(3, er.getId());
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(EmployeeRoleDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void delete(EmployeeRole er) {
        String sql = "DELETE FROM EmployeeRole WHERE ID = ?";
        try {
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setLong(1, er.getId());
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(EmployeeRoleDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
