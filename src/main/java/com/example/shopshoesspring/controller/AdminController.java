package com.example.shopshoesspring.controller;


import com.example.shopshoesspring.entity.Stamp;
import com.example.shopshoesspring.entity.StampType;
import com.example.shopshoesspring.repository.StampRepository;
import com.example.shopshoesspring.repository.StampTypeRepository;
import com.example.shopshoesspring.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {
    private final StampRepository stampRepository;
    private final StampTypeRepository stampTypeRepository;
    @Value("${upload.path}")
    private String uploadPath;
    @GetMapping("/home")
    public String homePage() {
        return "/admin/ahome";
    }

    @GetMapping("/addType")
    public String addTypePage() {
        return "/admin/addType";
    }
    @PostMapping("/addType")
    public String addType(String typeName) {
        StampType stampType = new StampType();
        stampType.setStampTypeName(typeName);
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

    @GetMapping("/deleteType/{id}")
    public String deleteType(@PathVariable Long id) {
        stampTypeRepository.deleteById(id);
        return "redirect:/admin/mrTypeList";
    }
    @GetMapping("/addStamp")
    public String addStampPage(Model model) {
        model.addAttribute("stampType", stampRepository.findAll());
        return "/admin/addStamp";
    }
    @PostMapping("/admin/uploadImage")
    public String uploadImage(@RequestParam("image") MultipartFile file,
                              @RequestParam("stampType") Long stampTypeId,
                              RedirectAttributes redirectAttributes) {
        if (file.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "Please select a file to upload");
            return "redirect:/admin/addStamp";
        }

        try {
            String fileName = UUID.randomUUID().toString() + "-" + file.getOriginalFilename();
            Path path = Paths.get(uploadPath + File.separator + fileName);
            Files.write(path, file.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "redirect:/admin/addStamp";
    }
}
