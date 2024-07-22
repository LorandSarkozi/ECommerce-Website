package com.example.demo.service;

import com.example.demo.constants.FileType;
import com.example.demo.constants.OrderStatus;
import com.example.demo.dto.AddProductInCartDto;
import com.example.demo.dto.CartItemsDto;
import com.example.demo.dto.OrderDto;
import com.example.demo.dto.PlaceOrderDto;
import com.example.demo.exceptions.ValidationException;
import com.example.demo.exporter.FileExporter;
import com.example.demo.exporter.XMLFileExporter;
import com.example.demo.model.*;
import com.example.demo.repository.*;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import org.hibernate.cache.spi.support.AccessedDataClassification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.StringWriter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Service class for managing cart-related operations.
 */
@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CouponRepository couponRepository;

    /**
     * Adds a product to the user's cart.
     *
     * @param addProductInCartDto DTO containing product and user IDs.
     * @return ResponseEntity indicating success or failure of the operation.
     */
    public ResponseEntity<?> addProductToCart(AddProductInCartDto addProductInCartDto) {
        // Find the active order for the user
        System.out.println("Entered!");
        System.out.println(addProductInCartDto.getUserId());
        Order activeOrder = orderRepository.findByUserIdAndOrderStatus(addProductInCartDto.getUserId(), OrderStatus.Pending);
        // Check if the product is already in the user's cart
        Optional<CartItems> optionalCartItems = cartItemRepository.findByProductIdAndOrderIdAndUserId(addProductInCartDto.getProductId(), activeOrder.getId(), addProductInCartDto.getUserId());


        if (optionalCartItems.isPresent()) {
            // Product already exists in the cart
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        } else {
            // Product does not exist in the cart, add it
            Optional<Product> optionalProduct = productRepository.findById(addProductInCartDto.getProductId());
            Optional<User> optionalUser = userRepository.findById(addProductInCartDto.getUserId());

            if (optionalProduct.isPresent() && optionalUser.isPresent()) {
                // Create a new cart item
                CartItems cart = new CartItems();
                cart.setProduct(optionalProduct.get());
                cart.setPrice(optionalProduct.get().getPrice());
                cart.setQuantity(1L);
                cart.setUser(optionalUser.get());
                cart.setOrder(activeOrder);

                // Save the cart item
                CartItems updateCart = cartItemRepository.save(cart);

                // Update the total amount of the active order
                activeOrder.setTotalAmount(activeOrder.getTotalAmount() + cart.getPrice());
                activeOrder.setAmount(activeOrder.getAmount() + cart.getPrice());
                activeOrder.getCartItems().add(cart);

                orderRepository.save(activeOrder);

                return ResponseEntity.status(HttpStatus.CREATED).body(cart);

            } else {
                // User or Product not found
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User or Product not found");
            }
        }
    }

    /**
     * Retrieves the user's cart by their ID.
     *
     * @param userId ID of the user.
     * @return DTO representing the user's cart.
     */
    public OrderDto getCartByUserId(Long userId) {
        // Find the active order for the user
        Order activeOrder = orderRepository.findByUserIdAndOrderStatus(userId, OrderStatus.Pending);
        //Optional<User> optionalUser = userRepository.findById(userId);

      /*  if (activeOrder == null) {
            Order order = new Order();
            order.setAmount(0D);
            order.setTotalAmount(0D);
            order.setDiscount(0L);
            order.setUser(optionalUser.get());
            order.setOrderStatus(OrderStatus.Pending);
            order.setCartItems(new ArrayList<>());
            orderRepository.save(order);

            return order.getOrderDto();
        }*/
        // Map cart items to DTOs
        List<CartItemsDto> cartItemsDtoList = activeOrder.getCartItems().stream().map(CartItems::getCartDto).collect(Collectors.toList());

        // Create and return the order DTO
        OrderDto orderDto = new OrderDto();
        orderDto.setAmount(activeOrder.getAmount());
        orderDto.setId(activeOrder.getId());
        orderDto.setOrderStatus(activeOrder.getOrderStatus());
        orderDto.setDiscount(activeOrder.getDiscount());
        orderDto.setTotalAmount(activeOrder.getTotalAmount());
        orderDto.setCartItems(cartItemsDtoList);
        if(activeOrder.getCoupon() != null){
            orderDto.setCouponName(activeOrder.getCoupon().getName());
        }

        return orderDto;
    }

    public OrderDto applyCoupon(Long userId, String code){

        Order activeOrder = orderRepository.findByUserIdAndOrderStatus(userId, OrderStatus.Pending);
        Coupon coupon = couponRepository.findByCode(code).orElseThrow(() ->  new ValidationException("Coupon not found."));

        if(couponIsExpired(coupon)){
            throw new ValidationException("Coupon is expired");
        }

        double discountAmount = ((coupon.getDiscount() / 100.0) * activeOrder.getTotalAmount());
        double netAmount = activeOrder.getTotalAmount() - discountAmount;

        activeOrder.setAmount(netAmount);
        activeOrder.setDiscount((long)discountAmount);
        activeOrder.setCoupon(coupon);

        orderRepository.save(activeOrder);
        return activeOrder.getOrderDto();


    }

    private boolean couponIsExpired(Coupon coupon){
        Date currentDate = new Date();
        Date expirationDate = coupon.getExpirationDate();

        return expirationDate!=null && currentDate.after(expirationDate);
    }

    public OrderDto increaseProductQuantity(AddProductInCartDto addProductInCartDto){

        Order activeOrder = orderRepository.findByUserIdAndOrderStatus(addProductInCartDto.getUserId(), OrderStatus.Pending);

        Optional<Product> optionalProduct = productRepository.findById(addProductInCartDto.getProductId());

        Optional<CartItems> optionalCartItems = cartItemRepository.findByProductIdAndOrderIdAndUserId(addProductInCartDto.getProductId(),activeOrder.getId(),addProductInCartDto.getUserId());

        if(optionalProduct.isPresent() && optionalCartItems.isPresent()){
            CartItems cartItem = optionalCartItems.get();
            Product product = optionalProduct.get();

            activeOrder.setAmount(activeOrder.getAmount() + product.getPrice());
            activeOrder.setTotalAmount(activeOrder.getTotalAmount() + product.getPrice());

            cartItem.setQuantity(cartItem.getQuantity() + 1);

            if(activeOrder.getCoupon() != null){
                double discountAmount = ((activeOrder.getCoupon().getDiscount() / 100.0) * activeOrder.getTotalAmount());
                double netAmount = activeOrder.getTotalAmount() - discountAmount;

                activeOrder.setAmount(netAmount);
                activeOrder.setDiscount((long)discountAmount);
            }

            cartItemRepository.save(cartItem);
            orderRepository.save(activeOrder);
            return activeOrder.getOrderDto();
        }
        return null;

    }


    public OrderDto decreaseProductQuantity(AddProductInCartDto addProductInCartDto){

        Order activeOrder = orderRepository.findByUserIdAndOrderStatus(addProductInCartDto.getUserId(), OrderStatus.Pending);

        Optional<Product> optionalProduct = productRepository.findById(addProductInCartDto.getProductId());

        Optional<CartItems> optionalCartItems = cartItemRepository.findByProductIdAndOrderIdAndUserId(addProductInCartDto.getProductId(),activeOrder.getId(),addProductInCartDto.getUserId());

        if(optionalProduct.isPresent() && optionalCartItems.isPresent()){
            CartItems cartItem = optionalCartItems.get();
            Product product = optionalProduct.get();

            activeOrder.setAmount(activeOrder.getAmount() - product.getPrice());
            activeOrder.setTotalAmount(activeOrder.getTotalAmount() - product.getPrice());

            cartItem.setQuantity(cartItem.getQuantity() - 1);

            if(activeOrder.getCoupon() != null){
                double discountAmount = ((activeOrder.getCoupon().getDiscount() / 100.0) * activeOrder.getTotalAmount());
                double netAmount = activeOrder.getTotalAmount() - discountAmount;

                activeOrder.setAmount(netAmount);
                activeOrder.setDiscount((long)discountAmount);
            }

            cartItemRepository.save(cartItem);
            orderRepository.save(activeOrder);
            return activeOrder.getOrderDto();
        }
        return null;

    }

    public OrderDto placeOrder(PlaceOrderDto placeOrderDto){
        Order activeOrder = orderRepository.findByUserIdAndOrderStatus(placeOrderDto.getUserId(), OrderStatus.Pending);
        Optional<User> optionalUser = userRepository.findById(placeOrderDto.getUserId());

        if(optionalUser.isPresent()){
            activeOrder.setOrderDescription(placeOrderDto.getOrderDescription());
            activeOrder.setAddress(placeOrderDto.getAddress());
            activeOrder.setDate(new Date());
            activeOrder.setOrderStatus(OrderStatus.Placed);
            activeOrder.setTrackingId(UUID.randomUUID());

            orderRepository.save(activeOrder);
            System.out.println("Active order saved");
            Order order = new Order();
            order.setAmount(0D);
            order.setTotalAmount(0D);
            order.setDiscount(0L);
            order.setUser(optionalUser.get());
            order.setOrderStatus(OrderStatus.Pending);
            orderRepository.save(order);
            System.out.println("Order saved");

            return activeOrder.getOrderDto();

        }
        return null;
    }

    public List<OrderDto> getMyPlacedOrders(Long userId){
        return orderRepository.findByUserIdAndOrderStatusIn(userId, List.of(OrderStatus.Placed,OrderStatus.Shipped,OrderStatus.Delivered)).stream().map(Order::getOrderDto).collect(Collectors.toList());
    }

    public OrderDto searchOrderByTrackingId(UUID trackingId){
        Optional<Order> optionalOrder = orderRepository.findByTrackingId(trackingId);
        if(optionalOrder.isPresent()){
            return optionalOrder.get().getOrderDto();
        }
        return null;
    }

    public String exportOrderDetails(UUID trackingId, String fileType) {
        Order order = orderRepository.findByTrackingId(trackingId).get();
        return convertToXml(order.getOrderDto());
    }

    public String convertToXml(OrderDto order) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(OrderDto.class);
            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            StringWriter sw = new StringWriter();
            marshaller.marshal(order, sw);

            return sw.toString();
        } catch (JAXBException e) {
            e.printStackTrace();
            return null;
        }
    }
}
