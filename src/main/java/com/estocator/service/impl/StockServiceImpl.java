package com.estocator.service.impl;

import com.estocator.domain.model.Stock;
import com.estocator.domain.repository.StockRepository;
import com.estocator.service.StockService;
import com.estocator.service.exception.BusinessException;
import com.estocator.service.exception.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.Optional.ofNullable;

@Service
public class StockServiceImpl implements StockService {

    private final StockRepository stockRepository;

    public StockServiceImpl(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Stock> findAll() {

        return this.stockRepository.findAll();

    }

    @Override
    @Transactional(readOnly = true)
    public Stock findById(Long id) {

        return this.stockRepository.findById(id).orElseThrow(NotFoundException::new);

    }

    @Override
    @Transactional
    public Stock create(Stock stock) {
        ofNullable(stock).orElseThrow(() -> new BusinessException("Stock cannot be null"));
        ofNullable(stock.getCompany()).orElseThrow(() -> new BusinessException("Company needs to be informed"));

        if (stockRepository.existsByCompanyCNPJ(stock.getCompany().getCNPJ())) {
            throw new BusinessException("This company already exists.");
        }

        return this.stockRepository.save(stock);

    }

    @Override
    @Transactional
    public Stock update(Long id, Stock stock) {

        Stock stockFound =this.findById(id);
        ofNullable(stockFound).orElseThrow(() -> new BusinessException("Stock not exists"));

        stockFound.setCompany(stock.getCompany());
        stockFound.setItens(stock.getItens());

        return this.stockRepository.save(stockFound);

    }

    @Override
    @Transactional
    public void delete(Long id) {

        Stock stockFound =this.findById(id);
        this.stockRepository.delete(stockFound);

    }

}
