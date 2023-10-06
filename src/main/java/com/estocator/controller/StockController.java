package com.estocator.controller;

import com.estocator.controller.dto.StockDto;
import com.estocator.service.StockService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("/stocks")
@Tag(name = "Stocks Controller", description = "RESTful API for managing stocks")
public record StockController(StockService stockService) {

    @GetMapping
    @Operation(summary = "Get all stocks", description = "Retrieve a list of all registered stocks")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operation successful")
    })
    public ResponseEntity<List<StockDto>> findAll() {
        var stocks = stockService.findAll();
        var stocksDto = stocks.stream().map(StockDto::new).collect(Collectors.toList());
        return ResponseEntity.ok(stocksDto);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a stock by ID", description = "Retrieve a specific stock based on its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operation successful"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<StockDto> findById(@PathVariable Long id) {
        var stock = stockService.findById(id);
        return ResponseEntity.ok(new StockDto(stock));
    }

    @PostMapping
    @Operation(summary = "Create a new stock", description = "Create a new stock and return the created stock's data")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User created successfully"),
            @ApiResponse(responseCode = "422", description = "Invalid user data provided")
    })
    public ResponseEntity<StockDto> create(@RequestBody StockDto stockDto) {
        var stock = stockService.create(stockDto.toModel());
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(stock.getId())
                .toUri();
        return ResponseEntity.created(location).body(new StockDto(stock));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a user", description = "Update the data of an existing user based on its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User updated successfully"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "422", description = "Invalid user data provided")
    })
    public ResponseEntity<StockDto> update(@PathVariable Long id, @RequestBody StockDto stockDto) {
        var user = stockService.update(id, stockDto.toModel());
        return ResponseEntity.ok(new StockDto(user));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a user", description = "Delete an existing user based on its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "User deleted successfully"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        stockService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
