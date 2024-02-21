package com.sh.onezip.cart.service;

import com.sh.onezip.cart.dto.CartDto;
import com.sh.onezip.cart.entity.Cart;
import com.sh.onezip.cart.repository.CartRepository;
import com.sh.onezip.member.entity.Member;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class CartService {

    @Autowired
    CartRepository cartRepository;

    @Autowired
    ModelMapper modelMapper;

    public List<CartDto> findAllCartlist(Cart cart) {
        List<Cart> carts = cartRepository.findAllCartlist(cart);
        List<CartDto> cartDtoList = new ArrayList<>();
        System.out.println(cartDtoList + " ???");
        for(Cart cartlist : carts) {
            cartDtoList.add(convertTocartlist(cartlist));
        }
        return cartDtoList;

    }


    private CartDto convertTocartlist(Cart cartlist) {
        CartDto cartDto = modelMapper.map(cartlist, CartDto.class);
        System.out.println(cartDto + " ???");
        return cartDto;
    }

//    public void addCart(Member member, Cart cart, int cartQuantity) {
//
//        Cart cart1 = cartRepository.findByMemberId(member.getMemberId());
//
//        if(cart1 == null) {
//            cart = Cart.createCart(member);
//            cartRepository.save(cart);
//        }
//    }
//    public Cart cartView(String productName) {
//    }

}
//        List<CartDto> cartList = new ArrayList<>();
//        List<CartDto> cartItems =  // 장바구니에 담긴 상품 목록 가져오기
//
//        for (CartDto cartItem : cartItems) {
//            CartDto cartDto = new CartDto();
//            cartDto.setId(cartItem.getId());
//            cartDto.setMemberId(cartItem.getMemberId());
//            cartDto.setProduct(cartItem.getProduct());
//            cartDto.setCartQuantity(cartItem.getQuantity());
//            // 추가적인 로직을 통해 할인적용 후 최종 가격(리스트 노출 가격) 설정
//
//            cartList.add(cartDto);
//        }
//
//        return cartList;
//    }


