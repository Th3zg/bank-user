package com.user_services.user_services.controller;

import com.user_services.user_services.dto.request.CreateUserRequest;
import com.user_services.user_services.services.UserServicesImpl;
import com.user_services.user_services.util.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/customer")
public class UserController {
  private final Logger logger = LoggerFactory.getLogger(UserController.class);

  private final UserServicesImpl customerServices;

  @Autowired
  public UserController(UserServicesImpl customerServices) {
    this.customerServices = customerServices;
  }
//
//    @GetMapping
//    public ResponseEntity<?> getAllCustomers() {
//      return ResponseEntity.ok(customerServices.getAllCustomers());
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<?> getCustomerById(@PathVariable Long id) {
//      return ResponseEntity.ok(customerServices.getCustomerById(id));
//    }

  @PostMapping
  public ResponseEntity<?> createCustomer(@RequestBody CreateUserRequest request) {
    logger.info("solicitud recibida: {}", request);

    Result<Void> response = customerServices.createUser(request);

    logger.info("Usuario creado con exito");
    return ResponseEntity.status(HttpStatus.CREATED).body(
            Result.success("User successfully created: " + response));
  }

//    @PutMapping("/{id}")
//    public ResponseEntity<?> updateCustomer(
//            @PathVariable Long id,
//            @RequestBody @Valid  customerDTO) {
//       updatedCustomer = customerServices.updateCustomer(id, customerDTO);
//      return ResponseEntity.ok(updatedCustomer);
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<?> deleteCustomer(@PathVariable Long id) {
//      customerServicess.deleteCustomer(id);
//      return ResponseEntity.noContent().build();
//    }

//    @GetMapping("/search")
//    public ResponseEntity<?> searchCustomers(
//            @RequestParam(required = false) String firstName,
//            @RequestParam(required = false) String lastName,
//            @RequestParam(required = false) String email) {
//      return ResponseEntity.ok(customerServices.searchCustomers(firstName, lastName, email));
}