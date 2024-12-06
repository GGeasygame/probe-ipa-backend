package org.example.backend.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.backend.dto.TextDto;
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
    public @ResponseBody Iterable<TextDto> getTexts() {
        return textService.getTexts();
    }

    @PostMapping()
    public @ResponseBody TextDto addText(@RequestBody TextDto text) {
        return textService.addText(text);
    }

    @PutMapping("/{id}")
    public @ResponseBody TextDto updateText(@PathVariable Integer id, @RequestBody TextDto text) {
        return textService.updateText(id, text);
    }
}
