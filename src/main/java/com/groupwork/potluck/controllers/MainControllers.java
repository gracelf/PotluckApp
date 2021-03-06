package com.groupwork.potluck.controllers;


import com.groupwork.potluck.models.ChefandDish;
import com.groupwork.potluck.potluckrepo.PotluckRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class MainControllers {

    @Autowired
    PotluckRepo potluckRepo;


    //Home Page
    @GetMapping("/")
    public String defaultRequest(Model model) {
        String myMessage = "welcome to the Potluck Application";
        model.addAttribute("message", myMessage);
        return "welcome";
    }


    @GetMapping("/adish")
    public String addResume(Model model) {
        model.addAttribute("newcd", new ChefandDish());
        return "adish";
    }

    @PostMapping("/adish")
    public String postResume(@Valid @ModelAttribute("newcd") ChefandDish chefanddish, BindingResult bResult) {
        if(bResult.hasErrors()){
            return "adish";
        }

        potluckRepo.save(chefanddish);

        return "result";
    }

    @GetMapping("/displayall")
    public String displayall(Model model)
    {

        Iterable<ChefandDish> cdlist= potluckRepo.findAll();
        model.addAttribute("cd", cdlist);
        return "displayall";
    }

    @GetMapping("/searchchef")
    public String chefSearch(Model model)
    {

        model.addAttribute("searchChef",new ChefandDish());
        return "searchchef";
    }

    @PostMapping("/searchchef")
    public String searchChefMethod(@ModelAttribute("searchChef") ChefandDish chefsearchdish, Model model)
    {

        Iterable<ChefandDish>  listChef= potluckRepo.findAllByFirstname(chefsearchdish.getFirstname());
        model.addAttribute("lchef",listChef);

         return "chefresult";
    }


    @GetMapping("/searchdish")
    public String dishsearch(Model model)
    {

        model.addAttribute("searchDish",new ChefandDish());
        return "searchdish";
    }
    @PostMapping("/searchdish")
    public String searchDishMethod(@ModelAttribute("searchDish") ChefandDish chefsearchdish, Model model)
    {

        Iterable<ChefandDish>  listDish= potluckRepo.findAllByDishContains(chefsearchdish.getDish());
        model.addAttribute("ldish",listDish);

        return "dishresult";
    }



    }
