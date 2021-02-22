package kr.or.connect.JDBCexam.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import kr.or.connect.JDBCexam.dto.Role;

public class RoleDao {
	
	//Timezone, SSL문제 해결 url 추가
	private static String dburl = "jdbc:mysql://localhost:3306/connectdb?serverTimezone=UTC&useSSL=false";
	private static String dbUser = "connectuser";
	private static String dbpasswd = "connect123!@#";

	//INSERT
	public int addRole(Role role) {
		int insertCount = 0;	//add count result return
		
		Connection conn = null;
		PreparedStatement ps = null;
		//insert 구문 이기 때문에 resultset은 필요 없다.
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");	//mysql 제공 class가 메모리에 올라와야 함.
			conn = DriverManager.getConnection(dburl, dbUser, dbpasswd);
			String sql = "INSERT INTO role (role_id, description) VALUES (?, ?)";	//query
			ps = conn.prepareStatement(sql);
			
			ps.setInt(1, role.getRoleId());
			ps.setString(2, role.getDescription());
			
			insertCount = ps.executeUpdate(); //insert, update, delete
			
		}catch (Exception ex) {
			ex.printStackTrace();
		}finally {
			if (ps != null) {
				try {
					ps.close();
				}catch (Exception ex) {}
			}
			if (conn != null) {
				try {
					conn.close();
				}catch (Exception ex) {}
			}
		}
		return insertCount;
	}
	
	//UPDATE
	public int updateRole(Role role) {
		int updateCount = 0;
		
		Connection conn = null;
		PreparedStatement ps = null;
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(dburl, dbUser,dbpasswd);
			String sql = "UPDATE role SET description='CEO' WHERE role_id=?";
			ps = conn.prepareStatement(sql);
			
			ps.setInt(1, role.getRoleId());
			
			updateCount = ps.executeUpdate();
			
		}catch (Exception ex) {
			ex.printStackTrace();
		}finally {
			if (ps != null) {
				try {
					ps.close();
				}catch (Exception ex) {}
			}
			if (conn != null) {
				try {
					conn.close();
				}catch (Exception ex) {}
			}
		}
		return updateCount;
	}
	
	//DELETE
	public int deleteRole(Role role) {
		int deleteCount = 0;
		
		Connection conn = null;
		PreparedStatement ps = null;
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(dburl, dbUser, dbpasswd);
			String sql = "DELETE FROM role WHERE role_id=?";
			ps = conn.prepareStatement(sql);
			
			ps.setInt(1, role.getRoleId());
			
			deleteCount = ps.executeUpdate();
			
		}catch (Exception ex) {
			ex.printStackTrace();
		}finally {
			if (ps != null) {
				try {
					ps.close();
				}catch (Exception ex) {}
			}
			if (conn != null) {
				try {
					conn.close();
				}catch (Exception ex) {}
			}
		}
		
		return deleteCount;
	}
	
	public Role getRole(Integer roleId) {
		Role role = null;
		Connection conn = null; //connect object
		PreparedStatement ps = null; //statement object
		ResultSet rs = null; //결과 값 담아낼 객체
		
		
		// 예외 상황 많아서 모두 확인 해줘야 한다.
		try {
			//version 문제 때문에 .cj. 추가
			Class.forName("com.mysql.cj.jdbc.Driver");	//mysql 제공 class가 메모리에 올라와야 함.
			conn = DriverManager.getConnection(dburl, dbUser, dbpasswd);
			String sql = "SELECT description,role_id FROM ROLE WHERE role_id = ?";	//query
			ps = conn.prepareStatement(sql);
			ps.setInt(1, roleId);	//query의 ?를 대체
			rs = ps.executeQuery();
			
			if (rs.next()) {
				Integer id = rs.getInt("role_id");
				String description = rs.getString(1);
				
				role = new Role(id, description);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}finally { //MUST close! 
			if (rs != null) {
				try {
					rs.close();
				}catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (ps != null) {
				try {
					ps.close();
				}catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (conn != null) {
				try {
					conn.close();
				}catch (SQLException e) {
					e.printStackTrace();
				}
			}
			
		}
		
		
		return role;
	}
	
	
}
