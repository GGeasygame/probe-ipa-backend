package org.example.backend.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.backend.dto.TextRequestDto;
import org.example.backend.dto.TextResponseDto;
import org.example.backend.service.TextService;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/texts")
@RequiredArgsConstructor
@Slf4j
public class TextController {


    private final TextService textService;

    @GetMapping()
    public @ResponseBody Iterable<TextResponseDto> getTexts() {
        return textService.getTexts();
    }

    @PostMapping()
    public @ResponseBody TextResponseDto addText(@RequestBody TextRequestDto text) {
        return textService.addText(text);
    }

    @PutMapping("/{id}")
    public @ResponseBody TextResponseDto updateText(@PathVariable Integer id, @RequestBody TextRequestDto text) {
        return textService.updateText(id, text);
    }
}
