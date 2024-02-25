package com.sh.onezip.cart.service;

import com.sh.onezip.cart.dto.CartDto;
import com.sh.onezip.cart.entity.Cart;
import com.sh.onezip.cart.repository.CartRepository;
import com.sh.onezip.member.entity.Member;
import com.sh.onezip.product.dto.ProductCartCreateDto;
import com.sh.onezip.product.entity.Product;
import com.sh.onezip.product.repository.ProductRepository;
import com.sh.onezip.productoption.entity.ProductOption;
import com.sh.onezip.productoption.repository.ProductOptionRepository;
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

    // 한보경 코드 start

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

    // 한보경 코드 end

    // 김명준 코드 start

    @Autowired
    ProductOptionRepository productOptionRepository;

    @Autowired
    ProductRepository productRepository;

    public List<Cart> findAllByMemberId(String memberId) {
        return cartRepository.findAllByMember(memberId);
    }

    public Cart convertToCart(ProductCartCreateDto productCartCreateDto) {
        Cart cart = modelMapper.map(productCartCreateDto, Cart.class);
        cart.setProduct(productRepository.findById(productCartCreateDto.getProductId()).orElse(null));
        ProductOption productOption =
                productOptionRepository.findById(
                        Long.parseLong(productCartCreateDto.getSelectOption().split("#")[0])).orElse(null);
        Product product = productRepository.findById(productCartCreateDto.getProductId()).orElse(null);
        cart.setOptionCost(productOption.getOptionCost());
        cart.setPoptionName(productOption.getOptionName());
        cart.setTotalStock(productOption.getTotalStock());
        cart.setCartQuantity(productCartCreateDto.getProductQuantity());
        cart.setCartStatus("N");
        cart.setProductName(product.getProductName());
        cartRepository.save(cart);
        return cart;
    }

    // 김명준 코드 end

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


