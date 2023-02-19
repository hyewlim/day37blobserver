package com.example.day37server.controllers;

import com.example.day37server.models.Post;
import com.example.day37server.repository.SQLRepository;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
public class PostUploadController {
    @Autowired
    private SQLRepository sqlRepository;

    @PostMapping(path = "/upload")
    public String uploadPost(@RequestPart MultipartFile file,
                             @RequestPart String comment,
                             Model model) throws IOException {

        String key = "";

        Post post = new Post();
        post.setComments(comment);
        post.setPicture(file.getBytes());

        key = sqlRepository.save(post);


        model.addAttribute("comment", comment);
        model.addAttribute("file", file);
        model.addAttribute("key", key);

        return "upload";

    }

    @PostMapping(path = "/api/post")
    @ResponseBody
    public ResponseEntity<String> uploadPostForAngular(@RequestPart MultipartFile file,
                                               @RequestPart String comment) throws IOException {

        String key = "";

        Post post = new Post();
        post.setComments(comment);
        post.setPicture(file.getBytes());

        key = sqlRepository.save(post);

        JsonObject result = Json.createObjectBuilder()
                .add("key", key)
                .build();


        return ResponseEntity.ok(result.toString());

    }

    @GetMapping("/get/{post_id}")
    public String getPost(@PathVariable String post_id, Model model) {

        Post post = sqlRepository.get(post_id);

        String image = Base64.encodeBase64String(post.getPicture());


        model.addAttribute("comment", post.getComments());
        model.addAttribute("file", image);
        model.addAttribute("key", post.getPostId());


        return "upload";



    }
}
