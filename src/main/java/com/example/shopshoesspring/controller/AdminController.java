package com.example.shopshoesspring.controller;


import com.example.shopshoesspring.entity.Stamp;
import com.example.shopshoesspring.entity.StampType;
import com.example.shopshoesspring.repository.StampRepository;
import com.example.shopshoesspring.repository.StampTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {
    private final StampRepository stampRepository;
    private final StampTypeRepository stampTypeRepository;

    private final String uploadPath = "D:\\images\\";

    @GetMapping("/home")
    public String homePage() {
        return "/admin/ahome";
    }

    @GetMapping("/addType")
    public String addTypePage() {
        return "/admin/addType";
    }

    @PostMapping("/addType")
    public String addType(String mrTypeName) {
        StampType stampType = new StampType();
        stampType.setStampTypeName(mrTypeName);
        stampTypeRepository.save(stampType);
        return "redirect:/admin/typeList";
    }

    @GetMapping("/typeList")
    public String typeList(Model model) {
        model.addAttribute("mrTypeList", stampTypeRepository.findAll());
        return "/admin/typeList";
    }

    @GetMapping("/updateType/{id}")
    public String updateType(@PathVariable Long id, Model model) {
        Optional<StampType> mrType = stampTypeRepository.findById(id);
        if (mrType.isPresent()) {
            model.addAttribute("mrType", mrType.get());
        }
        return "admin/updateMrType";
    }
    @GetMapping("/viewStamp")
    public String viewStamp(Model model) {
        model.addAttribute("mrTypeList", stampTypeRepository.findAll());
        return "/admin/viewStamp";
    }
    @PostMapping("/updateType/{id}")
    public String updateType(@PathVariable("id") Long id, @RequestParam("mrTypeName") String mrTypeName) {
        StampType stampType = new StampType();
        stampType.setStampTypeName(mrTypeName);
        stampType.setId(id);
        stampTypeRepository.save(stampType);
        return "redirect:/admin/typeList";
    }

    @GetMapping("/deleteType/{id}")
    public String deleteType(@PathVariable Long id) {
        stampTypeRepository.deleteById(id);
        return "redirect:/admin/typeList";
    }
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        stampRepository.deleteById(id);
        return "redirect:/admin/viewStamp";
    }
    @GetMapping("/select/{id}")
    public String select(@PathVariable Long id, Model model) {
        List<Stamp> stampList = stampRepository.findByStampTypeId(id);
        model.addAttribute("stampList", stampList);
        return "admin/data";
    }

    @GetMapping("/addStamp")
    public String addStampPage(Model model) {
        model.addAttribute("stampTypes", stampTypeRepository.findAll());
        return "/admin/addStamp";
    }

    @PostMapping("/uploadImage")
    public String uploadImage(@RequestParam("image") MultipartFile file, @RequestParam("stampType") Long stampTypeId, RedirectAttributes redirectAttributes) {
        if (file.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "Please select a file to upload");
            return "redirect:/admin/addStamp";
        }
        Optional<StampType> stampType = stampTypeRepository.findById(stampTypeId);
        Long id = null;
        if (stampType.isPresent()) {
            StampType stampType1 = stampType.get();
            Stamp stamp = new Stamp();
            stamp.setStampType(stampType1);
            Stamp stamp1 = stampRepository.save(stamp);
            id = stamp1.getId();
        }
        try {

            String fileName = id + ".png";
            Path path = Paths.get(uploadPath + fileName);
            Files.write(path, file.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "redirect:/admin/addStamp";
    }
}
