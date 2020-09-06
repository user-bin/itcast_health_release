package com.itheima.security;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.pojo.Permission;
import com.itheima.pojo.Role;
import com.itheima.pojo.SysUser;
import com.itheima.service.UserService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.List;

public class SecurityConfigUserService implements UserDetailsService {

    @Reference
    UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // 1. 从数据库查询用户对象
        SysUser sysUser = userService.findByUsername(username);
        if(sysUser == null){
            return null;
        }
//      创建权限列表
        List<GrantedAuthority> authorityList = new ArrayList<>();
//        遍历角色对象
        for (Role role : sysUser.getRoles()) {
            SimpleGrantedAuthority grantedAuthority = new SimpleGrantedAuthority( role.getKeyword());
            authorityList.add(grantedAuthority);
            //遍历该角色对应的权限
            for (Permission permission : role.getPermissions()) {
                SimpleGrantedAuthority grantedAuthority2 = new SimpleGrantedAuthority( permission.getKeyword());
                authorityList.add(grantedAuthority2);
            }
        }
//        2. 包装UserDetails， 返回给安全框架
        User user = new User(sysUser.getUsername(),sysUser.getPassword(),authorityList );
        return user;
    }
}
