package com.sh.onezip.cart.repository;

import com.sh.onezip.cart.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Long> {
    @Query("select c, m from Cart c left join fetch c.member m")
    List<Cart> findAllCartlist(Cart cart);

//    Cart findByMemberId(String memberId);
}
