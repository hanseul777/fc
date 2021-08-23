package org.zerock.fc.controller.sub;

import org.zerock.fc.annotations.Controller;
import org.zerock.fc.annotations.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller(value = "/member")
public class MemberController {


    @GetMapping("/member/signup.do")
    public String singup(HttpServletRequest request, HttpServletResponse response){
        System.out.println("member signup.............");
        System.out.println("member signup.............");
        System.out.println("member signup.............");
        System.out.println("member signup.............");
        System.out.println("member signup.............");

        return null;
    }
}