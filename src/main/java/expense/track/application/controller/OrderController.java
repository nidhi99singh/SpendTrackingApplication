package expense.track.application.controller;

import expense.track.application.enums.ApiResponseCode;
import expense.track.application.exception.ValidationException;
import expense.track.application.request.OrderRequest;
import expense.track.application.response.generics.ResponseDTO;
import expense.track.application.response.utils.ResponseUtil;
import expense.track.application.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private ResponseUtil responseUtil;

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping("/placeOrder")
    public String addProduct(@RequestBody OrderRequest orderRequest) throws ValidationException {
        return orderService.addProduct(orderRequest);
    }

    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @GetMapping("/orderDetails/{orderId}")
    public ResponseDTO<?> getOderDetails(@PathVariable String orderId) throws ValidationException {
        return responseUtil.ok(orderService.getOrderDetails(orderId), ApiResponseCode.SUCCESS);
    }

}
