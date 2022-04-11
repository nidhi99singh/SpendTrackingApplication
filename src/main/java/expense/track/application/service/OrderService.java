package expense.track.application.service;

import expense.track.application.entity.Order;
import expense.track.application.entity.OrderProduct;
import expense.track.application.entity.Products;
import expense.track.application.entity.UserActivity;
import expense.track.application.exception.ValidationException;
import expense.track.application.repository.*;
import expense.track.application.request.OrderRequest;
import expense.track.application.util.CommonUtils;
import expense.track.application.util.UserActivityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
public class OrderService {

    @Autowired
    private ProductsRepository productsRepository;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private WalletRepository walletRepository;

    @Autowired
    private OrderProductRepository orderProductRepository;

    @Autowired
    private UserActivityRepository userActivityRepository;

    public String userActivity(String email, String description) {
        UserActivity userActivity = new UserActivity();
        userActivity.setId(CommonUtils.generateUUID());
        userActivity.setEmail(email);
        userActivity.setActivityDescription(description);
        userActivity.setLocalDateTime(LocalDateTime.now());
        userActivityRepository.save(userActivity);
        return "history saved";
    }

    public String addProduct(OrderRequest orderRequest) throws ValidationException {
        Order order = new Order();
        order.setOrderId(CommonUtils.generateUUID());
        order.setOrderDate(LocalDateTime.now());
        order.setQuantity(orderRequest.getQuantity());

        order.setProduct(productsRepository.setProduct(orderRequest.getProductId()));
        order.setUser(usersRepository.setUser(orderRequest.getEmail()));

        //update Quantity
        Integer existingQuantity = productsRepository.getExistingQuantity(orderRequest.getProductId());
        Integer requestQuantity = orderRequest.getQuantity();
        if (existingQuantity >= requestQuantity) {
            Integer quantity = existingQuantity - requestQuantity;
            productsRepository.updateProductQuantity(quantity, orderRequest.getProductId());
        } else {
            throw new ValidationException(HttpStatus.BAD_REQUEST.value(), "Insufficient quantity, only" + existingQuantity + " products left");
        }

        //Update WalletBalance
        Integer price = (productsRepository.getCurrentPrice(orderRequest.getProductId())) * requestQuantity;
        order.setPrice(price);
        Integer existingBal = walletRepository.getWalletBalance(orderRequest.getUserId());

        if (price > existingBal)
            throw new ValidationException(HttpStatus.BAD_REQUEST.value(), "Insufficient Balance,can not place order");
        Integer newBal = existingBal - price;
        walletRepository.updateWalletBalance(newBal, orderRequest.getUserId());
        orderRepository.save(order);

        //order history in MongoDB
        OrderProduct orderProduct = new OrderProduct();
        orderProduct.setQuantity(orderRequest.getQuantity());
        orderProduct.setPrice(price);
        orderProduct.setLocalDateTime(LocalDateTime.now());
        orderProduct.setEmail(orderRequest.getEmail());
        orderProductRepository.save(orderProduct);

        //user Activity in mongoDB
        userActivity(orderRequest.getEmail(), "order placed");
        return "Order Placed" + "TotalOrder value:" + price + "remaining Balance:" + newBal;


    }

    public Order getOrderDetails(String id) throws ValidationException {
        Order order = orderRepository.findById(id).orElse(null);
        if (Objects.isNull(order))
            throw new ValidationException(HttpStatus.BAD_REQUEST.value(), "order does not exist");
        return order;
    }

}
