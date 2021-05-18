package com.example.backpackotlin.biz.core.product.service.impl;

import com.example.backpackotlin.biz.comm.message.MulLangMessage;
import com.example.backpackotlin.biz.comm.util.StringUtil;
import com.example.backpackotlin.biz.core.product.dto.OrderDto;
import com.example.backpackotlin.biz.core.product.dto.ProductDto;
import com.example.backpackotlin.biz.core.product.entity.Order;
import com.example.backpackotlin.biz.core.product.entity.Product;
import com.example.backpackotlin.biz.core.product.repo.ProductRepository;
import com.example.backpackotlin.biz.core.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final MulLangMessage lang;

    @Override
    public Product insertProduct(ProductDto dto) {
        return productRepository.insertProduct(dto);
    }

    @Override
    public Page<ProductDto> selectListProduct(Pageable pageable) {
        return productRepository.selectListProduct(pageable);
    }

    @Override
    public Order insertOrder(OrderDto.Req dto) {

        String orderId = StringUtil.randomKey(12);

        while (!productRepository.confirmOrderId(orderId)) {
            orderId = StringUtil.randomKey(12);
        }

        dto.setOrderId(orderId);

        return productRepository.insertOrder(dto);
    }

    @Override
    public List<OrderDto.Res> selectListOrder(OrderDto.Req dto) {
        return productRepository.selectListOrder(dto);
    }

    @Override
    public Page<OrderDto.UserOrderRes> selectPageUserOrder(Pageable pageable, OrderDto.UserOrderReq dto) {
        return productRepository.selectPageUserOrder(pageable, dto);
    }
}
