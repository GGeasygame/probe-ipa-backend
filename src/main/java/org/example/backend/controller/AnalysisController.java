package org.example.backend.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.backend.dto.AnalysisRequestDto;
import org.example.backend.dto.AnalysisResponseDto;
import org.example.backend.service.AnalysisService;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/analysis")
@RequiredArgsConstructor
@Slf4j
public class AnalysisController {

    private final AnalysisService analysisService;

    @PostMapping()
    public List<AnalysisResponseDto> analyseText(@RequestBody @Valid AnalysisRequestDto request) {
        return analysisService.analyse(request);
    }
}
