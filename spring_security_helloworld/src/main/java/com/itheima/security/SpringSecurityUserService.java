package com.itheima.security;

import com.itheima.pojo.SysUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SpringSecurityUserService implements UserDetailsService {

    //模拟数据库中的用户数据
    public  static Map<String, SysUser> map = new HashMap<>();
    static {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        SysUser user1 = new SysUser();
        user1.setUsername("admin");
        user1.setPassword(passwordEncoder.encode("1234"));

        SysUser user2 = new SysUser();
        user2.setUsername("xiaoming");
        user2.setPassword(passwordEncoder.encode("1234"));

        map.put(user1.getUsername(),user1);
        map.put(user2.getUsername(),user2);
    }
    /**
     * 根据用户名加载用户信息
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUser userInDb = map.get(username);//模拟根据用户名查询数据库
        if(userInDb == null){
            //根据用户名没有查询到用户
            return null;
        }
        //模拟数据库中的密码，后期需要查询数据库
        List<GrantedAuthority> list = new ArrayList<>();
        //授权，后期需要改为查询数据库动态获得用户拥有的权限和角色
        list.add(new SimpleGrantedAuthority("ROLE_ADMIN"));

        UserDetails user = new User(userInDb.getUsername(),userInDb.getPassword(),list);
        return user;
    }
}