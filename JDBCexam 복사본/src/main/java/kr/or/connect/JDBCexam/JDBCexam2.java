package kr.or.connect.JDBCexam;

import kr.or.connect.JDBCexam.dao.RoleDao;
import kr.or.connect.JDBCexam.dto.Role;

public class JDBCexam2 {

	public static void main(String[] args) {
		int roleId = 500;
		String description = "CTO";
		
		Role role = new Role(roleId, description);
		
		RoleDao dao = new RoleDao();
		//int insertCount = dao.addRole(role);
		
		//System.out.println("insertCount : "+insertCount);
		
		int updateCount = dao.updateRole(role);
		System.out.println("updateCount : "+updateCount);
		
		int deleteCount = dao.deleteRole(role);
		System.out.println("deleteCount : "+deleteCount);
	}

}
