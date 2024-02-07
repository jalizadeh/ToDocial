package com.jalizadeh.todocial.controller.gym;

import com.jalizadeh.todocial.controller.admin.model.SettingsGeneralConfig;
import com.jalizadeh.todocial.model.gym.GymDay;
import com.jalizadeh.todocial.model.gym.GymPlan;
import com.jalizadeh.todocial.model.gym.GymPlanWeekDay;
import com.jalizadeh.todocial.repository.GymDayRepository;
import com.jalizadeh.todocial.repository.GymPlanRepository;
import com.jalizadeh.todocial.repository.GymPlanWeekDayRepository;
import com.jalizadeh.todocial.service.ShoppingCartService;
import com.jalizadeh.todocial.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/gym/shop")
public class GymShopController {

    @Autowired
    private UserService userService;

    @Autowired
    private SettingsGeneralConfig settings;

    @Autowired
    private GymPlanRepository gymPlanRepository;

    @Autowired
    private GymDayRepository gymDayRepository;

    @Autowired
    private GymPlanWeekDayRepository gymplanWeekDayRepository;

    @Autowired
    private ShoppingCartService shoppingCartService;

    @GetMapping()
    public String showShop(ModelMap model){
        model.put("settings", settings);
        model.put("PageTitle", "Gym Shop");
        model.put("plans", gymPlanRepository.findAllByIsPublicTrueAndIsForSaleTrue());

        return "gym/shop";
    }

    @GetMapping("/product/plan/{id}")
    public String showProduct(@PathVariable Long id, ModelMap model, RedirectAttributes redirectAttributes){
        model.put("settings", settings);
        model.put("PageTitle", "Gym Shop : Product");

        Optional<GymPlan> plan = gymPlanRepository.findByIdAndIsPublicTrueAndIsForSaleTrue(id);
        if (!plan.isPresent()) {
            redirectAttributes.addFlashAttribute("exception", "The requested plan with id " + id + " doesn't exist");
            return "redirect:/error";
        }

        GymPlan foundPlan = plan.get();
        List<GymDay> daysOfPlan = gymDayRepository.findAllByPlanIdOrderByDayNumber(foundPlan.getId());
        List<GymPlanWeekDay> pwd = gymplanWeekDayRepository.findAllByPlanId(foundPlan.getId());

        model.put("plan", foundPlan);
        model.put("days", daysOfPlan);
        model.put("pwd", pwd);

        return "gym/plan-product";
    }

    @GetMapping("/cart")
    public String showShoppingCart(ModelMap model){
        model.put("items", shoppingCartService.getItemsInCart());
        model.put("total", shoppingCartService.getTotal());
        return "gym/cart";
    }

    @GetMapping("/cart/add/{id}")
    public String addToCart(@PathVariable Long id, ModelMap model, RedirectAttributes redirectAttributes){
        Optional<GymPlan> plan = gymPlanRepository.findByIdAndIsPublicTrueAndIsForSaleTrue(id);
        if (!plan.isPresent()) {
            redirectAttributes.addFlashAttribute("exception", "The requested plan with id " + id + " doesn't exist");
            return "redirect:/error";
        }

        GymPlan foundPlan = plan.get();
        shoppingCartService.addItem(foundPlan);
        return "redirect:/gym/shop";
    }

    @GetMapping("/cart/remove/{id}")
    public String removeFromCart(@PathVariable Long id, ModelMap model, RedirectAttributes redirectAttributes){
        Optional<GymPlan> plan = gymPlanRepository.findByIdAndIsPublicTrueAndIsForSaleTrue(id);
        if (!plan.isPresent()) {
            redirectAttributes.addFlashAttribute("exception", "The requested plan with id " + id + " doesn't exist");
            return "redirect:/error";
        }

        GymPlan foundPlan = plan.get();
        shoppingCartService.removeItem(foundPlan);
        return "redirect:/gym/shop/cart";
    }


    @GetMapping("/cart/checkout")
    public String checkout(ModelMap model, RedirectAttributes redirectAttributes){

        return "redirect:/gym";
    }

}
