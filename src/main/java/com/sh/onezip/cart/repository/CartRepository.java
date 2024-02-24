package com.sh.onezip.cart.repository;

import com.sh.onezip.cart.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Long> {

    // 한보경 코드 start

    @Query("select c, m from Cart c left join fetch c.member m")
    List<Cart> findAllCartlist(Cart cart);

//    Cart findByMemberId(String memberId);

    // 한보경 코드 end

    // 김명준 코드 start

    @Query("from Cart c where (c.member.memberId = :memberId) and (c.cartStatus = 'N')")
    List<Cart> findAllByMember(String memberId);

    // 김명준 코드 end

}
