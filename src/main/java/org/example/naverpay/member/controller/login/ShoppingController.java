package org.example.naverpay.member.controller.login;

import org.example.naverpay.member.dto.ShoppingDTO;
import org.example.naverpay.member.service.ShoppingService;
import org.example.naverpay.session.SessionMgr;
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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

@Controller
@RequestMapping("/members")
public class ShoppingController {

    private SessionMgr sessionMgr = SessionMgr.getInstance();

    private ShoppingService shoppingService = ShoppingService.getInstance();

    @Autowired
    public ShoppingController(SessionMgr sessionMgr){
        this.sessionMgr = sessionMgr;
    }

    @GetMapping(value = "/shopping")
    public String shoppingPage(Locale locale, Model model, HttpServletRequest request, HttpSession session) {

        if (session.getAttribute("SESSION_ID") == null) {
            return "redirect:/";
        }
        if (session.getAttribute("SESSION_ID") != null) {
            model.addAttribute("mId", sessionMgr.get(session));
        }
            String mId = session.getAttribute("SESSION_ID").toString();
            String period = getDefaltDate();
            List<ShoppingDTO> shoppingListDTO = shoppingService.getShoppingList(mId, period);
            model.addAttribute("shoppingList", shoppingListDTO);
        return "/member/login/shopping";
    }

    @PostMapping("/shopping")
    public String searchShoppingPage(@RequestParam String period,
                                     Model model, HttpServletRequest request,
                                     HttpSession session,
                                     HttpServletResponse response){

        if (session.getAttribute("SESSION_ID") == null) {
            return "redirect:/";
        }
        String mId = session.getAttribute("SESSION_ID").toString();
        List<ShoppingDTO> shoppingDTOList = shoppingService.getShoppingList(mId,period);
        model.addAttribute("shoppingList",shoppingDTOList);

        return "/member/login/shopping";
    }
    public String getDefaltDate() {
        LocalDate now = LocalDate.now().minusYears(2);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");
        String defaltDate = now.format(formatter);
        return defaltDate;
    }
}
