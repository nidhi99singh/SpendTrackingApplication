package expense.track.application.service;

import expense.track.application.entity.Order;
import expense.track.application.entity.OrderProduct;
import expense.track.application.entity.Products;
import expense.track.application.exception.ValidationException;
import expense.track.application.repository.*;
import expense.track.application.request.OrderRequest;
import expense.track.application.util.CommonUtils;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

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

    public String addProduct(OrderRequest orderRequest) throws ValidationException {
        Order order = new Order();
        order.setOrderId(CommonUtils.generateUUID());
        order.setOrderDate(orderRequest.getLocalDateTime());
        order.setQuantity(orderRequest.getQuantity());

        order.setProduct(productsRepository.setProduct(orderRequest.getProductId()));
        order.setUser(usersRepository.setUser(orderRequest.getEmail()));

        //update Quantity
        Integer existingQuantity = productsRepository.getExistingQuantity(orderRequest.getProductId());
        Integer newQty = orderRequest.getQuantity();
        Integer quantity = existingQuantity - newQty;
        if (existingQuantity >= orderRequest.getQuantity()) {
            productsRepository.updateProductQuantity(quantity, orderRequest.getProductId());
        } else {
            throw new ValidationException(HttpStatus.BAD_REQUEST.value(), "Insufficient quantity, only" + existingQuantity + " products left");
        }

        //Update WalletBalance
        Integer price = (productsRepository.getCurrentPrice(orderRequest.getProductId())) * newQty;
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
        orderProduct.setLocalDateTime(orderRequest.getLocalDateTime());
        orderProduct.setEmail(orderRequest.getEmail());
        orderProductRepository.insert(orderProduct);
        return "Order Placed" + "TotalOrder value:" + price + "remaining Balance:" + newBal;
    }

    public Order getOrderDetails(String id) throws ValidationException {
        Order order = orderRepository.findById(id).orElse(null);
        if (Objects.isNull(order))
            throw new ValidationException(HttpStatus.BAD_REQUEST.value(), "order does not exist");
        return order;
    }

}
