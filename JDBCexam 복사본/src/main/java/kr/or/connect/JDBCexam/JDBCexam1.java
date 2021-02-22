package kr.or.connect.JDBCexam;

import kr.or.connect.JDBCexam.dao.RoleDao;
import kr.or.connect.JDBCexam.dto.Role;

public class JDBCexam1 {

	public static void main(String[] args) {
		RoleDao dao = new RoleDao();
		Role role = dao.getRole(100);
		System.out.println(role);
	}

}
