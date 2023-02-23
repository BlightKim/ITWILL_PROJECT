package dao;

import vo.User;

public interface Dao {
  User userSelect(String id) throws Exception;
  int delete(String id) throws Exception;
  int insert(User user) throws Exception;
  int update(User user) throws Exception;
}
