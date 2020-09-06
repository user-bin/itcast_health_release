package com.itheima.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/user")
public class UserController {
    @RequestMapping("/add")
    @PreAuthorize("hasAuthority('add')")//表示用户必须拥有add权限才能调用当前方法
    public String add(){
        System.out.println("add...");
        return "success";
    }

    @RequestMapping("/edit")
    @PreAuthorize("hasAuthority('edit')")//表示用户必须拥有edit权限才能调用当前方法
    public String edit(){
        System.out.println("edit...");
        return "success";
    }

    @RequestMapping("/select")
    @PreAuthorize("hasAuthority('select')")//表示用户必须拥有select权限才能调用当前方法
    public String select(){
        System.out.println("select...");
        return "success";
    }

    @RequestMapping("/delete")
    @PreAuthorize("hasAuthority('delete')")//表示用户必须拥有delete权限才能调用当前方法
    public String delete(){
        System.out.println("delete...");
        return "success";
    }
}