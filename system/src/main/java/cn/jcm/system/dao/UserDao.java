package cn.jcm.system.dao;

import cn.jcm.system.domain.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserDao {
	@Select( "select * from user where id = #{id}" )
	@Options( useCache = true, flushCache = Options.FlushCachePolicy.FALSE, timeout = 10000 )
	User findUserById( @Param( "id" ) String id );

	@Select( "select * from user" )
	@Options( useCache = true, flushCache = Options.FlushCachePolicy.FALSE, timeout = 10000 )
	List<User> findAllUser();

	@Insert( "insert into user(userName, age, sex) values(#{userName}, #{age}, #{sex})" )
	@Options( flushCache = Options.FlushCachePolicy.TRUE, timeout = 20000, useGeneratedKeys = true )
	int saveUser( @Param( "user" ) User user );

	@Update( "update user set userName=#{userName}, age=#{age}, sex=#{sex} where id=#{id}" )
	@Options( flushCache = Options.FlushCachePolicy.TRUE, timeout = 20000 )
	int updateUser( @Param( "user" ) User user );

	@Delete( "delete from user where id = #{id}" )
	@Options( flushCache = Options.FlushCachePolicy.TRUE, timeout = 20000 )
	int deleteUser( @Param( "id" ) String id );
}
