package org.example.naverpay.member.controller.nonlogin;

import org.example.naverpay.cookie.CookieMgr;
import org.example.naverpay.member.dto.MembersDTO;
import org.example.naverpay.member.service.MembersService;
import org.example.naverpay.session.SessionMgr;
import org.example.naverpay.util.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/members")
public class LoginController { // 로그인 화면

    private SessionMgr sessionMgr; // = SessionMgr.getInstance();
    private CookieMgr cookieMgr; // = CookieMgr.getInstance();
    private MembersService membersService = MembersService.getInstance();


    @Autowired
    public LoginController(SessionMgr sessionMgr, CookieMgr cookieMgr) {
        this.sessionMgr = sessionMgr;
        this.cookieMgr = cookieMgr;
    }

    @GetMapping("/login")
    public String loginPage(HttpServletRequest request, HttpSession session) {
        String view = "member/nonlogin/login";

        if (session.getAttribute("SESSION_ID") != null) { // 로그인이 되어있는 상태
            return "redirect:/";
        }

        String autoLogin = cookieMgr.get(request, "AUTO_LOGIN");
        String cookieId = cookieMgr.get(request, "COOKIE_ID");


//        if (autoLogin != null && cookieId != null) {
//            if (memberService.autoLogin(autoLogin, cookieId)) {
//                sessionMgr.create(session, cookieId);
//                view = "redirect:/";
//            }
//        }

        return view;
    }

    @PostMapping("/login")
    public String doLogin(@RequestParam String mId,
                          @RequestParam String mPw,
                          @RequestParam(required = false) String save,
                          Model model, HttpServletRequest request,
                          HttpSession session,
                          HttpServletResponse response) {

        String view = loginPage(request, session); // login.jsp
        Status respStatus = Status.FAIL;

        MembersDTO membersDTO = membersService.login(mId, mPw);
        System.out.println(membersDTO);
        if (membersDTO != null) {
            sessionMgr.create(session, mId);

//            saveCookieForAutoLogin(uId, save, response);
            model.addAttribute("mId", session.getAttribute("SESSION_ID"));

            view = "member/login/firstpage";
            respStatus = Status.SUCCESS;
        }

        session.setAttribute("login", respStatus);
        return view;
    }
}