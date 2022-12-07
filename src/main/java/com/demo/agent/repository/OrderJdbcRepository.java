package com.demo.agent.repository;

import com.demo.agent.domain.Celebrity;
import com.demo.agent.domain.Order;
import com.demo.agent.domain.OrderItem;
import com.demo.agent.domain.OrderStatus;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.nio.ByteBuffer;
import java.time.LocalDateTime;
import java.util.*;

@Repository
public class OrderJdbcRepository implements OrderRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public OrderJdbcRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private Map<String, Object> toOrderParamMap(Order order) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("orderId", order.getOrderId().toString());
        paramMap.put("phoneNumber", order.getPhoneNumber());
        paramMap.put("address", order.getAddress());
        paramMap.put("orderStatus", order.getOrderStatus().toString());
        paramMap.put("createdAt", order.getCreatedAt());
        paramMap.put("updatedAt", order.getUpdatedAt());
        return paramMap;
    }

    private Map<String, Object> toOrderItemParamMap(Order order, OrderItem item) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("orderId", order.getOrderId().toString());
        paramMap.put("albumId", item.getAlbumId().toString());
        paramMap.put("celebrity", item.getCelebrity().toString());
        paramMap.put("albumName", item.getAlbumName());
        paramMap.put("price", item.getPrice());
        paramMap.put("quantity", item.getQuantity());
        paramMap.put("createdAt", order.getCreatedAt());
        paramMap.put("updatedAt", order.getUpdatedAt());
        return paramMap;
    }

    private static UUID toUUID(byte[] bytes) {
        var byteBuffer = ByteBuffer.wrap(bytes);
        return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
    }

    private RowMapper<Order> orderRowMapper = ((rs, rowNum) -> {
        UUID orderId = toUUID(rs.getBytes("order_id"));
        String phoneNumber = rs.getString("phone_number");
        String address = rs.getString("address");
        OrderStatus orderStatus = OrderStatus.valueOf(rs.getString("order_status"));
        LocalDateTime createdAt = rs.getTimestamp("created_at").toLocalDateTime();
        LocalDateTime updatedAt = rs.getTimestamp("updated_at").toLocalDateTime();
        return new Order(orderId, phoneNumber, address, orderStatus, createdAt, updatedAt);
    });

    private RowMapper<OrderItem> orderItemRowMapper = ((rs, rowNum) -> {
        UUID albumId = toUUID(rs.getBytes("album_id"));
        Celebrity celebrity = Celebrity.valueOf(rs.getString("celebrity"));
        String albumName = rs.getString("album_name");
        int price = rs.getInt("price");
        int quantity = rs.getInt("quantity");
        return new OrderItem(albumId, albumName, celebrity, price, quantity);
    });

    @Override
    @Transactional
    public Order insert(Order order) {
        jdbcTemplate.update("INSERT INTO orders(order_id, phone_number, address, order_status, created_at, updated_at) "
                + "VALUES(UUID_TO_BIN(:orderId), :phoneNumber, :address, :orderStatus, :createdAt, :updatedAt)", toOrderParamMap(order));
        order.getOrderItems()
                .forEach(item -> jdbcTemplate.update("INSERT INTO order_items(order_id, album_id, celebrity, album_name ,price, quantity, created_at, updated_at) "
                                + "VALUES(UUID_TO_BIN(:orderId), UUID_TO_BIN(:albumId), :celebrity, :albumName, :price, :quantity, :createdAt, :updatedAt)", toOrderItemParamMap(order, item)));
        return order;
    }

    @Override
    public List<Order> findAll() {
        return jdbcTemplate.query("SELECT * FROM orders", orderRowMapper);
    }

    @Override
    public List<Order> findByDate(String date) {
        return jdbcTemplate.query("SELECT * FROM orders WHERE DATE_FORMAT(created_at,'%Y-%m-%d') = :createdAt", Collections.singletonMap("createdAt", date), orderRowMapper);
    }

    // --- orderItem --- //

    @Override
    public List<OrderItem> findById(UUID orderID) {
        return jdbcTemplate.query("SELECT * FROM order_items WHERE order_id = UUID_TO_BIN(:orderId)",
                Collections.singletonMap("orderId", orderID.toString()),
                orderItemRowMapper);
    }
}
