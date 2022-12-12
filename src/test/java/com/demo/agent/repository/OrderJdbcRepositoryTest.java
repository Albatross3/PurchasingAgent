package com.demo.agent.repository;

import com.demo.agent.domain.*;
import com.demo.agent.domain.Order;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
class OrderJdbcRepositoryTest {

    @Autowired
    AlbumRepository albumRepository;

    @Autowired
    OrderRepository orderRepository;

    Album album1;
    Album album2;

    @BeforeEach
    void setup() {
        LocalDateTime now = LocalDateTime.now();
        album1 = new Album(UUID.randomUUID(), Celebrity.ITZY, "IT'z ICY", 15000, "image",now, now);
        album2 = new Album(UUID.randomUUID(), Celebrity.BLACKPINK, "BORN PINK", 14000, "image",now.plusDays(1), now.plusDays(1));
        albumRepository.insert(album1);
        albumRepository.insert(album2);
    }

    @Test
    @DisplayName("주문이 정상적으로 저장된다")
    @Transactional
    void insert() {
        // 주문 아이템 고르고 -> 배송지 넣고 주문
        OrderItem orderItem1 = new OrderItem(album1.getAlbumId(), "IT'z ICY", Celebrity.ITZY, 15000, 3);
        OrderItem orderItem2 = new OrderItem(album2.getAlbumId(), "BORN PINK", Celebrity.BLACKPINK, 14000, 2);
        List<OrderItem> items = Arrays.asList(orderItem1, orderItem2);

        Order order = new Order(UUID.randomUUID(), "010-8979-6288", "강남구 삼성로 99길 14 삼성파크아파트 101동 407호", items, OrderStatus.ACCEPTED, LocalDateTime.now(), LocalDateTime.now());
        orderRepository.insert(order);

        var orders = orderRepository.findAll();
        assertThat(orders).isNotEmpty();
        assertThat(orders.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("날짜별 주문이 조회된다")
    @Transactional
    void findByDate() {
        // given -> 날짜 별 주문 2개
        LocalDateTime present = LocalDateTime.now();
        LocalDateTime tomorrow = present.plusDays(1);

        OrderItem orderItem1 = new OrderItem(album1.getAlbumId(), "IT'z ICY", Celebrity.ITZY, 15000, 3);
        OrderItem orderItem2 = new OrderItem(album2.getAlbumId(), "BORN PINK", Celebrity.BLACKPINK, 14000, 2);
        List<OrderItem> items1 = Arrays.asList(orderItem1, orderItem2);

        Order order1 = new Order(UUID.randomUUID(), "010-8979-6288", "강남구 삼성로 99길 14 삼성파크아파트 101동 407호", items1, OrderStatus.ACCEPTED, present, present);
        orderRepository.insert(order1);

        OrderItem orderItem3 = new OrderItem(album1.getAlbumId(), "IT'z ICY", Celebrity.ITZY, 15000, 4);
        OrderItem orderItem4 = new OrderItem(album2.getAlbumId(), "BORN PINK", Celebrity.BLACKPINK, 14000, 5);
        List<OrderItem> items2 = Arrays.asList(orderItem3, orderItem4);

        Order order2 = new Order(UUID.randomUUID(), "010-8979-6288", "강남구 삼성로 99길 14 삼성파크아파트 101동 407호", items2, OrderStatus.ACCEPTED, tomorrow, tomorrow);
        orderRepository.insert(order2);

        // when
        List<Order> orderByDate = orderRepository.findByDate(present.toLocalDate().toString());

        // then
        assertThat(orderByDate).isNotEmpty();
        assertThat(orderByDate.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("주문 아이디로 주문 아이템이 정상적으로 조회 된다")
    @Transactional
    void findOrderItemsByOrderId() {
        // given
        LocalDateTime present = LocalDateTime.now();
        OrderItem orderItem1 = new OrderItem(album1.getAlbumId(), "IT'z ICY", Celebrity.ITZY, 15000, 3);
        OrderItem orderItem2 = new OrderItem(album2.getAlbumId(), "BORN PINK", Celebrity.BLACKPINK, 14000, 2);
        List<OrderItem> items = Arrays.asList(orderItem1, orderItem2);

        Order order = new Order(UUID.randomUUID(), "010-8979-6288", "강남구 삼성로 99길 14 삼성파크아파트 101동 407호", items, OrderStatus.ACCEPTED, present, present);
        orderRepository.insert(order);

        // when
        List<OrderItem> orderItems = orderRepository.findById(order.getOrderId());

        // then
        assertThat(orderItems).isNotEmpty();
        assertThat(orderItems.size()).isEqualTo(2);

    }
}