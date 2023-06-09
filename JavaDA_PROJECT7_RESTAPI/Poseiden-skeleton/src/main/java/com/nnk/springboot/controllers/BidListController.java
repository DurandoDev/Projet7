package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@Controller
public class BidListController {

    @Autowired
    private BidListRepository bidListRepository;

    private static final Logger logger = LoggerFactory.getLogger(BidListController.class);

    @RequestMapping("/bidList/list")
    public String home(Model model) {
        logger.info("home() method called");

        model.addAttribute("bidList", bidListRepository.findAll());

        logger.info("Retrieved bid lists: {}", bidListRepository.findAll());
        return "bidList/list";
    }

    @GetMapping("/bidList/add")
    public String addBidForm(BidList bidList) {
        logger.info("addBidForm() method called");
        return "bidList/add";
    }


    @PostMapping("/bidList/validate")
    public String validate(@Valid BidList bidList, BindingResult result, Model model) {
        if (result.hasErrors()) {
            logger.info("Validation failed for bid: {}", bidList);
            return "bidList/add";
        }
        bidListRepository.save(bidList);
        logger.info("Validation succeeded for bid: {}", bidList);
        model.addAttribute("message", "BidList add successfully !");
        return "redirect:/bidList/list";
    }


    @GetMapping("/bidList/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        BidList bidList = bidListRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid bid Id:" + id));
        logger.debug("Displaying update form for bid: {}", bidList);
        model.addAttribute("bidList", bidList);
        return "bidList/update";
    }

    @PostMapping("/bidList/update/{id}")
    public String updateBid(@PathVariable("id") Integer id, @Valid BidList bidList,
                             BindingResult result, Model model) {
        if (result.hasErrors()) {
            logger.info("Update failed for bid: {}", bidList);
            return "bidList/update";
        }

        bidList.setBidListId(id);
        bidListRepository.save(bidList);
        model.addAttribute("bidList", bidListRepository.findAll());
        logger.info("Bid updated successfully: {}", bidList);
        return "redirect:/bidList/list";
    }

    @GetMapping("/bidList/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, Model model) {
        bidListRepository.findAll();
        BidList bidList = bidListRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid bidList Id:" + id));
        bidListRepository.delete(bidList);
        model.addAttribute("bidList", bidListRepository.findAll());
        logger.info("Bid deleted successfully: {}", bidList);
        return "redirect:/bidList/list";
    }

}
