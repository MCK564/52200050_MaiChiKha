package com.java.midtermShopWeb.controllers;

import com.java.midtermShopWeb.services.role.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.prefix}/roles")
@RequiredArgsConstructor
public class RoleController {
    private final RoleService roleService;
    @GetMapping()
    public ResponseEntity<?> getAllRoles(){
        try{
            return ResponseEntity.ok().body(roleService);
        }catch(Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping()
    public ResponseEntity<?> createRole(
            @RequestBody String name
    ){
        return ResponseEntity.ok("haha");
    }

}
