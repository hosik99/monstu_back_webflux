package com.icetea.monstu_back.controller;

import com.icetea.monstu_back.enums.Role;
import com.icetea.monstu_back.enums.Status;
import com.icetea.monstu_back.model.Members;
import com.icetea.monstu_back.service.MembersService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/members")
public class MembersController {

    private final MembersService membersService;

    public MembersController(MembersService membersService) {
        this.membersService = membersService;
    }

    @GetMapping("/m")
    public Members getMembers() {
        String nullValue = null;
        nullValue.length();

        return new Members(1L,"name","password", Status.ACTIVE, Role.GUEST, LocalDateTime.now(),LocalDateTime.now());
    }
}
