package com.example.shopshoesspring.controller;

import com.example.shopshoesspring.entity.Stamp;
import com.example.shopshoesspring.entity.StampType;
import com.example.shopshoesspring.repository.StampRepository;
import com.example.shopshoesspring.repository.StampTypeRepository;
import com.example.shopshoesspring.service.UserService;
import com.example.shopshoesspring.test.Test;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Controller
@RequestMapping("/client")
public class ClientController {

    private final StampRepository stampRepository;
    private final StampTypeRepository stampTypeRepository;
    private final Test test;
    private final String uploadPath = "D:\\images\\";


    @GetMapping("/home")
    public String homePage() {
        return "/client/сhome";
    }

    @GetMapping("/analyze")
    public String analyzePage(Model model) {
        model.addAttribute("mrTypeList", stampTypeRepository.findAll());
        return "/client/analyze";
    }
    @GetMapping("/select/{id}")
    public String select(@PathVariable Long id, Model model) {
        model.addAttribute("id", id);
        return "client/data";
    }

    @PostMapping("/uploadImage")
    public String uploadImage(@RequestParam("image") MultipartFile file, @RequestParam("id") Long id, Model model) {
        if (file.isEmpty()) {
            return "redirect:/admin/addStamp";
        }

        try {

            String fileName = "test" + ".png";
            Path path = Paths.get(uploadPath + fileName);
            Files.write(path, file.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<Stamp> stamps = stampRepository.findByStampTypeId(id);
        double max = 0.0;
        String testFile = uploadPath +"test" + ".png";
        for(Stamp stamp : stamps) {
         Double res = Double.valueOf(test.check(testFile,uploadPath+stamp.getId() + ".png"));
            if(res > max) {
                max = res;
            }
        }
        model.addAttribute("max", max);
        return "client/res";
    }
}