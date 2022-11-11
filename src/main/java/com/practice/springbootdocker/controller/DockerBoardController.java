package com.practice.springbootdocker.controller;

import com.practice.springbootdocker.domain.dto.CommentDto;
import com.practice.springbootdocker.domain.dto.DockerBoardDto;
import com.practice.springbootdocker.domain.entity.Comment;
import com.practice.springbootdocker.domain.entity.DockerBoard;
import com.practice.springbootdocker.domain.entity.Hospital;
import com.practice.springbootdocker.repository.CommentRepository;
import com.practice.springbootdocker.repository.DockerBoardRepository;
import com.practice.springbootdocker.repository.HospitalRepository;
import com.practice.springbootdocker.service.HospitalService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/notice")
@Slf4j
public class DockerBoardController {

    private final DockerBoardRepository dockerBoardRepository; // DI 해준다.
    private final CommentRepository commentRepository;
    private final HospitalService hospitalService;
    public DockerBoardController(DockerBoardRepository dockerBoardRepository, CommentRepository commentRepository, HospitalService hospitalService) {
        this.dockerBoardRepository = dockerBoardRepository;
        this.commentRepository = commentRepository;
        this.hospitalService = hospitalService;
    }

    @GetMapping("")
    public String initialPage() {
        return "redirect:/notice/all";
    }

    @GetMapping("/privacy")
    public String privacyPage(@RequestParam String name, Model model) {
        model.addAttribute("name", name);
        return "dockerboard/privacy";
    }

    @GetMapping("/terms")
    public String termsPage() {
        return "dockerboard/terms";
    }

    @GetMapping("/new")
    public String createNewPostForm() {
        return "dockerboard/new";
    }

    @PostMapping("/post")
    public String createNewPost(DockerBoardDto dockerBoardDto) {
        log.info("Title:{} Contents:{} Author:{}", dockerBoardDto.getTitle(), dockerBoardDto.getContents(), dockerBoardDto.getAuthor());
        DockerBoard savedDockerBoard = dockerBoardRepository.save(dockerBoardDto.toEntity());
        return String.format("redirect:/notice/%d", savedDockerBoard.getId());
    }

    @GetMapping("/{id}")
    public String selectSingleRecord(@PathVariable Long id, Model model) {
        Optional<DockerBoard> optDockerBoard = dockerBoardRepository.findById(id);
        Optional<List<Comment>> comments = Optional.ofNullable(commentRepository.findByPostToComment_Id(id));
        if (comments.isPresent()) model.addAttribute("comment", comments.get());
        if (optDockerBoard.isPresent()) {
            model.addAttribute("record", optDockerBoard.get());
            return "dockerboard/show";
        } else {
            model.addAttribute("message", String.format("id %d를 찾을 수 없습니다", id));
            return "error";
        }
    }

    @GetMapping("/all")
    public String listAllRecords(Model model) {
        List<DockerBoard> dockerBoards = dockerBoardRepository.findAll();
        model.addAttribute("records", dockerBoards);
        return "dockerboard/list";
    }

    @GetMapping("/{id}/patch")
    public String pageToUpdate(@PathVariable Long id, Model model) {
        Optional<DockerBoard> optionalDockerBoard = dockerBoardRepository.findById(id);
        if (optionalDockerBoard.isPresent()) {
            model.addAttribute("old", optionalDockerBoard.get());
            return "dockerboard/edit";
        } else {
            model.addAttribute("message", String.format("id %d를 찾을 수 없습니다", id));
            return "error";
        }
    }

    @PostMapping("/{id}/change")
    public String updateRecord(@PathVariable Long id, DockerBoardDto dockerBoardDto, Model model) {
        Optional<DockerBoard> dockerBoardRecord = dockerBoardRepository.findById(id);
        if (dockerBoardRecord.isPresent()) {
            dockerBoardRepository.save(dockerBoardDto.toEntity());
            return String.format("redirect:/notice/%d", id);
        } else {
            model.addAttribute("message", String.format("id %d가 존재하지 않습니다.", id));
            return "error";
        }
    }

    @GetMapping("/{id}/null")
    public String deleteRecord(@PathVariable Long id) {
        dockerBoardRepository.deleteById(id);
        return "redirect:/notice/all";
    }

    @PostMapping("/{id}/feedback")
    public String commentPage(@PathVariable Long id, CommentDto commentDto, Model model) {
        log.info("author:{} contents:{}", commentDto.getAuthor(), commentDto.getContents());
        Optional<DockerBoard> optionalDockerBoard = dockerBoardRepository.findById(id);
        if (optionalDockerBoard.isPresent()) {
            Comment comment = commentDto.toEntity(optionalDockerBoard.get());
            commentRepository.save(comment);
            log.info("comment:{}", comment.getId());
//            model.addAttribute("comment", comment);
            return String.format("redirect:/notice/%d", id);
        } else return "error";
    }

    @GetMapping("/hospitals/info")
    public String pagingHospitalsInfo(Model model, @PageableDefault(size = 20, sort="id", direction= Sort.Direction.ASC) Pageable pageable) {
        model.addAttribute("information",  hospitalService.hospitalPage(pageable));
        model.addAttribute("previous", pageable.previousOrFirst());
        model.addAttribute("next", pageable.next());
        return "dockerboard/hospitals";
    }

    @GetMapping("/hospitals/result")
    public String searchKeyword(String keyword, Model model, @PageableDefault(size = 20, sort="id", direction= Sort.Direction.ASC) Pageable pageable) {
        model.addAttribute("search",hospitalService.searchHospitalName(keyword, pageable));
        model.addAttribute("keyword", keyword);
        model.addAttribute("next", pageable.next());
        model.addAttribute("previous", pageable.previousOrFirst());
        return "dockerboard/search";
    }
}
