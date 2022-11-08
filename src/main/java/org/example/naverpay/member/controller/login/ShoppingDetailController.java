package org.example.naverpay.member.controller.login;

import org.example.naverpay.member.dto.PaymentDTO;
import org.example.naverpay.member.dto.ShoppingDTO;
import org.example.naverpay.member.service.PaymentService;
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
import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

@Controller
@RequestMapping("/members")
public class ShoppingDetailController {

    private SessionMgr sessionMgr = SessionMgr.getInstance();

    private PaymentService paymentService = PaymentService.getInstance();
    private ShoppingService shoppingService = ShoppingService.getInstance();

    @Autowired
    public ShoppingDetailController(SessionMgr sessionMgr){ this.sessionMgr = sessionMgr; }


    @GetMapping(value = "/shoppingDetail") // 결제 내역 화면 접근
    public String shoppingPage(Locale locale, Model model, HttpServletRequest request, HttpSession session,
    @RequestParam("sId") String sId) {


        if (session.getAttribute("SESSION_ID") != null) {
            model.addAttribute("mId", sessionMgr.get(session));
        }
        model.addAttribute("sId",sId);

        PaymentDTO paymentDTO = paymentService.getPaymentInfo(sId);
        model.addAttribute("paymentDTO",paymentDTO);
        ShoppingDTO shoppingDTO = shoppingService.getShoppingInfo(sId);
        model.addAttribute("shoppingDTO",shoppingDTO);

        return "/member/login/shoppingDetail";
    }
    @PostMapping(value = "/shoppingDetail")
    public String deleteShoppingList(Locale locale, Model model, HttpServletRequest request, HttpSession session,
                                     @RequestParam("sId") String sId) {

        if (session.getAttribute("SESSION_ID") == null) {
            return "redirect:/";
        }

        shoppingService.deleteShoppingList(sId);
        String mId = session.getAttribute("SESSION_ID").toString();
        String startDate = getStartDate();
        List<ShoppingDTO> shoppingDTOList = shoppingService.getShoppingList(mId,startDate);
        model.addAttribute("shoppingList",shoppingDTOList);

        return "/member/login/shopping";
    }

    public String getCurrentDate() {

        LocalDate now = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");
        String currentDate = now.format(formatter);

        return currentDate;
    }

    public String getStartDate(){

        LocalDate now = LocalDate.now().minusYears(2);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");
        String startDate = now.format(formatter);

        return startDate;
    }
}
