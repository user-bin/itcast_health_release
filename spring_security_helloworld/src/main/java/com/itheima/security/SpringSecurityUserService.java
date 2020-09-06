package com.itheima.security;

import com.itheima.pojo.Permission;
import com.itheima.pojo.Role;
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
        //创建角色
        Role adminRole = new Role();
        adminRole.setName("管理员");
        adminRole.setKeyword("ROLE_ADMIN");
        //创建权限
        Permission addPermission = new Permission();
        addPermission.setName("添加");
        addPermission.setKeyword("add");
        //创建权限
        Permission editPermission = new Permission();
        editPermission.setName("修改");
        editPermission.setKeyword("edit");
        Permission selectPermission = new Permission();
        selectPermission.setName("查询");
        selectPermission.setKeyword("select");
        //把权限添加到角色中
        adminRole.getPermissions().add(addPermission);
        adminRole.getPermissions().add(editPermission);
        adminRole.getPermissions().add(selectPermission);
        //把角色赋值给 用户
        user1.getRoles().add(adminRole);

        SysUser user2 = new SysUser();
        user2.setUsername("xiaoming");
        user2.setPassword(passwordEncoder.encode("1234"));
        //创建角色
        Role userRole = new Role();
        userRole.setName("用户");
        userRole.setKeyword("ROLE_USER");
        //添加权限
        userRole.getPermissions().add(selectPermission);
        //添加角色
        user2.getRoles().add(userRole);

        map.put(user1.getUsername(), user1);
        map.put(user2.getUsername(), user2);
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
        for (Role role : userInDb.getRoles()) {
            SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role.getKeyword());
            list.add(authority);
            for (Permission permission : role.getPermissions()) {
                SimpleGrantedAuthority authority1 = new SimpleGrantedAuthority(permission.getKeyword());
                list.add(authority1);
            }
        }

        UserDetails user = new User(userInDb.getUsername(),userInDb.getPassword(),list);
        return user;
    }
}