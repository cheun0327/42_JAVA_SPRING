package kr.or.connect.JDBCexam;

import java.util.List;

import kr.or.connect.JDBCexam.dao.RoleDao;
import kr.or.connect.JDBCexam.dto.Role;

public class JDBCexam3 {

	public static void main(String[] args) {
		RoleDao dao = new RoleDao();
		
		List<Role> list = dao.getRoles();
		
		for(Role role : list) {
			System.out.println(role);
		}

	}

}
