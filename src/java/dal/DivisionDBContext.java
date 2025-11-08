package dal;

import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Division;
import model.Employee;
import java.util.List;

public class DivisionDBContext extends DBContext<Division> {

    @Override
    public ArrayList<Division> list() {
        ArrayList<Division> list = new ArrayList<>();
        String sql = "SELECT ID, name, head_division_id FROM Division";
        try {
            PreparedStatement stm = connection.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Division div = new Division();
                div.setId(rs.getLong("ID"));
                div.setName(rs.getString("name"));

                long headId = rs.getLong("head_division_id");
                if (!rs.wasNull()) {
                    Employee head = new Employee();
                    head.setId(headId);
                    div.setHeadDivision(head);
                }

                list.add(div);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DivisionDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    @Override
    public Division get(int id) {
        Division div = null;
        String sql = "SELECT ID, name, head_division_id FROM Division WHERE ID = ?";
        try {
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, id);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                div = new Division();
                div.setId(rs.getLong("ID"));
                div.setName(rs.getString("name"));

                long headId = rs.getLong("head_division_id");
                if (!rs.wasNull()) {
                    Employee head = new Employee();
                    head.setId(headId);
                    div.setHeadDivision(head);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(DivisionDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return div;
    }

    @Override
    public void insert(Division div) {
        String sql = "INSERT INTO Division (name, head_division_id) VALUES (?, ?)";
        try {
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, div.getName());
            if (div.getHeadDivision() != null) stm.setLong(2, div.getHeadDivision().getId()); 
            else stm.setNull(2, Types.BIGINT);
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DivisionDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void update(Division div) {
        String sql = "UPDATE Division SET name = ?, head_division_id = ? WHERE ID = ?";
        try {
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, div.getName());
            if (div.getHeadDivision() != null) stm.setLong(2, div.getHeadDivision().getId()); 
            else stm.setNull(2, Types.BIGINT);
            stm.setLong(3, div.getId());
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DivisionDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void delete(Division div) {
        String sql = "DELETE FROM Division WHERE ID = ?";
        try {
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setLong(1, div.getId());
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DivisionDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public List<Division> GetListDivisions() {
        List<Division> list = new ArrayList<>();
        try {
            String sql = "SELECT ID, Name FROM Division";
            PreparedStatement stm = connection.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Division d = new Division();
                d.setId(rs.getLong("ID"));
                d.setName(rs.getString("Name"));
                list.add(d);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return list;
    }
}
